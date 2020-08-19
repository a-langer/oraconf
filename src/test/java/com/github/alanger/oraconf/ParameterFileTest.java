package com.github.alanger.oraconf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

/**
 *
 * @author mulander
 */
public class ParameterFileTest {
    private final static int ENTRY_COUNT = 68;

    private static ConfigurationFile cFile = new ParameterFile("tnsnames.ora");

    public ParameterFileTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        cFile = new Parser(cFile).parse();
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
     * Test of getFile method, of class ParameterFile.
     */
    @Test
    public void testGetFile() {
        System.out.println("getFile");
        File expResult = new File("tnsnames.ora");
        assertEquals(expResult, cFile.getFile());
        System.out.println("  " + cFile.getFile().getPath());
    }

    /**
     * Test of getParameters method, of class ParameterFile.
     */
    @Test
    public void testGetParameters() {
        System.out.println("getParameters");
        int count = 0;
        for (ConfigurationParameter cp : cFile.getParameters()) {
            assertNotNull(cp);
            count++;
        }
        assertEquals(ENTRY_COUNT, count);
        System.out.println("  " + count);
    }

    /**
     * Test of addParameter method, of class ParameterFile.
     */
    @Test
    public void testAddParameter() {
        System.out.println("addParameter");
        ConfigurationParameter p = new Parameter("ADD_TEST", "42");
        ConfigurationParameter expResult = p;
        cFile.addParameter(p);
        ConfigurationParameter result = cFile.findParameter(p);
        cFile.removeParameter(p);
        assertEquals(expResult, result);
        printParameter(result, "  ");
    }

    /**
     * Test of removeParameter method, of class ParameterFile.
     */
    @Test
    public void testRemoveParameter() {
        System.out.println("removeParameter");
        ConfigurationParameter p = new Parameter("REMOVE_TEST", "42");
        cFile.addParameter(p);
        ConfigurationParameter result = cFile.removeParameter(p);
        assertNotNull(result);
        assertNull(cFile.findParameter(p));
        printParameter(result, "  ");
    }

    /**
     * Test of findParameter method, of class ParameterFile.
     */
    @Test
    public void testFindParameter() {
        System.out.println("findParameter");
        ConfigurationParameter p = new Parameter("FIND_TEST", "42");
        cFile.addParameter(p);
        ConfigurationParameter expResult = p;
        ConfigurationParameter result = cFile.findParameter(p);
        cFile.removeParameter(p);
        assertEquals(expResult, result);
        printParameter(result, "  ");
    }

