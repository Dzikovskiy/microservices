package by.dzikovskiy.usermicroservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private long id;
    private String name;
    private List<VisaDto> visas;
}
