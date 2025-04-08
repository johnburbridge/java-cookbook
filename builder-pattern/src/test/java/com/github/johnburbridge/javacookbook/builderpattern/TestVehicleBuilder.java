package com.github.johnburbridge.javacookbook.builderpattern;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestVehicleBuilder {

    @Test
    void testBuildCar() {
        Vehicle car = new VehicleBuilder()
                .withWheels(4)
                .withEngine("electric")
                .withDoors(4)
                .drivesOn(TERRAIN.Land)
                .build();
        assertThat(car).extracting(
                        Vehicle::doors,
                        Vehicle::engine,
                        Vehicle::terrain,
                        Vehicle::wheels)
                .containsExactly(4, "electric", TERRAIN.Land, 4);
    }

    @Test
    void buildBike() {
        Vehicle bike = new VehicleBuilder()
                .withWheels(2)
                .drivesOn(TERRAIN.Land)
                .build();
        assertThat(bike).extracting(
                        Vehicle::doors,
                        Vehicle::engine,
                        Vehicle::terrain,
                        Vehicle::wheels)
                .containsExactly(0, null, TERRAIN.Land, 2);
    }
}
