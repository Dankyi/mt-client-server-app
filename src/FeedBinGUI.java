import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FeedBinGUI extends JFrame implements Runnable, Observer {
    private JMenuBar jmbTop;
    private JMenu binMenu;
    private JMenuItem inspect;
    private JMenuItem fill;
    private JMenuItem removeQty;
    private JMenuItem renameProd;
    private JMenuItem flush;
    private JMenuItem newProd;
    private JMenuItem exit;
    private JPanel panel;
    private JComboBox binsCombox;

    private final String defaultCBStr = "--Select a Bin--";
    private FeedBin bin;
    private ArrayList<FeedBin> feedBinsArray = new ArrayList<>();

    public FeedBinGUI(ArrayList<FeedBin> feedBinArray) {
        for (FeedBin feedBin : feedBinArray) {
            feedBin.addObserver(this);
            feedBinsArray.add(feedBin);
        }

        // Create a combobox of bins
        panel = new JPanel();
        binsCombox = new JComboBox();
        binsCombox.addItem(defaultCBStr);
        for (FeedBin feedBin : feedBinsArray) {
            binsCombox.addItem("Bin " + feedBin.getBinNumber());
        }
        panel.add(binsCombox);
        getContentPane().add(panel,"Center");
        pack();

        // Create the menu components
        jmbTop = new JMenuBar();
        binMenu = new JMenu("Bin");
        inspect = new JMenuItem("Inspect the Bin");
        fill = new JMenuItem("Add Quantity");
        removeQty = new JMenuItem("Remove Quantity");
        renameProd = new JMenuItem("Rename Product");
        flush = new JMenuItem("Flush the Bin");
        newProd = new JMenuItem("Add New Product to Bin");
        exit = new JMenuItem("Exit");
        binMenu.add(inspect);
        binMenu.add(fill);
        binMenu.add(removeQty);
        binMenu.add(renameProd);
        binMenu.add(flush);
        binMenu.add(newProd);
        binMenu.add(new JSeparator());
        binMenu.add(exit);
        jmbTop.add(binMenu);
        setJMenuBar(jmbTop);

        // Disable the binMenu until a bin is selected
        binMenu.setEnabled(false);

        // Disable some features on the menu until
        // a product has been added to a bin
        disableFields();

        binsCombox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String binNum = (String) binsCombox.getSelectedItem();
                if (binNum.equals(defaultCBStr)) {
                    binMenu.setEnabled(false);
                    setTitle("FeedBin Controller");
                }
                else if (binNum.equals("Bin 1")) {
                    bin = feedBinArray.get(0);
                    setTitle("FeedBin Controller (Bin " +
                            bin.getBinNumber() + ")");
                    binMenu.setEnabled(true);
                    resetBinMenu(bin);
                    newProd.setEnabled(true);
                } else if (binNum.equals("Bin 2")) {
                    bin = feedBinArray.get(1);
                    setTitle("FeedBin Controller (Bin " +
                            bin.getBinNumber() + ")");
                    binMenu.setEnabled(true);
                    resetBinMenu(bin);
                    newProd.setEnabled(true);
                } else if (binNum.equals("Bin 3")) {
                    bin = feedBinArray.get(2);
                    setTitle("FeedBin Controller (Bin " +
                            bin.getBinNumber() + ")");
                    binMenu.setEnabled(true);
                    resetBinMenu(bin);
                    newProd.setEnabled(true);
                }
            }
        });
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });

        inspect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                InspectDialog id = new InspectDialog(
                        FeedBinGUI.this, true, bin);
                id.setVisible(true);
            }
        });

        fill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                FillDialog fd = new FillDialog(
                        FeedBinGUI.this, true, bin);
                fd.setVisible(true);
            }
        });

        removeQty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RemoveQtyDialog rd = new RemoveQtyDialog(
                        FeedBinGUI.this, true, bin);
                rd.setVisible(true);
            }
        });

        renameProd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RenameProdDialog rd = new RenameProdDialog(
                        FeedBinGUI.this, true, bin);
                rd.setVisible(true);
            }
        });

        newProd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AddNewProdDialog rd = new AddNewProdDialog(
                        FeedBinGUI.this, true, bin);
                rd.setVisible(true);
                resetBinMenu(bin);
            }
        });

        flush.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bin.flush();
                disableFields();
                newProd.setEnabled(true);
                JOptionPane.showMessageDialog(
                        FeedBinGUI.this,
                        "The bin is now empty.",
                        "Flush Confirmation",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
            }
        });

        setTitle("FeedBin Controller");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
    }

    private void disableFields() {
        fill.setEnabled(false);
        removeQty.setEnabled(false);
        renameProd.setEnabled(false);
        flush.setEnabled(false);
    }

    private void enableFields() {
        fill.setEnabled(true);
        removeQty.setEnabled(true);
        renameProd.setEnabled(true);
        flush.setEnabled(true);
    }

    private void resetBinMenu(FeedBin feedBin) {
        if (feedBin.getProductName().equals("--")) {
            disableFields();
        } else {
            enableFields();
            newProd.setEnabled(false);
        }
    }

    @Override
    public void run() {
        this.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("FeedBinGUI update called");

        if (binsCombox.getSelectedItem().equals(defaultCBStr)) {
            setTitle("FeedBin Controller");
        } else {
            setTitle("FeedBin Controller (Bin " +
                    bin.getBinNumber() + ")");
        }

        // Call the bin update method to update the bin
        // in the database anytime there is a change
        try {
            if (bin != null) {
                bin.updateBinInDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
