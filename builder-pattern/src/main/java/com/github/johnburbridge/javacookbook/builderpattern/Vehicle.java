package com.github.johnburbridge.javacookbook.builderpattern;

public record Vehicle(
        int wheels,
        String engine,
        int doors,
        TERRAIN terrain
) {
}
