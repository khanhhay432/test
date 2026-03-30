package bai3;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ErrorCollectorExample.class);

        System.out.println("Số test chạy: " + result.getRunCount());
        System.out.println("Số test lỗi: " + result.getFailureCount());

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Test thành công: " + result.wasSuccessful());
    }
}