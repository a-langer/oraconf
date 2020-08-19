package com.github.alanger.oraconf;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a configuration file parameter key-value pair.
 * 
 * @author mulander
 */
public class Parameter implements ConfigurationParameter {
    /**
     * Parameter name
     * 
     * @example (test=val)
     * @example Parameter.name == "test"
     */
    private String name;
    /**
     * The value of this parameter
     * 
     * @example (test=val)
     * @example Parameter.value == "val"
     */
    private String value;
    /**
     * List of parameters
     */
    private List<ConfigurationParameter> values = new ArrayList<ConfigurationParameter>();

    /**
     * Creates a new parameter instance
     */
    public Parameter() {
    }

    /**
     * Creates a new parameter with the given name
     * 
     * @param name
     *            name of the parameter
     */
    public Parameter(String name) {
        this.name = name;
    }

    /**
     * Creates a new parameter with a single value
     * 
     * @param name
     *            Name of the parameter
     * @param value
     *            String value of the parameter
     */
    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Creates a parameter with a list of parameters as it's value
     * 
     * @param name
     *            Name of the parameter
     * @param values
     *            A list of parameters grouped under this name
     */
    public Parameter(String name, List<ConfigurationParameter> values) {
        this.name = name;
        this.values = values;
    }

    /**
     * Returns the name of the parameter
     * 
     * @return the name of the parameter
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the parameter name
     * 
     * @param name
     *            new parameter name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get's the value of this parameter
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * Set's the value of this parameter
     * 
     * @param value
     *            new parameter String value
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Adds a parameter to the list of parameters
     * 
     * @param p
     *            a new Parameter
     * @return true if correctly added
     */
    @Override
    public boolean addValue(ConfigurationParameter p) {
        return values.add(p);
    }

    /**
     * Removes a parameter from the list of parameters (values)
     * 
     * @param p
     *            Parameter to remove
     * @return true if the item was removed
     */
    @Override
    public boolean removeValue(ConfigurationParameter p) {
        return this.values.remove(p);
    }

    /**
     * Returns an iterator for the list of parameters
     * 
     * @example for(Parameter p : params) { do something with p }
     * @return an iterator over the list of parameters
     */
    @Override
    public Iterable<ConfigurationParameter> getValues() {
        return (Iterable<ConfigurationParameter>) this.values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append(" = ");
        if (this.getValue() != null) {
            sb.append(this.getValue());
        } else {
            for (ConfigurationParameter p : this.getValues()) {
                sb.append("(").append(p.toString()).append(")");
            }
        }
        return sb.toString();
    }

    @Override
    public String toJson() {
        return toJson(this, 0);
    }

    public static String toJson(ConfigurationParameter p, int depth) {
        StringBuilder sb = new StringBuilder();
        indent(sb, depth);
        sb.append("{").append("\n");
        indent(sb, depth);
        sb.append("  ");
        appendJsonValue(sb, p.getName());
        sb.append(" : ");

        if (p.getValue() != null) {
            appendJsonValue(sb, p.getValue());
        } else {
            int i = 0;

            sb.append("\n");
            indent(sb, depth + 1);
            sb.append("[");

            depth++;
            for (ConfigurationParameter cp : p.getValues()) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("\n");
                sb.append(toJson(cp, depth + 1));
                i++;
            }
            depth--;

            sb.append("\n");
            indent(sb, depth + 1);
            sb.append("]");
        }
        sb.append("\n");
        indent(sb, depth);
        sb.append("}");
        return sb.toString();
    }

    private static void appendJsonValue(StringBuilder sb, String text) {
        sb.append("\"");

        for (int i = 0, n = text.length(); i < n; i++) {
            char c = text.charAt(i);

            if (c == '"' || c == '\\') {
                sb.append("\\" + c);
            } else if (c == '\b') {
                sb.append("\\b");
            } else if (c == '\f') {
                sb.append("\\f");
            } else if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else if (c == '\t') {
                sb.append("\\t");
            } else {
                sb.append(c);
            }
        }

        sb.append("\"");
    }

    private static void indent(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
    }
}
