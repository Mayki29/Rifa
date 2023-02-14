package com.rifasgeneracion.rifas.dao;

import com.rifasgeneracion.rifas.models.Rifa;

import java.util.List;

public interface RifaDao {
    List<Rifa> listarRifasPorUsuario(Integer id);
    Rifa crearRifa(Rifa rifa);
    String confirmarVenta(Integer id);
    Rifa getById(Integer id);
    List<Rifa> getListByName(Integer idUser, String data, String option);
}
