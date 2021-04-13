package by.dzikovskiy.postgresmicro.entity;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UserDto user;
    private Integer visaCount;
}
