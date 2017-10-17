package com.example.saimounika.inclass08;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/*
InClass09
 */


public class MainActivity extends AppCompatActivity implements ExpenseFragment.Expenseinterface,AddExpenseFragment.AddExpenseInterface,
        ShowExpenseFragment.ShowExpenseInterface, EditFragment.SaveExpenseInterface{

    LinearLayout linearLayout;
    ArrayList<ExpenseItems> expenselist =  new ArrayList<>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mExpenseRef = mDatabase.child("expenses");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_main);

        getFragmentManager().beginTransaction()
                .add(linearLayout.getId(),ExpenseFragment.newInstance(expenselist),"expense fragment")
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void expenseitemsobjectdetails(ExpenseItems expenseItems, Integer position) {

        getFragmentManager().beginTransaction()
                .replace(linearLayout.getId(),ShowExpenseFragment.newInstance(expenseItems, position),"show fragment")
                .addToBackStack(null)
                .commit();



    }

    @Override
    public void removeExpenseObject(int position) {

        expenselist.remove(position);
        mExpenseRef.setValue(expenselist);

        Toast.makeText(MainActivity.this,"Expense Deleted",Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction()
                .replace(linearLayout.getId(),ExpenseFragment.newInstance(expenselist),"expense fragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void gotoAddFragment() {

        getFragmentManager().beginTransaction()
                .replace(linearLayout.getId(), new AddExpenseFragment(),"add expense")
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void addExpenseNewobject(ExpenseItems expenseItems) {

        if(expenseItems!= null){
            expenselist.add(expenseItems);
            mExpenseRef.setValue(expenselist);
            getFragmentManager().beginTransaction()
                    .replace(linearLayout.getId(),ExpenseFragment.newInstance(expenselist),"expense fragment")
                    .addToBackStack(null)
                    .commit();
        }



    }

    @Override
    public void cancelclickedAtAddExpense() {

        getFragmentManager().beginTransaction()
                .replace(linearLayout.getId(),ExpenseFragment.newInstance(expenselist),"expense fragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void showfragmentfinishAll() {

        getFragmentManager().beginTransaction()
                .replace(linearLayout.getId(), ExpenseFragment.newInstance(expenselist),"expense fragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void editExpenseObject(ExpenseItems expenseItems, int position) {
        getFragmentManager().beginTransaction()
                .replace(linearLayout.getId(), EditFragment.newInstance(expenseItems, position), "edit fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void saveExpenseObject(ExpenseItems expenseItems, int position) {
        expenselist.set(position, expenseItems);
        mExpenseRef.setValue(expenselist);
        getFragmentManager().beginTransaction()
                .replace(linearLayout.getId(),ShowExpenseFragment.newInstance(expenseItems, position),"show fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelclickedSaveExpense() {
        getFragmentManager().beginTransaction()
                .replace(linearLayout.getId(), ExpenseFragment.newInstance(expenselist),"expense fragment")
                .addToBackStack(null)
                .commit();
    }
}
