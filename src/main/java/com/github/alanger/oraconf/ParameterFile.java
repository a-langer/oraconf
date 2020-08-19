package com.github.alanger.oraconf;

import java.io.File;
import java.util.HashMap;

/**
 *
 * @author mulander
 */
public class ParameterFile implements ConfigurationFile {
    /**
     * A hash of parsed parameters
     */
    private HashMap<String, ConfigurationParameter> parameters = new HashMap<String, ConfigurationParameter>();
    private HashMap<String, String> alias = new HashMap<String, String>();
    private HashMap<String, String> canonical = new HashMap<String, String>();
    private File cFile;

    public ParameterFile(String fileName) {
        this.cFile = new File(fileName);
    };

    @Override
    public File getFile() {
        return this.cFile;
    }

    @Override
    public Iterable<ConfigurationParameter> getParameters() {
        return parameters.values();
    }

    @Override
    public ConfigurationParameter addAliasedParameter(ConfigurationParameter p, String[] aliases) {
        String canonicalName = aliases[0];
        for (int i = 1; i < aliases.length; i++) {
            this.alias.put(aliases[i], canonicalName);
        }
        this.canonical.put(canonicalName, p.getName()); // contains comma separated aliases
        p.setName(canonicalName);
        return parameters.put(p.getName(), p);
    }

    @Override
    public ConfigurationParameter addParameter(ConfigurationParameter p) {
        this.canonical.put(p.getName(), p.getName());
        return parameters.put(p.getName(), p);
    }

    @Override
    public ConfigurationParameter removeParameter(ConfigurationParameter p) {
        if (this.isCanonical(p)) {
            String[] aliases = this.canonical.get(p.getName()).split(",");
            for (String a : aliases) {
                this.alias.remove(a);
            }
            this.canonical.remove(p.getName());
        }
        return parameters.remove(p.getName());
    }

    @Override
    public ConfigurationParameter removeParameter(String pName) {
        return removeParameter(new Parameter(pName));
    }

    @Override
    public boolean isCanonical(ConfigurationParameter p) {
        return this.canonical.containsKey(p.getName());
    }

    @Override
    public boolean isCanonical(String pName) {
        return this.canonical.containsKey(pName);
    }

    @Override
    public boolean isAlias(ConfigurationParameter p) {
        return this.alias.containsKey(p.getName());
    }

    @Override
    public boolean isAlias(String pName) {
        return this.alias.containsKey(pName);
    }

    @Override
    public ConfigurationParameter findParameter(ConfigurationParameter p) {
        String name = p.getName();
        if (this.isAlias(p)) {
            name = this.alias.get(name);
        }
        ConfigurationParameter cp = parameters.get(name);
        if (cp != null) {
            cp.setName(this.canonical.get(name));
        }
        return cp;
    }

    @Override
    public ConfigurationParameter findParameter(String pName) {
        return findParameter(new Parameter(pName));
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int i = 0;
        for (ConfigurationParameter cp : this.getParameters()) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("\n");
            sb.append(Parameter.toJson(cp, 1));
            i++;
        }
        sb.append("\n");
        sb.append("]");
        return sb.toString();
    }

}
