package by.dzikovskiy.postgresmicro.dao;

import by.dzikovskiy.postgresmicro.dto.AuditDto;
import by.dzikovskiy.postgresmicro.mapper.AuditDtoMapper;
import by.dzikovskiy.postgresmicro.repository.AuditRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuditDao {
    private final AuditRepository auditRepository;
    private final AuditDtoMapper auditDtoMapper;

    public AuditDto save(AuditDto auditDto) {
        return Optional.ofNullable(auditDto)
                .map(auditDtoMapper::auditDtoToAudit)
                .map(auditRepository::save)
                .map(auditDtoMapper::auditToAuditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
