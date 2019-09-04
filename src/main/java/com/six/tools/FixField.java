package com.six.tools;

public class FixField {
    private final int tag;
    private final String name;

    public FixField(int tag, String name) {
        this.tag = tag;
        this.name = name;
    }

    public int tag() {
        return tag;
    }

    public String name() {
        return name;
    }
}
