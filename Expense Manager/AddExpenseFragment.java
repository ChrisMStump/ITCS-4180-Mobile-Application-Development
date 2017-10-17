package com.example.saimounika.inclass08;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpenseFragment.AddExpenseInterface} interface
 * to handle interaction events.
 */
public class AddExpenseFragment extends Fragment {

    private AddExpenseInterface mListener;

    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Add Expense");


        final TextView expensename = (TextView)getActivity().findViewById(R.id.editText_add_expensename);
        final TextView amount  = (TextView)getActivity().findViewById(R.id.editText_add_amount);
        Button addexpense =(Button)getActivity().findViewById(R.id.button_save_expense);
        Button cancel = (Button)getActivity().findViewById(R.id.button_add_cancel);

        final Spinner spinner = (Spinner)getActivity().findViewById(R.id.spinner_add);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.array, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String expense = expensename.getText().toString();
                String  amountvalue = amount.getText().toString();
                String spinnervalue = spinner.getSelectedItem().toString();

                if(TextUtils.isEmpty(expense)||TextUtils.isEmpty(String.valueOf(amountvalue))||
                       spinnervalue.equals("select category")){

                    Toast.makeText(getActivity(),"Please enter correct data",Toast.LENGTH_SHORT).show();
                }
                else {

                    ExpenseItems expenseItems = new ExpenseItems();
                    expenseItems.setAmount(amountvalue);
                    expenseItems.setCategory(spinnervalue);
                    expenseItems.setExpensename(expense);
                    Date currentdate = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    String formattedDate = formatter.format(currentdate);
                    expenseItems.setDate(formattedDate.toString());

                    mListener.addExpenseNewobject(expenseItems);


                }




            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mListener.cancelclickedAtAddExpense();
            }
        });








    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AddExpenseInterface) {
            mListener = (AddExpenseInterface) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AddExpenseInterface {
        // TODO: Update argument type and name
        void addExpenseNewobject(ExpenseItems expenseItems);
        void cancelclickedAtAddExpense();

    }
}
