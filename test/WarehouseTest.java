import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

import static org.junit.Assert.*;

public class WarehouseTest {


    @Test
    public void addToInventory() {
        Warehouse Warehouse1 = new Warehouse("Number 1");
        BikePart Part1 = new BikePart("10spFrontDerailuer,1234567897,60.0,50.5,true,10");
        BikePart Part2 = new BikePart("10spFrontDerailuer,1234567833,60.0,50.5,true,5");
        BikePart Part3 = new BikePart("10spFrontDerailuer,1234567134,60.0,50.5,true,10");
        Collections.addAll(Warehouse1.Inventory(),Part1,Part2,Part3);

        assertEquals("10spFrontDerailuer,1234567897,60.0,50.5,true,10",Warehouse1.Inventory().get(0).getInfo());
    }

    @Test
    public void getPartFromInventory() {
        Warehouse Warehouse1 = new Warehouse("Number 1");
        BikePart Part1 = new BikePart("10spFrontDerailuer,1234567897,60.0,50.5,true,10");
        BikePart Part2 = new BikePart("10spFrontDerailuer,1234567833,60.0,50.5,true,5");
        BikePart Part3 = new BikePart("10spFrontDerailuer,1234567134,60.0,50.5,true,10");
        Collections.addAll(Warehouse1.Inventory(),Part1,Part2,Part3);
        assertEquals(10,Warehouse1.Inventory().get(0).getQuantity());
    }

    @Test
    public void inventory() {
        Warehouse Warehouse1 = new Warehouse("Number 1");
        BikePart Part1 = new BikePart("10spFrontDerailuer,1234567897,60.0,50.5,true,10");
        BikePart Part2 = new BikePart("10spFrontDerailuer,1234567833,60.0,50.5,true,5");
        BikePart Part3 = new BikePart("10spFrontDerailuer,1234567134,60.0,50.5,true,10");
        Collections.addAll(Warehouse1.Inventory(),Part1,Part2,Part3);


        String inventory = Warehouse1.Inventory().get(0).getInfo() + Warehouse1.Inventory().get(1).getInfo() + Warehouse1.Inventory().get(2).getInfo();
        String right = "10spFrontDerailuer,1234567897,60.0,50.5,true,10"+"10spFrontDerailuer,1234567833,60.0,50.5,true,5"+"10spFrontDerailuer,1234567134,60.0,50.5,true,10";
        assertEquals(right,inventory);
    }

    @Test
    public void getWarehouseName() {
        Warehouse Warehouse1 = new Warehouse("Number 1");
        assertEquals("Number 1",Warehouse1.getWarehouseName());
    }

    @Test
    public void getTxtFileName() {
        Warehouse Warehouse1 = new Warehouse("Number 1");
        assertNull(null,Warehouse1.getTxtFileName());
    }

    @Test
    public void setTxtFileName() {
        Warehouse Warehouse1 = new Warehouse("Number 1");
        Warehouse1.setTxtFileName("JasonTxt");
        Warehouse1.setTxtFileName("JohnTxt");
        assertEquals("JohnTxt",Warehouse1.getTxtFileName());
    }
}