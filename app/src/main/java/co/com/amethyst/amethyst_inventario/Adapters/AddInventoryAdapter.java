package co.com.amethyst.amethyst_inventario.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

import co.com.amethyst.amethyst_inventario.Activities.AddInventory;
import co.com.amethyst.amethyst_inventario.ArrayListHelpers.SaleItems;
import co.com.amethyst.amethyst_inventario.R;

/**
 * Created by Angel on 5/28/2016.
 */
public class AddInventoryAdapter extends RecyclerView.Adapter<AddInventoryAdapter.MainViewHolder> {
    private LayoutInflater inflater;
    Context context;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<String> qtyDataset=new ArrayList<>();
    ArrayList<Integer> spDataset=new ArrayList<>();
    ArrayList<String> spStrDataset=new ArrayList<>();
    int index;



    public AddInventoryAdapter(Context context, ArrayList<String> data) {
        inflater= LayoutInflater.from(context);
        spDataset.add(0);
        qtyDataset.add("");
        this.context=context;
        this.spinnerAdapter=new ArrayAdapter<>(context,android.R.layout.simple_dropdown_item_1line,data);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0) {
            View view = inflater.inflate(R.layout.row_add_inventory, parent, false);
            ViewHolder1 holder = new ViewHolder1(view, new QtyEditTextListener(),new SpinnerListener());
            return holder;
        }
        else {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_item, parent, false);
            ViewHolder2 holder = new ViewHolder2(view1,null,null);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            ViewHolder1 mViewHolder1=(ViewHolder1) holder;
            setUpView(position,mViewHolder1);
        } else{
            index=position;
            ViewHolder2 viewHolder2=(ViewHolder2) holder;
        }
    }

    class MainViewHolder extends RecyclerView.ViewHolder  {

        public MainViewHolder(View itemView, QtyEditTextListener qtyeditTextListener, SpinnerListener spinnerListener) {
            super(itemView);
        }
    }

    class ViewHolder1 extends MainViewHolder{
        EditText editTextQty;
        EditText editTextPr;
        Spinner spinner;
        QtyEditTextListener qtyeditTextListener;
        SpinnerListener spinnerListener;
        public ViewHolder1(View itemView, QtyEditTextListener qtyeditTextListener, SpinnerListener spinnerListener) {
            super(itemView, qtyeditTextListener,spinnerListener);
            editTextQty=(EditText) itemView.findViewById(R.id.editTextSale);
            editTextPr=(EditText) itemView.findViewById(R.id.editText);
            spinner=(Spinner) itemView.findViewById(R.id.spinnerSale);
            this.qtyeditTextListener=qtyeditTextListener;
            this.spinnerListener=spinnerListener;
            editTextQty.addTextChangedListener(qtyeditTextListener);
            spinner.setOnItemSelectedListener(spinnerListener);
        }
    }

    public class ViewHolder2 extends MainViewHolder implements View.OnClickListener {

        public ViewHolder2(View itemView, QtyEditTextListener editTextListener, SpinnerListener spinnerListener) {
            super(itemView, editTextListener, spinnerListener);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            qtyDataset.add("");
            spDataset.add(0);
            notifyDataSetChanged();
        }
    }
    private class SpinnerListener implements AdapterView.OnItemSelectedListener {
        int position;
        public void updatePosition(int position)
        {
            this.position=position;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            spDataset.set(position,i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class QtyEditTextListener implements TextWatcher {
        int position;

        public void updatePosition(int position){
            this.position=position;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
     qtyDataset.set(position, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }



    @Override
    public int getItemCount() {
        return spDataset.size()+1 ;
    }

    public void remove(int position) {
        qtyDataset.remove(position);
        spDataset.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (position == getItemCount()-1) {
            viewType = 1;
        }
        return viewType;
    }

    public ArrayList<String> getQtyDataset() {
        for (String s:qtyDataset) {
            if(s.equals("")){
                new AlertDialog.Builder(context)
                        .setTitle("Cantidad no determinada")
                        .setMessage("No ha digitado una cantidad")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return null;
            }
        }
        return qtyDataset;
    }

    public void setQtyDataset(ArrayList<String> qtyDataset) {
        this.qtyDataset = qtyDataset;
    }

    public ArrayList<String> getSpStrDataset() {
        for (int i = 0; i <spDataset.size() ; i++) {
            if(spDataset.size()<=spStrDataset.size()){
                spStrDataset.set(i,spinnerAdapter.getItem(spDataset.get(i)));
            }else {
                spStrDataset.add(i,spinnerAdapter.getItem(spDataset.get(i)));
            }
        }
        for (String s:spStrDataset) {
            if(s.equals("Producto")){
                new AlertDialog.Builder(context)
                        .setTitle("Producto no seleccionado")
                        .setMessage("No ha seleccionado un producto")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return null;
            }
        }
        return spStrDataset;
    }

    private void setUpView(int position, ViewHolder1 mViewHolder1) {
        mViewHolder1.spinner.setAdapter(spinnerAdapter);
        mViewHolder1.qtyeditTextListener.updatePosition(position);
        mViewHolder1.spinnerListener.updatePosition(position);
        mViewHolder1.editTextQty.setText(qtyDataset.get(position));
        mViewHolder1.spinner.setSelection(spDataset.get(position));
    }

}
