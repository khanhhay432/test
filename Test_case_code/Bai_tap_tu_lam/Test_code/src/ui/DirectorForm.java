package ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Model.Organization;
import Service.OrganizationService;

public class DirectorForm extends JFrame {
    
    private OrganizationService orgService = new OrganizationService();
    private Organization currentOrg;
    
    // Table for organization list
    private JTable orgTable;
    private DefaultTableModel tableModel;
    
    // Form fields (editable)
    private JTextField txtOrgName = new JTextField(30);
    private JTextField txtAddress = new JTextField(30);
    private JTextField txtPhone = new JTextField(30);
    private JTextField txtEmail = new JTextField(30);
    
    // Buttons
    private JButton btnNew = new JButton("New");
    private JButton btnSave = new JButton("Save");
    private JButton btnUpdate = new JButton("Update");
    private JButton btnDelete = new JButton("Delete");
    private JButton btnRefresh = new JButton("Refresh");
    private JButton btnClear = new JButton("Clear");
    private JButton btnClose = new JButton("Close");
    
    private int selectedOrgId = -1;
    
    public DirectorForm(int orgId) {
        // Ignore orgId parameter, we'll manage all organizations now
        setTitle("Organization Management");
        setSize(900, 700);
        setLayout(new BorderLayout(10, 10));
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top: Organization form
        JPanel orgFormPanel = createOrgFormPanel();
        mainPanel.add(orgFormPanel, BorderLayout.NORTH);
        
        // Center: Table
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Bottom: Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Button actions
        btnNew.addActionListener(e -> newOrganization());
        btnSave.addActionListener(e -> saveOrganization());
        btnUpdate.addActionListener(e -> updateOrganization());
        btnDelete.addActionListener(e -> deleteOrganization());
        btnRefresh.addActionListener(e -> loadOrganizations());
        btnClear.addActionListener(e -> clearForm());
        btnClose.addActionListener(e -> dispose());
        
        // Table selection listener
        orgTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectOrganization();
            }
        });
        
        // Load all organizations
        loadOrganizations();
        
        // Initially, enable Save, disable Update
        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createOrgFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(34, 139, 34), 2), 
            "Organization Information"
        ));
        panel.setBackground(new Color(240, 255, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 1: Organization Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblNameTitle = new JLabel("Organization Name:");
        lblNameTitle.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblNameTitle, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtOrgName.setFont(new Font("Arial", Font.PLAIN, 12));
        txtOrgName.setPreferredSize(new Dimension(300, 28));
        panel.add(txtOrgName, gbc);
        
        // Row 2: Address
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblAddressTitle = new JLabel("Address:");
        lblAddressTitle.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblAddressTitle, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtAddress.setFont(new Font("Arial", Font.PLAIN, 12));
        txtAddress.setPreferredSize(new Dimension(300, 28));
        panel.add(txtAddress, gbc);
        
        // Row 1 Column 2: Phone
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblPhoneTitle = new JLabel("Phone:");
        lblPhoneTitle.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblPhoneTitle, gbc);
        
        gbc.gridx = 3; gbc.weightx = 1.0;
        txtPhone.setFont(new Font("Arial", Font.PLAIN, 12));
        txtPhone.setPreferredSize(new Dimension(200, 28));
        panel.add(txtPhone, gbc);
        
        // Row 2 Column 2: Website/Email
        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblEmailTitle = new JLabel("Website/Email:");
        lblEmailTitle.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblEmailTitle, gbc);
        
        gbc.gridx = 3; gbc.weightx = 1.0;
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        txtEmail.setPreferredSize(new Dimension(200, 28));
        panel.add(txtEmail, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1), 
            "Organizations List"
        ));
        
        // Create table
        String[] columns = {"ID", "Organization Name", "Address", "Phone", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        orgTable = new JTable(tableModel);
        orgTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orgTable.setRowHeight(25);
        orgTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        orgTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        orgTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        orgTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        orgTable.getColumnModel().getColumn(4).setPreferredWidth(180);
        
        JScrollPane scrollPane = new JScrollPane(orgTable);
        scrollPane.setPreferredSize(new Dimension(850, 300));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNew.setBackground(new Color(34, 139, 34));
        btnNew.setForeground(Color.WHITE);
        btnNew.setFocusPainted(false);
        btnNew.setPreferredSize(new Dimension(90, 35));
        
        btnSave.setBackground(new Color(0, 128, 0));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setPreferredSize(new Dimension(90, 35));
        
        btnUpdate.setBackground(new Color(30, 144, 255));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setPreferredSize(new Dimension(90, 35));
        
        btnDelete.setBackground(new Color(220, 20, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setPreferredSize(new Dimension(90, 35));
        
        btnRefresh.setBackground(new Color(70, 130, 180));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(90, 35));
        
        btnClear.setBackground(new Color(128, 128, 128));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new Dimension(90, 35));
        
        btnClose.setPreferredSize(new Dimension(90, 35));
        
        panel.add(btnNew);
        panel.add(btnSave);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        panel.add(btnRefresh);
        panel.add(btnClose);
        
        return panel;
    }
    
    private void loadOrganizations() {
        try {
            // Clear table
            tableModel.setRowCount(0);
            
            // Load from database
            List<Organization> organizations = orgService.getAllOrganizations();
            
            for (Organization org : organizations) {
                tableModel.addRow(new Object[]{
                    org.getOrgId(),
                    org.getOrgName(),
                    org.getAddress() != null ? org.getAddress() : "",
                    org.getPhone() != null ? org.getPhone() : "",
                    org.getEmail() != null ? org.getEmail() : ""
                });
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error loading organizations: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void selectOrganization() {
        int selectedRow = orgTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedOrgId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            txtOrgName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtAddress.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhone.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
            
            // Enable Update and Delete, disable Save
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }
    
    private void newOrganization() {
        clearForm();
        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    private void saveOrganization() {
        try {
            Organization org = new Organization();
            org.setOrgName(txtOrgName.getText().trim());
            org.setAddress(txtAddress.getText().trim());
            org.setPhone(txtPhone.getText().trim());
            org.setEmail(txtEmail.getText().trim());
            
            // Validate
            String error = orgService.validate(org);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Save
            int newId = orgService.saveOrganization(org);
            JOptionPane.showMessageDialog(this, 
                "Organization saved successfully! ID: " + newId, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            loadOrganizations();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error saving organization: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void updateOrganization() {
        if (selectedOrgId < 0) {
            JOptionPane.showMessageDialog(this, "Please select an organization to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Organization org = new Organization();
            org.setOrgId(selectedOrgId);
            org.setOrgName(txtOrgName.getText().trim());
            org.setAddress(txtAddress.getText().trim());
            org.setPhone(txtPhone.getText().trim());
            org.setEmail(txtEmail.getText().trim());
            
            // Validate
            String error = orgService.validateForUpdate(org);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update
            boolean success = orgService.updateOrganization(org);
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Organization updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadOrganizations();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update organization!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error updating organization: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void deleteOrganization() {
        if (selectedOrgId < 0) {
            JOptionPane.showMessageDialog(this, "Please select an organization to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this organization?\nThis action cannot be undone!", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = orgService.deleteOrganization(selectedOrgId);
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Organization deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadOrganizations();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to delete organization!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error deleting organization: " + ex.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    private void clearForm() {
        txtOrgName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        orgTable.clearSelection();
        selectedOrgId = -1;
        
        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}
