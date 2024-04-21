package kz.solva.task.transactionsservice.service.impls;

import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import kz.solva.task.transactionsservice.entity.limit.Limit;
import kz.solva.task.transactionsservice.repository.limit.LimitRepo;
import kz.solva.task.transactionsservice.service.limit.LimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class LimitServiceImpl implements LimitService {
    private final LimitRepo limitRepo;

    @Override
    public Limit isExists(BigDecimal account, ExpenseCategory expenseCategory) {
        log.info("Проверяю есть ли ранее установленный лимит для этого аккаунта и типа транзакции в БД {}", account);
        Limit limit = limitRepo.findRecentLimits3(account,expenseCategory);
        if (limit != null) {
            return limit;
        } else {
            return null;
        }
    }
}
