/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControllerJPA.ComunaJpaController;
import Model.Comuna;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author KevinRoss
 */
@Controller
@PersistenceContext(unitName = "DalkinPU")
public class comunaControlador {

   
    @RequestMapping(value="/ver_comuna.htm", method = RequestMethod.GET)
    public String otroMetodo (Model model){
        return "index";
    }
    
//siempre los metodos deben retornar un string en los controladores
    @RequestMapping(value="/ver_comuna.htm",method = RequestMethod.POST)
  
    public String  recibir(@RequestParam("comuna") String nombre,Model model)
    {
        if(nombre.trim().equals(""))
        {
            return "error500";
        }
        else{
            Comuna c = new Comuna();
            c.setNombre(nombre);                    
            ComunaJpaController cjpa = new ComunaJpaController();
            cjpa.create(c);
            model.addAttribute("comuna",c);
            return "registroCompleto"; //nombre de la vista 
        }
    
    }
}
