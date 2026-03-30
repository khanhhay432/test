package bai2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersonTest_TryCatch {

    @Test
    public void testAgeNegative_TryCatch() {
        try {
            new Person("Fpoly", -1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Age must be >= 0", e.getMessage());
        }
    }
}