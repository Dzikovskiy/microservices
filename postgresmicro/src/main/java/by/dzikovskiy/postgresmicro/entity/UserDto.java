package by.dzikovskiy.postgresmicro.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;

    private List<VisaDto> visas;
}
