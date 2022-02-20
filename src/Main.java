import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<FeedBin> feedBinArray = new ArrayList<FeedBin>();

        FeedBin feedBin1 = new FeedBin(1, "--");
        FeedBin feedBin2 = new FeedBin(2, "--");
        FeedBin feedBin3 = new FeedBin(3, "--");

        feedBinArray.add(feedBin1);
        feedBinArray.add(feedBin2);
        feedBinArray.add(feedBin3);

        // FeedBinGUI
        FeedBinGUI fbGUI1 = new FeedBinGUI(feedBinArray);
        Thread fbThread1 = new Thread(fbGUI1);

        // SupervisorGUI
        SupervisorGUI supGUI = new SupervisorGUI(feedBinArray);
        Thread supThread1 = new Thread(supGUI);

        // Start the threads
        fbThread1.start();
        supThread1.start();
    }
}
