package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    private JTextField txtUnitId;
    private JTextField txtName;
    private JTextArea txtDesc;

    private JLabel lblUnitIdError;
    private JLabel lblNameError;
    private JLabel lblDescError;

    private List<OrgUnit> database = new ArrayList<>();

    public Main() {
        setTitle("Add Organization Unit");
        setSize(560, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(20, 30, 20, 30));
        add(root);

        // ===== TITLE =====
        JLabel title = new JLabel("Add Organization Unit");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        root.add(title, BorderLayout.NORTH);

        // ===== FORM =====
        JPanel form = new JPanel(new GridBagLayout());
        root.add(form, BorderLayout.CENTER);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 0, 4, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        int row = 0;

        // UnitId
        addLabel(form, "Unit Id", row++);
        txtUnitId = new JTextField();
        addField(form, txtUnitId, row++);
        lblUnitIdError = addError(form, row++);

        // Name
        addLabel(form, "Name *", row++);
        txtName = new JTextField();
        addField(form, txtName, row++);
        lblNameError = addError(form, row++);

        // Description
        addLabel(form, "Description", row++);
        txtDesc = new JTextArea(4, 20);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDesc);
        addField(form, scroll, row++);
        lblDescError = addError(form, row++);

        // Hint
        JLabel hint = new JLabel("This unit will be added under Organization   * Required");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setForeground(Color.GRAY);
        g.gridy = row++;
        g.gridx = 0;
        form.add(hint, g);

        // ===== BUTTONS =====
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(90, 32));
        btnCancel.addActionListener(this::cancelForm);

        JButton btnSave = new JButton("Save");
        btnSave.setPreferredSize(new Dimension(90, 32));
        btnSave.setBackground(new Color(124, 179, 66));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(this::saveUnit);

        btnPanel.add(btnCancel);
        btnPanel.add(btnSave);

        root.add(btnPanel, BorderLayout.SOUTH);

        database.add(new OrgUnit("OU001", "Sales", "Sales dept"));
    }

    private void addLabel(JPanel panel, String text, int row) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = row;
        g.anchor = GridBagConstraints.WEST;
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(lbl, g);
    }

    private void addField(JPanel panel, Component comp, int row) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = row;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comp, g);
    }

    private JLabel addError(JPanel panel, int row) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = row;
        g.anchor = GridBagConstraints.WEST;
        JLabel lbl = new JLabel(" ");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(Color.RED);
        panel.add(lbl, g);
        return lbl;
    }

    private void saveUnit(ActionEvent e) {
        // BUGGY VERSION – intentionally weak validation

        String unitId = txtUnitId.getText();   // BUG05: no trim
        String name = txtName.getText();       // BUG01: allow empty
        String desc = txtDesc.getText();       // BUG06: no sanitize

        // BUG04: no length validation
        // BUG03: allow special characters
        // BUG02: no duplicate check

        database.add(new OrgUnit(unitId, name, desc));

        System.out.println("=== DATABASE ===");
        for (OrgUnit u : database) {
            System.out.println("ID=" + u.unitId + " | Name=" + u.name + " | Desc=" + u.desc);
        }

        JOptionPane.showMessageDialog(this, "Saved successfully!");
    }

    private void cancelForm(ActionEvent e) {
        txtUnitId.setText("");
        txtName.setText("");
        txtDesc.setText("");
        clearErrors();
    }

    private void clearErrors() {
        lblUnitIdError.setText(" ");
        lblNameError.setText(" ");
        lblDescError.setText(" ");
    }

    static class OrgUnit {
        String unitId;
        String name;
        String desc;

        OrgUnit(String unitId, String name, String desc) {
            this.unitId = unitId;
            this.name = name;
            this.desc = desc;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }

    private void showDatabase() {
        String[] cols = {"UnitId", "Name", "Description"};
        String[][] data = new String[database.size()][3];

        for (int i = 0; i < database.size(); i++) {
            OrgUnit u = database.get(i);
            data[i][0] = u.unitId;
            data[i][1] = u.name;
            data[i][2] = u.desc;
        }

        JTable table = new JTable(data, cols);
        JScrollPane sp = new JScrollPane(table);

        JOptionPane.showMessageDialog(this, sp, "Database", JOptionPane.INFORMATION_MESSAGE);
    }
}