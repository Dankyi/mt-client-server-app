import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckBatchDialog extends JDialog {
    private JPanel panel;
    private JLabel prodNameLabel;
    private JLabel currVolumeLabel;
    private JButton OKButton;
    private ArrayList<FeedBin> feedBinsArray = new ArrayList<>();
    private ArrayList<String> prodNamesArray = new ArrayList<>();
    private ArrayList<String> currVolArray = new ArrayList<>();

    CheckBatchDialog(SupervisorGUI parent, boolean modal, ArrayList<FeedBin> feedBins) {
        super(parent, "Batch Inspection", modal);
        Box box = Box.createVerticalBox();
        panel = new JPanel();
        prodNameLabel = new JLabel("A batch cannot be made because there is " +
                "currently no quantity of products available in any of the bins.");
        currVolumeLabel = new JLabel();

        for (FeedBin feedBin : feedBins) {
            if (feedBin.getCurrentVolume() > 0.0) {
                feedBinsArray.add(feedBin);
            }
        }

        for (FeedBin feedBin : feedBinsArray) {
            if (feedBin.getCurrentVolume() > 0.0) {
                System.out.println(feedBin.getProductName());
                prodNamesArray.add(feedBin.getProductName());
                currVolArray.add(String.valueOf(feedBin.getCurrentVolume()));
            }
        }

        if (prodNamesArray.size() > 1) {
            if (prodNamesArray.size() == 2) {
                prodNameLabel.setText("A batch could be made out of these two currently " +
                        "available products: " + prodNamesArray.get(0) + " and "
                        + prodNamesArray.get(1));

                currVolumeLabel.setText("with each having a current volume of: " +
                        currVolArray.get(0) + " and " + currVolArray.get(1) +
                        " cubic metres respectively.");
            } else {
                prodNameLabel.setText("A batch could be made out from any two of these three " +
                        "currently available products: " + prodNamesArray.get(0) +
                        ", " + prodNamesArray.get(1) + " and " + prodNamesArray.get(2));

                currVolumeLabel.setText("with each having a current volume of: " +
                        currVolArray.get(0) + ", " + currVolArray.get(1) + " and " +
                        currVolArray.get(2) + " cubic metres respectively.");
            }
        } else if (prodNamesArray.size() == 1) {
            prodNameLabel = new JLabel("A batch cannot be made because only " +
                    "one product i.e. " + prodNamesArray.get(0) + " is currently " +
                    "available i.e. in bin " + feedBinsArray.get(0).getBinNumber() + ".");

            currVolumeLabel.setText("At least two products with " +
                    "quantities are needed before a batch can be made.");
        }

        OKButton = new JButton("OK");
        OKButton.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                dispose();
            }
        });

        box.add(prodNameLabel);
        box.add(currVolumeLabel);
        getContentPane().add(box);

        panel.add(OKButton);
        getContentPane().add(panel, "South");
        pack();
    }
}
