public class VehicleBuilder {

    private int wheels;
    private String engine;
    private int doors;
    private TERRAIN terrain;

    public VehicleBuilder() {
    }

    public VehicleBuilder withWheels(int wheels) {
        this.wheels = wheels;
        return this;
    }

    public VehicleBuilder withEngine(String engine) {
        this.engine = engine;
        return this;
    }

    public VehicleBuilder withDoors(int doors) {
        this.doors = doors;
        return this;
    }

    public VehicleBuilder drivesOn(TERRAIN terrain) {
        this.terrain = terrain;
        return this;
    }

    public Vehicle build() {
        return new Vehicle(wheels, engine, doors, terrain);
    }
}
