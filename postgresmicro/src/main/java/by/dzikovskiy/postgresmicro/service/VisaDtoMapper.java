package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.entity.Visa;
import by.dzikovskiy.postgresmicro.entity.VisaDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VisaDtoMapper {

    VisaDtoMapper VISA_MAPPER = Mappers.getMapper(VisaDtoMapper.class);

    Visa visaDtoToVisa(VisaDto visaDto);

    VisaDto visaToVisaDto(Visa visa);
}
