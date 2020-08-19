package com.github.alanger.oraconf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

/**
 * 
 * @author mulander
 */
public class Parser {

    private ConfigurationFile cFile = null;
    private LinkedList<ConfigurationParameter> par = new LinkedList<ConfigurationParameter>();
    private boolean foundValue = false; // did we found a literal value
    private boolean isComment = false;
    private boolean beginningOfLine = true;

    Parser() {
    }

    public Parser(ConfigurationFile configFile) throws FileNotFoundException {
        this.cFile = configFile;
    }

    public ConfigurationFile parse() throws IOException {
        Reader cr = new FileReader(cFile.getFile());
        int ret; // return code for Reader
        char c; // converted to a character
        StringBuilder sb = new StringBuilder();

        while ((ret = cr.read()) != -1) { // read until end of line
            c = (char) ret;

            if (isComment(c))
                continue;
            if (c == ' ' || c == '\n' || c == '\r')
                continue;

            beginningOfLine = false;

            // process entry
            if (c == '(') {
                foundValue = false;
            } else if (c == ')') {
                ConfigurationParameter child = par.pop();
                if (foundValue) {
                    child.setValue(sb.toString());
                    sb = new StringBuilder();
                    foundValue = false;
                }
                par.getFirst().addValue(child);
                if (par.size() == 1) {
                    ConfigurationParameter entry = par.pop();
                    if (entry.getName().contains(",")) {
                        String[] aliases = entry.getName().split(",");
                        cFile.addAliasedParameter(entry, aliases);
                    } else {
                        cFile.addParameter(entry);
                    }
                    par.clear();
                }
            } else if (c == '=') {
                ConfigurationParameter child = new Parameter(sb.toString());

                par.push(child);

                sb = new StringBuilder();
                foundValue = true;
            } else {
                sb.append(c);
            }
        }
        cr.close();
        return cFile;
    }

    /**
     * Checks if the current state indicates that we are inside of a comment. Note that # is treated as a comment only
     * when being at the beginning of the line. Comments can be included using the pound sign # at the beginning of a
     * line. Anything following the sign to the end of the line is considered a comment. Note that (OBJECT=\#123) is a
     * valid entry and not a comment.
     * 
     * @param c
     * @return
     */
    private boolean isComment(char c) {
        if (isComment && c != '\n') {
            return true;
        } else if ((c == '#') && beginningOfLine) {
            // first char on line
            isComment = true; // enter comment mode
            beginningOfLine = false;
            return true;
        } else if (c == '\n') {
            // reset markers and comments on newline
            beginningOfLine = true;
            if (isComment) {
                isComment = false;
            }
        }
        return false;
    }
}
