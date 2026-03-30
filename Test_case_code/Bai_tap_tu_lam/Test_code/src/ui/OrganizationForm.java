package ui;
import javax.swing.*;
import java.awt.*;
import Model.Organization;
import Service.OrganizationService;

public class OrganizationForm extends JFrame {

    // Tab components
    private JTabbedPane tabbedPane = new JTabbedPane();
    
    // Details 1 fields
    private JTextField txtOrgName = new JTextField();
    private JTextArea txtShortDesc = new JTextArea(3, 20);
    private JTextField txtLeadContact = new JTextField();
    private JTextField txtAddressLine1 = new JTextField();
    private JTextField txtAddressLine2 = new JTextField();
    private JTextField txtAddressLine3 = new JTextField();
    private JTextField txtPostcode = new JTextField();
    private JTextField txtCountry = new JTextField();
    private JTextField txtCounty = new JTextField();
    
    // Details 2 fields
    private JCheckBox chkPreferredOrg = new JCheckBox();
    private JCheckBox chkExpressionOfInterest = new JCheckBox();
    private JTextField txtTypeOfBusiness = new JTextField();
    private JTextField txtSICCode = new JTextField();
    private JTextArea txtFullDescription = new JTextArea(3, 20);
    private JTextField txtPhoneNumber = new JTextField();
    private JTextField txtFax = new JTextField();
    private JTextField txtWebAddress = new JTextField();
    
    // Buttons
    private JButton btnSave = new JButton("Save");
    private JButton btnBack = new JButton("Back");
    private JButton btnDirector = new JButton("Director");
    
    private OrganizationService service = new OrganizationService();
    private int currentOrgId = 0;

    public OrganizationForm() {
        setTitle("Organisation Details");
        setSize(650, 650);
        setLayout(new BorderLayout());
        
        // Add tabbed pane
        tabbedPane.addTab("Details 1", createDetails1Panel());
        tabbedPane.addTab("Details 2", createDetails2Panel());
        tabbedPane.addTab("Details 3", new JPanel()); // Empty for now
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Bottom panel with buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnBack);
        buttonPanel.add(btnDirector);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Initially disable Director button until save
        btnDirector.setEnabled(false);
        
        // Button actions
        btnSave.addActionListener(e -> save());
        btnBack.addActionListener(e -> dispose());
        btnDirector.addActionListener(e -> openDirector());
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createDetails1Panel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Organization Name
        addField(panel, gbc, row++, "Organization Name", txtOrgName);
        
        // Organization Short Description
        addFieldWithTextArea(panel, gbc, row++, "Organization Short Description", txtShortDesc);
        
        // Lead Contact
        addFieldWithButton(panel, gbc, row++, "Lead Contact", txtLeadContact, "Lookup");
        
        // Address Line 1
        addField(panel, gbc, row++, "Address Line 1", txtAddressLine1);
        
        // Address Line 2
        addField(panel, gbc, row++, "Address Line 2", txtAddressLine2);
        
        // Address Line 3
        addField(panel, gbc, row++, "Address Line 3", txtAddressLine3);
        
        // Postcode
        addFieldWithButton(panel, gbc, row++, "Postcode", txtPostcode, "Lookup");
        
        // Country
        addField(panel, gbc, row++, "Country", txtCountry);
        
        // County
        addField(panel, gbc, row++, "County", txtCounty);
        
        return panel;
    }
    
    private JPanel createDetails2Panel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Preferred Organisation
        addCheckboxField(panel, gbc, row++, "Preferred Organisation", chkPreferredOrg);
        
        // Expression of Interest
        addCheckboxField(panel, gbc, row++, "Expression of Interest", chkExpressionOfInterest);
        
        // Type of Business
        addField(panel, gbc, row++, "Type of Business", txtTypeOfBusiness);
        
        // SIC Code
        addField(panel, gbc, row++, "SIC Code", txtSICCode);
        
        // Organisation Full Description
        addFieldWithTextArea(panel, gbc, row++, "Organisation Full Description", txtFullDescription);
        
        // Phone Number
        addField(panel, gbc, row++, "Phone Number", txtPhoneNumber);
        
        // Fax
        addField(panel, gbc, row++, "Fax", txtFax);
        
        // Web Address
        addField(panel, gbc, row++, "Web Address", txtWebAddress);
        
        return panel;
    }
    
    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(200, 25));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        field.setPreferredSize(new Dimension(250, 25));
        panel.add(field, gbc);
        
        gbc.weightx = 0;
    }
    
    private void addFieldWithTextArea(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextArea textArea) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(200, 25));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(250, 60));
        panel.add(scrollPane, gbc);
        
        gbc.weightx = 0;
    }
    
    private void addFieldWithButton(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field, String buttonText) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(200, 25));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        field.setPreferredSize(new Dimension(250, 25));
        panel.add(field, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0;
        JButton btn = new JButton(buttonText);
        btn.setForeground(Color.BLUE);
        btn.setPreferredSize(new Dimension(80, 25));
        panel.add(btn, gbc);
    }
    
    private void addCheckboxField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JCheckBox checkbox) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(200, 25));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        panel.add(checkbox, gbc);
    }
    
    private void save() {
        try {
            Organization org = new Organization();
            org.setOrgName(txtOrgName.getText());
            org.setAddress(txtAddressLine1.getText());
            org.setPhone(txtPhoneNumber.getText());
            org.setEmail(txtWebAddress.getText());
            
            String error = service.validate(org);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            currentOrgId = service.saveOrganization(org);
            JOptionPane.showMessageDialog(this, "Save successfully! Organization ID: " + currentOrgId, "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Enable Director button after successful save
            btnDirector.setEnabled(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openDirector() {
        if (currentOrgId > 0) {
            new DirectorForm(currentOrgId);
        } else {
            JOptionPane.showMessageDialog(this, "Please save the organization first!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
