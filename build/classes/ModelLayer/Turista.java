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
@Table(name = "turista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turista.findAll", query = "SELECT t FROM Turista t")
    , @NamedQuery(name = "Turista.findByIdTur", query = "SELECT t FROM Turista t WHERE t.idTur = :idTur")
    , @NamedQuery(name = "Turista.findByPasaporte", query = "SELECT t FROM Turista t WHERE t.pasaporte = :pasaporte")
    , @NamedQuery(name = "Turista.findByNombre", query = "SELECT t FROM Turista t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Turista.findByApellidos", query = "SELECT t FROM Turista t WHERE t.apellidos = :apellidos")
    , @NamedQuery(name = "Turista.findByEdad", query = "SELECT t FROM Turista t WHERE t.edad = :edad")
    , @NamedQuery(name = "Turista.findBySexo", query = "SELECT t FROM Turista t WHERE t.sexo = :sexo")
    , @NamedQuery(name = "Turista.findByTelefono", query = "SELECT t FROM Turista t WHERE t.telefono = :telefono")})
@EntityListeners(UtilsLayer.JPATraceEventTrigger.class)
public class Turista implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tur")
    private Integer idTur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pasaporte")
    private String pasaporte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "edad")
    private int edad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "sexo")
    private String sexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "telefono")
    private String telefono;
    @JoinColumn(name = "tur_id_pais", referencedColumnName = "id_pais")
    @ManyToOne(optional = false)
    private Pais turIdPais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contIdTur")
    private List<Contratos> contratosList;

    public Turista() {
    }

    public Turista(Integer idTur) {
        this.idTur = idTur;
    }

    public Turista(Integer idTur, String pasaporte, String nombre, String apellidos, int edad, String sexo, String telefono) {
        this.idTur = idTur;
        this.pasaporte = pasaporte;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.sexo = sexo;
        this.telefono = telefono;
    }

    public Integer getIdTur() {
        return idTur;
    }

    public void setIdTur(Integer idTur) {
        this.idTur = idTur;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        String sex = "";
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        switch (sexo) {
            case "masculino":
                sex = prop.getProperty("tag_male");
                break;
            case "femenino":
                sex = prop.getProperty("tag_female");
                break;
        }
        return sex;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Pais getTurIdPais() {
        return turIdPais;
    }

    public void setTurIdPais(Pais turIdPais) {
        this.turIdPais = turIdPais;
    }

    @XmlTransient
    public List<Contratos> getContratosList() {
        return contratosList;
    }

    public void setContratosList(List<Contratos> contratosList) {
        this.contratosList = contratosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTur != null ? idTur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turista)) {
            return false;
        }
        Turista other = (Turista) object;
        if ((this.idTur == null && other.idTur != null) || (this.idTur != null && !this.idTur.equals(other.idTur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);

        return prop.getProperty("tag_passport") + ": " + pasaporte + "/ " + prop.getProperty("tag_name") + ": " + nombre + "/ " + prop.getProperty("tag_lastName") + ": " + apellidos;
    }

    @Override
    public Integer getId() {
        return this.idTur;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
