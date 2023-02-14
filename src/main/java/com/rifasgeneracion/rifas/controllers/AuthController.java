package com.rifasgeneracion.rifas.controllers;

import com.rifasgeneracion.rifas.dao.VendedorDao;
import com.rifasgeneracion.rifas.models.Vendedor;
import com.rifasgeneracion.rifas.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    public VendedorDao vendedorDao;

    @Autowired
    public JWTUtil jwtUtil;


    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Vendedor vendedor){
        System.out.println("Controlador incia");
        System.out.println(vendedor.getNombre() + vendedor.getPassword() +' ' +vendedor.getIdVendedor());
        Vendedor vendedorLogueado = vendedorDao.login(vendedor);

        System.out.println("regreso al controlador y va a comparar");

        if(vendedorLogueado != null){
            System.out.println("Ingreso y no fue null y va a crear el token");
            String token = String.valueOf(vendedorLogueado.getIdVendedor()) + '-'+ vendedorLogueado.getNombre();
            System.out.println("debio crear el token");
            return token;
        }
        return "Fail";

    }
}
