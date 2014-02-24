package de.fanero.map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;

public class CharacterPrototypeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void bulletsNotNull() throws Exception {
        CharacterPrototype cp = new CharacterPrototype();

        assertNotNull(cp.getBulletPrototypes());
    }
}
