package co.com.amethyst.amethyst_inventario.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 4/12/2016.
 */
public class InventoryDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="amethyst_db";
    public static final int DB_VERSION=1;
    public static final String CREATE_QUERY="create table " + InventoryDatabase.Inventory.TABLE_NAME+" ( "+ InventoryDatabase.Inventory.ID+" integer,"+ InventoryDatabase.Inventory.NAME+" text, "
            + InventoryDatabase.Inventory.AVAILABLE+" int, "+ InventoryDatabase.Inventory.SOLD+" int);";
    private static  final String DROP_QUERY="drop table if exists "+ InventoryDatabase.Inventory.TABLE_NAME+";";
    public static final String GET_ENTRY="SELECT * FROM "+ InventoryDatabase.Inventory.TABLE_NAME+" WHERE "+ InventoryDatabase.Inventory.ID+" = ";
    public static final String GET_ENTRY_BY_NAME="SELECT * FROM "+ InventoryDatabase.Inventory.TABLE_NAME+" WHERE "+ InventoryDatabase.Inventory.NAME+" LIKE '";
    public InventoryDBHelper (Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        addEntry(01,"Capa Amber",0,0,db);
        addEntry(02,"Capa Cuarzo",0,0,db);
        addEntry(03,"Cuello Jaspe",0,0,db);
        createTable(db,"capa_amber");
        createTable(db,"capa_cuarzo");
        createTable(db,"cuello_jaspe");
        copyDBToSDCard();
    }

    public void createTable(SQLiteDatabase db, String name){
        name = name.replaceAll(" ", "_").toLowerCase();
        db.execSQL("CREATE TABLE '"+name+"' ("+InventoryDatabase.Detail.DATE+" text,"+ InventoryDatabase.Detail.CUSTOMER+" text, "+ InventoryDatabase.Detail.PRICE+" real, "+InventoryDatabase.Detail.QTY+" integer);" );
    }

