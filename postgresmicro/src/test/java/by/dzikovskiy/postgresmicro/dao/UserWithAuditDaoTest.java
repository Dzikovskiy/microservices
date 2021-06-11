package by.dzikovskiy.postgresmicro.dao;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.dto.VisaDto;
import by.dzikovskiy.postgresmicro.entity.Country;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import by.dzikovskiy.postgresmicro.service.AuditService;
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

@SpringBootTest(classes = UserWithAuditDao.class)
@AutoConfigureMockMvc
class UserWithAuditDaoTest {

    private static UserDto userDto;
    private static UserResponse userResponse;
    @Autowired
    private UserWithAuditDao userWithAuditDao;
    @MockBean
    private AuditService auditService;
    @MockBean
    private UserDao userDao;

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
    void Given_UserDto_When_Save_Then_ReturnUserDto() {
        //given
        given(userDao.save(any())).willReturn(userResponse);
        //when
        UserResponse response = userWithAuditDao.save(userDto);
        //then
        assertEquals(userResponse, response);
        Mockito.verify(userDao, times(1)).save(any());
        Mockito.verify(auditService, times(1)).save(any());
    }

    @Test
    void Given_UserDto_When_FindById_Then_ReturnTheUserDto() {
        //given
        given(userDao.findById(any())).willReturn(Optional.of(userDto));
        //when
        Optional<UserDto> user = userWithAuditDao.findById(userDto.getId());
        //then
        assertEquals(userDto, user.get());
        Mockito.verify(auditService, times(1)).save(any());
    }

    @Test
    void Given_UserId_When_DeleteById_Then_Call_UserDao_And_AuditService_OneTime() {
        //when
        userWithAuditDao.deleteById(userDto.getId());
        //then
        Mockito.verify(userDao, times(1)).deleteById(any());
        Mockito.verify(auditService, times(1)).save(any());

    }

    @Test
    void Given_UserDto_When_Update_Then_ReturnTheUserDto() {
        //given
        given(userDao.update(any())).willReturn(userDto);
        //when
        UserDto user = userWithAuditDao.update(userDto);
        //then
        assertEquals(userDto, user);
        Mockito.verify(userDao, times(1)).update(any());
        Mockito.verify(auditService, times(1)).save(any());
    }
}