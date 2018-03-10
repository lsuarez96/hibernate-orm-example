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
@Table(name = "listado_situaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListadoSituaciones.findAll", query = "SELECT l FROM ListadoSituaciones l")
    , @NamedQuery(name = "ListadoSituaciones.findByChapa", query = "SELECT l FROM ListadoSituaciones l WHERE l.chapa = :chapa")
    , @NamedQuery(name = "ListadoSituaciones.findByNombreMarca", query = "SELECT l FROM ListadoSituaciones l WHERE l.nombreMarca = :nombreMarca")
    , @NamedQuery(name = "ListadoSituaciones.findByTipoSituacion", query = "SELECT l FROM ListadoSituaciones l WHERE l.tipoSituacion = :tipoSituacion")
    , @NamedQuery(name = "ListadoSituaciones.findByEstaAlquilado", query = "SELECT l FROM ListadoSituaciones l WHERE l.estaAlquilado = :estaAlquilado")
    , @NamedQuery(name = "ListadoSituaciones.findByIdView", query = "SELECT l FROM ListadoSituaciones l WHERE l.idView = :idView")})
public class ListadoSituaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "chapa")
    private String chapa;
    @Size(max = 2147483647)
    @Column(name = "nombre_marca")
    private String nombreMarca;
    @Size(max = 2147483647)
    @Column(name = "tipo_situacion")
    private String tipoSituacion;
    @Size(max = 2147483647)
    @Column(name = "esta_alquilado")
    private String estaAlquilado;
    @Column(name = "id_view")
    @Id
    private BigInteger idView;

    public ListadoSituaciones() {
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public String getTipoSituacion() {
        return tipoSituacion;
    }

    public void setTipoSituacion(String tipoSituacion) {
        this.tipoSituacion = tipoSituacion;
    }

    public String getEstaAlquilado() {
        return estaAlquilado;
    }

    public void setEstaAlquilado(String estaAlquilado) {
        this.estaAlquilado = estaAlquilado;
    }

    public BigInteger getIdView() {
        return idView;
    }

    public void setIdView(BigInteger idView) {
        this.idView = idView;
    }

}
