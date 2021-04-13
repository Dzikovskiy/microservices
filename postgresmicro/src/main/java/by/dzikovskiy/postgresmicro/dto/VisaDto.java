package by.dzikovskiy.postgresmicro.dto;

import by.dzikovskiy.postgresmicro.entity.Country;
import lombok.Data;

@Data
public class VisaDto {

    private Long id;

    private Country country;
}
