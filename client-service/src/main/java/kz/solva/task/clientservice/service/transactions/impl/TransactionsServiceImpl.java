package kz.solva.task.clientservice.service.transactions.impl;

import kz.solva.task.clientservice.service.transactions.TransactionsService;
import kz.solva.task.clientservice.utils.transactions.TransactionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {
    private final TransactionsUtils transactionsUtils;
    @Override
    public List<Map<String, Object>> getTransactionsOverLimit() {
        return transactionsUtils.getTransactionsOverLimit();
    }
}
