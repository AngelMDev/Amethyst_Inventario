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

import co.com.amethyst.amethyst_inventario.Activities.NewSaleActivity;
import co.com.amethyst.amethyst_inventario.ArrayListHelpers.SaleItems;
import co.com.amethyst.amethyst_inventario.R;

/**
 * Created by Angel on 5/21/2016.
 */
public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.MainViewHolder>
{
    private LayoutInflater inflater;
    Context context;
    ArrayList<SaleItems> arrayList=new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<String> qtyDataset=new ArrayList<>();
    ArrayList<String> prDataset=new ArrayList<>();
    ArrayList<Integer> spDataset=new ArrayList<>();
    ArrayList<String> spStrDataset=new ArrayList<>();
    int index;
    TextView tv;
    NumberFormat format=NumberFormat.getCurrencyInstance();
    private static final String TAG = SaleAdapter.class.getSimpleName();



    public SaleAdapter(Context context, ArrayList<SaleItems> data, TextView tv){
        inflater=LayoutInflater.from(context);
        this.tv=tv;
        arrayList=data;
        qtyDataset.add("");
        prDataset.add("");
        spDataset.add(0);
        this.context=context;
        NewSaleActivity newSaleActivity=new NewSaleActivity();
        spinnerAdapter = new ArrayAdapter<>(context,android.R.layout.simple_dropdown_item_1line,newSaleActivity.refreshEntries(context));

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

    public ArrayList<String> getPrDataset() {

        for (String s:prDataset) {
            if(s.equals("")){
                new AlertDialog.Builder(context)
                        .setTitle("Precio no determinado")
                        .setMessage("No ha digitado un precio")
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
        return prDataset;
    }

    public void setPrDataset(ArrayList<String> prDataset) {
        this.prDataset = prDataset;
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

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0) {
            View view = inflater.inflate(R.layout.row_sale, parent, false);
            ViewHolder1 holder = new ViewHolder1(view, new QtyEditTextListener(),new PrEditTextListener(),new SpinnerListener());
            Log.d(TAG, "onCreateViewHolder: Executing");
            return holder;
        }
        else {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_item, parent, false);
            ViewHolder2 holder = new ViewHolder2(view1,null,null,null);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (position == getItemCount()-1) {
            viewType = 1;
        }
        return viewType;
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

    private void setUpView(int position, ViewHolder1 mViewHolder1) {
        mViewHolder1.spinner.setAdapter(spinnerAdapter);
        mViewHolder1.qtyeditTextListener.updatePosition(position);
        mViewHolder1.prEditTextListener.updatePosition(position);
        mViewHolder1.spinnerListener.updatePosition(position);
        mViewHolder1.editTextQty.setText(qtyDataset.get(position));
        mViewHolder1.editTextPr.setText(prDataset.get(position));
        mViewHolder1.spinner.setSelection(spDataset.get(position));

    }

    @Override
    public int getItemCount() {

        return arrayList.size()+1 ;
    }

    class MainViewHolder extends RecyclerView.ViewHolder  {

        public MainViewHolder(View itemView, QtyEditTextListener qtyeditTextListener,PrEditTextListener prediTextListener, SpinnerListener spinnerListener) {
            super(itemView);
        }
    }

    class ViewHolder1 extends MainViewHolder{
        EditText editTextQty;
        EditText editTextPr;
        Spinner spinner;
        QtyEditTextListener qtyeditTextListener;
        PrEditTextListener prEditTextListener;
        SpinnerListener spinnerListener;
        public ViewHolder1(View itemView, QtyEditTextListener qtyeditTextListener, PrEditTextListener prEditTextListener, SpinnerListener spinnerListener) {
            super(itemView, qtyeditTextListener, prEditTextListener,spinnerListener);
            editTextQty=(EditText) itemView.findViewById(R.id.editTextSale);
            editTextPr=(EditText) itemView.findViewById(R.id.editText);
            spinner=(Spinner) itemView.findViewById(R.id.spinnerSale);
            this.qtyeditTextListener=qtyeditTextListener;
            this.prEditTextListener=prEditTextListener;
            this.spinnerListener=spinnerListener;
            editTextQty.addTextChangedListener(qtyeditTextListener);
            editTextPr.addTextChangedListener(prEditTextListener);
            spinner.setOnItemSelectedListener(spinnerListener);
        }
    }

    class ViewHolder2 extends MainViewHolder implements View.OnClickListener {

        public ViewHolder2(View itemView, QtyEditTextListener editTextListener, PrEditTextListener prEditTextListener, SpinnerListener spinnerListener) {
            super(itemView, editTextListener,prEditTextListener,spinnerListener);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SaleItems saleItems=new SaleItems(index);
            arrayList.add(index,saleItems);
            qtyDataset.add("");
            prDataset.add("");
            spDataset.add(0);
            notifyDataSetChanged();
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
            calculateTotal();
        }
    }

    private class PrEditTextListener implements TextWatcher {
        int position;

        public void updatePosition(int position){
            this.position=position;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            prDataset.set(position, charSequence.toString());

        }

        @Override
        public void afterTextChanged(Editable editable) {
            calculateTotal();
        }
    }

    private void calculateTotal() {
        int total = 0;
        int price = 0;
        int qty = 0;


        Log.d(TAG, "calculateTotal: called");

        for (int i = 0; i < prDataset.size(); i++) {
            String strPrice = prDataset.get(i);
            String strQty = qtyDataset.get(i);
            if (strPrice.equals("")) {
                price = 0;
            } else {
                price = Integer.parseInt(strPrice);
            }
            if (strQty.equals("")) {
                qty = 0;
            } else {
                qty = Integer.parseInt(strQty);
            }
            total += price * qty;

            tv.setText(format.format(total));
        }

    }
    public void remove(int position) {
        arrayList.remove(position);
        qtyDataset.remove(position);
        prDataset.remove(position);
        spDataset.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
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
}
