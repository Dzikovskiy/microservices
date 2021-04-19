package by.dzikovskiy.kafkamicro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;

    private List<VisaDto> visas;
}
