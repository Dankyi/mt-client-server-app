import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SupervisorGUI extends JFrame implements Runnable, Observer {
    private FeedBin bin;
    private ArrayList<FeedBin> feedBins = new ArrayList<>();
    private JMenuBar jmbTop;
    private JMenu binMenu;
    private JMenuItem checkBatch;
    private JMenuItem report;
    private JMenuItem makeBatch;
    private JMenuItem checkBinsStatus;
    private JMenuItem exit;

    public SupervisorGUI(ArrayList<FeedBin> feedBinArray) {
        super();
        for (FeedBin fb : feedBinArray) {
            bin = fb;
            bin.addObserver(this);
            feedBins.add(bin);
        }

        initSupGUI();
    }

    private void initSupGUI() {
        jmbTop = new JMenuBar();
        binMenu = new JMenu("Supervisor");
        checkBinsStatus = new JMenuItem("Check Status of Bins");
        checkBatch = new JMenuItem("Check Batch");
        report = new JMenuItem("Batch Report");
        makeBatch = new JMenuItem("Make up a Batch");
        exit = new JMenuItem("Exit");
        binMenu.add(checkBinsStatus);
        binMenu.add(checkBatch);
        binMenu.add(report);
        binMenu.add(makeBatch);
        binMenu.add(new JSeparator());
        binMenu.add(exit);
        jmbTop.add(binMenu);
        setJMenuBar(jmbTop);

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });

        checkBinsStatus.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                CheckStatusDialog binStatus = new CheckStatusDialog(SupervisorGUI.this, true, feedBins);
                binStatus.setVisible(true);
            }
        });

        checkBatch.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                CheckBatchDialog checkatch = new CheckBatchDialog(SupervisorGUI.this, true, feedBins);
                checkatch.setVisible(true);
            }
        });
    }

    @Override
    public void run() {
        this.setVisible(true);
        setTitle("Supervisor Controller");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("=============================");
        System.out.println("SupervisorGUI update called");
        for (FeedBin fb : feedBins) {
            System.out.println("Bin Number: " + fb.getBinNumber() +
                    "\nProduct Name: " + fb.getProductName() +
                    "\nMaximum Volume: " + fb.getMaxVolume() +
                    "\nCurrent Volume: " + fb.getCurrentVolume() +
                    "\n-----------------------------"
            );
        }
    }
}
