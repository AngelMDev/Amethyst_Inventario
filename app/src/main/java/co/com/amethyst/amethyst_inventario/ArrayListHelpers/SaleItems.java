package co.com.amethyst.amethyst_inventario.ArrayListHelpers;

/**
 * Created by Angel on 5/21/2016.
 */
public class SaleItems {
    int editTextId;
    int spinnerId;
    int id;
    public SaleItems(int id){
        this.setId(id);
    }

    public int getEditTextId() {
        return editTextId;
    }

    public void setEditTextId(int editTextId) {
        this.editTextId = editTextId;
    }

    public int getSpinnerId() {
        return spinnerId;
    }

    public void setSpinnerId(int spinnerId) {
        this.spinnerId = spinnerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
