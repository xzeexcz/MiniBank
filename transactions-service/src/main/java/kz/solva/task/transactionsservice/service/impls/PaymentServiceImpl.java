package kz.solva.task.transactionsservice.service.impls;

import kz.solva.task.transactionsservice.api.ClientLimitRequest;
import kz.solva.task.transactionsservice.dto.payment.PaymentDto;
import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import kz.solva.task.transactionsservice.entity.limit.Limit;
import kz.solva.task.transactionsservice.entity.payment.Payment;
import kz.solva.task.transactionsservice.mapper.payment.PaymentMapper;
import kz.solva.task.transactionsservice.repository.limit.LimitRepo;
import kz.solva.task.transactionsservice.repository.payment.PaymentRepo;
import kz.solva.task.transactionsservice.service.limit.LimitService;
import kz.solva.task.transactionsservice.service.payment.PaymentService;
import kz.solva.task.transactionsservice.utils.limit.LimitUtils;
import kz.solva.task.transactionsservice.utils.payment.PaymentUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;
    private final LimitRepo limitRepo;
    private final PaymentMapper paymentMapper;
    private final LimitService limitService;
    private final LimitUtils limitUtils;
    private final PaymentUtils paymentUtils;
    private final Logger logger = LoggerFactory.getLogger(Payment.class);
    private final Logger loggerLimit = LoggerFactory.getLogger(Limit.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public ResponseEntity<? extends Object> makePayment(PaymentDto paymentDto) {
        if (paymentDto != null) {
            Payment payment = paymentMapper.toPaymentEntity(paymentDto);
            try {
                loggerLimit.info("Проверка на ранее установленный лимит...");
                Limit limit = limitService.isExists(paymentDto.getAccount_from(),
                        paymentDto.getExpenseCategory());

                if (limit != null) {
                    loggerLimit.info("Совершение транзакции с учетом раннего лимита.");
                    loggerLimit.info("Регистрация лимита.");

                    Limit newLimit = new Limit();

                    newLimit.setLimitBalance(limitUtils.doCalculateLimitBalance(limit.getLimitBalance(), paymentDto.getSum(), paymentDto.getCurrencyShortname()));
                    newLimit.setAccount(paymentDto.getAccount_from());
                    newLimit.setCurrencyShortname(CurrencyShortname.USD);
                    newLimit.setExpenseCategory(paymentDto.getExpenseCategory());
                    newLimit.setDateTime(ZonedDateTime.now());
                    newLimit.setLimitSum(limit.getLimitSum());

                    executorService.submit(() -> {
                        limitRepo.save(newLimit);
                    });
                    executorService.submit(() -> {
                        if (setFlag(payment.getSum(), limit.getLimitBalance(), payment.getCurrencyShortname())) {
                            payment.setLimit_exceded(true);
                        } else {
                            payment.setLimit_exceded(false);
                        }
                        payment.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Almaty")));
                        paymentRepo.save(payment);
                    });

                } else {
                    loggerLimit.info("Нет ранее установленного лимита для этого счета и типа транзакции.");
                    loggerLimit.info("Установка лимита для этого счета и типа транзакции.");

                    Limit limit1 = new Limit();
                    limit1.setLimitSum(1000);
                    limit1.setLimitBalance(limitUtils.doCalculateLimitBalance(limit1.getLimitSum(), payment.getSum(), payment.getCurrencyShortname()));
                    limit1.setAccount(payment.getAccount_from());
                    limit1.setExpenseCategory(payment.getExpenseCategory());
                    limit1.setCurrencyShortname(CurrencyShortname.USD);
                    limit1.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Almaty")));

                    executorService.submit(() -> {
                        limitRepo.save(limit1);
                    });
                    executorService.submit(() -> {
                        payment.setLimit_exceded(setFlag(payment.getSum(), limit1.getLimitSum(), payment.getCurrencyShortname()));
                        payment.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Almaty")));
                        paymentRepo.save(payment);
                    });
                }
            } catch (NullPointerException e) {
                logger.atWarn().log("Произошла ошибка при проверке на ранее установленный лимит");
                e.printStackTrace();
            }
            return ResponseEntity.ok("Транзакция была успешна");
        } else {
            return ResponseEntity.ok("Транзакция не может быть пустой");
        }
    }

    @Override
    public boolean setFlag(double paymentSum, double limitSum, CurrencyShortname currencyShortname) {
        return paymentUtils.isBiggerThanLimit(paymentSum, limitSum, currencyShortname);
    }

    @Override
    public ResponseEntity<?> setNewLimit(ClientLimitRequest clientLimitRequest) {
        if (clientLimitRequest != null) {
            if (limitUtils.checkData(clientLimitRequest)) {
                try {
                    loggerLimit.info("Проверка на ранее установленный лимит для этого типа транзакии и аккаунта");
                    Limit limit = limitRepo.findRecentLimits3(clientLimitRequest.getAccount(), clientLimitRequest.getExpenseCategory());

                    if (limit != null) {

                        Limit limit1 = new Limit();
                        loggerLimit.info("Установка нового лимита для этого типа транзакции и аккаунта с учетом раннее установленного лимита");

                        executorService.submit(() -> {

                            limit1.setLimitSum(clientLimitRequest.getLimit());
                            limit1.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Almaty")));
                            limit1.setLimitBalance((clientLimitRequest.getLimit() + (limit.getLimitBalance())));
                            limit1.setAccount(clientLimitRequest.getAccount());
                            limit1.setExpenseCategory(clientLimitRequest.getExpenseCategory());
                            limit1.setCurrencyShortname(limit.getCurrencyShortname());
                            limitRepo.save(limit1);

                        });

                        loggerLimit.info("Установлен новый лимит для этого типа транзакции и аккаунта");
                        return ResponseEntity.ok("Установка лимита прошла успешно");
                    } else {
                        loggerLimit.info("Не существует ранее установленного лимита для этого типа транзакции");
                        return ResponseEntity.ok("Не существует ранее установленного лимита для этого типа транзакции");
                    }
                } catch (NullPointerException e) {
                    loggerLimit.info("Что то пошло не так...");
                    return ResponseEntity.ok("Произошла ошибка");
                }
            } else {
                return ResponseEntity.ok("Данные неверны");
            }
        } else {
            return ResponseEntity.ok("Запрос изменение лимита не может быть пустым");
        }
    }
}

