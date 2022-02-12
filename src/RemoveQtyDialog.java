import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveQtyDialog extends JDialog implements ActionListener {
    private FeedBin bin;
    private JTextField input;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label;
    private JButton applyButton;
    private JButton clearButton;
    private JButton cancelButton;

    RemoveQtyDialog(FeedBinGUI parent, boolean modal, FeedBin binObject) {
        super(parent, "Bin Quantity Deduction", modal); // call superclass constructor
        bin = binObject;
        panel1 = new JPanel();
        panel2 = new JPanel();
        label = new JLabel("How many quantity to remove from the bin?");
        input = new JTextField(10);
        applyButton = new JButton("Apply");
        clearButton = new JButton("Clear");
        cancelButton = new JButton("Cancel");
        applyButton.addActionListener(this);
        clearButton.addActionListener(this);
        cancelButton.addActionListener(this);
        panel1.add(label);
        panel1.add(input);
        getContentPane().add(panel1, "North");
        panel2.add(applyButton);
        panel2.add(clearButton);
        panel2.add(cancelButton);
        getContentPane().add(panel2, "South");
        pack();
    }

    // button event handler
    public void actionPerformed(ActionEvent event) {
        String butLabel = event.getActionCommand();

        if (butLabel.equals("Apply")) {
            // Regex for all positive numbers including decimals
            String pattern = "^[0-9]\\d*(\\.\\d+)?$";
            String volumeInput = input.getText();
            if (volumeInput.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Quantity missing. Enter a value to proceed.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else if (!volumeInput.matches(pattern)) {
                JOptionPane.showMessageDialog(this,
                        "Quantity must be positive number or decimal.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else {
                double currentVolume = bin.getCurrentVolume();
                double volume = Double.parseDouble(volumeInput);
                if (volume > currentVolume) {
                    JOptionPane.showMessageDialog(this,
                            "Cannot remove more than what is currently available.",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE,
                            null);
                } else {
                    bin.removeProduct(volume);
                    JOptionPane.showMessageDialog(this,
                            "Quantity removed successfully.",
                            "Success!",
                            JOptionPane.INFORMATION_MESSAGE,
                            null);
                    dispose();
                }
            }
        } else if (butLabel.equals("Clear")) {
            input.setText("");
        } else { // must have pressed the cancel button
            dispose();
        }
    }
}