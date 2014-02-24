package de.fanero.stream;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class MapLoaderTest {

    @Test
    public void test() {
        MapLoader mapLoader = new MapLoader();

        ByteArrayInputStream stream = new ByteArrayInputStream("{ \"name\": \"testname\" }".getBytes(Charset
                .forName("UTF-8")));

        MMap map = mapLoader.load(stream);

        assertEquals("testname", map.getName());
    }

}
