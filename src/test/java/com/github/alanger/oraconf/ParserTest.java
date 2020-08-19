package com.github.alanger.oraconf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mulander
 */
public class ParserTest {
    private final static int ENTRY_COUNT = 68;
    private static ConfigurationFile cFile = new ParameterFile("tnsnames.ora");

    public ParserTest() {
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
     * Test of parse method, of class Parser.
     */
    @Test
    public void testParse() throws Exception {
        System.out.println("parse");
        int count = 0;
        for (ConfigurationParameter cp : cFile.getParameters()) {
            assertNotNull(cp);
            count++;
        }
        assertEquals(ENTRY_COUNT, count);
        String expected = "D2 = (DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.51.107)(PORT = 1521)))(CONNECT_DATA = (SID = D2)(SERVER = DEDICATED)))";
        assertEquals(expected, cFile.findParameter(new Parameter("D2")).toString());
    }
}
