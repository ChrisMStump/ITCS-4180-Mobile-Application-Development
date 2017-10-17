package com.example.saimounika.inclass08;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
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
 * {@link EditFragment.SaveExpenseInterface} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ExpenseItems expenseItems;
    private int position;

    private SaveExpenseInterface mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(ExpenseItems expenseItems, int position) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,expenseItems);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expenseItems = getArguments().getParcelable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Edit Expense");

        final TextView expensename = (TextView)getActivity().findViewById(R.id.editText_add_expensename);
        final TextView amount  = (TextView)getActivity().findViewById(R.id.editText_add_amount);
        Button addexpense =(Button)getActivity().findViewById(R.id.button_save_expense);
        Button cancel = (Button)getActivity().findViewById(R.id.button_add_cancel);

        final Spinner spinner = (Spinner)getActivity().findViewById(R.id.spinner_add);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.array, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        expensename.setText(expenseItems.getExpensename());
        amount.setText(expenseItems.getAmount());
        spinner.setSelection(adapter.getPosition(expenseItems.getCategory()));

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

                    mListener.saveExpenseObject(expenseItems, position);


                }




            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelclickedSaveExpense();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SaveExpenseInterface) {
            mListener = (SaveExpenseInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface SaveExpenseInterface {
        void saveExpenseObject(ExpenseItems expenseItems, int position);
        void cancelclickedSaveExpense();
    }
}
