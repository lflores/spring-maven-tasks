package com.triadsoft;

import software.amazon.awscdk.core.App;

import java.util.Arrays;

public class TasksInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        new TasksInfraStack(app, "tasks-infra-stack");

        app.synth();
    }
}
