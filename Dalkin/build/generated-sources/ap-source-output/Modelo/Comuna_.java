package Modelo;

import Modelo.LocalComida;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-22T22:36:28")
@StaticMetamodel(Comuna.class)
public class Comuna_ { 

    public static volatile CollectionAttribute<Comuna, LocalComida> localComidaCollection;
    public static volatile SingularAttribute<Comuna, Integer> id;
    public static volatile SingularAttribute<Comuna, String> nombre;

}