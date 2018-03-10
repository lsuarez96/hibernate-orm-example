/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer;

import UtilsLayer.Auditable;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yordanka
 */
@Entity
@Table(name = "contratos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contratos.findAll", query = "SELECT c FROM Contratos c")
    , @NamedQuery(name = "Contratos.findByFechaI", query = "SELECT c FROM Contratos c WHERE c.fechaI = :fechaI")
    , @NamedQuery(name = "Contratos.findByFechaF", query = "SELECT c FROM Contratos c WHERE c.fechaF = :fechaF")
    , @NamedQuery(name = "Contratos.findByFechaEntrega", query = "SELECT c FROM Contratos c WHERE c.fechaEntrega = :fechaEntrega")
    , @NamedQuery(name = "Contratos.findByImporteTotal", query = "SELECT c FROM Contratos c WHERE c.importeTotal = :importeTotal")
    , @NamedQuery(name = "Contratos.findByIdContrato", query = "SELECT c FROM Contratos c WHERE c.idContrato = :idContrato")})
@EntityListeners(UtilsLayer.JPATraceEventTrigger.class)
public class Contratos implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_i")
    @Temporal(TemporalType.DATE)
    private Date fechaI;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_f")
    @Temporal(TemporalType.DATE)
    private Date fechaF;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "importe_total")
    private Double importeTotal;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contrato")
    private Integer idContrato;
    @JoinColumn(name = "cont_id_auto", referencedColumnName = "id_auto")
    @ManyToOne(optional = false)
    private Autos contIdAuto;
    @JoinColumn(name = "cont_id_chof", referencedColumnName = "id_chofer")
    @ManyToOne
    private Choferes contIdChof;
    @JoinColumn(name = "cont_id_forma_pago", referencedColumnName = "id_pago")
    @ManyToOne(optional = false)
    private FormaPago contIdFormaPago;
    @JoinColumn(name = "cont_id_tur", referencedColumnName = "id_tur")
    @ManyToOne(optional = false)
    private Turista contIdTur;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private Usuarios idUsuario;

    public Contratos() {
    }

    public Contratos(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Contratos(Integer idContrato, Date fechaI, Date fechaF) {
        this.idContrato = idContrato;
        this.fechaI = fechaI;
        this.fechaF = fechaF;
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
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

    public Double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Autos getContIdAuto() {
        return contIdAuto;
    }

    public void setContIdAuto(Autos contIdAuto) {
        this.contIdAuto = contIdAuto;
    }

    public Choferes getContIdChof() {

        return contIdChof;
    }

    public Choferes getChofer() {
        if (contIdChof == null) {
            Choferes c = new Choferes();
            c.setNumeroId("-");
            return c;
        }
        return contIdChof;
    }

    public void setContIdChof(Choferes contIdChof) {
        this.contIdChof = contIdChof;
    }

    public FormaPago getContIdFormaPago() {
        return contIdFormaPago;
    }

    public void setContIdFormaPago(FormaPago contIdFormaPago) {
        this.contIdFormaPago = contIdFormaPago;
    }

    public Turista getContIdTur() {
        return contIdTur;
    }

    public void setContIdTur(Turista contIdTur) {
        this.contIdTur = contIdTur;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaEntregaAsString() {
        if (this.fechaEntrega == null) {
            return "-";
        }
        return fechaEntrega.toString();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContrato != null ? idContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contratos)) {
            return false;
        }
        Contratos other = (Contratos) object;
        if ((this.idContrato == null && other.idContrato != null) || (this.idContrato != null && !this.idContrato.equals(other.idContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModelLayer.Contratos[ idContrato=" + idContrato + " ]";
    }

    @Override
    public Integer getId() {
        return this.idContrato;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
