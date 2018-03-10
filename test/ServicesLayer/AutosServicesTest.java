/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Autos;
import ModelLayer.Situaciones;
import VisualLayer.MainClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Yordanka
 */
public class AutosServicesTest {

    private AutosServices instance;
    private Autos insertInst;

    public AutosServicesTest() {
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
        instance = ServicesLocator.getServicesLocatorInstance().getAutosServices();
        insertInst = new Autos();
        insertInst.setChapa("t423k8");
        insertInst.setColor("azul");
        insertInst.setIdModeloAuto(ServicesLocator.getServicesLocatorInstance().getModeloServices().findModelos(2));
        insertInst.setIdSituacionAuto(new Situaciones(6, "libre"));
        insertInst.setKilometros(0);
        try {
            instance.create(insertInst);
            insertInst.setIdAuto(instance.findCarByPlate("t423k8").getId());
        } catch (Exception ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        instance = null;
        insertInst = null;
        try {
            Connection con = ServicesLocator.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete from autos where chapa=?");
            ps.setString(1, "t423k8");
            ps.executeQuery();
            con.close();

        } catch (SQLException ex) {
            // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of create method, of class AutosServices.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateInsertSameElementTwice() throws Exception {
        System.out.println("create");
        int initCarCount = instance.getAutosCount();
        //instance.create(insertInst);
        // assertTrue("Fail to insert", initCarCount < instance.getAutosCount());
        boolean exceptTrown = false;
        try {
            initCarCount = instance.getAutosCount();
            instance.create(insertInst);
        } catch (Exception e) {
            exceptTrown = true;
        }
        assertTrue(exceptTrown && initCarCount == instance.getAutosCount());

        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of edit method, of class AutosServices.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        int id = instance.findCarByPlate(insertInst.getChapa()).getId();
        insertInst.setColor("verde");
        insertInst.setIdAuto(id);
        instance.edit(insertInst);
        assertTrue("verde".equals(instance.findAutos(insertInst.getId()).getColor()));
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class AutosServices.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = insertInst.getId();
        int cant = instance.getAutosCount();
        instance.destroy(id);
        Assert.assertTrue(cant > instance.getAutosCount());
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class AutosServices.
     */
    @Test
    public void testDestroyNonExistingElement() throws Exception {
        System.out.println("destroy");
        Integer id = insertInst.getId();
        int cant = instance.getAutosCount();
        boolean exceptTrown = false;
        if (instance.findAutos(id) != null) {
            try {
                Connection con = ServicesLocator.getConnection();
                PreparedStatement ps = con.prepareStatement("Delete from autos where chapa=?");
                ps.setString(1, "t423k8");
                ps.executeQuery();
                con.close();

            } catch (SQLException ex) {
                // Logger.getLogger(AutosServicesTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            cant = instance.getAutosCount();
        }
        try {
            instance.destroy(id);
        } catch (Exception e) {
            exceptTrown = true;
        }
        Assert.assertTrue(exceptTrown && cant == instance.getAutosCount());
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

}
