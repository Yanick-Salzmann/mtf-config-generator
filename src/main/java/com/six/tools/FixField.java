package com.six.tools;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class FixField {
    private final int tag;
    private final String name;
}
