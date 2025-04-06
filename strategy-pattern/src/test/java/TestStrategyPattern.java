import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStrategyPattern {

    @Test
    void testBarkStrategy() {
        Animal dog = new Animal(new BarkStrategy());
        assertThat(dog.makeSound()).isEqualTo("woof");
    }

    @Test
    void testMeowStrategy() {
        Animal cat = new Animal(new MeowStrategy());
        assertThat(cat.makeSound()).isEqualTo("meow");
    }
}
