import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class MakeBatchDialog extends JDialog implements ActionListener {
    private JPanel panel;
    private JLabel prod1Label;
    private JLabel prod2Label;
    private JComboBox prod1Combox;
    private JComboBox prod2Combox;
    private JLabel prod1PctLabel;
    private JLabel prod2PctLabel;
    private JTextField prod1PctTF;
    private JTextField prod2PctTF;
    private JLabel batchQtyLabel;
    private JTextField batchQtyTF;
    private JPanel buttonsPanel;
    private JButton makeBatchButton;
    private JButton clearButton;
    private JButton cancelButton;
    private Box boxInfo;
    private ArrayList<FeedBin> feedBinsArray = new ArrayList<>();
    private ArrayList<String> prodNamesArray = new ArrayList<>();
    private ArrayList<String> currVolArray = new ArrayList<>();
    private final String defaultCBStr = "--Select Product--";

    public MakeBatchDialog(SupervisorGUI parent, boolean modal, ArrayList<FeedBin> feedBins) {
        super(parent, "Make a Batch", modal);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        panel = new JPanel();
        panel.setLayout(new GridLayout(5,2,0, 12));
        prod1Label = new JLabel("Product 1: ");
        prod2Label = new JLabel("Product 2: ");
        prod1PctLabel = new JLabel("% of Product 1: ");
        prod2PctLabel = new JLabel("% of Product 2: ");
        prod1PctTF = new JTextField(10);
        prod2PctTF = new JTextField(10);
        prod1Combox = new JComboBox();
        prod2Combox = new JComboBox();
        batchQtyLabel = new JLabel("Batch Quantity: ");
        batchQtyTF = new JTextField(10);
        buttonsPanel = new JPanel();

        for (FeedBin feedBin : feedBins) {
            if (feedBin.getCurrentVolume() > 0.0) {
                feedBinsArray.add(feedBin);
            }
        }

        prod1Combox.addItem(defaultCBStr);
        prod2Combox.addItem(defaultCBStr);
        for (FeedBin feedBin : feedBinsArray) {
            if (feedBin.getCurrentVolume() > 0.0) {
                prod1Combox.addItem(feedBin.getProductName());
                prod2Combox.addItem(feedBin.getProductName());
                prodNamesArray.add(feedBin.getProductName());
                currVolArray.add(String.valueOf(feedBin.getCurrentVolume()));
            }
        }

        System.out.println(prodNamesArray);
        System.out.println(currVolArray);

        prod1Combox.setBounds(50, 50,130,20);
        prod2Combox.setBounds(50, 50,130,20);

        panel.add(prod1Label);
        panel.add(prod1Combox);
        panel.add(prod1PctLabel);
        panel.add(prod1PctTF);

        panel.add(prod2Label);
        panel.add(prod2Combox);
        panel.add(prod2PctLabel);
        panel.add(prod2PctTF);
        panel.add(batchQtyLabel);
        panel.add(batchQtyTF);

        boxInfo = new Box(BoxLayout.Y_AXIS);
        boxInfo.add(panel);
        getContentPane().add(boxInfo);

        makeBatchButton = new JButton("Make Batch");
        clearButton = new JButton("Clear");
        cancelButton = new JButton("Cancel");
        makeBatchButton.addActionListener(this);
        clearButton.addActionListener(this);
        cancelButton.addActionListener(this);
        buttonsPanel.add(makeBatchButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(cancelButton);
        getContentPane().add(buttonsPanel, "South");
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String btnClicked = event.getActionCommand();
        String prod1Name = (String) prod1Combox.getSelectedItem();
        String prod2Name = (String) prod2Combox.getSelectedItem();
        int prodIndex1 = prod1Combox.getSelectedIndex();
        int prodIndex2 = prod2Combox.getSelectedIndex();
        String product1Pct = prod1PctTF.getText();
        String product2Pct = prod2PctTF.getText();
        double currProd1Qty = 0.0;
        double currProd2Qty = 0.0;
        double prod1QtyToRemove = 0.0;
        double prod2QtyToRemove = 0.0;
        String batchQty = batchQtyTF.getText();

        // Regex for all positive numbers including decimals
        String pattern = "^[0-9]\\d*(\\.\\d+)?$";

        if (btnClicked.equals("Make Batch")) {
            // Check if a product is selected in each of the combo boxes
            if (prod1Name.equals(defaultCBStr)
                    || prod2Name.equals(defaultCBStr)) {
                JOptionPane.showMessageDialog(this,
                        "Select two different products " +
                                "from the two options to proceed.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else if (product1Pct.isEmpty() || product2Pct.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter a percentage value in each of " +
                                "the '% of Product' fields to proceed.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else if (!product1Pct.matches(pattern)
                    || !product2Pct.matches(pattern)) {
                JOptionPane.showMessageDialog(this,
                        "Percentage fields " +
                                "must be a positive number.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
                // Percentage provided must sum up to 100
            } else if ((Double.parseDouble(product1Pct)
                    + Double.parseDouble(product2Pct)) != 100.0) {
                JOptionPane.showMessageDialog(this,
                        "The percentages entered must sum up to 100%.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
                // Products must be different to make a batch of a recipe
            } else if (prod1Name.equals(prod2Name)) {
                JOptionPane.showMessageDialog(this,
                        "A batch cannot be made out of the same " +
                                "product. Select two different products.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else if (batchQty.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter a batch quantity you will like to " +
                                "make out of these two different products.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else if (!batchQty.matches(pattern)) {
                JOptionPane.showMessageDialog(this,
                        "Batch quantity must be a " +
                                "positive number or decimal.",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            } else {
                if (prodNamesArray.size() == 1) {
                    JOptionPane.showMessageDialog(this,
                            "A batch cannot be made " +
                                    "out of only one product.",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE,
                            null);
                } else if (prodNamesArray.size() == 2) {
                    if (prodIndex1 == 1 && prodIndex2 == 2) {
                        currProd1Qty = Double.parseDouble(currVolArray.get(0));
                        currProd2Qty = Double.parseDouble(currVolArray.get(1));
                    } else if (prodIndex1 == 2 && prodIndex2 == 1) {
                        currProd1Qty = Double.parseDouble(currVolArray.get(1));
                        currProd2Qty = Double.parseDouble(currVolArray.get(0));
                    }
                } else if (prodNamesArray.size() == 3) {
                    if (prodIndex1 == 1 && prodIndex2 == 2) {
                        currProd1Qty = Double.parseDouble(currVolArray.get(0));
                        currProd2Qty = Double.parseDouble(currVolArray.get(1));
                    } else if (prodIndex1 == 1 && prodIndex2 == 3) {
                        currProd1Qty = Double.parseDouble(currVolArray.get(0));
                        currProd2Qty = Double.parseDouble(currVolArray.get(2));
                    } else if (prodIndex1 == 2 && prodIndex2 == 1) {
                        currProd1Qty = Double.parseDouble(currVolArray.get(1));
                        currProd2Qty = Double.parseDouble(currVolArray.get(0));
                    } else if (prodIndex1 == 2 && prodIndex2 == 3) {
                        currProd1Qty = Double.parseDouble(currVolArray.get(1));
                        currProd2Qty = Double.parseDouble(currVolArray.get(2));
                    } else if (prodIndex1 == 3 && prodIndex2 == 1) {
                        currProd1Qty = Double.parseDouble(currVolArray.get(2));
                        currProd2Qty = Double.parseDouble(currVolArray.get(0));
                    } else if (prodIndex1 == 3 && prodIndex2 == 2) {
                        currProd1Qty = Double.parseDouble(currVolArray.get(2));
                        currProd2Qty = Double.parseDouble(currVolArray.get(1));
                    }
                }

                prod1QtyToRemove = (Double.parseDouble(product1Pct)/100) * Double.parseDouble(batchQty);
                prod2QtyToRemove = (Double.parseDouble(product2Pct)/100) * Double.parseDouble(batchQty);

                if ((currProd1Qty >= prod1QtyToRemove) && (currProd2Qty >= prod2QtyToRemove)) {
                    // Since the defaultCBStr is an extra index, we deduct 1
                    // to obtain the actual FeedBin object in the Arraylist
                    feedBinsArray.get(prodIndex1-1).removeProduct(prod1QtyToRemove);
                    feedBinsArray.get(prodIndex2-1).removeProduct(prod2QtyToRemove);

                    // Update the database of the changes to each of the two bins
                    try {
                        feedBinsArray.get(prodIndex1-1).updateBinInDB();
                        feedBinsArray.get(prodIndex2-1).updateBinInDB();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    double currVol1 = feedBinsArray.get(prodIndex1-1).getCurrentVolume();
                    double currVol2 = feedBinsArray.get(prodIndex2-1).getCurrentVolume();
                    JOptionPane.showMessageDialog(this,
                            "A batch of " + batchQty + " cubic metres of " +
                                    "product " + prod1Name + " and " + prod2Name +
                                    " \nhas been made successfully. The volumes that" +
                                    " have been removed \nfrom each of the products " +
                                    "are: " + prod1QtyToRemove + " and " + prod2QtyToRemove +
                                    " cubic metres \nrespectively. The products now have " +
                                    "current volumes of: " + currVol1 + " and \n" + currVol2 +
                                    " respectively.",
                            "Batch Info",
                            JOptionPane.INFORMATION_MESSAGE,
                            null);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "A batch of " + batchQty + " cubic metres cannot be " +
                                    "made. The calculated \nvolumes/quantities required to " +
                                    "be removed from " + prod1Name + " \nand " + prod2Name +
                                    " are " + prod1QtyToRemove + " and " + prod2QtyToRemove +
                                    " respectively but the current \nvolumes of each are " +
                                    currProd1Qty + " and " + currProd2Qty + " respectively.",
                            "Batch Info",
                            JOptionPane.ERROR_MESSAGE,
                            null);
                }
            }
        } else if (btnClicked.equals("Clear")) {
            prod1Combox.setSelectedItem(prod1Combox.getItemAt(0));
            prod2Combox.setSelectedItem(prod2Combox.getItemAt(0));
            prod1PctTF.setText("");
            prod2PctTF.setText("");
            batchQtyTF.setText("");
        } else {
            dispose();
        }
    }
}
