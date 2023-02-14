package com.rifasgeneracion.rifas.dao;

import com.rifasgeneracion.rifas.models.Vendedor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class VendedorDaoImp implements VendedorDao{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Vendedor login(Vendedor vendedor) {
        String query = "FROM Vendedor WHERE nombre = :nombre";
        System.out.println("Va iniciar consulta");
        List<Vendedor> lista = entityManager.createQuery(query).setParameter("nombre", vendedor.getNombre()).getResultList();
        System.out.println("Desicion");
        if(lista.isEmpty()){
            System.out.println("vacio");
            return null;
        }
        String password = lista.get(0).getPassword();
        System.out.println(lista.get(0).getPassword());
        System.out.println("getPass");
        System.out.println(vendedor.getPassword());
        if(vendedor.getPassword().equals(password)){
            System.out.println("comaparo");
            return lista.get(0);
        }
        System.out.println("no fue true");
        return null;
    }
}
