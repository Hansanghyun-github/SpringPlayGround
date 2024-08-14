package com.example.springplayground.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class LocalConfigFileUtils {
    private static final YamlPropertyReader yamlPropertyReader;

    static {
        String path;
        try {
            path = LocalConfigFileUtils.class.getClassLoader().getResources("application-local.yml").nextElement().getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        yamlPropertyReader = new YamlPropertyReader(path);
    }

    private LocalConfigFileUtils() {
    }

    public static Object getProperty(String key) {
        return yamlPropertyReader.getProperty(key);
    }


}
