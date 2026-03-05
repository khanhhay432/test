package bai2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PersonTest_Expected {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testAgeNegative_Rule() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Age must be >= 0");

        new Person("Fpoly", -1);
    }
}