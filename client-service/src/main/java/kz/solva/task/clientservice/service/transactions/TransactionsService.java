package kz.solva.task.clientservice.service.transactions;

import java.util.List;
import java.util.Map;

public interface TransactionsService {
    List<Map<String, Object>> getTransactionsOverLimit();
}
