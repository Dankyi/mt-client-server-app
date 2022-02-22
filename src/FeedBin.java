import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Observable;

/*
    A class for modelling the state and operation of an 'animal feed bin'
    as used in the factory production of animal feedstuffs
*/
public class FeedBin extends Observable {
    // FeedBin instance variables
    private int binNumber;
    private String productName;
    private double maxVolume;
    private double currentVolume;

    // method FeedBin : constructor
    public FeedBin(int binNo, String prodName) {
        binNumber = binNo; // bin identifier
        productName = prodName; // product in bin
        maxVolume = 40.0; // maximum capacity in cubic metres
        currentVolume = 0.0; // bin starts in the empty state
    }

    public FeedBin(int binNumber, String productName, double maxVolume, double currentVolume) {
        this.binNumber = binNumber;
        this.productName = productName;
        this.maxVolume = maxVolume;
        this.currentVolume = currentVolume;
    }

    // method setProductName - used to change the product assigned to the bin
    // can only do this if the bin is empty
    // i.e. use flush() method first
    public boolean setProductName(String newName) {
        if (currentVolume == 0.0) {
            productName = newName;
            notifyObs();
            return true;
        } else
            return false;
    }

    public void setBinNumber(int binNumber) {
        this.binNumber = binNumber;
        notifyObs();
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
        notifyObs();
    }

    public void setCurrentVolume(double currentVolume) {
        this.currentVolume = currentVolume;
        notifyObs();
    }

    // method flush - used to completely empty the bin
    public void flush() {
        productName = "--";
        currentVolume = 0.0;
        notifyObs();
    }

    // method addProduct - can only add if there is sufficient room
    public boolean addProduct(double volume) {
        if (maxVolume >= currentVolume + volume) {
            currentVolume += volume;
            notifyObs();
            return true;
        } else
            return false;
    }

    // method removeProduct - removes quantity requested only if
    // its not greater than the currently available quantity
    public boolean removeProduct(double volume) {
        if (currentVolume >= volume) {
            currentVolume -= volume;
            notifyObs();
            return true;
        } else {
            return false;
        }
    }

    // accessor methods for each FeedBin instance variable
    public int getBinNumber() {
        return binNumber;
    }

    public String getProductName() {
        return productName;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public double getCurrentVolume() {
        return currentVolume;
    }

    // fire notifications to any registered Observers
    private void notifyObs() {
        setChanged();
        notifyObservers();
    }

    // This updates a bin's details in the database
    public void updateBinInDB() throws SQLException {
        //1. Connect to the database
        DBConfig dbConfig = new DBConfig();
        Connection conn = dbConfig.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            // 2. Create a string that holds our SQL update command with ? for user inputs
            String sql = "UPDATE feedbins SET binnumber = ?, productname = ?, maxvolume = ?, currentvolume = ? "
                    + "WHERE binnumber = ?";

            // 3. Prepare the query against SQL Injection
            preparedStatement = conn.prepareStatement(sql);

            // 4. Bind the parameters
            preparedStatement.setInt(1, binNumber);
            preparedStatement.setString(2, productName);
            preparedStatement.setDouble(3, maxVolume);
            preparedStatement.setDouble(4, currentVolume);
            preparedStatement.setInt(5, binNumber);

            // 5. Run the command on the SQL Server
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            if (conn != null){
                conn.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
    }
}

