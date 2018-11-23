package Modelo;

import Modelo.Cliente;
import Modelo.LocalComida;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-22T22:36:28")
@StaticMetamodel(Puntuacion.class)
public class Puntuacion_ { 

    public static volatile SingularAttribute<Puntuacion, LocalComida> localComidaId;
    public static volatile SingularAttribute<Puntuacion, Cliente> clienteId;
    public static volatile SingularAttribute<Puntuacion, Integer> valoracion;
    public static volatile SingularAttribute<Puntuacion, Integer> id;
    public static volatile SingularAttribute<Puntuacion, String> comentario;

}