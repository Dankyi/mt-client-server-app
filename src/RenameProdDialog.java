import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenameProdDialog extends JDialog implements ActionListener {
    private FeedBin bin;
    private JTextField currentNameTF;
    private JTextField newNameTF;
    private JPanel currentNamePanel;
    private JLabel currentNameLabel;
    private JPanel newNamePanel;
    private JLabel newNameLabel;
    private JPanel buttonsPanel;
    private JButton applyButton;
    private JButton clearButton;
    private JButton cancelButton;

    RenameProdDialog(FeedBinGUI parent, boolean modal, FeedBin binObject) {
        super(parent, "Rename Product", modal);
        bin = binObject;
        currentNamePanel = new JPanel();
        currentNameLabel = new JLabel("Current Name: ");
        currentNameTF = new JTextField(10);
        currentNameTF.setText(bin.getProductName());
        currentNameTF.setEnabled(false);
        currentNamePanel.add(currentNameLabel);
        currentNamePanel.add(currentNameTF);
        getContentPane().add(currentNamePanel, "North");

        newNamePanel = new JPanel();
        newNameLabel = new JLabel("New Name:      ");
        newNameTF = new JTextField(10);
        newNamePanel.add(newNameLabel);
        newNamePanel.add(newNameTF);
        getContentPane().add(newNamePanel, "Center");

        buttonsPanel = new JPanel();
        applyButton = new JButton("Apply");
        clearButton = new JButton("Clear");
        cancelButton = new JButton("Cancel");
        applyButton.addActionListener(this);
        clearButton.addActionListener(this);
        cancelButton.addActionListener(this);
        buttonsPanel.add(applyButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(cancelButton);
        getContentPane().add(buttonsPanel, "South");
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String newName = newNameTF.getText();
        String btnClicked = event.getActionCommand();

        if (btnClicked.equals("Apply")) {
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Product's new name missing. Enter a name to proceed.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else if (!bin.setProductName(newName)) {
                JOptionPane.showMessageDialog(this,
                        "Product name cannot be changed since there is some quantity left in the bin.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Product name changed successfully.",
                        "Success!",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
                dispose();
            }
        } else if (btnClicked.equals("Clear")) {
            newNameTF.setText("");
        } else {
            dispose();
        }
    }
}
