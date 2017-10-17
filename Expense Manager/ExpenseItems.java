package com.example.saimounika.inclass08;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sai Mounika on 6/20/2017.
 */

public class ExpenseItems implements Parcelable{

    String expensename,category,amount,date, key;

    public ExpenseItems() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExpensename() {
        return expensename;
    }

    public void setExpensename(String expensename) {
        this.expensename = expensename;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected ExpenseItems(Parcel in) {
        expensename = in.readString();
        category = in.readString();
        amount = in.readString();
        date = in.readString();
    }

    public static final Creator<ExpenseItems> CREATOR = new Creator<ExpenseItems>() {
        @Override
        public ExpenseItems createFromParcel(Parcel in) {
            return new ExpenseItems(in);
        }

        @Override
        public ExpenseItems[] newArray(int size) {
            return new ExpenseItems[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expensename);
        dest.writeString(category);
        dest.writeString(amount);
        dest.writeString(date);
    }
}
