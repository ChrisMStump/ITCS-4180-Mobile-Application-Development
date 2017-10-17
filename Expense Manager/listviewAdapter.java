package com.example.saimounika.inclass08;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sai Mounika on 6/20/2017.
 */

public class listviewAdapter extends ArrayAdapter<ExpenseItems> {

    List<ExpenseItems> mData;
    Context mContext;
    int mResource;
    int mtextType;

    public listviewAdapter(Context context, int resource,int text, List<ExpenseItems> objects) {
        super(context, resource, objects);
        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
        this.mtextType = text;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }

        ExpenseItems expenseItems = mData.get(position);

        TextView expensename = (TextView) convertView.findViewById(R.id.textView_listview_expensename);
        TextView price = (TextView) convertView.findViewById(R.id.textView_listview_price);

        expensename.setText(expenseItems.getExpensename());
        price.setText("$"+expenseItems.getAmount());



        return convertView;
    }


}
