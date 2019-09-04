package com.six.tools;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class MtfConfigGenerator {
    public static void main(String... args) throws IOException {
        val dictionaries = Arrays.stream(FixMessageFormats.values())
                .map(QuickFixDictionary::new)
                .collect(Collectors.toList());

        String htmlContent = html.MtfConfigHelper.render(
                dictionaries.stream().collect(Collectors.toMap(QuickFixDictionary::fixFormat, QuickFixDictionary::fields)),
                Arrays.stream(FixMessageFormats.values()).map(FixMessageFormats::displayName).collect(Collectors.toList())
        ).body();

        Files.writeString(Paths.get("MtfConfigHelper.html"), htmlContent, StandardCharsets.UTF_8);
    }
}
