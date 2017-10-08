package com.nedogeek.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AggressorData {

    private Set<String> callers = new HashSet<>();
    private Set<String> raisers = new HashSet<>();
    private Set<String> threeBetters = new HashSet<>();

    public Set<String> getCallers() {
        return callers;
    }

    public void addCallers(String... callers) {
        this.callers.addAll(Arrays.asList(callers));
    }

    public Set<String> getRaisers() {
        return raisers;
    }

    public void addRaisers(String... raisers) {
        this.raisers.addAll(Arrays.asList(raisers));
    }

    public Set<String> getThreeBetters() {
        return threeBetters;
    }

    public void addThreeBetters(String... threeBetters) {
        this.threeBetters.addAll(Arrays.asList(threeBetters));
    }
}
