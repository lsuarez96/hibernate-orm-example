/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer.Views;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yordanka
 */
@Entity
@Table(name = "contrato_por_modelo_marca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoPorModeloMarca.findAll", query = "SELECT c FROM ContratoPorModeloMarca c")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByNombreMarca", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.nombreMarca = :nombreMarca")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByNombreModelo", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.nombreModelo = :nombreModelo")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByTotalCarrosMarMod", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.totalCarrosMarMod = :totalCarrosMarMod")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByTotalDiasAlquilado", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.totalDiasAlquilado = :totalDiasAlquilado")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByImporteTarjeta", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.importeTarjeta = :importeTarjeta")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByImporteCheque", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.importeCheque = :importeCheque")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByImporteEfectivo", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.importeEfectivo = :importeEfectivo")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByCalcularImporteMarca", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.calcularImporteMarca = :calcularImporteMarca")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByImporteTotal", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.importeTotal = :importeTotal")
    , @NamedQuery(name = "ContratoPorModeloMarca.findByIdView", query = "SELECT c FROM ContratoPorModeloMarca c WHERE c.idView = :idView")})
public class ContratoPorModeloMarca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nombre_marca")
    private String nombreMarca;
    @Size(max = 1024)
    @Column(name = "nombre_modelo")
    private String nombreModelo;
    @Column(name = "total_carros_mar_mod")
    private Integer totalCarrosMarMod;
    @Column(name = "total_dias_alquilado")
    private Integer totalDiasAlquilado;
    @Size(max = 2147483647)
    @Column(name = "importe_tarjeta")
    private String importeTarjeta;
    @Size(max = 2147483647)
    @Column(name = "importe_cheque")
    private String importeCheque;
    @Size(max = 2147483647)
    @Column(name = "importe_efectivo")
    private String importeEfectivo;
    @Size(max = 2147483647)
    @Column(name = "calcular_importe_marca")
    private String calcularImporteMarca;
    @Size(max = 2147483647)
    @Column(name = "importe_total")
    private String importeTotal;
    @Column(name = "id_view")
    @Id
    private BigInteger idView;

    public ContratoPorModeloMarca() {
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public Integer getTotalCarrosMarMod() {
        return totalCarrosMarMod;
    }

    public void setTotalCarrosMarMod(Integer totalCarrosMarMod) {
        this.totalCarrosMarMod = totalCarrosMarMod;
    }

    public Integer getTotalDiasAlquilado() {
        return totalDiasAlquilado;
    }

    public void setTotalDiasAlquilado(Integer totalDiasAlquilado) {
        this.totalDiasAlquilado = totalDiasAlquilado;
    }

    public String getImporteTarjeta() {
        return importeTarjeta;
    }

    public void setImporteTarjeta(String importeTarjeta) {
        this.importeTarjeta = importeTarjeta;
    }

    public String getImporteCheque() {
        return importeCheque;
    }

    public void setImporteCheque(String importeCheque) {
        this.importeCheque = importeCheque;
    }

    public String getImporteEfectivo() {
        return importeEfectivo;
    }

    public void setImporteEfectivo(String importeEfectivo) {
        this.importeEfectivo = importeEfectivo;
    }

    public String getCalcularImporteMarca() {
        return calcularImporteMarca;
    }

    public void setCalcularImporteMarca(String calcularImporteMarca) {
        this.calcularImporteMarca = calcularImporteMarca;
    }

    public String getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(String importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigInteger getIdView() {
        return idView;
    }

    public void setIdView(BigInteger idView) {
        this.idView = idView;
    }

}
