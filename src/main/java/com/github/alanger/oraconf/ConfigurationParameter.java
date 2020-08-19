package com.github.alanger.oraconf;

/**
 *
 * @author mulander
 */
public interface ConfigurationParameter {

    /**
     * Adds a parameter to the list of parameters
     * 
     * @param p
     *            a new Parameter
     * @return true if correctly added
     */
    boolean addValue(ConfigurationParameter p);

    /**
     * Returns the name of the parameter
     * 
     * @return the name of the parameter
     */
    String getName();

    /**
     * Get's the value of this parameter
     */
    String getValue();

    /**
     * Returns an iterator for the list of parameters
     * 
     * @example for(Parameter p : params) { do something with p }
     * @return an iterator over the list of parameters
     */
    Iterable<ConfigurationParameter> getValues();

    /**
     * Removes a parameter from the list of parameters (values)
     * 
     * @param p
     *            Parameter to remove
     * @return true if the item was removed
     */
    boolean removeValue(ConfigurationParameter p);

    /**
     * Sets the parameter name
     * 
     * @param name
     *            new parameter name
     */
    void setName(String name);

    /**
     * Set's the value of this parameter
     * 
     * @param value
     *            new parameter String value
     */
    void setValue(String value);

    /**
     * Serialize TNS parameters to JSON format
     * 
     * @return json as string
     */
    String toJson();

    @Override
    String toString();

}
