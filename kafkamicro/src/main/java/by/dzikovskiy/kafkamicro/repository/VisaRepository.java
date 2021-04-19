package by.dzikovskiy.kafkamicro.repository;

import by.dzikovskiy.kafkamicro.entity.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
}
