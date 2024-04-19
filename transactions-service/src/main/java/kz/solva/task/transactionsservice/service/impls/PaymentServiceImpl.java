package kz.solva.task.transactionsservice.service.impls;

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

                    limitRepo.save(newLimit);

                    logger.info("Регистрация транзакции.");

                    if (setFlag(payment.getSum(), limit.getLimitBalance(), payment.getCurrencyShortname())) {
                        payment.setLimit_exceded(true);
                    } else {
                        payment.setLimit_exceded(false);
                    }
                    payment.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Almaty")));

                    paymentRepo.save(payment);

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

                    limitRepo.save(limit1);

                    logger.info("Регистрация транзакции.");

                    if (setFlag(payment.getSum(), limit1.getLimitSum(), payment.getCurrencyShortname())) {
                        payment.setLimit_exceded(true);
                    } else {
                        payment.setLimit_exceded(false);
                    }
                    payment.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Almaty")));

                    paymentRepo.save(payment);
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
        if (paymentUtils.isBiggerThanLimit(paymentSum, limitSum, currencyShortname)) {
            return true;
        } else {
            return false;
        }
    }
}

