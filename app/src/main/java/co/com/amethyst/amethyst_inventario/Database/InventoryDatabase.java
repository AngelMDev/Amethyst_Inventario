package co.com.amethyst.amethyst_inventario.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Angel on 4/12/2016.
 */
public class InventoryDatabase {
   public static class Inventory
   {
       public static final String TABLE_NAME="inventory";
       public static final String ID="referencia";
       public static final String NAME="nombre";
       public static final String AVAILABLE="disponible";
       public static final String SOLD="vendido";
   }

    public static class Detail
    {
        public static final String CUSTOMER="cliente";
        public static final String DATE="date";
        public static final String QTY="cantidad";
        public static final String PRICE="Precio";
    }
}
