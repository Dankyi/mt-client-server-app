import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FillDialog extends JDialog implements ActionListener {
    private FeedBin bin;
    private JTextField input;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label;
    private JButton applyButton;
    private JButton clearButton;
    private JButton cancelButton;

    // FillDialog constructor
    FillDialog(FeedBinGUI parent, boolean modal, FeedBin binObject) {
        super (parent, "Bin Filling", modal); // call superclass constructor
        bin = binObject;
        panel1 = new JPanel();
        panel2 = new JPanel();
        label = new JLabel("How much is to be added?");
        input = new JTextField(10);
        applyButton = new JButton("Apply");
        clearButton = new JButton("Clear");
        cancelButton = new JButton("Cancel");
        applyButton.addActionListener(this);
        clearButton.addActionListener(this);
        cancelButton.addActionListener(this);
        panel1.add(label);
        panel1.add(input);
        getContentPane().add(panel1,"North");
        panel2.add(applyButton);
        panel2.add(clearButton);
        panel2.add(cancelButton);
        getContentPane().add(panel2,"South");
        pack();
    }

    // button event handler
    public void actionPerformed(ActionEvent event) {
        double volume;
        String outcome;
        String butLabel = event.getActionCommand();

        if (butLabel.equals("Apply")) {
            try {
                volume = Double.parseDouble(input.getText());
                if (volume < 0.0)
                    throw new NumberFormatException();
                if (!bin.addProduct(volume)) // interact with the bin object here
                    throw new FillException();
                JOptionPane.showMessageDialog(this,
                        "Addition Confirmed",
                        "Fill Report",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
                dispose();
            } catch (FillException e) {
                JOptionPane.showMessageDialog(this,
                        "Not enough room in the bin",
                        "Fill Report",
                        JOptionPane.WARNING_MESSAGE,
                        null);
            }
        } else if(butLabel.equals("Clear")) {
            input.setText("");
        } else { // must have pressed the cancel button
            dispose();
        }
    } // method actionPerformed
} // class AddDialog
class FillException extends RuntimeException {}