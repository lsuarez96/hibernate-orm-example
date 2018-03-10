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
@Table(name = "choferes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Choferes.findAll", query = "SELECT c FROM Choferes c")
    , @NamedQuery(name = "Choferes.findByIdChofer", query = "SELECT c FROM Choferes c WHERE c.idChofer = :idChofer")
    , @NamedQuery(name = "Choferes.findByNumeroId", query = "SELECT c FROM Choferes c WHERE c.numeroId = :numeroId")
    , @NamedQuery(name = "Choferes.findByNombre", query = "SELECT c FROM Choferes c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Choferes.findByApellidos", query = "SELECT c FROM Choferes c WHERE c.apellidos = :apellidos")
    , @NamedQuery(name = "Choferes.findByDireccion", query = "SELECT c FROM Choferes c WHERE c.direccion = :direccion")})
@EntityListeners(UtilsLayer.JPATraceEventTrigger.class)
public class Choferes implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chofer")
    private Integer idChofer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "numero_id")
    private String numeroId;
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
    @Size(min = 1, max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @JoinColumn(name = "categoria", referencedColumnName = "id_cat")
    @ManyToOne(optional = false)
    private Categorias categoria;
    @OneToMany(mappedBy = "contIdChof")
    private List<Contratos> contratosList;

    public Choferes() {
    }

    public Choferes(Integer idChofer) {
        this.idChofer = idChofer;
    }

    public Choferes(Integer idChofer, String numeroId, String nombre, String apellidos, String direccion) {
        this.idChofer = idChofer;
        this.numeroId = numeroId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
    }

    public Integer getIdChofer() {
        return idChofer;
    }

    public void setIdChofer(Integer idChofer) {
        this.idChofer = idChofer;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
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
        hash += (idChofer != null ? idChofer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Choferes)) {
            return false;
        }
        Choferes other = (Choferes) object;
        if ((this.idChofer == null && other.idChofer != null) || (this.idChofer != null && !this.idChofer.equals(other.idChofer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        String nombre = prop.getProperty("tag_name");
        String apellidos = prop.getProperty("tag_lastName") + ": ";
        String categoria = prop.getProperty("tag_category" + ": ");

        return nombre + ": " + this.nombre + "/ " + apellidos + this.apellidos + "/ " + categoria + this.categoria.toString();
    }

    @Override
    public Integer getId() {
        return this.idChofer;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
