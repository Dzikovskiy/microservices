package by.dzikovskiy.postgresmicro.controller;

import by.dzikovskiy.postgresmicro.dto.UserDto;
import by.dzikovskiy.postgresmicro.dto.VisaDto;
import by.dzikovskiy.postgresmicro.entity.Country;
import by.dzikovskiy.postgresmicro.entity.UserResponse;
import by.dzikovskiy.postgresmicro.service.UserServiceWithAuditImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static UserDto userDto;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserServiceWithAuditImpl userWithAuditService;

    @BeforeAll
    static void initAll() {
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("john");
        List<VisaDto> visaDtoList = Arrays.asList(
                new VisaDto(1L, Country.BELARUS),
                new VisaDto(2L, Country.USA));
        userDto.setVisas(visaDtoList);
    }

    @Test
    void Given_CorrectUserDto_When_PerformingGetWithCorrectId_Then_ReturnTheUserDto() throws Exception {
        //given
        given(userWithAuditService.findById(userDto.getId())).willReturn(Optional.of(userDto));

        //when
        mvc.perform(get("/postgres/users/1")
                //then
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId().intValue())))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.visas", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    void Given_EmptyOptional_When_PerformingGetWithNotCorrectId_Then_ReturnNotFound() throws Exception {
        //given
        given(userWithAuditService.findById(any())).willReturn(Optional.empty());

        //when
        mvc.perform(get("/postgres/users/1")
                //then
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void Given_UserDto_When_PerformingPost_Then_ReturnTheUserDto() throws Exception {
        //given
        given(userWithAuditService.save(any())).willReturn(new UserResponse(userDto, 1));

        //when
        mvc.perform(post("/postgres/users")
                //then
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/postgres/users/" + userDto.getId()))
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    void Given_UserDto_When_PerformingPut_Then_ReturnOk() throws Exception {
        //given
        given(userWithAuditService.findById(1L)).willReturn(Optional.of(userDto));

        //when
        mvc.perform(put("/postgres/users/1")
                //then
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    void Given_NotExistUserDto_When_PerformingPut_Then_ReturnNotFound() throws Exception {
        //given
        given(userWithAuditService.findById(1L)).willReturn(Optional.empty());

        //when
        mvc.perform(put("/postgres/users/1")
                //then
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void Given_IdOfExistUser_When_PerformingDelete_Then_ReturnNoContent() throws Exception {
        //given
        given(userWithAuditService.findById(any())).willReturn(Optional.of(userDto));

        //when
        mvc.perform(delete("/postgres/users/1")
                //then
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void Given_IdOfNotExistUser_When_PerformingDelete_Then_ReturnNotFound() throws Exception {
        //given
        given(userWithAuditService.findById(any())).willReturn(Optional.empty());

        //when
        mvc.perform(delete("/postgres/users/1")
                //then
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}