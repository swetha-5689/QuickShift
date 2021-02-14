package com.quickshift.quickshiftbackend.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Nurse {

    @NotNull
    @Size(min = 1, max = 120)
    @Pattern(regexp = "^(?!\\s).*(?<!\\s)$", message = "Name should not contain any leading or trailing whitespaces")
    private String name;



    @NotNull
    @Size(min = 1, max = 8)
    @Pattern(regexp = "^(?!\\s).*(?<!\\s)$", message = "Name should not contain any leading or trailing whitespaces")
    private String shortId;

    @Override
    public String toString() {
        return name;
    }

    private Set<Skill> skillProficiencySet = null;

    public Set<Skill> getSkillProficiencySet() {
        return skillProficiencySet;
    }

    public void setSkillProficiencySet(Set<Skill> skillProficiencySet) {
        this.skillProficiencySet = skillProficiencySet;
    }

    public Nurse(@NotNull @Size(min = 1, max = 120) @Pattern(regexp = "^(?!\\s).*(?<!\\s)$",
            message = "Name should not contain any leading or trailing whitespaces") String name, Set<Skill> skillProficiencySet) {
        this.name = name;
        this.skillProficiencySet = skillProficiencySet;
    }

    public static String generateShortIdFromName(String name) {
        return Arrays.stream(name.split(" ")) // Separate name where there spaces ("Amy Cole" -> ["Amy", "Cole])
                .limit(3) // Limit to the first three parts
                .map(s -> s.substring(0, 1)) // Get the first character of the part
                .collect(Collectors.joining("")); // Join the parts together
    }

    public Nurse(String name) {
        this.name = name;
        this.shortId = generateShortIdFromName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortId() {
        return shortId;
    }
}
