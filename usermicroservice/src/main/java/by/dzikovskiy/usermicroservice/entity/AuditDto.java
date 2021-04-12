package by.dzikovskiy.usermicroservice.entity;

import lombok.Data;

@Data
public class AuditDto {
    private Long id;
    private String message;

    public AuditDto(String message) {
        this.message = message;
    }
}
