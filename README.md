[![Build Status](https://travis-ci.org/a-langer/oraconf.svg?branch=master)](https://travis-ci.org/a-langer/oraconf)
[![license](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/a-langer/oraconf/blob/master/LICENSE)
[![Maven JitPack](https://img.shields.io/github/tag/a-langer/oraconf.svg?label=maven)](https://jitpack.io/#a-langer/oraconf)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.a-langer/oraconf/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.a-langer/oraconf)

# Parser for tnsnames.ora files

Library `oraconf-<version>.jar` enables working with files in Oracle configuration format as described by [documentation][1]. 
This project is a fork of [oraconf][2].

# Supported features:

* Parsing tnsnames.ora files.
* Query a parsed file to retrieve a specific entry.
* Manipulating the parsed entries.
* Constructing new entries.
* Serializing parsed/new entries in a unified format that can be saved back to a file.
* Export to JSON format (including command line).
* No dependency on third-party libraries.

# Getting the library using Maven

Add this dependency to your `pom.xml` to reference the library:

```xml
<dependency>
    <groupId>com.github.a-langer</groupId>
    <artifactId>oraconf</artifactId>
    <version>0.0.1</version>
</dependency>
```

# Usage

Print all entries in a test.ora tnsnames file:

```java
import java.io.FileNotFoundException;
import java.io.IOException;
import com.github.alanger.oraconf.ConfigurationParameter;
import com.github.alanger.oraconf.ConfigurationFile;
import com.github.alanger.oraconf.Parser;
import com.github.alanger.oraconf.ParameterFile;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ConfigurationFile cFile = new Parser(new ParameterFile("test.ora")).parse();
        for(ConfigurationParameter cp : cFile.getParameters()) {
            System.out.println( cp.toString() );
        }
    }
}
```

Parse a tnsnames file and find a specific entry by parameter printing it to stdout:

```java
cFile = new Parser(cFile).parse();
System.out.println(cFile.findParameter(new Parameter("Area52")).toString());
```

Parse a tnsnames file and find a specific entry by name printing it to stdout:

```java
cFile = new Parser(cFile).parse();
System.out.println(cFile.findParameter("Area52").toString());
```

Parse a tnsnames file and find a specific entry by name printing it to stdout as JSON:

```java
cFile = new Parser(cFile).parse();
System.out.println(cFile.findParameter("Area52").toJson());
```

Export to JSON from command line using [oraconf-*.jar][8]:

```bash
java -jar ./oraconf-*.jar /etc/tns_admin/tnsnames.ora > tnsnames.json
```

# Related resources
* [oraconf][2] - Original project by 'mulander' on bitbucket.
* [orafile][3] - Java for dealing with Oracle SQL*Net .ora files.
* [oracle.net.nl.NLParamParser][5] - Class from [oracle jdbc driver][6] (see [example][7]).
* [TNSNamesReader][4] - Example of one java class for parsing .ora files.

[1]: https://docs.oracle.com/database/121/NETRF/tnsnames.htm#NETRF007
[2]: https://bitbucket.org/mulander/oraconf/wiki/Home
[3]: https://github.com/gtri/orafile
[4]: https://www.programmersought.com/article/647684118/
[5]: https://github.com/caot/ojdbc6-11.2.0.2.0.src/blob/master/src/oracle/net/jdbc/nl/NLParamParser.java 
[6]: https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc8
[7]: https://www.sql.ru/forum/598369/jdbc-poluchit-spisok-baz?mid=6223824#6223824
[8]: https://github.com/a-langer/oraconf/releases




