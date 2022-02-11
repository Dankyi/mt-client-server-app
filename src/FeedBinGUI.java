import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FeedBinGUI extends JFrame {
    private JMenuBar jmbTop;
    private JMenu binMenu;
    private JMenuItem inspect;
    private JMenuItem fill;
    private JMenuItem flush;
    private JMenuItem exit;
    // here's the system object behind the interface
    private FeedBin bin;

    public FeedBinGUI() {
        // create a feed bin
        bin = new FeedBin(34, "Weety Bits");

        // Create the menu components
        jmbTop = new JMenuBar();
        binMenu = new JMenu("Bin");
        inspect = new JMenuItem("Inspect the Bin ...");
        fill = new JMenuItem("Add More Product ...");
        flush = new JMenuItem("Flush the Bin ...");
        exit = new JMenuItem("Exit");
        binMenu.add(inspect);
        binMenu.add(fill);
        binMenu.add(flush);
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
                InspectDialog id = new InspectDialog(FeedBinGUI.this,true,bin);
                id.setVisible(true);
            }
        });

        fill.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                FillDialog fd = new FillDialog(FeedBinGUI.this,true,bin);
                fd.setVisible(true);
            }
        });

        flush.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                bin.flush();
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


}
