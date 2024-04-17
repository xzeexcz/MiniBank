package kz.solva.task.transacitonservice.repository.currency;

import kz.solva.task.transacitonservice.entity.currency.Currency;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CurrencyRepo extends CassandraRepository<Currency,Long> {
}
