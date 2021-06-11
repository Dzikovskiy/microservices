package by.dzikovskiy.postgresmicro.repository;

import by.dzikovskiy.postgresmicro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
