package co.com.amethyst.amethyst_inventario.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

import co.com.amethyst.amethyst_inventario.Activities.Edititem;
import co.com.amethyst.amethyst_inventario.ArrayListHelpers.DetailSales;
import co.com.amethyst.amethyst_inventario.R;

/**
 * Created by Angel on 5/22/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MainViewHolder> {

    ArrayList<DetailSales> arrayList;
    LayoutInflater inflater;
    Format format= NumberFormat.getCurrencyInstance();
    public DetailAdapter(Context context, ArrayList<DetailSales> arrayList){
        inflater= LayoutInflater.from(context);
        this.arrayList=arrayList;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.row_product_detail,parent,false);
        MainViewHolder holder=new MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        MainViewHolder mainViewHolder=(MainViewHolder) holder;
        mainViewHolder.customer.setText(arrayList.get(position).getCustomer());
        mainViewHolder.date.setText(arrayList.get(position).getDate());
        float itemPrice=arrayList.get(position).getPrice();
        int itemQty=arrayList.get(position).getQty();
        mainViewHolder.price.setText(format.format(itemPrice));
        mainViewHolder.quantity.setText(String.valueOf(itemQty));
        mainViewHolder.total.setText(format.format(itemPrice*itemQty));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView customer,date,quantity,price,total;

        public MainViewHolder(View itemView) {
            super(itemView);
            customer=(TextView) itemView.findViewById(R.id.customerLabel);
            date=(TextView) itemView.findViewById(R.id.dateLabel);
            quantity=(TextView) itemView.findViewById(R.id.qtySoldLabel);
            price=(TextView) itemView.findViewById(R.id.priceLabel);
            total=(TextView) itemView.findViewById(R.id.valueLabel);
        }
    }


}
