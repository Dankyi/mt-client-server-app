import static org.junit.jupiter.api.Assertions.*;

class FeedBinTest {
    private FeedBin bin;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        bin = new FeedBin(1, "Weety Bits");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void setProductName() {
        bin.setProductName("Cornmeal");
        assertEquals(bin.getProductName(), "Cornmeal");
    }

    @org.junit.jupiter.api.Test
    void setBinNumber() {
        bin.setBinNumber(2);
        assertEquals(bin.getBinNumber(), 2);
    }

    @org.junit.jupiter.api.Test
    void setMaxVolume() {
        bin.setMaxVolume(50);
        assertEquals(bin.getMaxVolume(), 50);
    }

    @org.junit.jupiter.api.Test
    void setCurrentVolume() {
        bin.setCurrentVolume(20);
        assertEquals(bin.getCurrentVolume(), 20);
    }

    @org.junit.jupiter.api.Test
    void flush() {
        bin.flush();
        assertEquals(bin.getBinNumber(), 1);
        assertEquals(bin.getProductName(), "--");
        assertEquals(bin.getMaxVolume(), 40);
        assertEquals(bin.getCurrentVolume(), 0.0);
    }

    @org.junit.jupiter.api.Test
    void addProduct() {
        assertTrue(bin.addProduct(30));
        assertFalse(bin.addProduct(50));
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        bin.addProduct(40);
        assertTrue(bin.removeProduct(20));
        assertFalse(bin.removeProduct(60));
    }

    @org.junit.jupiter.api.Test
    void getBinNumber() {
        assertEquals(bin.getBinNumber(), 1);
    }

    @org.junit.jupiter.api.Test
    void getProductName() {
        assertEquals(bin.getProductName(), "Weety Bits");
    }

    @org.junit.jupiter.api.Test
    void getMaxVolume() {
        assertEquals(bin.getMaxVolume(), 40);
    }

    @org.junit.jupiter.api.Test
    void getCurrentVolume() {
        assertEquals(bin.getCurrentVolume(), 0.0);

        bin.setCurrentVolume(30);
        assertEquals(bin.getCurrentVolume(), 30);
    }
}