    @Test
    public void testFindParameterByName() {
        String name = "K2";
        System.out.println("testFindParameterByName");
        ConfigurationParameter expResult = cFile.findParameter(new Parameter(name));
        assertNotNull(expResult);
        ConfigurationParameter result = cFile.findParameter(name);
        assertEquals(expResult, result);
        printParameter(result, "  ");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testJsonValid() {
        Gson gson = new Gson();
        String name = "K2";
        System.out.println("testJsonValid");
        ConfigurationParameter tns = cFile.findParameter(name);

        Map<String, Object> map = gson.fromJson(tns.toJson(), Map.class);

        List<Map> ROOT = (List) map.get(tns.getName());

        List<Map> DESCRIPTION_LIST = (List) ROOT.get(0).get("DESCRIPTION_LIST");

        List<Map> DESCRIPTION_0 = (List) DESCRIPTION_LIST.get(0).get("DESCRIPTION");

        List<Map> ADDRESS_LIST_0 = (List) DESCRIPTION_0.get(0).get("ADDRESS_LIST");

        List<Map> ADDRESS_0 = (List) ADDRESS_LIST_0.get(0).get("ADDRESS");
        String PROTOCOL = (String) ADDRESS_0.get(0).get("PROTOCOL");
        String HOST = (String) ADDRESS_0.get(1).get("HOST");
        String PORT = (String) ADDRESS_0.get(2).get("PORT");
        System.out.println(String.format("  PROTOCOL: %s, HOST: %s, PORT: %s", PROTOCOL, HOST, PORT));
        assertEquals("TCP", PROTOCOL);
        assertEquals("172.20.51.137", HOST);
        assertEquals("1521", PORT);

        List<Map> ADDRESS_1 = (List) ADDRESS_LIST_0.get(1).get("ADDRESS");
        String PROTOCOL_1 = (String) ADDRESS_1.get(0).get("PROTOCOL");
        String HOST_1 = (String) ADDRESS_1.get(1).get("HOST");
        String PORT_1 = (String) ADDRESS_1.get(2).get("PORT");
        System.out.println(String.format("  PROTOCOL_1: %s, HOST_1: %s, PORT_1: %s", PROTOCOL_1, HOST_1, PORT_1));
        assertEquals("TCP", PROTOCOL_1);
        assertEquals("172.20.51.138", HOST_1);
        assertEquals("1522", PORT_1);

        List<Map> CONNECT_DATA_0 = (List) DESCRIPTION_0.get(1).get("CONNECT_DATA");
        String SID_0 = (String) CONNECT_DATA_0.get(0).get("SID");
        String SERVER_0 = (String) CONNECT_DATA_0.get(1).get("SERVER");
        System.out.println(String.format("  SID_0: %s, SERVER_0: %s", SID_0, SERVER_0));
        assertEquals("K2", SID_0);
        assertEquals("DEDICATED", SERVER_0);

        List<Map> DESCRIPTION_1 = (List) DESCRIPTION_LIST.get(1).get("DESCRIPTION");

        List<Map> ADDRESS_LIST_1 = (List) DESCRIPTION_1.get(0).get("ADDRESS_LIST");

        List<Map> ADDRESS_1_0 = (List) ADDRESS_LIST_1.get(0).get("ADDRESS");
        String PROTOCOL_1_0 = (String) ADDRESS_1_0.get(0).get("PROTOCOL");
        String HOST_1_0 = (String) ADDRESS_1_0.get(1).get("HOST");
        String PORT_1_0 = (String) ADDRESS_1_0.get(2).get("PORT");
        System.out.println(
                String.format("  PROTOCOL_1_0: %s, HOST_1_0: %s, PORT_1_0: %s", PROTOCOL_1_0, HOST_1_0, PORT_1_0));
        assertEquals("UDP", PROTOCOL_1_0);
        assertEquals("172.20.51.139", HOST_1_0);
        assertEquals("1523", PORT_1_0);

        List<Map> CONNECT_DATA_1 = (List) DESCRIPTION_1.get(1).get("CONNECT_DATA");
        String SID_1 = (String) CONNECT_DATA_1.get(0).get("SID");
        String SERVER_1 = (String) CONNECT_DATA_1.get(1).get("SERVER");
        System.out.println(String.format("  SID_1: %s, SERVER_1: %s", SID_1, SERVER_1));
        assertEquals("K2_1", SID_1);
        assertEquals("DEDICATED_1", SERVER_1);
    }

    @Test
    public void testMainClassFromPath() throws Exception {
        PrintStream original = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                // DO NOTHING
            }
        }));
        Main.main("tnsnames.ora");
        System.setOut(original);
    }

    @Test
    public void testMainClassFromProperties() throws Exception {
        PrintStream original = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                // DO NOTHING
            }
        }));
        System.setProperty("oracle.net.tns_admin", System.getenv("BASE_DIR"));
        Main.main(new String[] {});
        System.clearProperty("oracle.net.tns_admin");
        System.setOut(original);
    }

    @Test(expected = FileNotFoundException.class)
    public void testMainClassFromNotFound() throws Exception {
        Main.main("/file/Not/Exist/Path/tnsnames.ora");
    }

    @Test(expected = IllegalStateException.class)
    public void testMainClassFromNull() throws Exception {
        System.clearProperty("oracle.net.tns_admin");
        Main.main(new String[] {});
    }

    private void printParameter(ConfigurationParameter p, String indent) {
        System.out.println(String.format(indent + "name: %s, value: %s", p.getName(), p.getValue()));
        for (ConfigurationParameter cp : p.getValues()) {
            printParameter(cp, indent + "  ");
        }
    }

}
