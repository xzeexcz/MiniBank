package kz.solva.task.clientservice.repository.currency;

import kz.solva.task.clientservice.entity.currency.Currency;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyRepo extends CassandraRepository<Currency, UUID> {
    Currency findFirstBySymbolEqualsOrderByDateTimeDesc(String symbol);

    @Query("SELECT * FROM t_currency WHERE symbol= ?0 ALLOW FILTERING")
    List<Currency> findBySymbolEquals(String symbol);
}
