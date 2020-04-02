/**
 * Luis Maldonado
 * CPSC 240 Section 2
 * Professor Ravishankar
 * I Pledge....
 */
public class OfficeManager extends LoginAccount {
    private String smWHname;
    private Warehouse ManagerWH;

    public OfficeManager(Person user, String uName, String pWord) {
        super(user, uName, pWord,3);
        this.ManagerWH = new Warehouse(smWHname);
        this.smWHname = user.getLastName() +"WareHouse";
    }
    public Warehouse getWH(){
        return this.ManagerWH;
    }
}

