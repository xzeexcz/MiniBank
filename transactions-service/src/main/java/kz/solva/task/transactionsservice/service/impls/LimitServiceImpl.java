package kz.solva.task.transactionsservice.service.impls;

import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import kz.solva.task.transactionsservice.entity.limit.Limit;
import kz.solva.task.transactionsservice.mapper.limit.LimitMapper;
import kz.solva.task.transactionsservice.repository.limit.LimitRepo;
import kz.solva.task.transactionsservice.service.limit.LimitService;
import kz.solva.task.transactionsservice.utils.limit.LimitUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class LimitServiceImpl implements LimitService {
    private final LimitRepo limitRepo;
    private final LimitMapper limitMapper;
    private final LimitUtils limitUtils;
    private final Logger logger = LoggerFactory.getLogger(Limit.class);

    @Override
    public Limit isExists(BigDecimal account, ExpenseCategory expenseCategory) {
        logger.info("Проверяю есть ли ранее установленный лимит для этого аккаунта и типа транзакции в БД");
//        Limit limit = limitRepo.findFirstByAccountAndExpenseCategoryEqualsOrderByDateTimeDesc(account, expenseCategory);
        Limit limit = limitRepo.findRecentLimits2(account,expenseCategory, ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC));
        if (limit != null) {
            return limit;
        } else {
            return null;
        }
    }

//    @Override
//    public void setNewLimit(BigDecimal account, String category, double sum) {
//        logger.info("Устанавливаю новый лимит для этого типа аккаунта и транзакции в БД.");
//        Limit limit = new Limit();
//        limit.setAccount(account);
//        limit.setExpenseCategory(ExpenseCategory.valueOf(category));
//        limit.setLimitSum(1000.00);
//        limit.setCurrencyShortname(CurrencyShortname.USD);
//        limit.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Almaty")));
//        limit.setLimitBalance(limitUtils.doCalculateLimitBalance(1000.00, sum,));
//    }
}
