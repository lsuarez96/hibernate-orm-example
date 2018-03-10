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
@Table(name = "situaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Situaciones.findAll", query = "SELECT s FROM Situaciones s")
    , @NamedQuery(name = "Situaciones.findByIdSit", query = "SELECT s FROM Situaciones s WHERE s.idSit = :idSit")
    , @NamedQuery(name = "Situaciones.findByTipoSituacion", query = "SELECT s FROM Situaciones s WHERE s.tipoSituacion = :tipoSituacion")})
@EntityListeners(UtilsLayer.JPATraceEventTrigger.class)
public class Situaciones implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sit")
    private Integer idSit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "tipo_situacion")
    private String tipoSituacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSituacionAuto")
    private List<Autos> autosList;

    public Situaciones() {
    }

    public Situaciones(Integer idSit) {
        this.idSit = idSit;
    }

    public Situaciones(Integer idSit, String tipoSituacion) {
        this.idSit = idSit;
        this.tipoSituacion = tipoSituacion;
    }

    public Integer getIdSit() {
        return idSit;
    }

    public void setIdSit(Integer idSit) {
        this.idSit = idSit;
    }

    public String getTipoSituacion() {
        String sit = "-";
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        switch (tipoSituacion) {
            case "ocupado":
                sit = prop.getProperty("tag_ocupated");
                break;
            case "libre":
                sit = prop.getProperty("tag_free");
                break;
            case "taller":
                sit = prop.getProperty("tag_shop");
                break;

        }
        return sit;
    }

    public void setTipoSituacion(String tipoSituacion) {
        this.tipoSituacion = tipoSituacion;
    }

    @XmlTransient
    public List<Autos> getAutosList() {
        return autosList;
    }

    public void setAutosList(List<Autos> autosList) {
        this.autosList = autosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSit != null ? idSit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Situaciones)) {
            return false;
        }
        Situaciones other = (Situaciones) object;
        if ((this.idSit == null && other.idSit != null) || (this.idSit != null && !this.idSit.equals(other.idSit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String sit = "-";
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        switch (tipoSituacion) {
            case "ocupado":
                sit = prop.getProperty("tag_ocupated");
                break;
            case "libre":
                sit = prop.getProperty("tag_free");
                break;
            case "taller":
                sit = prop.getProperty("tag_shop");
                break;

        }
        return sit;
    }

    @Override
    public Integer getId() {
        return this.idSit;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
