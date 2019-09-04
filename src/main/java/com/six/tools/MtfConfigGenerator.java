package com.six.tools;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class MtfConfigGenerator {
    public static void main(String... args) {
        val dictionaries = Arrays.stream(FixMessageFormats.values())
                .map(QuickFixDictionary::new)
                .collect(Collectors.toList());

        log.info("{}", dictionaries);
    }
}
