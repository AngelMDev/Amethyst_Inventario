package co.com.amethyst.amethyst_inventario.ArrayListHelpers;

/**
 * Created by Angel on 4/12/2016.
 */
public class InventoryInfo {
    private int id;
    private String name;
    private int available;
    private int sold;
    public InventoryInfo(int id,String name,int available,int sold)
    {
        this.setId(id);
        this.setName(name);
        this.setAvailable(available);
        this.setSold(sold);
    }
    public InventoryInfo (String name){
        this.setId(id);
        this.setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
