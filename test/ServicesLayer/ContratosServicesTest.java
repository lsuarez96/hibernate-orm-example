/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Autos;
import ModelLayer.Contratos;
import ModelLayer.Situaciones;
import ModelLayer.Turista;
import VisualLayer.MainClass;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Yordanka
 */
public class ContratosServicesTest {

    private AutosServices as;
    private Autos a;
    private Turista t;
    private TuristaServices ts;
    private Contratos c;
    private ContratosServices cs;

    public ContratosServicesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        MainClass.setLoggedUsser(ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName("test-user"));
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete from autos where chapa=?");
            ps.setString(1, "t423k8");
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        as = ServicesLocator.getServicesLocatorInstance().getAutosServices();
        a = new Autos();
        a.setChapa("t423k8");
        a.setColor("azul");
        a.setIdModeloAuto(ServicesLocator.getServicesLocatorInstance().getModeloServices().findModelos(2));
        a.setIdSituacionAuto(new Situaciones(6, "libre"));
        a.setKilometros(0);
        try {
            as.create(a);
            a.setIdAuto(as.findCarByPlate("t423k8").getId());
        } catch (Exception ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ts = ServicesLocator.getServicesLocatorInstance().getTuristaServices();
        t = new Turista();
        t.setPasaporte("tid1234");
        t.setApellidos("Prueba");
        t.setNombre("Prueba");
        t.setEdad(20);
        t.setSexo("masculino");
        t.setTelefono("123456789");
        t.setTurIdPais(ServicesLocator.getServicesLocatorInstance().getPaisServices().findPais(54));
        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete from turista where pasaporte=?");
            ps.setString(1, "tid1234");
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Insert into turista(pasaporte,nombre,apellidos,edad,tur_id_pais,telefono,sexo) "
                    + "values(?,?,?,?,?,?,?)");
            ps.setString(1, "tid1234");
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getApellidos());
            ps.setInt(4, t.getEdad());
            ps.setInt(5, t.getTurIdPais().getId());
            ps.setString(6, t.getTelefono());
            ps.setString(7, t.getSexo());
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        t.setIdTur(getTuristIdByPass("tid1234"));
        cs = new ContratosServices();
        c = new Contratos();
        c.setContIdAuto(a);
        c.setContIdChof(null);
        c.setContIdFormaPago(ServicesLocator.getServicesLocatorInstance().getFormaPagoServices().findFormaPago(4));
        c.setContIdTur(t);
        c.setFechaEntrega(null);
        c.setFechaF(new Date(2017, 12, 03));
        c.setFechaI(new Date(2017, 06, 30));
        c.setIdUsuario(ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName("test-user"));
        try {
            cs.create(c);
        } catch (Exception ex) {
            Logger.getLogger(ContratosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @After
    public void tearDown() {
        as = null;
        a = null;
        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete from contratos where cont_id_tur=?, fecha_i=?,cont_id_auto=?");
            ps.setString(3, "t423k8");
            ps.setString(1, t.getPasaporte());
            ps.setDate(2, new Date(2017, 06, 30));
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete from autos where chapa=?");
            ps.setString(1, "t423k8");
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete from turista where pasaporte=?");
            ps.setString(1, "tid1234");
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of create method, of class ContratosServices.
     */
    @Test
    public void testCreateTwiceSameRecord() throws Exception {
        System.out.println("create");
        Contratos contratos = c;
        ContratosServices instance = cs;
        int count = cs.getContratosCount();
        boolean except = false;
        try {
            instance.create(contratos);
        } catch (Exception e) {
            except = true;
        }
        assertTrue(except && count == cs.getContratosCount());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class ContratosServices.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Contratos contratos = c;
        ContratosServices instance = cs;
        c.setFechaEntrega(new Date(2017, 8, 1));
        instance.edit(contratos);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        assertTrue(c.getFechaEntrega() != cs.findContratos(c.getId()).getFechaEntrega());
    }

    /**
     * Test of destroy method, of class ContratosServices.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        ContratosServices instance = new ContratosServices();
        //instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    private int getTuristIdByPass(String pass) {
        for (Turista tc : ts.findTuristaEntities()) {
            if (tc.getPasaporte().equals(pass)) {
                return tc.getId();
            }
        }
        return -1;
    }
}
