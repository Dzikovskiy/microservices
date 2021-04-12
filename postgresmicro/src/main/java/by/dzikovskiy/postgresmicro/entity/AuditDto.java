package by.dzikovskiy.postgresmicro.entity;

import lombok.Data;

@Data
public class AuditDto {
    private Long id;
    private String message;
}
