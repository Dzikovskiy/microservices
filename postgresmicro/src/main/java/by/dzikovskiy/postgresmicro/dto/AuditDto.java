package by.dzikovskiy.postgresmicro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditDto {
    private Long id;
    private String message;

    public AuditDto(String message) {
        this.message = message;
    }
}
