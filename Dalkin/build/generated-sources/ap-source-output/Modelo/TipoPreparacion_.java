package Modelo;

import Modelo.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-22T22:36:28")
@StaticMetamodel(TipoPreparacion.class)
public class TipoPreparacion_ { 

    public static volatile SingularAttribute<TipoPreparacion, Integer> id;
    public static volatile CollectionAttribute<TipoPreparacion, Producto> productoCollection;
    public static volatile SingularAttribute<TipoPreparacion, String> nombre;

}