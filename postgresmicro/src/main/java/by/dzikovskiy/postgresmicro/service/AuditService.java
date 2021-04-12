package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.entity.AuditDto;
import by.dzikovskiy.postgresmicro.mapper.AuditDtoMapper;
import by.dzikovskiy.postgresmicro.repository.AuditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    private final AuditDtoMapper auditDtoMapper;

    public AuditDto save(AuditDto auditDto) {
        return auditDtoMapper.auditToAuditDto(auditRepository.save(auditDtoMapper.auditDtoToAudit(auditDto)));
    }
}
