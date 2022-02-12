import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedBinGUI extends JFrame {
    private JMenuBar jmbTop;
    private JMenu binMenu;
    private JMenuItem inspect;
    private JMenuItem fill;
    private JMenuItem removeQty;
    private JMenuItem renameProd;
    private JMenuItem flush;
    private JMenuItem newProd;
    private JMenuItem exit;
    // here's the system object behind the interface
    private FeedBin bin;

    public FeedBinGUI() {
        // create a feed bin
        bin = new FeedBin(34, "Weety Bits");

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

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });

        inspect.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                InspectDialog id = new InspectDialog(FeedBinGUI.this,true, bin);
                id.setVisible(true);
            }
        });

        fill.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                FillDialog fd = new FillDialog(FeedBinGUI.this,true, bin);
                fd.setVisible(true);
            }
        });

        removeQty.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                RemoveQtyDialog rd = new RemoveQtyDialog(FeedBinGUI.this,true, bin);
                rd.setVisible(true);
            }
        });

        renameProd.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                RenameProdDialog rd = new RenameProdDialog(FeedBinGUI.this,true, bin);
                rd.setVisible(true);
            }
        });

        newProd.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                AddNewProdDialog rd = new AddNewProdDialog(FeedBinGUI.this,true, bin);
                rd.setVisible(true);
                
                if (bin.getProductName().isEmpty()) {
                    disableFields();
                } else {
                    enableFields();
                }
            }
        });

        flush.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                bin.flush();
                disableFields();
                JOptionPane.showMessageDialog(
                        FeedBinGUI.this,
                        "The bin is now empty",
                        "Flush Confirmation",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
            }
        });
        setTitle ("Feed Bin Controller");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(400, 200);
    }

    public static void main (String args[]) {
        FeedBinGUI demo = new FeedBinGUI();
        demo.setLocation(400,400);
        demo.setVisible(true);
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
}
