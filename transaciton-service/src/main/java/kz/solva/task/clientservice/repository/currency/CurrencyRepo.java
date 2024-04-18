package kz.solva.task.clientservice.repository.currency;

import kz.solva.task.clientservice.entity.currency.Currency;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CurrencyRepo extends CassandraRepository<Currency,Long> {
}
