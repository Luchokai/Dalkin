package Controlador;

import ControllerJPA.ClienteJpaController;
import ControllerJPA.ComunaJpaController;
import ControllerJPA.LocalComidaJpaController;

import Model.Cliente;
import Model.Comuna;
import Model.LocalComida;

import java.util.List;
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
public class RegistrarLocalControlador {
    
  
    
@RequestMapping(value="/ver_local.htm", method = RequestMethod.GET)
    public String mostrarCombobox(Model model) {
        List<Comuna> comunas = null;

        ComunaJpaController comunasdb = new ComunaJpaController();

        comunas = comunasdb.findComunaEntities();

        model.addAttribute("comunas", comunas);

        List<Cliente> clientes = null;

        ClienteJpaController clientesdb = new ClienteJpaController();
        clientes = clientesdb.findClienteEntities();

        model.addAttribute("clientes", clientes);

        return "registrarLocal";
    }
   
@RequestMapping(value="/guardar_cliente.htm", method = RequestMethod.POST)
    public String recibir(@RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("rut") String rut,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo,
            @RequestParam("comuna") int comuna,
            @RequestParam("cliente") int cliente,
            Model model) {

        LocalComida l = new LocalComida();
        l.setNombre(nombre);
        l.setDescripcion(descripcion);
        l.setRut(rut);
        l.setDireccion(direccion);
        l.setTelefono(telefono);
        l.setCorreo(correo);
        
        Comuna c = new Comuna();
        c.setId(comuna);
        l.setComunaId(c);
        
        Cliente cl = new Cliente();
        cl.setId(cliente);
        l.setClienteId(cl);

        LocalComidaJpaController localcomii = new LocalComidaJpaController();

        localcomii.create(l);

       
        return "registroCompleto"; //nombre de la vista 

    }


}