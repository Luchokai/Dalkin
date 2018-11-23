/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("/verUsuario.htm")
public class RegistrarUsuarioControlador {
    @RequestMapping(method = RequestMethod.GET)
    public String otroMetodo (Model model){
        return "index";
    }
}
