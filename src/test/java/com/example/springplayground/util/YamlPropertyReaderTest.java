package com.example.springplayground.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class YamlPropertyReaderTest {
    @Test
    void YamlReadTest() throws Exception {
        // when
        Object property = LocalConfigFileUtils.getProperty("server.tomcat.max-connections");

        // then (have to check application-local.yml file's value)
        assertThat(property).isEqualTo(5);
    }

}