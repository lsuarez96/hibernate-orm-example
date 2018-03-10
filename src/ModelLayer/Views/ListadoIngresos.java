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
@Table(name = "listado_ingresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListadoIngresos.findAll", query = "SELECT l FROM ListadoIngresos l")
    , @NamedQuery(name = "ListadoIngresos.findByAnno", query = "SELECT l FROM ListadoIngresos l WHERE l.anno = :anno")
    , @NamedQuery(name = "ListadoIngresos.findByIngresoAnno", query = "SELECT l FROM ListadoIngresos l WHERE l.ingresoAnno = :ingresoAnno")
    , @NamedQuery(name = "ListadoIngresos.findByMes", query = "SELECT l FROM ListadoIngresos l WHERE l.mes = :mes")
    , @NamedQuery(name = "ListadoIngresos.findByIngresoMes", query = "SELECT l FROM ListadoIngresos l WHERE l.ingresoMes = :ingresoMes")
    , @NamedQuery(name = "ListadoIngresos.findByIdView", query = "SELECT l FROM ListadoIngresos l WHERE l.idView = :idView")})
public class ListadoIngresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "anno")
    private Integer anno;
    @Size(max = 2147483647)
    @Column(name = "ingreso_anno")
    private String ingresoAnno;
    @Size(max = 2147483647)
    @Column(name = "mes")
    private String mes;
    @Size(max = 2147483647)
    @Column(name = "ingreso_mes")
    private String ingresoMes;
    @Column(name = "id_view")
    @Id
    private BigInteger idView;

    public ListadoIngresos() {
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public String getIngresoAnno() {
        return ingresoAnno;
    }

    public void setIngresoAnno(String ingresoAnno) {
        this.ingresoAnno = ingresoAnno;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getIngresoMes() {
        return ingresoMes;
    }

    public void setIngresoMes(String ingresoMes) {
        this.ingresoMes = ingresoMes;
    }

    public BigInteger getIdView() {
        return idView;
    }

    public void setIdView(BigInteger idView) {
        this.idView = idView;
    }

}
