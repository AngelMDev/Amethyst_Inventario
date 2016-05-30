package co.com.amethyst.amethyst_inventario.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.amethyst.amethyst_inventario.Adapters.DetailAdapter;
import co.com.amethyst.amethyst_inventario.ArrayListHelpers.DetailSales;
import co.com.amethyst.amethyst_inventario.Database.InventoryDBHelper;
import co.com.amethyst.amethyst_inventario.R;

public class Edititem extends AppCompatActivity {
    private Toolbar toolbar;
    private int index=0;
    private int pos=0;
    EditText idTV, name, ava, sold;
    SQLiteDatabase db;
    Cursor cursor;
    InventoryDBHelper inventoryDBHelper;
    int avaInt,soldInt;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DetailSales> arrayList=new ArrayList<>();
    Cursor cursor1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edititem);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar...");
        idTV=(EditText) findViewById(R.id.detailRef);
        name=(EditText) findViewById(R.id.detailName);
        ava=(EditText) findViewById(R.id.detailAvailable);
        sold=(EditText) findViewById(R.id.detailSold);
        recyclerView=(RecyclerView)findViewById(R.id.detailRecycler);
        String indexS=getIntent().getStringExtra("INDEX");
        String posS=getIntent().getStringExtra("POS");
        Log.d("POS",posS);
        pos=Integer.parseInt(posS);
        inventoryDBHelper=new InventoryDBHelper(this);
        db = inventoryDBHelper.getWritableDatabase();
        index=Integer.parseInt(indexS);
        List<String> valuesList=inventoryDBHelper.getDetail(index,db);
        idTV.setText(valuesList.get(0));
        name.setText(valuesList.get(1));
        ava.setText(valuesList.get(2));
        sold.setText(valuesList.get(3));
        setArrayListData();
        layoutManager=new LinearLayoutManager(this);
        adapter=new DetailAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

    }

    private void setArrayListData() {
        String itemname=name.getText().toString();
        itemname=itemname.replaceAll(" ", "_").toLowerCase();
        SQLiteDatabase sqlite=inventoryDBHelper.getReadableDatabase();
        cursor1=inventoryDBHelper.getDetailSales(sqlite,itemname);
        cursor1.moveToFirst();
        do {
            try {
                DetailSales details = new DetailSales(cursor1.getString(0),cursor1.getString(1),cursor1.getFloat(2),cursor1.getInt(3));
                arrayList.add(details);
            } catch (Exception e) {
                Log.d("ARRAY","Couldnt add. Exception: "+e);
            }
        }
        while (cursor1.moveToNext() && !cursor1.isNull(0));
        inventoryDBHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final InventoryDBHelper inventoryDBHelper=new InventoryDBHelper(this);
        int ref=Integer.parseInt(idTV.getText().toString());
        if(id==R.id.action_save){
            if(ava.getText().toString() .equals("")){
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
                inventoryDBHelper.editEntry(index, ref, name.getText().toString(),avaInt,soldInt, db);

                Toast.makeText(this, "Producto editado.", Toast.LENGTH_LONG).show();
                finish();
            }
        else if(id==R.id.action_delete){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Desea eliminar este producto?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            localDeleteEntry();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }

        return super.onOptionsItemSelected(item);

    }

    private void localDeleteEntry() {
        inventoryDBHelper.deleteEntry(index,db);
        Toast.makeText(this, "Producto eliminado.", Toast.LENGTH_LONG).show();
    }

    public int getLatestPos(){
        return pos;
    }

    private boolean checkDuplicates(int a) {
        cursor = inventoryDBHelper.getID(db);
        do {
            try {
                if (a == cursor.getInt(0)) {
                    Toast.makeText(this, "Esta referencia ya está usada", Toast.LENGTH_LONG).show();
                    return true;
                } else
                    return false;
            } catch (Exception e) {
                Log.d("ARRAY", "Couldnt add. Exception: " + e);
            }
        }
        while (cursor.moveToNext() && !cursor.isNull(0));
        return false;
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }



        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
