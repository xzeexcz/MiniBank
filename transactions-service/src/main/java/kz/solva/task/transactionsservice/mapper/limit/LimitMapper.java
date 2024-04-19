package kz.solva.task.transactionsservice.mapper.limit;

import kz.solva.task.transactionsservice.dto.limit.LimitDto;
import kz.solva.task.transactionsservice.entity.limit.Limit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LimitMapper {
    Limit toLimitEntity(LimitDto limitDto);
    LimitDto toLimitDto(Limit limit);
    List<Limit> toLimitEntityList(List<LimitDto> limitDtoList);
    List<LimitDto> toLimitDtoList(List<Limit> limitList);
}
