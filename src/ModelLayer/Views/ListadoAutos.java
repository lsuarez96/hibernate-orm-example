/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer.Views;

import java.io.Serializable;
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
@Table(name = "listado_autos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListadoAutos.findAll", query = "SELECT l FROM ListadoAutos l")
    , @NamedQuery(name = "ListadoAutos.findByChapa", query = "SELECT l FROM ListadoAutos l WHERE l.chapa = :chapa")
    , @NamedQuery(name = "ListadoAutos.findByNombreMarca", query = "SELECT l FROM ListadoAutos l WHERE l.nombreMarca = :nombreMarca")
    , @NamedQuery(name = "ListadoAutos.findByNombreModelo", query = "SELECT l FROM ListadoAutos l WHERE l.nombreModelo = :nombreModelo")
    , @NamedQuery(name = "ListadoAutos.findByColor", query = "SELECT l FROM ListadoAutos l WHERE l.color = :color")
    , @NamedQuery(name = "ListadoAutos.findByKilometros", query = "SELECT l FROM ListadoAutos l WHERE l.kilometros = :kilometros")
    , @NamedQuery(name = "ListadoAutos.findByIdView", query = "SELECT l FROM ListadoAutos l WHERE l.idView = :idView")})
public class ListadoAutos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "chapa")
    private String chapa;
    @Size(max = 2147483647)
    @Column(name = "nombre_marca")
    private String nombreMarca;
    @Size(max = 1024)
    @Column(name = "nombre_modelo")
    private String nombreModelo;
    @Size(max = 2147483647)
    @Column(name = "color")
    private String color;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kilometros")
    private Float kilometros;
    @Column(name = "id_view")
    @Id
    private Integer idView;

    public ListadoAutos() {
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

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getKilometros() {
        return kilometros;
    }

    public void setKilometros(Float kilometros) {
        this.kilometros = kilometros;
    }

    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

}
