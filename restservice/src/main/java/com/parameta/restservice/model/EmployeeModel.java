package com.parameta.restservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "EMPLEADOS", schema = "DBMASTER")
public class EmployeeModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 8010832163051609138L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOMBRES")
    private String names;

    @Column(name = "APELLIDOS")
    private String lastNames;

    @Column(name = "ID_TIPO_DOCUMENTO")
    private Integer documentType;

    @Column(name = "NUMERO_DOCUMENTO")
    private String documentNumber;

    @Column(name = "FECHA_NACIMIENTO")
    private Date birthDate;

    @Column(name = "FECHA_VINCULACION")
    private Date hiringDate;

    @Column(name = "ID_CARGO")
    private Integer positionId;

    @Column(name = "SALARIO")
    private Double salary;

    @OneToOne
    @JoinColumn(name = "ID_TIPO_DOCUMENTO", referencedColumnName = "ID", insertable = false, updatable = false)
    private DocumentType document;

    @OneToOne
    @JoinColumn(name = "ID_CARGO", referencedColumnName = "ID", insertable = false, updatable = false)
    private Position position;

}
