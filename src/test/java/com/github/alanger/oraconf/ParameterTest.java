package com.github.alanger.oraconf;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mulander
 */
public class ParameterTest {

    public ParameterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Parameter.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Parameter instance = new Parameter("some_name");
        String expResult = "some_name";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Parameter.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "test";
        Parameter instance = new Parameter("some_name");
        instance.setName(name);
        assertEquals(name, instance.getName());
    }

    /**
     * Test of getValue method, of class Parameter.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        Parameter instance = new Parameter("some_name", "42");
        String expResult = "42";
        String result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of setValue method, of class Parameter.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        String value = "24";
        Parameter instance = new Parameter("some_name", "42");
        instance.setValue(value);
        assertEquals(value, instance.getValue());
    }

    /**
     * Test of addValue method, of class Parameter.
     */
    @Test
    public void testAddValue() {
        System.out.println("addValue");
        ConfigurationParameter p = new Parameter("child");
        Parameter instance = new Parameter("root");
        ConfigurationParameter expResult = p;
        instance.addValue(p);
        Iterable<ConfigurationParameter> value = instance.getValues();
        ConfigurationParameter result = (ConfigurationParameter) value.iterator().next();
        assertEquals(expResult, result);
    }

    /**
     * Test of removeValue method, of class Parameter.
     */
    @Test
    public void testRemoveValue() {
        System.out.println("removeValue");
        ConfigurationParameter p = new Parameter("child");
        Parameter instance = new Parameter("root");
        instance.addValue(p);
        instance.removeValue(p);
        Iterable<ConfigurationParameter> value = instance.getValues();
        boolean expResult = false;
        assertEquals(expResult, value.iterator().hasNext());
    }

    /**
     * Test of getValues method, of class Parameter.
     */
    @Test
    public void testGetValues() {
        System.out.println("getValues");
        Parameter instance = new Parameter("root");
        Parameter child1 = new Parameter("child");
        Parameter child2 = new Parameter("child");
        assertEquals(false, instance.getValues().iterator().hasNext());
        instance.addValue(child1);
        instance.addValue(child2);
        Iterator<ConfigurationParameter> result = instance.getValues().iterator();
        assertEquals(child1, result.next());
        assertEquals(child2, result.next());
        assertEquals(false, result.hasNext());
    }

    /**
     * Test of toString method, of class Parameter.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Parameter instance = new Parameter("root");
        instance.addValue(new Parameter("child", "value"));
        String expResult = "root = (child = value)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
