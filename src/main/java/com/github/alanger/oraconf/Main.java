/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.alanger.oraconf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author mulander
 */
public class Main {
    public static void main(String... args) throws FileNotFoundException, IOException {
        String path = null;
        if (args != null && args.length > 0) {
            path = args[0];
        } else if (System.getProperty("oracle.net.tns_admin") != null) {
            path = System.getProperty("oracle.net.tns_admin") + File.separator + "tnsnames.ora";
        } else if (System.getenv("TNS_ADMIN") != null) {
            path = System.getenv("TNS_ADMIN") + File.separator + "tnsnames.ora";
        }

        if (path == null) {
            String message = "TNS path not specified! Provide path in command line, example: \n"
                    + "  java -jar oraconf*.jar /etc/tns_admin/tnsnames.ora > tnsnames.json \n" + "  or \n"
                    + "  java -Doracle.net.tns_admin=/etc/tns_dir -jar oraconf*.jar > tnsnames.json \n" + "  or \n"
                    + "  TNS_ADMIN=/etc/tns_dir \n" + "  java -jar oraconf*.jar > tnsnames.json";
            throw new IllegalStateException(message);
        }

        ConfigurationFile cFile = new Parser(new ParameterFile(path)).parse();
        System.out.println(cFile.toJson());
    }
}
