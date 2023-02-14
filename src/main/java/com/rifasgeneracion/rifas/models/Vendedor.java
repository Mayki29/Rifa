package com.rifasgeneracion.rifas.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendedores")

public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendedor")
    @Getter @Setter
    private Integer idVendedor;
    @Column(name = "nombre")
    @Getter @Setter
    private String nombre;
    @Column(name = "password")
    @Getter @Setter
    private String password;
    @OneToMany(mappedBy = "vendedor")
    private List<Rifa> rifas;
}
