package by.dzikovskiy.postgresmicro.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "audits")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
}
