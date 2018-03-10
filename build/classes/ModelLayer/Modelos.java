/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer;

import UtilsLayer.Auditable;
import UtilsLayer.LanguageSelector;
import VisualLayer.MainClass;
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
 * @author Yordanka
 */
@Entity
@Table(name = "modelos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modelos.findAll", query = "SELECT m FROM Modelos m")
    , @NamedQuery(name = "Modelos.findByIdModelo", query = "SELECT m FROM Modelos m WHERE m.idModelo = :idModelo")
    , @NamedQuery(name = "Modelos.findByNombreModelo", query = "SELECT m FROM Modelos m WHERE m.nombreModelo = :nombreModelo")})
@EntityListeners(UtilsLayer.JPATraceEventTrigger.class)
public class Modelos implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_modelo")
    private Integer idModelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "nombre_modelo")
    private String nombreModelo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModeloAuto")
    private List<Autos> autosList;
    @JoinColumn(name = "modelo_id_marca", referencedColumnName = "id_marca")
    @ManyToOne(optional = false)
    private Marcas modeloIdMarca;
    @JoinColumn(name = "modelo_id_tar", referencedColumnName = "id_tarifa")
    @ManyToOne(optional = false)
    private Tarifa modeloIdTar;

    public Modelos() {
    }

    public Modelos(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public Modelos(Integer idModelo, String nombreModelo) {
        this.idModelo = idModelo;
        this.nombreModelo = nombreModelo;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    @XmlTransient
    public List<Autos> getAutosList() {
        return autosList;
    }

    public void setAutosList(List<Autos> autosList) {
        this.autosList = autosList;
    }

    public Marcas getModeloIdMarca() {
        return modeloIdMarca;
    }

    public void setModeloIdMarca(Marcas modeloIdMarca) {
        this.modeloIdMarca = modeloIdMarca;
    }

    public Tarifa getModeloIdTar() {
        return modeloIdTar;
    }

    public void setModeloIdTar(Tarifa modeloIdTar) {
        this.modeloIdTar = modeloIdTar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModelo != null ? idModelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modelos)) {
            return false;
        }
        Modelos other = (Modelos) object;
        if ((this.idModelo == null && other.idModelo != null) || (this.idModelo != null && !this.idModelo.equals(other.idModelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new LanguageSelector(MainClass.language).getProperty("tag_marca") + ": " + this.modeloIdMarca.getNombreMarca() + "/ " + new LanguageSelector(MainClass.language).getProperty("tag_modelo") + ": " + nombreModelo;
    }

    @Override
    public Integer getId() {
        return this.idModelo;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
