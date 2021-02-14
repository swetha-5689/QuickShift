package com.quickshift.quickshiftbackend.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

public class Spot {
    @NotNull
    @Size(min = 1, max = 120)
    @Pattern(regexp = "^(?!\\s).*(?<!\\s)$", message = "Name should not contain any leading or trailing whitespaces")
    private String name;

    @NotNull
    private Set<Skill> requiredSkillSet;

    public Spot() {
    }

    public Spot(String name, Set<Skill> requiredSkillSet) {
        this.name = name;
        this.requiredSkillSet = requiredSkillSet;
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

    public Set<Skill> getRequiredSkillSet() {
        return requiredSkillSet;
    }

    public void setRequiredSkillSet(Set<Skill> requiredSkillSet) {
        this.requiredSkillSet = requiredSkillSet;
    }
}
