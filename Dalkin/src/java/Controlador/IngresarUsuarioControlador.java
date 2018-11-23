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
 * @author KevinRoss
 */
@Controller
@RequestMapping("/ver1.htm")
public class IngresarUsuarioControlador {
    public class RegistrarLocalControlador {
    @RequestMapping(method = RequestMethod.GET)
    public String otroMetodo (Model model){
        return "index";
    }
}
}
