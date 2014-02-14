package rob.scroller.map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;

public class EntityPrototypeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nameNotNull() throws Exception {
        EntityPrototype ep = new EntityPrototype();

        assertNotNull(ep.getName());
    }

    @Test
    public void nameDoesNotAllowNull() throws Exception {
        EntityPrototype ep = new EntityPrototype();

        thrown.expect(NullPointerException.class);
        ep.setName(null);
    }
}
