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
@Table(name = "listado_chofer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListadoChofer.findAll", query = "SELECT l FROM ListadoChofer l")
    , @NamedQuery(name = "ListadoChofer.findByNumeroId", query = "SELECT l FROM ListadoChofer l WHERE l.numeroId = :numeroId")
    , @NamedQuery(name = "ListadoChofer.findByNombre", query = "SELECT l FROM ListadoChofer l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "ListadoChofer.findByApellidos", query = "SELECT l FROM ListadoChofer l WHERE l.apellidos = :apellidos")
    , @NamedQuery(name = "ListadoChofer.findByDireccion", query = "SELECT l FROM ListadoChofer l WHERE l.direccion = :direccion")
    , @NamedQuery(name = "ListadoChofer.findByCategoria", query = "SELECT l FROM ListadoChofer l WHERE l.categoria = :categoria")
    , @NamedQuery(name = "ListadoChofer.findByCantCarrosManejadosChofer", query = "SELECT l FROM ListadoChofer l WHERE l.cantCarrosManejadosChofer = :cantCarrosManejadosChofer")
    , @NamedQuery(name = "ListadoChofer.findByIdView", query = "SELECT l FROM ListadoChofer l WHERE l.idView = :idView")})
public class ListadoChofer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "numero_id")
    private String numeroId;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 2147483647)
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "cant_carros_manejados_chofer")
    private Integer cantCarrosManejadosChofer;
    @Column(name = "id_view")
    @Id
    private Integer idView;

    public ListadoChofer() {
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCantCarrosManejadosChofer() {
        return cantCarrosManejadosChofer;
    }

    public void setCantCarrosManejadosChofer(Integer cantCarrosManejadosChofer) {
        this.cantCarrosManejadosChofer = cantCarrosManejadosChofer;
    }

    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

}
