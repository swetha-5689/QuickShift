package com.quickshift.quickshiftbackend.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Skill {


    @NotNull
    @Size(min = 1, max = 120)
    @Pattern(regexp = "^(?!\\s).*(?<!\\s)$", message = "Name should not contain any leading or trailing whitespaces")
    private String name;

    public Skill() {    }

    public Skill(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
