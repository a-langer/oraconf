package com.github.alanger.oraconf;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class EntryAliasFileParseTest {

    private static ConfigurationFile cFile = new ParameterFile("tnsnames-aliases.ora");

    @BeforeClass
    public static void setUpClass() throws Exception {
        cFile = new Parser(cFile).parse();
    }

    @Test
    public void entryCount() throws Exception {
        // One entry 4 aliases. The initial contributed file assumed that
        // the library should parse 4 entries but that in my humble opinion
        // would be contrary to what most users think as there is only a single
        // physical entry in the file with 4 names attached.
        assertThat(Arrays.asList(cFile.getParameters()).size(), equalTo(1));
    }

    @Test
    public void listOfAliasesIsNotSingleName() throws Exception {
        ConfigurationParameter entry = cFile
                .findParameter(new Parameter("multi_a1,multi_a2,multi_a2.world,multi_a2.global.coms"));
        assertThat(entry, nullValue());
    }

    @Test
    public void canBeAccessedWithArbitraryAlias() throws Exception {
        ConfigurationParameter multiA1 = cFile.findParameter(new Parameter("multi_a1"));
        assertThat(multiA1, not(nullValue()));
    }

    @Test
    public void entryAliasesAreEqual() throws Exception {
        ConfigurationParameter multiA1 = cFile.findParameter(new Parameter("multi_a1"));
        ConfigurationParameter multiA2 = cFile.findParameter(new Parameter("multi_a2"));
        assertThat(multiA1.getValues(), equalTo(multiA2.getValues()));
    }

    @Test
    public void entryAliasesAreEqualStrings() throws Exception {
        ConfigurationParameter multiA1 = cFile.findParameter(new Parameter("multi_a1"));
        ConfigurationParameter multiA2 = cFile.findParameter(new Parameter("multi_a2"));
        assertThat(multiA1.toString(), equalTo(multiA2.toString()));
    }

    @Test
    public void entryAliasesContainAllAliasesInName() throws Exception {
        ConfigurationParameter multiA1 = cFile.findParameter(new Parameter("multi_a1"));
        ConfigurationParameter multiA2 = cFile.findParameter(new Parameter("multi_a2"));
        ConfigurationParameter multiA2World = cFile.findParameter(new Parameter("multi_a2.world"));
        ConfigurationParameter multiA2Global = cFile.findParameter(new Parameter("multi_a2.global.coms"));

        String aliases = "multi_a1,multi_a2,multi_a2.world,multi_a2.global.coms";

        assertThat(multiA1.getName(), equalTo(aliases));
        assertThat(multiA2.getName(), equalTo(aliases));
        assertThat(multiA2World.getName(), equalTo(aliases));
        assertThat(multiA2Global.getName(), equalTo(aliases));
    }

    @Test
    public void entryAliasesSustainInGetParameters() throws Exception {
        String aliases = "multi_a1,multi_a2,multi_a2.world,multi_a2.global.coms";

        for (ConfigurationParameter cp : cFile.getParameters()) {
            assertThat(cp.getName().toString(), equalTo(aliases));
        }
    }
}
