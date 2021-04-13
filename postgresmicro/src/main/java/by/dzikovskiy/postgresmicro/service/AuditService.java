package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.postgresmicro.dao.AuditDao;
import by.dzikovskiy.postgresmicro.dto.AuditDto;
import by.dzikovskiy.postgresmicro.mapper.AuditDtoMapper;
import by.dzikovskiy.postgresmicro.repository.AuditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
public class AuditService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final AuditDao auditDao;

    public AuditDto save(AuditDto auditDto) {
        return auditDao.save(auditDto);
    }

    public AuditDto generateAudit(final String message) {
        Date date = new Date();
        return new AuditDto(message + " ; " + dateFormat.format(date));
    }
}
