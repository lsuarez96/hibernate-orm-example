/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer;

import UtilsLayer.Auditable;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Yordanka
 */
@Entity
@Table(name = "tarifa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarifa.findAll", query = "SELECT t FROM Tarifa t")
    , @NamedQuery(name = "Tarifa.findByIdTarifa", query = "SELECT t FROM Tarifa t WHERE t.idTarifa = :idTarifa")
    , @NamedQuery(name = "Tarifa.findByTarifaNormal", query = "SELECT t FROM Tarifa t WHERE t.tarifaNormal = :tarifaNormal")
    , @NamedQuery(name = "Tarifa.findByTarifaEspecial", query = "SELECT t FROM Tarifa t WHERE t.tarifaEspecial = :tarifaEspecial")})
@EntityListeners(UtilsLayer.JPATraceEventTrigger.class)
public class Tarifa implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tarifa")
    private Integer idTarifa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tarifa_normal")
    private Double tarifaNormal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tarifa_especial")
    private Double tarifaEspecial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modeloIdTar")
    private List<Modelos> modelosList;

    public Tarifa() {
    }

    public Tarifa(Integer idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Tarifa(Integer idTarifa, double tarifaNormal, double tarifaEspecial) {
        this.idTarifa = idTarifa;
        this.tarifaNormal = (tarifaNormal);
        this.tarifaEspecial = (tarifaEspecial);
    }

    public Integer getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Integer idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Double getTarifaNormal() {
        return (tarifaNormal);
    }

    public void setTarifaNormal(double tarifaNormal) {
        this.tarifaNormal = (tarifaNormal);;
    }

    public Double getTarifaEspecial() {
        return (tarifaEspecial);
    }

    public void setTarifaEspecial(double tarifaEspecial) {
        this.tarifaEspecial = (tarifaEspecial);;
    }

    @XmlTransient
    public List<Modelos> getModelosList() {
        return modelosList;
    }

    public void setModelosList(List<Modelos> modelosList) {
        this.modelosList = modelosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarifa != null ? idTarifa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarifa)) {
            return false;
        }
        Tarifa other = (Tarifa) object;
        if ((this.idTarifa == null && other.idTarifa != null) || (this.idTarifa != null && !this.idTarifa.equals(other.idTarifa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(this.tarifaNormal) + "/" + String.valueOf(this.tarifaEspecial);
    }

    @Override
    public Integer getId() {
        return this.idTarifa;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
