import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<FeedBin> feedBinsArray = new ArrayList<FeedBin>();

        // Fetch the Feedbins from database and store in an arraylist
        try {
            DBConfig dbConfig = new DBConfig();
            Connection conn = dbConfig.getConnection();
            Statement statement = null;
            ResultSet resultSet = null;

            statement = conn.createStatement();
            String sql = "SELECT * FROM feedbins";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int binNumber = resultSet.getInt("binnumber");
                String productName = resultSet.getString("productname");
                double maxVolume = resultSet.getDouble("maxvolume");
                double currentVolume = resultSet.getDouble("currentvolume");

                // Create a FeedBin object for row of data fetched
                FeedBin feedBin = new FeedBin(binNumber, productName,
                        maxVolume, currentVolume);

                // Add the FeedBin in the ArrayList
                feedBinsArray.add(feedBin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        FeedBin feedBin1 = new FeedBin(1, "--");
//        FeedBin feedBin2 = new FeedBin(2, "--");
//        FeedBin feedBin3 = new FeedBin(3, "--");
//
//        feedBinsArray.add(feedBin1);
//        feedBinsArray.add(feedBin2);
//        feedBinsArray.add(feedBin3);

        // FeedBinGUI
        FeedBinGUI fbGUI1 = new FeedBinGUI(feedBinsArray);
        Thread fbThread1 = new Thread(fbGUI1);

        // SupervisorGUI
        SupervisorGUI supGUI = new SupervisorGUI(feedBinsArray);
        Thread supThread1 = new Thread(supGUI);

        // Start the threads
        fbThread1.start();
        supThread1.start();
    }
}
