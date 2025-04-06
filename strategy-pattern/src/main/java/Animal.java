public class Animal {

    private final SoundStrategy soundStrategy;

    public Animal(SoundStrategy soundStrategy) {
        this.soundStrategy = soundStrategy;
    }

    public String makeSound() {
        return soundStrategy.makeSound();
    }
}
