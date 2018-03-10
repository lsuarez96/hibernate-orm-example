/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Choferes;
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
public class ChoferesServicesTest {

    Choferes t;
    ChoferesServices ts;

    public ChoferesServicesTest() {
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

        ts = ServicesLocator.getServicesLocatorInstance().getChoferServices();
        t = new Choferes();
        t.setNumeroId("tid1234");
        t.setApellidos("Prueba");
        t.setNombre("Prueba");
        t.setDireccion("Cuba prueba");
        t.setCategoria(ServicesLocator.getServicesLocatorInstance().getCategoriaServices().findCategorias(5));
        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete from choferes where numero_id=?");
            ps.setString(1, "tid1234");
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ts.create(t);

        } catch (Exception ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        t.setIdChofer(getDriverIdByNId("tid1234"));
    }

    @After
    public void tearDown() {
        t = null;
        ts = null;
        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete from choferes where numero_id=?");
            ps.setString(1, "tid1234");
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of create method, of class ChoferesServices.
     */
    @Test
    public void testCreateTwiceTheSame() throws Exception {
        System.out.println("create");
        Choferes choferes = t;
        ChoferesServices instance = ts;
        boolean except = false;
        try {
            instance.create(choferes);
        } catch (Exception e) {
            except = true;
        }
        assertTrue(except);
// TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class ChoferesServices.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Choferes choferes = t;
        ChoferesServices instance = ts;
        choferes.setDireccion("Prueba");
        instance.edit(choferes);
        assertTrue("Prueba".equals(ts.findChoferes(t.getId()).getDireccion()));
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    public int getDriverIdByNId(String id) {
        for (Choferes c : ts.findChoferesEntities()) {
            if (c.getNumeroId().equals(id)) {
                return c.getId();
            }
        }
        return -1;
    }
}
