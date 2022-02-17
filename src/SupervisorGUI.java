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
        checkBatch = new JMenuItem("Check Batch");
        report = new JMenuItem("Report");
        makeBatch = new JMenuItem("Make up Batch");
        checkBinsStatus = new JMenuItem("Check Bins Status");
        exit = new JMenuItem("Exit");
        binMenu.add(checkBatch);
        binMenu.add(report);
        binMenu.add(makeBatch);
        binMenu.add(checkBinsStatus);
        binMenu.add(new JSeparator());
        binMenu.add(exit);
        jmbTop.add(binMenu);
        setJMenuBar(jmbTop);
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
