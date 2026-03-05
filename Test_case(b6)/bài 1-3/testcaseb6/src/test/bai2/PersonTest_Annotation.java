package bai2;

import org.junit.Test;

public class PersonTest_Annotation {

    @Test(expected = IllegalArgumentException.class)
    public void testAgeNegative_Annotation() {
        new Person("Fpoly", -1);
    }
}