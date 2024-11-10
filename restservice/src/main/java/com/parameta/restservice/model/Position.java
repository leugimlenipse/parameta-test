package com.parameta.restservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "CARGOS", schema = "DBMASTER")
public class Position implements Serializable {

    @Serial
    private static final long serialVersionUID = 731979833991333277L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOMBRE")
    private String name;

}