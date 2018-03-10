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
@Table(name = "turistas_pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TuristasPais.findAll", query = "SELECT t FROM TuristasPais t")
    , @NamedQuery(name = "TuristasPais.findByNombrePais", query = "SELECT t FROM TuristasPais t WHERE t.nombrePais = :nombrePais")
    , @NamedQuery(name = "TuristasPais.findByPasaporte", query = "SELECT t FROM TuristasPais t WHERE t.pasaporte = :pasaporte")
    , @NamedQuery(name = "TuristasPais.findByNombre", query = "SELECT t FROM TuristasPais t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TuristasPais.findByApellidos", query = "SELECT t FROM TuristasPais t WHERE t.apellidos = :apellidos")
    , @NamedQuery(name = "TuristasPais.findByAutosAlquilados", query = "SELECT t FROM TuristasPais t WHERE t.autosAlquilados = :autosAlquilados")
    , @NamedQuery(name = "TuristasPais.findByImporteTotal", query = "SELECT t FROM TuristasPais t WHERE t.importeTotal = :importeTotal")
    , @NamedQuery(name = "TuristasPais.findByIdView", query = "SELECT t FROM TuristasPais t WHERE t.idView = :idView")})
public class TuristasPais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nombre_pais")
    private String nombrePais;
    @Size(max = 2147483647)
    @Column(name = "pasaporte")
    private String pasaporte;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "autos_alquilados")
    private Integer autosAlquilados;
    @Size(max = 2147483647)
    @Column(name = "importe_total")
    private String importeTotal;
    @Column(name = "id_view")
    @Id
    private BigInteger idView;

    public TuristasPais() {
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
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

    public Integer getAutosAlquilados() {
        return autosAlquilados;
    }

    public void setAutosAlquilados(Integer autosAlquilados) {
        this.autosAlquilados = autosAlquilados;
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
