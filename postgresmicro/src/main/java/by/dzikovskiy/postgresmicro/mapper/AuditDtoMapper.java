package by.dzikovskiy.postgresmicro.mapper;

import by.dzikovskiy.postgresmicro.entity.Audit;
import by.dzikovskiy.postgresmicro.entity.AuditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditDtoMapper {
    Audit auditDtoToAudit(AuditDto auditDto);

    AuditDto auditToAuditDto(Audit audit);
}
