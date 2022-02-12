import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewProdDialog extends JDialog implements ActionListener {
    private FeedBin bin;
    private JTextField binNumTF;
    private JTextField prodNameTF;
    private JPanel binNumPanel;
    private JLabel binNumLabel;
    private JPanel prodNamePanel;
    private JLabel prodNameLabel;
    private JPanel buttonsPanel;
    private JButton addProdButton;
    private JButton clearButton;
    private JButton cancelButton;

    AddNewProdDialog(FeedBinGUI parent, boolean modal, FeedBin binObject) {
        super(parent, "Add New Product", modal);
        bin = binObject;
        binNumPanel = new JPanel();
        binNumLabel = new JLabel("Bin Number:      ");
        binNumTF = new JTextField(10);
        binNumPanel.add(binNumLabel);
        binNumPanel.add(binNumTF);
        getContentPane().add(binNumPanel, "North");

        prodNamePanel = new JPanel();
        prodNameLabel = new JLabel("Product Name: ");
        prodNameTF = new JTextField(10);
        prodNamePanel.add(prodNameLabel);
        prodNamePanel.add(prodNameTF);
        getContentPane().add(prodNamePanel, "Center");

        buttonsPanel = new JPanel();
        addProdButton = new JButton("Add Product");
        clearButton = new JButton("Clear");
        cancelButton = new JButton("Cancel");
        addProdButton.addActionListener(this);
        clearButton.addActionListener(this);
        cancelButton.addActionListener(this);
        buttonsPanel.add(addProdButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(cancelButton);
        getContentPane().add(buttonsPanel, "South");
        pack();

    }
    @Override
    public void actionPerformed(ActionEvent event) {
        String binNum = binNumTF.getText();
        String prodName = prodNameTF.getText();
        String btnClicked = event.getActionCommand();

        if (btnClicked.equals("Add Product")) {
            if (binNum.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Bin number missing. Enter a number to proceed.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else {
                // Regex for positive numbers starting from 1
                String pattern = "[1-9]\\d*";
                if (!binNum.matches(pattern)) {
                    JOptionPane.showMessageDialog(this,
                            "Bin Number must be a digit starting from 1",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE,
                            null);
                } else if (prodName.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Product Name missing. Enter a name to proceed.",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE,
                            null);
                } else {
                    //bin = new FeedBin(Integer.parseInt(binNum), prodName);
                    bin.setBinNumber(Integer.parseInt(binNum));
                    bin.setProductName(prodName);
                    bin.setMaxVolume(40.0);
                    bin.setCurrentVolume(0.0);
                    JOptionPane.showMessageDialog(this,
                            "'" + prodName + "' " + "has been added to Bin '"
                                    + binNum + "' successfully.",
                            "Success!",
                            JOptionPane.INFORMATION_MESSAGE,
                            null);
                    dispose();
                }
            }
        } else if (btnClicked.equals("Clear")) {
            binNumTF.setText("");
            prodNameTF.setText("");
        } else {
            dispose();
        }
    }
}
