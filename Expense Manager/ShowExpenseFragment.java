package com.example.saimounika.inclass08;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ShowExpenseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ExpenseItems expenseItems;
    private int position;


    private ShowExpenseInterface mListener;

    public ShowExpenseFragment() {
        // Required empty public constructor
    }

    public static ShowExpenseFragment newInstance(ExpenseItems expenseItems, Integer position) {
        ShowExpenseFragment fragment = new ShowExpenseFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_expense, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Show Expense");

        TextView expensename = (TextView)getActivity().findViewById(R.id.textView_show_expensename);
        TextView category = (TextView)getActivity().findViewById(R.id.textView_show_category);
        TextView amount = (TextView)getActivity().findViewById(R.id.textView_show_amount);
        TextView date  = (TextView)getActivity().findViewById(R.id.textView_show_date);
        Button closebutton  = (Button)getActivity().findViewById(R.id.button_show_close);
        Button editbutton = (Button) getActivity().findViewById(R.id.button_edit_expense);

        if(expenseItems!=null){

            expensename.setText(expenseItems.getExpensename());
            category.setText(expenseItems.getCategory());
            amount.setText("$ "+expenseItems.getAmount());
            date.setText(expenseItems.getDate());

        }

        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showfragmentfinishAll();
            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editExpenseObject(expenseItems, position);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ShowExpenseInterface) {
            mListener = (ShowExpenseInterface) activity;
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
    public interface ShowExpenseInterface {
        // TODO: Update argument type and name
        void showfragmentfinishAll();
        void editExpenseObject(ExpenseItems expenseItems, int position);
    }
}
