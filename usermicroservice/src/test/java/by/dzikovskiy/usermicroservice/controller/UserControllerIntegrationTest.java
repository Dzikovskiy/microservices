package by.dzikovskiy.usermicroservice.controller;


import by.dzikovskiy.usermicroservice.entity.Country;
import by.dzikovskiy.usermicroservice.entity.User;
import by.dzikovskiy.usermicroservice.entity.VisaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(initializers = {WireMockInitializer.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static User user;

    @Autowired
    private WireMockServer wireMockServer;//Warning is ok because bean creates manually in WireMockInitializer

    @AfterAll
    public void afterEach() {
        this.wireMockServer.resetAll();
    }

    @BeforeAll
    public void init() throws JsonProcessingException {
        setupUser();
        setupGetStub();
    }

    public void setupUser() {
        user = new User();
        user.setId(1L);
        user.setName("john");
        List<VisaDto> visaDtoList = Arrays.asList(
                new VisaDto(1L, Country.BELARUS),
                new VisaDto(2L, Country.USA));
        user.setVisas(visaDtoList);
    }

    public void setupGetStub() throws JsonProcessingException {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/postgres/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(user))));

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/postgres/users/200"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void Given_UserInTheMockedEndpoint_When_PerformingGet_Then_ReturnTheUser() throws Exception {
        final String link = "/api/users/1";

        //when
        this.mockMvc.perform(MockMvcRequestBuilders.get(link)
                //then
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void Given_UserNotInTheMockedEndpoint_When_PerformingGet_Then_ReturnNotFound() throws Exception {
        final String link = "/api/users/200";

        //when
        this.mockMvc.perform(MockMvcRequestBuilders.get(link))
                //then
                .andExpect(status().isNotFound());
    }

}