/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Yordanka
 */
public class ServicesLocator {

    private AutosServices autosServices;
    private CategoriasServices categoriaServices;
    private ChoferesServices choferServices;
    private ContratosServices contratoServices;
    private FormaPagoServices formaPagoServices;
    private MarcasServices marcaServices;
    private ModelosServices modeloServices;
    private PaisServices paisServices;
    private RolServices rolServices;
    private ReportServices reportServices;
    private SituacionesServices situacionesServices;
    private TarifaServices tarifaServices;
    private TrazasServices trazaServices;
    private TuristaServices turistaServices;
    private UsuariosServices usuarioServices;
    private RolUsuarioServices rolUsuarioServices;
    private static ServicesLocator servicesLocatorInstance;

    private ServicesLocator() {

    }

    /**
     * @return the autosServices
     */
    public AutosServices getAutosServices() {
        if (autosServices == null) {
            autosServices = new AutosServices();
        }
        return autosServices;
    }

    /**
     * @return the categoriaServices
     */
    public CategoriasServices getCategoriaServices() {
        if (categoriaServices == null) {
            categoriaServices = new CategoriasServices();
        }
        return categoriaServices;
    }

    /**
     * @return the choferServices
     */
    public ChoferesServices getChoferServices() {
        if (choferServices == null) {
            choferServices = new ChoferesServices();
        }
        return choferServices;
    }

    /**
     * @return the contratoServices
     */
    public ContratosServices getContratoServices() {
        if (contratoServices == null) {
            contratoServices = new ContratosServices();
        }
        return contratoServices;
    }

    /**
     * @return the formaPagoServices
     */
    public FormaPagoServices getFormaPagoServices() {
        if (formaPagoServices == null) {
            formaPagoServices = new FormaPagoServices();
        }
        return formaPagoServices;
    }

    /**
     * @return the marcaServices
     */
    public MarcasServices getMarcaServices() {
        if (marcaServices == null) {
            marcaServices = new MarcasServices();
        }
        return marcaServices;
    }

    /**
     * @return the modeloServices
     */
    public ModelosServices getModeloServices() {
        if (modeloServices == null) {
            modeloServices = new ModelosServices();
        }
        return modeloServices;
    }

    /**
     * @return the paisServices
     */
    public PaisServices getPaisServices() {
        if (paisServices == null) {
            paisServices = new PaisServices();
        }
        return paisServices;
    }

    /**
     * @return the rolServices
     */
    public RolServices getRolServices() {
        if (rolServices == null) {
            rolServices = new RolServices();
        }
        return rolServices;
    }

    /**
     * @return the situacionesServices
     */
    public SituacionesServices getSituacionesServices() {
        if (situacionesServices == null) {
            situacionesServices = new SituacionesServices();
        }
        return situacionesServices;
    }

    /**
     * @return the tarifaServices
     */
    public TarifaServices getTarifaServices() {
        if (tarifaServices == null) {
            tarifaServices = new TarifaServices();
        }
        return tarifaServices;
    }

    /**
     * @return the trazaServices
     */
    public TrazasServices getTrazaServices() {
        if (trazaServices == null) {
            trazaServices = new TrazasServices();
        }
        return trazaServices;
    }

    /**
     * @return the turistaServices
     */
    public TuristaServices getTuristaServices() {
        if (turistaServices == null) {
            turistaServices = new TuristaServices();
        }
        return turistaServices;
    }

    /**
     * @return the usuarioServices
     */
    public UsuariosServices getUsuarioServices() {
        if (usuarioServices == null) {
            usuarioServices = new UsuariosServices();
        }
        return usuarioServices;
    }

    /**
     * @return the servicesLocatorInstance
     */
    public static ServicesLocator getServicesLocatorInstance() {
        if (servicesLocatorInstance == null) {
            servicesLocatorInstance = new ServicesLocator();
        }
        return servicesLocatorInstance;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/rentaCar", "postgres", "0000");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * @return the reportServices
     */
    public ReportServices getReportServices() {
        if (reportServices == null) {
            reportServices = new ReportServices();
        }
        return reportServices;
    }

    /**
     * @return the rolUsuarioServices
     */
    public RolUsuarioServices getRolUsuarioServices() {
        if (rolUsuarioServices == null) {
            rolUsuarioServices = new RolUsuarioServices();
        }
        return rolUsuarioServices;
    }
}
