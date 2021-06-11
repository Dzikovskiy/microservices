package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dao.AuditDao;
import by.dzikovskiy.postgresmicro.dto.AuditDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = AuditService.class)
@AutoConfigureMockMvc
class AuditServiceTest {

    private static AuditDto auditDto;
    @MockBean
    private AuditDao auditDao;
    @Autowired
    private AuditService auditService;

    @BeforeAll
    static void initAll() {
        auditDto = new AuditDto(1L, "message");
    }

    @Test
    void Given_UserDto_When_Save_Then_ReturnSavedUserDto() {
        //given
        given(auditDao.save(any())).willReturn(auditDto);

        //when
        AuditDto audit = auditService.save(auditDto);

        //then
        assertEquals(auditDto, audit);

    }

    @Test
    void Given_Message_When_GenerateAudit_Then_ReturnCorrectAuditDto() {
        //given
        String message = "message";


        //when
        AuditDto audit = AuditService.generateAudit(message);

        //then
        assertThat(audit.getMessage(), containsString(" ; "));
        assertThat(audit.getMessage(), startsWith("message"));
        assertThat(audit.getMessage(), matchesPattern("^.*\\d{4}[-](0?[1-9]|1[012])[-](0?[1-9]|[12][0-9]|3[01])\\s([0-5][0-9])[:]([0-5][0-9])[:]([0-5][0-9])$"));
    }
}