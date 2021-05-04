package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dto.AuditDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AuditServiceTest {

    @Test
    void AuditGeneratesWithCorrectData() {
        //given
        String message = "message";
        AuditDto auditDto;

        //when
        auditDto = AuditService.generateAudit(message);

        //then
        assertThat(auditDto.getMessage(), startsWith("message"));
        assertThat(auditDto.getMessage(), containsString(" ; "));
        assertThat(auditDto.getMessage(), matchesPattern("^.*\\d{4}[-](0?[1-9]|1[012])[-](0?[1-9]|[12][0-9]|3[01])\\s([0-5][0-9])[:]([0-5][0-9])[:]([0-5][0-9])$"));
    }
}