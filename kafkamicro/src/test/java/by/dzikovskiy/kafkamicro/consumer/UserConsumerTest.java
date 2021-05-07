package by.dzikovskiy.kafkamicro.consumer;

import by.dzikovskiy.kafkamicro.dto.UserDto;
import by.dzikovskiy.kafkamicro.entity.User;
import by.dzikovskiy.kafkamicro.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = {UserServiceImpl.class, UserConsumer.class})
@ExtendWith(SpringExtension.class)
class UserConsumerTest {
    @Autowired
    private UserConsumer userConsumer;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
     void Given_User_When_ConsumeUser_Then_Call_UserService_OneTime() {
        //given
        User user = new User();
        user.setVisas(null);
        user.setId(123L);
        user.setName("Name");
        given(userServiceImpl.save(any())).willReturn(user);
        //when
        userConsumer.consumeUser(new UserDto());
        //then
        verify(userServiceImpl,times(1)).save( any());
    }
}

