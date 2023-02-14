package com.rifasgeneracion.rifas.controllers;

import com.rifasgeneracion.rifas.dao.RifaDao;
import com.rifasgeneracion.rifas.models.Rifa;
import com.rifasgeneracion.rifas.utils.JWTUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RifaController {
    @Autowired
    private RifaDao rifaDao;

    @Autowired
    private JWTUtil jwtUtil;

    private boolean validarToken(String token){
        String vendedorId = obtenerId(token);
        return vendedorId != null;
    }
    private String obtenerId(String token){
        try{
            String t = new String();
            for(char i : token.toCharArray()){
                String a = String.valueOf(i);
                if (!a.equals("-")){
                    t += String.valueOf(i);
                }else{
                    break;
                }
            }
            System.out.println(t);

            if(t.matches("[+-]?\\d*(\\.\\d+)?") && !t.isEmpty()){
                return t;
            }
            return null;
        }catch (Error e){
            return null;
        }
        //System.out.println(t.matches("[+-]?\\d*(\\.\\d+)?"));
    }


    @RequestMapping(value = "api/rifas")
    public List<Rifa> rifasVendedor(@RequestHeader(value = "Authorization") String token){
        System.out.println(token);
        if(!validarToken(token)){
            System.out.println("Ingresó al null");
            return null;}
        System.out.println("verificacion");
        Integer id = Integer.valueOf(obtenerId(token));
        System.out.println(id);
        return rifaDao.listarRifasPorUsuario(id);
    }

    @RequestMapping(value = "api/crear", method = RequestMethod.POST)
    public Rifa crearRifa(@RequestHeader(value = "Authorization") String token,@RequestBody Rifa rifa){
        if(!validarToken(token)){
            return null;
        }
        Integer id = Integer.valueOf(obtenerId(token));
        rifa.setFecha(LocalDateTime.now());
        rifa.setIdVendedor(id);
        rifa.setPago(5D);
        rifa.setConfirmacion("Pendiente");
        Rifa aa = rifaDao.crearRifa(rifa);

        return aa;
    }

    @RequestMapping(value = "api/confirmar", method = RequestMethod.PUT)
    public String confirmaVenta(@RequestHeader(value = "Authorization") String token, @RequestHeader(value = "idRifa") Integer id){
        if(!validarToken(token)){
            return "NValidado";
        }
        return rifaDao.confirmarVenta(id);
    }

    @RequestMapping(value = "api/rifa/{id}", method = RequestMethod.GET)
    public Rifa getRifaById(@PathVariable(value = "id") Integer id){
        return rifaDao.getById(id);
    }
    @RequestMapping(value = "api/rifa/search", method = RequestMethod.POST)
    public List<Rifa> searchRifasByName(@RequestHeader(value = "Authorization") String token, @RequestBody String objRequest){

        if(!validarToken(token)){
            return null;
        }
        Integer id = Integer.valueOf(obtenerId(token));
        JSONObject datos = new JSONObject(objRequest);
        String data = datos.get("data").toString();
        String option = datos.get("option").toString();
        System.out.println(data+" "+option);
        return rifaDao.getListByName(id,data,option);
     }
}
