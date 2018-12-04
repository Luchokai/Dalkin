package Controlador;

 
import ControllerJPA.ComunaJpaController;
import ControllerJPA.LocalComidaJpaController;
import Model.Comuna;
import Model.LocalComida;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/reglocal.htm")
public class RegistrarLocalControlador {
   
    @RequestMapping(method = RequestMethod.GET)
    public String otroMetodo(Model model) {
        
           
             List<Comuna> comunas = null;
            
            ComunaJpaController comunasdb = new ComunaJpaController();
            comunas= comunasdb.findComunaEntities(); 
                
            model.addAttribute("comunas", comunas); 
            
            
        return "registrarLocal";
    }

    //siempre los metodos deben retornar un string en los controladores
    @RequestMapping(value = "/ver_local.htm", method = RequestMethod.POST)
     public String recibir(@RequestParam("nombre") String nombre, @RequestParam("rut") String rut, @RequestParam("direccion") String direccion, @RequestParam("telefono") String telefono, @RequestParam("correo") String correo, Model model) {

           
             List<Comuna> comunas = null;
            
            ComunaJpaController comunasdb = new ComunaJpaController();
            comunas= comunasdb.findComunaEntities(); 
           
        
        
        
            model.addAttribute("comunas", comunas); 
            return "registroCompleto"; //nombre de la vista       
    }
    
}
