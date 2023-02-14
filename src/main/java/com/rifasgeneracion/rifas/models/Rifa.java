package com.rifasgeneracion.rifas.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "rifas")

public class Rifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rifa")
    @Getter @Setter
    private int idRifa;
    @Column(name = "id_vendedor")
    @Getter @Setter
    private int idVendedor;
    @Column(name = "nombres")
    @Getter @Setter
    private String nombres;
    @Column(name = "apellidos")
    @Getter @Setter
    private String apellidos;
    @Column(name = "metodo_pago")
    @Getter @Setter
    private String metodoPago;
    @Column(name = "pago")
    @Getter @Setter
    private Double pago;
    @Column(name = "fecha")
    @Getter @Setter
    private LocalDateTime fecha;
    @Column(name="celular")
    @Getter @Setter
    private String celular;
    @Column(name="direccion")
    @Getter @Setter
    private String direccion;
    @Column(name = "confirmacion")
    @Getter @Setter
    private String confirmacion;

    @ManyToOne
    @JoinColumn(name = "id_vendedor", insertable=false, updatable = false)
    private Vendedor vendedor;
}
