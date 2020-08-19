package com.github.alanger.oraconf;

import java.io.File;

/**
 *
 * @author mulander
 */
public interface ConfigurationFile {

    public File getFile();

    public Iterable<ConfigurationParameter> getParameters();

    /**
     * Adds an aliased configuration parameter - multiple names pointing to a single parameter entry. The name of the
     * entry should be composed of comma separated aliases. The first aliases entry is treated as the canonical
     * parameter name. Aliased parameters are treated as one and changes applied to one of them are reflected to the
     * others.
     * 
     * @param entry
     *            Configuration parameter with details required to make a connection. It's name should be a comma
     *            separated list of all aliases.
     * @param aliases
     * @return the previous value associated with the canonical name, or null if there was no entry associated with that
     *         canonical name.
     */
    public ConfigurationParameter addAliasedParameter(ConfigurationParameter entry, String[] aliases);

    /**
     * Add a single non aliased parameter
     * 
     * @param p
     *            Configuration parameter with details required to make a connection
     * @return the previous value associated with the name, or null if there was no entry associated with that name.
     */
    public ConfigurationParameter addParameter(ConfigurationParameter p);

    /**
     * Remove a single non aliased parameter
     * 
     * @param p
     *            Parameter instance with at least it's name set
     * @return the removed value associated with the name, or null if there was no entry associated with that name.
     */
    public ConfigurationParameter removeParameter(ConfigurationParameter p);

    /**
     * Remove a single non aliased parameter
     * 
     * @param p
     *            A name of configuration parameter
     * @return the removed value associated with the name, or null if there was no entry associated with that name.
     */
    public ConfigurationParameter removeParameter(String pName);

    /**
     * Returns True if the parameter is a canonical (first) entry
     * 
     * @param p
     *            A configuration parameter
     * @return True if the parameter has aliases or is a sole entry
     */
    public boolean isCanonical(ConfigurationParameter p);

    /**
     * Returns True if the parameter is a canonical (first) entry
     * 
     * @param p
     *            A name of configuration parameter
     * @return True if the parameter has aliases or is a sole entry
     */
    public boolean isCanonical(String pName);

    /**
     * Returns True if the parameter is an alias of a canonical parameter.
     * 
     * @param p
     *            A configuration parameter
     * @return True if this parameter is an alias
     */
    public boolean isAlias(ConfigurationParameter p);

    /**
     * Returns True if the parameter is an alias of a canonical parameter.
     * 
     * @param p
     *            A name of configuration parameter
     * @return True if this parameter is an alias
     */
    public boolean isAlias(String pName);

    /**
     * Find a parameter by it's name. The query is performed by passing an instance of Parameter with a name. Additional
     * fields of the Parameter used for the query are ignored.
     * 
     * @param p
     *            Parameter instance with at least it's name set
     * @return a configuration parameter or null if not found
     */
    public ConfigurationParameter findParameter(ConfigurationParameter p);

    /**
     * Find a parameter by it's name. The query is performed by passing an name of Parameter.
     * 
     * @param p
     *            Name of Parameter
     * @return a configuration parameter or null if not found
     */
    public ConfigurationParameter findParameter(String pName);

    /**
     * Serialize all TNS file to JSON format
     * 
     * @return json as string
     */
    public String toJson();

}
