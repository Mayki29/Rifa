package com.rifasgeneracion.rifas.dao;

import com.rifasgeneracion.rifas.models.Rifa;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
public class RifaDaoImp implements RifaDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Rifa> listarRifasPorUsuario(Integer id) {
        String query = "FROM Rifa WHERE id_vendedor = :idv";
        return entityManager.createQuery(query).setParameter("idv",id).getResultList();

    }

    @Override
    public Rifa crearRifa(Rifa rifa) {
        try {
            entityManager.merge(rifa);
            System.out.println("Ingreso la rifa");
            System.out.println(rifa.getFecha());

            String query = "FROM Rifa WHERE id_vendedor = :idv AND fecha = :fch";
            List<Rifa> r = entityManager.createQuery(query).setParameter("idv", rifa.getIdVendedor()).setParameter("fch",rifa.getFecha()).getResultList();
            System.out.println(r);
            System.out.println(r.get(0).getNombres());

            return r.get(0);
        }catch(Exception e){
            System.out.println(e);
            return null;
        }

    }

    @Override
    public String confirmarVenta(Integer id) {
        try{
            Rifa rifa = entityManager.find(Rifa.class, id);
            rifa.setConfirmacion("Confirmada");
            entityManager.merge(rifa);
            return "Confirmada";
        }catch (Error e){
            return "Error";
        }
    }

    @Override
    public Rifa getById(Integer id) {
        return entityManager.find(Rifa.class, id);
    }

    @Override
    public List<Rifa> getListByName(Integer idUser, String data, String option) {
        String lowerData = data.toLowerCase()+"%";
        String query = null;
        switch (option){
            case "nombres":
                query = "FROM Rifa WHERE id_vendedor = :id AND LOWER(nombres) LIKE :data";
                break;
            case "apellidos":
                query = "FROM Rifa WHERE id_vendedor = :id AND LOWER(apellidos) LIKE :data";
                break;
            case "celular":
                query = "FROM Rifa WHERE id_vendedor = :id AND LOWER(celular) LIKE :data";
                break;
            case "direccion":
                query = "FROM Rifa WHERE id_vendedor = :id AND LOWER(direccion) LIKE :data";
                break;
            case "fecha":
                query = "FROM Rifa WHERE id_vendedor = :id AND DATE(fecha) = :data";
                Date fecha = java.sql.Date.valueOf(data);
                List<Rifa> rifas = entityManager.createQuery(query).setParameter("id",idUser).setParameter("data",fecha).getResultList();
                return rifas;
        }
        System.out.println(query);
        List<Rifa> rifas = entityManager.createQuery(query).setParameter("id",idUser).setParameter("data",lowerData).getResultList();
        return rifas;
    }
}
