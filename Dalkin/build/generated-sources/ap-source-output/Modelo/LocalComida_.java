package Modelo;

import Modelo.Cliente;
import Modelo.Comuna;
import Modelo.Producto;
import Modelo.Puntuacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-22T22:36:28")
@StaticMetamodel(LocalComida.class)
public class LocalComida_ { 

    public static volatile SingularAttribute<LocalComida, String> descripcion;
    public static volatile SingularAttribute<LocalComida, String> rut;
    public static volatile SingularAttribute<LocalComida, Cliente> clienteId;
    public static volatile SingularAttribute<LocalComida, String> correo;
    public static volatile SingularAttribute<LocalComida, String> direccion;
    public static volatile SingularAttribute<LocalComida, Integer> id;
    public static volatile CollectionAttribute<LocalComida, Producto> productoCollection;
    public static volatile SingularAttribute<LocalComida, String> telefono;
    public static volatile SingularAttribute<LocalComida, String> nombre;
    public static volatile CollectionAttribute<LocalComida, Puntuacion> puntuacionCollection;
    public static volatile SingularAttribute<LocalComida, Comuna> comunaId;

}