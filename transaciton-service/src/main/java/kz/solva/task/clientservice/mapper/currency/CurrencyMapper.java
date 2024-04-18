package kz.solva.task.clientservice.mapper.currency;

import kz.solva.task.clientservice.dto.twelvedata.TwelveDataResponse;
import kz.solva.task.clientservice.entity.currency.Currency;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mappings({
            @Mapping(target = "symbol", source = "symbol"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "dateTime", source = "dateTime"),
            @Mapping(target = "close", source = "close"),
            @Mapping(target = "previous_close", source = "previous_close"),
    })
    Currency toCurrencyEntity(TwelveDataResponse twelveDataResponse);
    List<Currency> toCurrencyEntityList(List<TwelveDataResponse> responses);
}
