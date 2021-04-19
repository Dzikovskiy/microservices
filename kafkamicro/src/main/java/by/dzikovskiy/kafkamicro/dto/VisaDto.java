package by.dzikovskiy.kafkamicro.dto;


import by.dzikovskiy.kafkamicro.entity.Country;
import lombok.Data;

@Data
public class VisaDto {

    private Long id;

    private Country country;
}
