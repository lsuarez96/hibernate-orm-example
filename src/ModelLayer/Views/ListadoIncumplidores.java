/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer.Views;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yordanka
 */
@Entity
@Table(name = "listado_incumplidores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListadoIncumplidores.findAll", query = "SELECT l FROM ListadoIncumplidores l")
    , @NamedQuery(name = "ListadoIncumplidores.findByNombre", query = "SELECT l FROM ListadoIncumplidores l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "ListadoIncumplidores.findByApellidos", query = "SELECT l FROM ListadoIncumplidores l WHERE l.apellidos = :apellidos")
    , @NamedQuery(name = "ListadoIncumplidores.findByFechaF", query = "SELECT l FROM ListadoIncumplidores l WHERE l.fechaF = :fechaF")
    , @NamedQuery(name = "ListadoIncumplidores.findByFechaEntrega", query = "SELECT l FROM ListadoIncumplidores l WHERE l.fechaEntrega = :fechaEntrega")
    , @NamedQuery(name = "ListadoIncumplidores.findByIdView", query = "SELECT l FROM ListadoIncumplidores l WHERE l.idView = :idView")})
public class ListadoIncumplidores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "fecha_f")
    @Temporal(TemporalType.DATE)
    private Date fechaF;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Column(name = "id_view")
    @Id
    private BigInteger idView;

    public ListadoIncumplidores() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public BigInteger getIdView() {
        return idView;
    }

    public void setIdView(BigInteger idView) {
        this.idView = idView;
    }

}
