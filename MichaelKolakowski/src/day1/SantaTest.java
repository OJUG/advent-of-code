package day1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michaelkolakowski on 8/16/16.
 */
public class SantaTest {

    @Test
    public void emptyInput() {
        assertEquals(0, Santa.goToFloor(""));
    }

}
