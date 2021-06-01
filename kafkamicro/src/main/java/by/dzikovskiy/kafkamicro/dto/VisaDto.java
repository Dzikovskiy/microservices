package by.dzikovskiy.kafkamicro.dto;


import by.dzikovskiy.kafkamicro.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisaDto {

    private Long id;

    private Country country;
}
