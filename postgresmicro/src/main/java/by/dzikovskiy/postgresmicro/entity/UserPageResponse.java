package by.dzikovskiy.postgresmicro.entity;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageResponse {
    private List<UserDto> users;
    private long totalItems;
    private int totalPages;
    private int currentPage;
}
