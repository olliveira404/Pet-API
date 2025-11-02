package com.jader.api.pet_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Espécie é obrigatória")
    @Column(nullable = false)
    private String especie;

    private String raca;

    @Min(value = 0, message = "Idade não pode ser negativa")
    private Integer idade;

    @NotBlank(message = "Tutor é obrigatório")
    private String tutor;


}
