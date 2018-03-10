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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Yordanka
 */
@Entity

@Table(name = "categorias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorias.findAll", query = "SELECT c FROM Categorias c")
    , @NamedQuery(name = "Categorias.findByIdCat", query = "SELECT c FROM Categorias c WHERE c.idCat = :idCat")
    , @NamedQuery(name = "Categorias.findByTipoCategoria", query = "SELECT c FROM Categorias c WHERE c.tipoCategoria = :tipoCategoria")})
@EntityListeners(UtilsLayer.JPATraceEventTrigger.class)
public class Categorias implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cat")
    private Integer idCat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "tipo_categoria")
    private String tipoCategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private List<Choferes> choferesList;

    public Categorias() {
    }

    public Categorias(Integer idCat) {
        this.idCat = idCat;
    }

    public Categorias(Integer idCat, String tipoCategoria) {
        this.idCat = idCat;
        this.tipoCategoria = tipoCategoria;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    @XmlTransient
    public List<Choferes> getChoferesList() {
        return choferesList;
    }

    public void setChoferesList(List<Choferes> choferesList) {
        this.choferesList = choferesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCat != null ? idCat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorias)) {
            return false;
        }
        Categorias other = (Categorias) object;
        if ((this.idCat == null && other.idCat != null) || (this.idCat != null && !this.idCat.equals(other.idCat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoCategoria;
    }

    @Override
    public Integer getId() {
        return this.idCat;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(Integer id) {
        setIdCat(id);
    }
    /*
    @Override
    public void onPostInsert(PostInsertEvent pie) {
        System.out.println("Evento persist");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister ep) {
        return false;
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPostUpdate(PostUpdateEvent pue) {
        System.out.println("Evento persist");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPostDelete(PostDeleteEvent pde) {
        System.out.println("Evento persist");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     */
}
