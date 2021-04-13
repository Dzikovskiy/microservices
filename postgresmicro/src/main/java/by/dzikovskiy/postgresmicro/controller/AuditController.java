package by.dzikovskiy.postgresmicro.controller;

import by.dzikovskiy.postgresmicro.dto.AuditDto;
import by.dzikovskiy.postgresmicro.service.AuditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/postgres")
@Slf4j
@AllArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @PostMapping("/audit")
    public ResponseEntity<AuditDto> saveAudit(@RequestBody AuditDto auditDto) {
        AuditDto auditSaved = auditService.save(auditDto);
        URI location = URI.create(String.format("/postgres/audit/%s", auditSaved.getId()));

        return ResponseEntity.created(location).body(auditSaved);
    }
}
