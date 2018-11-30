/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luisa
 */
@Entity
@Table(name = "local_comida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LocalComida.findAll", query = "SELECT l FROM LocalComida l")
    , @NamedQuery(name = "LocalComida.findById", query = "SELECT l FROM LocalComida l WHERE l.id = :id")
    , @NamedQuery(name = "LocalComida.findByNombre", query = "SELECT l FROM LocalComida l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "LocalComida.findByDescripcion", query = "SELECT l FROM LocalComida l WHERE l.descripcion = :descripcion")
    , @NamedQuery(name = "LocalComida.findByRut", query = "SELECT l FROM LocalComida l WHERE l.rut = :rut")
    , @NamedQuery(name = "LocalComida.findByDireccion", query = "SELECT l FROM LocalComida l WHERE l.direccion = :direccion")
    , @NamedQuery(name = "LocalComida.findByTelefono", query = "SELECT l FROM LocalComida l WHERE l.telefono = :telefono")
    , @NamedQuery(name = "LocalComida.findByCorreo", query = "SELECT l FROM LocalComida l WHERE l.correo = :correo")})
public class LocalComida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rut")
    private String rut;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "correo")
    private String correo;
    @OneToMany(mappedBy = "localComidaId")
    private Collection<Puntuacion> puntuacionCollection;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne
    private Cliente clienteId;
    @JoinColumn(name = "comuna_id", referencedColumnName = "id")
    @ManyToOne
    private Comuna comunaId;
    @OneToMany(mappedBy = "localId")
    private Collection<Producto> productoCollection;

    public LocalComida() {
    }

    public LocalComida(Integer id) {
        this.id = id;
    }

    public LocalComida(Integer id, String nombre, String descripcion, String rut, String direccion, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rut = rut;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @XmlTransient
    public Collection<Puntuacion> getPuntuacionCollection() {
        return puntuacionCollection;
    }

    public void setPuntuacionCollection(Collection<Puntuacion> puntuacionCollection) {
        this.puntuacionCollection = puntuacionCollection;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public Comuna getComunaId() {
        return comunaId;
    }

    public void setComunaId(Comuna comunaId) {
        this.comunaId = comunaId;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocalComida)) {
            return false;
        }
        LocalComida other = (LocalComida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.LocalComida[ id=" + id + " ]";
    }
    
}
