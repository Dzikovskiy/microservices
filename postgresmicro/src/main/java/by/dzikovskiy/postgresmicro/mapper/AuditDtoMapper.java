package by.dzikovskiy.postgresmicro.mapper;

import by.dzikovskiy.postgresmicro.dto.AuditDto;
import by.dzikovskiy.postgresmicro.entity.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditDtoMapper {
    Audit auditDtoToAudit(AuditDto auditDto);

    AuditDto auditToAuditDto(Audit audit);
}
