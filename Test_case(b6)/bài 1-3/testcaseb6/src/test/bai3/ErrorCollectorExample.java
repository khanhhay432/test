package bai3;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.equalTo;

public class ErrorCollectorExample {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void exampleTest() {
        collector.checkThat("Kiểm tra 1", 5, equalTo(5));
        collector.checkThat("Kiểm tra 2", 10, equalTo(20)); // sai
        collector.checkThat("Kiểm tra 3", "Hello", equalTo("Hello"));
        collector.checkThat("Kiểm tra 4", true, equalTo(false)); // sai
    }
}