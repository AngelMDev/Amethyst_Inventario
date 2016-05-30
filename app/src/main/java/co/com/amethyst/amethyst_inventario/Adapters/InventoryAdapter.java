package co.com.amethyst.amethyst_inventario.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.amethyst.amethyst_inventario.Activities.AddItem;
import co.com.amethyst.amethyst_inventario.Activities.Edititem;
import co.com.amethyst.amethyst_inventario.ArrayListHelpers.InventoryInfo;
import co.com.amethyst.amethyst_inventario.R;

/**
 * Created by Angel on 4/12/2016.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MainViewHolder> {
    private Context context;
    Context contextVH1;
    ArrayList<InventoryInfo> arrayList = new ArrayList<>();

    public InventoryAdapter(Context context, ArrayList<InventoryInfo> data) {
        this.arrayList = data;
        this.context = context;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_inventory, parent, false);
            MyViewHolder1 holder = new MyViewHolder1(view);
            contextVH1 = parent.getContext();
            return holder;
        } else {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_item, parent, false);
            MyViewHolder2 holder2 = new MyViewHolder2(view1);
            return holder2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (position == getItemCount()-1) {
            viewType = 1;
        }
        Log.d("VIEWTYPE", "Pos "+position+"");
        Log.d("VIEWTYPE.", "Size "+getItemCount()+"");
        Log.d("VIEWTYPE.", "Type "+viewType+"");
        return viewType;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            MyViewHolder1 myViewHolder = (MyViewHolder1) holder;
            setUpView1(position, myViewHolder);
        } else {
            MyViewHolder2 myViewHolder = (MyViewHolder2) holder;
        }
    }

    private void setUpView1(int position, MyViewHolder1 holder) {
        InventoryInfo inventoryInfo = arrayList.get(position);
        holder.id.setText(Integer.toString(inventoryInfo.getId()));
        holder.name.setText(inventoryInfo.getName());
        holder.available.setText(Integer.toString(inventoryInfo.getAvailable()));
        holder.sold.setText(Integer.toString(inventoryInfo.getSold()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size()+1;
    }


    class MyViewHolder1 extends MainViewHolder implements View.OnClickListener {
        TextView id, name, available, sold;

        public MyViewHolder1(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.productId);
            name = (TextView) itemView.findViewById(R.id.productName);
            available = (TextView) itemView.findViewById(R.id.availableUnits);
            sold = (TextView) itemView.findViewById(R.id.soldUnits);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(itemView.getContext(), Edititem.class);
            intent.putExtra("INDEX", String.valueOf(arrayList.get(getAdapterPosition()).getId()));
            intent.putExtra("POS", String.valueOf(getAdapterPosition()));
            context.startActivity(intent);
        }
    }

    class MyViewHolder2 extends MainViewHolder implements View.OnClickListener {

        public MyViewHolder2(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(itemView.getContext(),AddItem.class);
            context.startActivity(intent);
        }
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

}
