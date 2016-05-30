package co.com.amethyst.amethyst_inventario.Fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import co.com.amethyst.amethyst_inventario.Adapters.InventoryAdapter;
import co.com.amethyst.amethyst_inventario.Database.InventoryDBHelper;
import co.com.amethyst.amethyst_inventario.ArrayListHelpers.InventoryInfo;
import co.com.amethyst.amethyst_inventario.R;

public class InventoryFragment extends Fragment {

    ArrayList<InventoryInfo> arrayList = new ArrayList<>();
    private InventoryAdapter inventoryAdapter;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    SQLiteDatabase sqLiteDatabase;
    InventoryDBHelper inventoryDBHelper;
    Cursor cursor;
    Button button;
    int pos=5;
    public static InventoryFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        InventoryFragment fragment = new InventoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ArrayList<InventoryInfo> refreshArray(){
        arrayList.clear();
        sqLiteDatabase = inventoryDBHelper.getReadableDatabase();
        cursor = inventoryDBHelper.getInformation(sqLiteDatabase);
        do {
            try {
                InventoryInfo inventoryInfo = new InventoryInfo(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                arrayList.add(inventoryInfo);
            } catch (Exception e) {
                Log.d("ARRAY","Couldnt add. Exception: "+e);
            }
        }
        while (cursor.moveToNext() && !cursor.isNull(0));
        inventoryDBHelper.close();
        return arrayList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new LinearLayoutManager(this.getContext());
        inventoryDBHelper = new InventoryDBHelper(this.getContext());
        Log.d("FRAGMENT","onCreate");
    }

    public void refreshEntries(){

        arrayList=refreshArray();
        if (!arrayList.isEmpty()) {
            adapter = new InventoryAdapter(this.getContext(), arrayList);
            Log.d("ADAPTER","not empty");
        }
        else
            Log.d("ADAPTER","Empty");
        recyclerView.setAdapter(adapter);
        sqLiteDatabase.close();
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshEntries();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_inventory, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.inventoryList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        refreshEntries();
        return layout;
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