public void addDetailSales(String name,String date,String customer,float price,int qty, SQLiteDatabase db){
    ContentValues contentValues=new ContentValues();
    contentValues.put(InventoryDatabase.Detail.DATE,date);
    contentValues.put(InventoryDatabase.Detail.CUSTOMER,customer);
    contentValues.put(InventoryDatabase.Detail.PRICE,price);
    contentValues.put(InventoryDatabase.Detail.QTY,qty);
    name = name.replaceAll(" ", "_").toLowerCase();
    Long l=db.insert(name+"",null,contentValues);
}

    public Cursor getDetailSales(SQLiteDatabase db, String name)
    {
        String[] columns={InventoryDatabase.Detail.DATE, InventoryDatabase.Detail.CUSTOMER, InventoryDatabase.Detail.PRICE, InventoryDatabase.Detail.QTY};
        Cursor cursor=db.query(name+"",columns,null,null,null,null,null);
        return cursor;
    }

    public void copyDBToSDCard() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//co.com.amethyst.amethyst_inventario//databases//"+DB_NAME+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }

            }

        }  catch (Exception e) {

            Log.i("FO","exception="+e);
        }


    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d("DB","onOpen");
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        Log.d("DB","onConfigure");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addEntry(int id,String name, int available, int sold,SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();
        contentValues.put(InventoryDatabase.Inventory.ID,id);
        contentValues.put(InventoryDatabase.Inventory.NAME,name);
        contentValues.put(InventoryDatabase.Inventory.AVAILABLE,available);
        contentValues.put(InventoryDatabase.Inventory.SOLD,sold);
        Long l=db.insert(InventoryDatabase.Inventory.TABLE_NAME,null,contentValues);

    }

    public void editEntry(int oldId, int id,String name, int available, int sold,SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();
        contentValues.put(InventoryDatabase.Inventory.ID,id);
        contentValues.put(InventoryDatabase.Inventory.NAME,name);
        contentValues.put(InventoryDatabase.Inventory.AVAILABLE,available);
        contentValues.put(InventoryDatabase.Inventory.SOLD,sold);
        db.update(InventoryDatabase.Inventory.TABLE_NAME,contentValues, InventoryDatabase.Inventory.ID+"="+oldId,null);

    }

    public void updateSoldByName(String name,int sold, SQLiteDatabase db){
        int available=0;
        int indexAva;
        int indexSold;
        ContentValues contentValues=new ContentValues();
        Cursor cursor=db.rawQuery(GET_ENTRY_BY_NAME+name+"'",null);
        indexAva=cursor.getColumnIndex(InventoryDatabase.Inventory.AVAILABLE);
        indexSold=cursor.getColumnIndex(InventoryDatabase.Inventory.SOLD);
        cursor.moveToFirst();
        available=cursor.getInt(indexAva)-sold;
        sold=cursor.getInt(indexSold)+sold;
        contentValues.put(InventoryDatabase.Inventory.SOLD,sold);
        contentValues.put(InventoryDatabase.Inventory.AVAILABLE,available);
        db.update(InventoryDatabase.Inventory.TABLE_NAME,contentValues, InventoryDatabase.Inventory.NAME+" LIKE '"+name+"'",null);
    }
    public void updateAvaByName(String name,int qty, SQLiteDatabase db){
        int available=0;
        int indexAva;
        ContentValues contentValues=new ContentValues();
        Cursor cursor=db.rawQuery(GET_ENTRY_BY_NAME+name+"'",null);
        indexAva=cursor.getColumnIndex(InventoryDatabase.Inventory.AVAILABLE);
        cursor.moveToFirst();
        available=cursor.getInt(indexAva)+qty;
        contentValues.put(InventoryDatabase.Inventory.AVAILABLE,available);
        db.update(InventoryDatabase.Inventory.TABLE_NAME,contentValues, InventoryDatabase.Inventory.NAME+" LIKE '"+name+"'",null);
    }



    public void deleteEntry(int id,SQLiteDatabase db){
        db.delete(InventoryDatabase.Inventory.TABLE_NAME, InventoryDatabase.Inventory.ID+"="+id,null);
    }

    public void closeDatabase(SQLiteDatabase db){
        try {
            db.close();
        } catch (Exception ignored) {

        }
    }

    public Cursor getInformation(SQLiteDatabase db)
    {
        String[] columns={InventoryDatabase.Inventory.ID, InventoryDatabase.Inventory.NAME, InventoryDatabase.Inventory.AVAILABLE, InventoryDatabase.Inventory.SOLD};
        Cursor cursor=db.query(InventoryDatabase.Inventory.TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }

    public Cursor getID(SQLiteDatabase db){
        String[] columns={InventoryDatabase.Inventory.ID};
        Cursor cursor=db.query(InventoryDatabase.Inventory.TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }

    public Cursor getName(SQLiteDatabase db){
        String[] columns={InventoryDatabase.Inventory.NAME};
        Cursor cursor=db.query(InventoryDatabase.Inventory.TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }


    public List<String> getDetail(int id,SQLiteDatabase db){
        Cursor cursor=db.rawQuery(GET_ENTRY + id,null);
        int idColumn=cursor.getColumnIndex(InventoryDatabase.Inventory.ID);
        int nameColumn=cursor.getColumnIndex(InventoryDatabase.Inventory.NAME);
        int availableColumn=cursor.getColumnIndex(InventoryDatabase.Inventory.AVAILABLE);
        int soldColumn=cursor.getColumnIndex(InventoryDatabase.Inventory.SOLD);
        List<String>detailArray=new ArrayList<>();
        if (cursor.getCount()==1) {
            cursor.moveToFirst();
            detailArray.add(cursor.getString(idColumn));
            detailArray.add(cursor.getString(nameColumn));
            detailArray.add(cursor.getString(availableColumn));
            detailArray.add(cursor.getString(soldColumn));

        }else{
            Log.d("DATABASE","getCount="+cursor.getCount());
        }
        cursor.close();
        return detailArray;
    }
}
