/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Turista;
import VisualLayer.MainClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Yordanka
 */
public class TuristaServicesTest {

    private TuristaServices ts;
    private Turista t;

    public TuristaServicesTest() {
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
    }

    @After
    public void tearDown() {
        t = null;
        ts = null;
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
     * Test of create method, of class TuristaServices.
     */
    @Test
    public void testCreateTwiceTheSame() throws Exception {
        System.out.println("create");
        TuristaServices instance = ts;
        int actAmount = ts.getTuristaCount();
        boolean except = false;
        try {
            instance.create(t);
        } catch (Exception e) {
            except = true;
        }
        int aftAMount = ts.getTuristaCount();
        // TODO review the generated test code and remove the default call to fail.
        assertTrue("Insertion error", except && actAmount == aftAMount);
    }

    /**
     * Test of edit method, of class TuristaServices.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        t.setEdad(40);
        ts.edit(t);
        // TODO review the generated test code and remove the default call to fail.
        assertTrue(ts.findTurista(t.getId()).getEdad() == 40);
    }

    /**
     * Test of destroy method, of class TuristaServices.
     */
    @Test
    public void testDestroyUnexistingElement() throws Exception {
        System.out.println("destroy");
        Integer id = t.getId();
        TuristaServices instance = ts;
        instance.destroy(id);
        boolean except = false;
        int count = ts.getTuristaCount();
        try {
            instance.destroy(id);
        } catch (Exception e) {
            except = true;
        }
        assertTrue(except && count == ts.getTuristaCount());
        // TODO review the generated test code and remove the default call to fail.

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
