package com.six.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import quickfix.Message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum FixMessageFormats {
    FIX_40,
    FIX_41,
    FIX_42,
    FIX_43,
    FIX_44,
    FIX_50,
    FIX_50_SP1,
    FIX_50_SP2,
    FIXT_11;

    private final String resourceName;
    private final String displayName;
    private final String dictionary;

    FixMessageFormats() {
        resourceName = String.format("/%s.xml", name().replace("_", ""));
        displayName = buildDisplayName();
        dictionary = loadDictionary();
    }

    private String buildDisplayName() {
        String[] parts = name().split("_");
        String version = String.format("%s v%c.%c", parts[0], parts[1].charAt(0), parts[1].charAt(1));
        if(parts.length > 2) {
            version += " " + Arrays.stream(parts).skip(2).collect(Collectors.joining(" "));
        }

        return version;
    }

    private String loadDictionary() {
        try(InputStream is = Message.class.getResourceAsStream(resourceName)) {
            if(is == null) {
                throw new FileNotFoundException(resourceName);
            }
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Error fetching qfix dictionary", ex);
        }
    }

    public String displayName() {
        return displayName;
    }

    public String dictionary() {
        return dictionary;
    }
}
