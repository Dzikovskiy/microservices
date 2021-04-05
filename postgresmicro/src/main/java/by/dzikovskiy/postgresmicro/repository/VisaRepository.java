package by.dzikovskiy.postgresmicro.repository;

import by.dzikovskiy.postgresmicro.entity.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
}
