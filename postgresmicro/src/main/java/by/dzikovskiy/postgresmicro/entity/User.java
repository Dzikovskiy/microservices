package by.dzikovskiy.postgresmicro.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visa> visas;

    @PrePersist
    private void prePersist() {
        visas.forEach( visa -> visa.setUser(this));
    }
}
