package by.dzikovskiy.postgresmicro.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "visas")
public class Visa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}
