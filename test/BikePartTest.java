/**
 * I pledge...
 * @author Brittany Margelos
 * @author Benjamin Hichak
 * @author Luis Maldonado
 * CPCS 240 2-3:50
 */
import org.junit.Test;
import static org.junit.Assert.*;


public class BikePartTest {
    @Test
    public void testGetName() {
        BikePart name1 = new BikePart("carbonHandleBars42cm,1234567893,47.00,5.58,true,25");
        assertEquals("carbonHandleBars42cm", name1.getName());
    }

    @Test
    public void testSetName() {
        BikePart name = new BikePart("carbonHandleBars42cm,1234567893,47.00,5.58,true,25");
        name.setName("carbonHandleBars42cm");
        assertEquals("carbonHandleBars42cm", name.getName());
    }

    @Test
    public void testGetPartNumber() {
        BikePart number = new BikePart("FrontDerailuer,1234567899,60.00,50.50,true,10");
        assertEquals(1234567899, number.getPartNumber());
    }

    @Test
    public void testSetPartNumber() {
        BikePart num = new BikePart("FrontDerailuer,1234567899,60.00,50.50,true,10");
        num.setPartNumber(1234567899);
        assertEquals(1234567899, num.getPartNumber());

    }

    @Test
    public void testGetPrice() {
        BikePart price = new BikePart("mensBibsMedium,1234567900,110.00,99.00,false,5");
        assertEquals(110.00, 99.00, price.getPrice());
    }


    @Test
    public void testSetPrice() {
        BikePart price1 = new BikePart("mensBibsMedium,1234567900,110.00,99.00,false,5");
        price1.setPrice(110.00);
        assertEquals(110.00, 110.00, price1.getPartNumber());

    }

    @Test
    public void testGetSalesPrice() {
        BikePart sale = new BikePart("WTB_saddle1,1234567890,17.00,15.00,true,25");
        assertEquals(17.00, 15.00, sale.getSalesPrice());
    }


    @Test
    public void testSetSalesPrice() {
        BikePart sale1 = new BikePart("WTB_saddle,1234567890,17.00,15.00,true,25");
        sale1.setPrice(17.00);
        assertEquals(17.00, 15.00, sale1.getSalesPrice());

    }

    @Test
    public void testGetOnSale() {
        BikePart onSale = new BikePart("11spFrontDerailuer,1234567899,60.00,50.50,false,10");
        assertEquals(false, onSale.getOnSale());
        //when I tried putting true there ^ it gave back an error. It must be the same as the true/false in the ()
    }

    @Test
    public void testSetOnSale() {
        BikePart onSale1 = new BikePart("WTB_saddle,1234567890,17.00,15.00,false,25");
        onSale1.setOnSale(false);
        assertEquals(false, onSale1.getOnSale());
    }

    @Test
    public void testGetQuantity() {
        BikePart quan = new BikePart("11spRearDerailuer,1234567898,60.00,50.50,true,10");
        assertEquals(10, quan.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        BikePart quan1 = new BikePart("11spRearDerailuer,1234567898,60.00,50.50,true,10");
        quan1.setQuantity(10);
        assertEquals(10, quan1.getQuantity());
    }

    @Test
    public void testGetInfo() {
        BikePart info = new BikePart("10spFrontDerailuer,1234567897,60.0,50.0,true,10");
        assertEquals("10spFrontDerailuer,1234567897,60.0,50.0,true,10", info.getInfo());
    }

}//end of BikePartTest









