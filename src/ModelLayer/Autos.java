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
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yordanka
 */
@Entity
@Table(name = "autos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autos.findAll", query = "SELECT a FROM Autos a")
    , @NamedQuery(name = "Autos.findByIdAuto", query = "SELECT a FROM Autos a WHERE a.idAuto = :idAuto")
    , @NamedQuery(name = "Autos.findByChapa", query = "SELECT a FROM Autos a WHERE a.chapa = :chapa")
    , @NamedQuery(name = "Autos.findByColor", query = "SELECT a FROM Autos a WHERE a.color = :color")
    , @NamedQuery(name = "Autos.findByKilometros", query = "SELECT a FROM Autos a WHERE a.kilometros = :kilometros")})
@EntityListeners(UtilsLayer.JPATraceEventTrigger.class)
public class Autos implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auto")
    private Integer idAuto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "chapa")
    private String chapa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "color")
    private String color;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kilometros")
    private float kilometros;
    @JoinColumn(name = "id_modelo_auto", referencedColumnName = "id_modelo")
    @ManyToOne(optional = false)
    private Modelos idModeloAuto;
    @JoinColumn(name = "id_situacion_auto", referencedColumnName = "id_sit")
    @ManyToOne(optional = false)
    private Situaciones idSituacionAuto;

    public Autos() {
    }

    public Autos(Integer idAuto) {
        this.idAuto = idAuto;
    }

    public Autos(Integer idAuto, String chapa, String color, float kilometros) {
        this.idAuto = idAuto;
        this.chapa = chapa;
        this.color = color;
        this.kilometros = kilometros;
    }

    public Integer getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(Integer idAuto) {
        this.idAuto = idAuto;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getKilometros() {
        return kilometros;
    }

    public void setKilometros(float kilometros) {
        this.kilometros = kilometros;
    }

    public Modelos getIdModeloAuto() {
        return idModeloAuto;
    }

    public void setIdModeloAuto(Modelos idModeloAuto) {
        this.idModeloAuto = idModeloAuto;
    }

    public Situaciones getIdSituacionAuto() {
        return idSituacionAuto;
    }

    public void setIdSituacionAuto(Situaciones idSituacionAuto) {
        this.idSituacionAuto = idSituacionAuto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuto != null ? idAuto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autos)) {
            return false;
        }
        Autos other = (Autos) object;
        if ((this.idAuto == null && other.idAuto != null) || (this.idAuto != null && !this.idAuto.equals(other.idAuto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        return prop.getProperty("tag_plate") + chapa + " /" + prop.getProperty("tag_marca") + ": " + this.idModeloAuto.getModeloIdMarca().getNombreMarca() + " /" + prop.getProperty("tag_modelo") + ": " + this.idModeloAuto.getNombreModelo() + " /" + prop.getProperty("tag_color") + ": " + color + " /" + prop.getProperty("tag_import") + ": " + this.idModeloAuto.getModeloIdTar().getTarifaNormal();
    }

    @Override
    public Integer getId() {
        return this.idAuto;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
