package co.com.amethyst.amethyst_inventario.Activities;

import android.content.Context;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import co.com.amethyst.amethyst_inventario.Adapters.AddInventoryAdapter;
import co.com.amethyst.amethyst_inventario.Adapters.SaleAdapter;
import co.com.amethyst.amethyst_inventario.ArrayListHelpers.SaleItems;
import co.com.amethyst.amethyst_inventario.Database.InventoryDBHelper;
import co.com.amethyst.amethyst_inventario.R;

public class NewSaleActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> productList = new ArrayList<>();
    ArrayList<String> qtyDataset = new ArrayList<>();
    ArrayList<String> prDataset = new ArrayList<>();
    ArrayList<String> spDataset = new ArrayList<>();
    SaleAdapter adapter;
    InventoryDBHelper helper;
    SQLiteDatabase db;
    EditText editText;
    TextView tv;
    private static final String TAG = NewSaleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale);
        toolbar = (Toolbar) findViewById(R.id.tool_bar_sale);
        setSupportActionBar(toolbar);
        tv = (TextView) findViewById(R.id.total_value);
        getSupportActionBar().setTitle("Nueva venta...");
        recyclerView = (RecyclerView) findViewById(R.id.salesList);
        layoutManager = new LinearLayoutManager(this);
        ArrayList<SaleItems> data = new ArrayList<>();
        SaleItems saleItems = new SaleItems(1);
        data.add(0, saleItems);
        helper=new InventoryDBHelper(this);
        db=helper.getWritableDatabase();
        adapter = new SaleAdapter(this, data, tv);
        recyclerView.setAdapter(adapter);
        editText=(EditText)findViewById(R.id.customerNameET);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if(viewHolder instanceof AddInventoryAdapter.ViewHolder2) return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position=viewHolder.getAdapterPosition();
                adapter.remove(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sale, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cancel) {
            finish();
        } else if (id == R.id.action_save_sale) {
            Log.d(TAG, "onOptionsItemSelected: Value Null");
            prDataset = adapter.getPrDataset();
            qtyDataset = adapter.getQtyDataset();
            spDataset = adapter.getSpStrDataset();
            int parsedQty=0;
            float parsedPrice=0f;
            String name;

            if (spDataset != null & prDataset != null & qtyDataset != null) {
                for (int i = 0; i <spDataset.size() ; i++) {
                    parsedPrice=Float.parseFloat(prDataset.get(i));
                    parsedQty=Integer.parseInt(qtyDataset.get(i));
                    name=spDataset.get(i);
                    helper.updateSoldByName(name,parsedQty,db);
                    DateFormat df = new SimpleDateFormat("EEE, dd/MMM/yyyy, hh:mm a");
                    String date = df.format(Calendar.getInstance().getTime());
                    helper.addDetailSales(name,date,editText.getText().toString(),parsedPrice,parsedQty,db);
                    finish();
                }

            }

        }
        return super.onOptionsItemSelected(item);

    }

    public ArrayList<String> refreshEntries(Context context) {

        productList = refreshArray(context);

        return productList;
    }

    public ArrayList<String> refreshArray(Context context) {
        SQLiteDatabase db;
        Cursor cursor;
        InventoryDBHelper inventoryDBHelper;
        productList.clear();
        productList.add("Producto");
        inventoryDBHelper = new InventoryDBHelper(context);
        db = inventoryDBHelper.getReadableDatabase();
        cursor = inventoryDBHelper.getName(db);
        do {
            try {
                productList.add(cursor.getString(0));
            } catch (Exception e) {
                Log.d(TAG, "refreshArray: Couldn't add. Exception: " + e);
            }
        }
        while (cursor.moveToNext() && !cursor.isNull(0));
        return productList;
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
