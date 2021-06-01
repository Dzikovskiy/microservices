package by.dzikovskiy.kafkamicro.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.dzikovskiy.kafkamicro.dao.UserDao;
import by.dzikovskiy.kafkamicro.dto.UserDto;
import by.dzikovskiy.kafkamicro.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = {UserServiceImpl.class, UserDao.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserDao userDao;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void Given_User_When_Save_Then_Return_TheUser_And_CallUserDao_OneTime() {
        //given
        User user = new User();
        user.setVisas(null);
        user.setId(123L);
        user.setName("Name");
        given(userDao.save(any())).willReturn(user);
        //when
        User userSaved = userServiceImpl.save(new UserDto());
        //then
        assertSame(user, userSaved);
        verify(userDao,times(1)).save(any());
    }
}

