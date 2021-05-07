package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dao.UserWithAuditDao;
import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.dto.VisaDto;
import by.dzikovskiy.postgresmicro.entity.Country;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = UserServiceWithAuditImpl.class)
@AutoConfigureMockMvc
class UserServiceWithAuditImplTest {

    @Autowired
    private UserServiceWithAuditImpl userServiceWithAudit;
    private static UserDto userDto;
    private static UserResponse userResponse;
    @MockBean
    private UserWithAuditDao userWithAuditDao;

    @BeforeAll
    static void setUp() {
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("john");
        List<VisaDto> visaDtoList = Arrays.asList(
                new VisaDto(1L, Country.BELARUS),
                new VisaDto(2L, Country.USA));
        userDto.setVisas(visaDtoList);
        userResponse = new UserResponse();
        userResponse.setUser(userDto);
        userResponse.setVisaCount(2);
    }

    @Test
    void Given_MockedUserResponse_When_SavingUserDto_Then_ReturnCorrectUserResponse() {
        //given
        given(userWithAuditDao.save(any())).willReturn(userResponse);

        //when
        UserResponse response = userServiceWithAudit.save(userDto);

        //then
        assertEquals(userResponse, response);
    }

    @Test
    void Given_UserDtoInDb_When_FindById_Then_ReturnUserDto() {
        //given
        given(userWithAuditDao.findById(any())).willReturn(Optional.of(userDto));

        //when
        Optional<UserDto> optionalUserDto = userServiceWithAudit.findById(userDto.getId());

        //then
        assertEquals(userDto, optionalUserDto.get());
    }

    @Test
    void Should_Call_UserWithAuditDao_OneTime_When_DeleteById() {
        //when
        userServiceWithAudit.deleteById(userDto.getId());

        //then
        Mockito.verify(userWithAuditDao, times(1)).deleteById(userDto.getId());
    }

    @Test
    void Given_UserDto_When_Update_Then_ReturnUpdatedUserDto() {
        //given
        given(userWithAuditDao.update(any())).willReturn(userDto);

        //when
        UserDto updated = userServiceWithAudit.update(userDto);

        //then
        assertEquals(userDto, updated);
    }
}