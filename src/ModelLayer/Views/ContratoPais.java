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
@Table(name = "contrato_pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoPais.findAll", query = "SELECT c FROM ContratoPais c")
    , @NamedQuery(name = "ContratoPais.findByNombrePais", query = "SELECT c FROM ContratoPais c WHERE c.nombrePais = :nombrePais")
    , @NamedQuery(name = "ContratoPais.findByNombreMarca", query = "SELECT c FROM ContratoPais c WHERE c.nombreMarca = :nombreMarca")
    , @NamedQuery(name = "ContratoPais.findByNombreModelo", query = "SELECT c FROM ContratoPais c WHERE c.nombreModelo = :nombreModelo")
    , @NamedQuery(name = "ContratoPais.findByDiasAlquilado", query = "SELECT c FROM ContratoPais c WHERE c.diasAlquilado = :diasAlquilado")
    , @NamedQuery(name = "ContratoPais.findByDiasProrroga", query = "SELECT c FROM ContratoPais c WHERE c.diasProrroga = :diasProrroga")
    , @NamedQuery(name = "ContratoPais.findByImporteEfectivo", query = "SELECT c FROM ContratoPais c WHERE c.importeEfectivo = :importeEfectivo")
    , @NamedQuery(name = "ContratoPais.findByCalcularImporteMarca", query = "SELECT c FROM ContratoPais c WHERE c.calcularImporteMarca = :calcularImporteMarca")
    , @NamedQuery(name = "ContratoPais.findByIdView", query = "SELECT c FROM ContratoPais c WHERE c.idView = :idView")})
public class ContratoPais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nombre_pais")
    private String nombrePais;
    @Size(max = 2147483647)
    @Column(name = "nombre_marca")
    private String nombreMarca;
    @Size(max = 1024)
    @Column(name = "nombre_modelo")
    private String nombreModelo;
    @Column(name = "dias_alquilado")
    private Integer diasAlquilado;
    @Column(name = "dias_prorroga")
    private Integer diasProrroga;
    @Size(max = 2147483647)
    @Column(name = "importe_efectivo")
    private String importeEfectivo;
    @Size(max = 2147483647)
    @Column(name = "calcular_importe_marca")
    private String calcularImporteMarca;
    @Column(name = "id_view")
    @Id
    private BigInteger idView;

    public ContratoPais() {
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
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

    public Integer getDiasAlquilado() {
        return diasAlquilado;
    }

    public void setDiasAlquilado(Integer diasAlquilado) {
        this.diasAlquilado = diasAlquilado;
    }

    public Integer getDiasProrroga() {
        return diasProrroga;
    }

    public void setDiasProrroga(Integer diasProrroga) {
        this.diasProrroga = diasProrroga;
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

    public BigInteger getIdView() {
        return idView;
    }

    public void setIdView(BigInteger idView) {
        this.idView = idView;
    }

}
