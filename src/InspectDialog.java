import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InspectDialog extends JDialog {
    private FeedBin bin;
    private Box boxInfo;
    private Box boxButton;
    private JPanel panel;
    private JLabel label [];
    private JButton OKButton;

    // InspectDialog constructor
    InspectDialog(FeedBinGUI parent, boolean modal, FeedBin binObject) {
        super (parent, "Bin Inspection", modal); // call superclass constructor
        int i;
        bin = binObject;
        getContentPane ().setLayout
                (new BoxLayout (getContentPane (), BoxLayout.Y_AXIS));
        panel = new JPanel();
        panel.setLayout(new GridLayout(4,2) );
        label = new JLabel[8];
        label[0] = new JLabel("Bin Number: ");
        label[2] = new JLabel("Contains: ");
        label[4] = new JLabel("Maximum Volume: ");
        label[6] = new JLabel("Current Volume: ");
        label[1] = new JLabel(String.valueOf(bin.getBinNumber()));
        label[3] = new JLabel(bin.getProductName());
        label[5] = new JLabel(String.valueOf(bin.getMaxVolume()));
        label[7] = new JLabel(String.valueOf(bin.getCurrentVolume()));
        OKButton = new JButton("OK");
        OKButton.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                dispose();
            }
        });
        for (i = 0; i< 8 ; i++) // labels go into a 4 x 2 grid
            panel.add(label[i]);
        boxInfo = new Box(BoxLayout.Y_AXIS);
        boxButton = new Box(BoxLayout.Y_AXIS);
        boxInfo.add(panel);
        boxButton.add(OKButton);
        getContentPane().add(boxInfo);
        getContentPane().add(boxButton);
        pack();
    }
}
