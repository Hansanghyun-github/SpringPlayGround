package com.example.springplayground.util;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

public class YamlPropertyReader {

    private final Map<String, Object> properties;

    public YamlPropertyReader(String yamlFilePath) {
        Yaml yaml = new Yaml();
        try (Reader yamlFile = new FileReader(yamlFilePath)) {
            properties = yaml.load(yamlFile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file: " + yamlFilePath, e);
        }
    }

    public Object getProperty(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = properties;
        Object value = null;

        for (String k : keys) {
            value = currentMap.get(k);
            if (value instanceof Map) {
                currentMap = (Map<String, Object>) value;
            } else {
                break;
            }
        }
        return value;
    }

}
