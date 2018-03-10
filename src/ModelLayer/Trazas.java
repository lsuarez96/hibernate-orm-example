/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yordanka
 */
@Entity
@Table(name = "trazas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trazas.findAll", query = "SELECT t FROM Trazas t")
    , @NamedQuery(name = "Trazas.findByIdTraza", query = "SELECT t FROM Trazas t WHERE t.idTraza = :idTraza")
    , @NamedQuery(name = "Trazas.findByOperacion", query = "SELECT t FROM Trazas t WHERE t.operacion = :operacion")
    , @NamedQuery(name = "Trazas.findByTablaModificada", query = "SELECT t FROM Trazas t WHERE t.tablaModificada = :tablaModificada")
    , @NamedQuery(name = "Trazas.findByFecha", query = "SELECT t FROM Trazas t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "Trazas.findByDireccionIp", query = "SELECT t FROM Trazas t WHERE t.direccionIp = :direccionIp")
    , @NamedQuery(name = "Trazas.findByIdTuplaModificada", query = "SELECT t FROM Trazas t WHERE t.idTuplaModificada = :idTuplaModificada")})
public class Trazas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_traza")
    private Integer idTraza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "operacion")
    private String operacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "tabla_modificada")
    private String tablaModificada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "direccion_ip")
    private String direccionIp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tupla_modificada")
    private int idTuplaModificada;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private Usuarios idUsuario;
    @Column(name = "hora")
    private String hora;

    public Trazas() {
    }

    public Trazas(Integer idTraza) {
        this.idTraza = idTraza;
    }

    public Trazas(Integer idTraza, String operacion, String tablaModificada, Date fecha, String direccionIp, int idTuplaModificada, String hora) {
        this.idTraza = idTraza;
        this.operacion = operacion;
        this.tablaModificada = tablaModificada;
        this.fecha = fecha;
        this.direccionIp = direccionIp;
        this.idTuplaModificada = idTuplaModificada;
        this.hora = hora;
    }

    public Integer getIdTraza() {
        return idTraza;
    }

    public void setIdTraza(Integer idTraza) {
        this.idTraza = idTraza;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getTablaModificada() {
        return tablaModificada;
    }

    public void setTablaModificada(String tablaModificada) {
        this.tablaModificada = tablaModificada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public int getIdTuplaModificada() {
        return idTuplaModificada;
    }

    public void setIdTuplaModificada(int idTuplaModificada) {
        this.idTuplaModificada = idTuplaModificada;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTraza != null ? idTraza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trazas)) {
            return false;
        }
        Trazas other = (Trazas) object;
        if (this.fecha == other.fecha && (this.direccionIp == null ? other.direccionIp == null : this.direccionIp.equals(other.direccionIp)) && this.idUsuario == this.idUsuario && this.idTuplaModificada == other.idTuplaModificada && (this.tablaModificada == null ? other.tablaModificada == null : this.tablaModificada.equals(other.tablaModificada))) {
            return true;
        }
        if ((this.idTraza == null && other.idTraza != null) || (this.idTraza != null && !this.idTraza.equals(other.idTraza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(idTraza) + "/ " + this.getOperacion() + "/ " + this.getTablaModificada();
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

}
