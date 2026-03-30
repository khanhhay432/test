package ui;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // Chạy UI theo đúng chuẩn Swing thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OrganizationForm(); // Mở form chính
            }
        });

    }
}
