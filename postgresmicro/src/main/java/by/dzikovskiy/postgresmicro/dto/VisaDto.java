package by.dzikovskiy.postgresmicro.dto;

import by.dzikovskiy.postgresmicro.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisaDto {

    private Long id;

    private Country country;
}
