/*
 * Copyright (C) 2024 John Burbridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.github.johnburbridge.javacookbook.builderpattern;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TestVehicleBuilder {

    @Test
    void testBuildCar() {
        Vehicle car =
                new VehicleBuilder()
                        .withWheels(4)
                        .withEngine("electric")
                        .withDoors(4)
                        .drivesOn(TERRAIN.Land)
                        .build();
        assertThat(car)
                .extracting(Vehicle::doors, Vehicle::engine, Vehicle::terrain, Vehicle::wheels)
                .containsExactly(4, "electric", TERRAIN.Land, 4);
    }

    @Test
    void buildBike() {
        Vehicle bike = new VehicleBuilder().withWheels(2).drivesOn(TERRAIN.Land).build();
        assertThat(bike)
                .extracting(Vehicle::doors, Vehicle::engine, Vehicle::terrain, Vehicle::wheels)
                .containsExactly(0, null, TERRAIN.Land, 2);
    }
}
