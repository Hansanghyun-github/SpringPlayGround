package com.example.springplayground.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class LocalConfigFileUtils {
    private static final YamlPropertyReader yamlPropertyReader;

    static {
        Enumeration<URL> resources = null;
        try {
            resources = LocalConfigFileUtils.class.getClassLoader().getResources("application-local.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        yamlPropertyReader = new YamlPropertyReader(resources.nextElement().getPath());

    }

    public static Object getProperty(String key) {
        return yamlPropertyReader.getProperty(key);
    }


}
