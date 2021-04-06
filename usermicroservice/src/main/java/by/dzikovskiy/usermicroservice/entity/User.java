package by.dzikovskiy.usermicroservice.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private long id;
    private String name;
    private List<VisaDto> visas;
}
