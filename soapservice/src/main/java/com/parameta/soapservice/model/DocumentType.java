package com.parameta.soapservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "TIPOS_DOCUMENTO", schema = "DBMASTER")
public class DocumentType implements Serializable {

    @Serial
    private static final long serialVersionUID = 8588482740764940618L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CODIGO")
    private String code;

    @Column(name = "NOMBRE")
    private String name;
}
