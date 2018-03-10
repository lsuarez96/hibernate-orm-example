/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer.Views;

import java.io.Serializable;
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
@Table(name = "listado_contratos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListadoContratos.findAll", query = "SELECT l FROM ListadoContratos l")
    , @NamedQuery(name = "ListadoContratos.findByNombre", query = "SELECT l FROM ListadoContratos l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "ListadoContratos.findByNombreModelo", query = "SELECT l FROM ListadoContratos l WHERE l.nombreModelo = :nombreModelo")
    , @NamedQuery(name = "ListadoContratos.findByNombreMarca", query = "SELECT l FROM ListadoContratos l WHERE l.nombreMarca = :nombreMarca")
    , @NamedQuery(name = "ListadoContratos.findByChapa", query = "SELECT l FROM ListadoContratos l WHERE l.chapa = :chapa")
    , @NamedQuery(name = "ListadoContratos.findByTipoPago", query = "SELECT l FROM ListadoContratos l WHERE l.tipoPago = :tipoPago")
    , @NamedQuery(name = "ListadoContratos.findByFechaI", query = "SELECT l FROM ListadoContratos l WHERE l.fechaI = :fechaI")
    , @NamedQuery(name = "ListadoContratos.findByFechaF", query = "SELECT l FROM ListadoContratos l WHERE l.fechaF = :fechaF")
    , @NamedQuery(name = "ListadoContratos.findByCalcularDiasProrrogaContrato", query = "SELECT l FROM ListadoContratos l WHERE l.calcularDiasProrrogaContrato = :calcularDiasProrrogaContrato")
    , @NamedQuery(name = "ListadoContratos.findByHayChofer", query = "SELECT l FROM ListadoContratos l WHERE l.hayChofer = :hayChofer")
    , @NamedQuery(name = "ListadoContratos.findByImporteTotal", query = "SELECT l FROM ListadoContratos l WHERE l.importeTotal = :importeTotal")
    , @NamedQuery(name = "ListadoContratos.findByIdView", query = "SELECT l FROM ListadoContratos l WHERE l.idView = :idView")})
public class ListadoContratos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 1024)
    @Column(name = "nombre_modelo")
    private String nombreModelo;
    @Size(max = 2147483647)
    @Column(name = "nombre_marca")
    private String nombreMarca;
    @Size(max = 2147483647)
    @Column(name = "chapa")
    private String chapa;
    @Size(max = 2147483647)
    @Column(name = "tipo_pago")
    private String tipoPago;
    @Column(name = "fecha_i")
    @Temporal(TemporalType.DATE)
    private Date fechaI;
    @Column(name = "fecha_f")
    @Temporal(TemporalType.DATE)
    private Date fechaF;
    @Column(name = "calcular_dias_prorroga_contrato")
    private Integer calcularDiasProrrogaContrato;
    @Size(max = 2147483647)
    @Column(name = "hay_chofer")
    private String hayChofer;
    @Size(max = 2147483647)
    @Column(name = "importe_total")
    private String importeTotal;
    @Column(name = "id_view")
    @Id
    private Integer idView;

    public ListadoContratos() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
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

    public Integer getCalcularDiasProrrogaContrato() {
        return calcularDiasProrrogaContrato;
    }

    public void setCalcularDiasProrrogaContrato(Integer calcularDiasProrrogaContrato) {
        this.calcularDiasProrrogaContrato = calcularDiasProrrogaContrato;
    }

    public String getHayChofer() {
        return hayChofer;
    }

    public void setHayChofer(String hayChofer) {
        this.hayChofer = hayChofer;
    }

    public String getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(String importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

}
