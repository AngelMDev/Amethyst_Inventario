package co.com.amethyst.amethyst_inventario.ArrayListHelpers;

/**
 * Created by Angel on 5/23/2016.
 */
public class DetailSales {
    private int qty;
    private String date;
    private String customer;
    private float price;
public DetailSales(String date, String customer, float price, int qty){
    this.setDate(date);
    this.setCustomer(customer);
    this.setPrice(price);
    this.setQty(qty);
}

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}
