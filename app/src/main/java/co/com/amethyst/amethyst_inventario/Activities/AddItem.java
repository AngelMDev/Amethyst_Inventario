package co.com.amethyst.amethyst_inventario.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.amethyst.amethyst_inventario.Database.InventoryDBHelper;
import co.com.amethyst.amethyst_inventario.R;

/**
 * Created by Angel on 5/17/2016.
 */
public class AddItem extends AppCompatActivity {
    Toolbar toolbar;
    EditText idTV, name, ava, sold;
    SQLiteDatabase db;
    InventoryDBHelper inventoryDBHelper;
    Cursor cursor;
    ArrayList arrayList;
    String ref;
    int avaInt, soldInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar...");
        idTV = (EditText) findViewById(R.id.addID);
        name = (EditText) findViewById(R.id.addName);
        ava = (EditText) findViewById(R.id.addAva);
        sold = (EditText) findViewById(R.id.addSold);
        avaInt=0;
        soldInt=0;
        inventoryDBHelper = new InventoryDBHelper(this);
        db = inventoryDBHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            ref = idTV.getText().toString();
            if(ava.getText().toString().equals("")){
                avaInt=0;
            }
            else{
                avaInt=Integer.parseInt(ava.getText().toString());
            }
            if(sold.getText().toString().equals("")){
                soldInt=0;
            }
            else{
                soldInt=Integer.parseInt(sold.getText().toString());
            }
            if (ref.equals("")) {
                Toast.makeText(this, "El producto debe tener una referencia.", Toast.LENGTH_LONG).show();
            }
            else if(!ref.equals("")) {
                Log.d("IF", "past first check: " + ref);
                if (!checkDuplicates(Integer.parseInt(ref)))
                {
                    inventoryDBHelper.addEntry(Integer.parseInt(idTV.getText().toString()), name.getText().toString(),avaInt , soldInt, db);
                    inventoryDBHelper.createTable(db,name.getText().toString());
                    Toast.makeText(this, "Nuevo producto agregado.", Toast.LENGTH_LONG).show();
                    finish();
                }
        }

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkDuplicates(int a) {
        cursor = inventoryDBHelper.getID(db);
        do {
            try {
                Log.d("CHECK", cursor.getInt(0) + "");
                if (a == cursor.getInt(0)) {
                    Toast.makeText(this, "Esta referencia ya está usada", Toast.LENGTH_LONG).show();
                    Log.d("CHECK", "true");
                    return true;
                }
            } catch (Exception e) {
                Log.d("ARRAY", "Couldnt add. Exception: " + e);
            }
        }
        while (cursor.moveToNext() && !cursor.isNull(0));
        Log.d("CHECK", "false");
        return false;
    }
}

