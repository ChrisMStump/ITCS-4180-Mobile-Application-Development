package com.example.saimounika.inclass08;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link ExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class
ExpenseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView listView;
    LinearLayout expenselinearlayout;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mExpenseRef = mDatabase.child("expenses");

    private String mParam1;
    private ArrayList<ExpenseItems> expenseItemsArrayList;

    private Expenseinterface mListener;

    public ExpenseFragment() {
    }

    public static ExpenseFragment newInstance(ArrayList<ExpenseItems> expenseItemsArrayList) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, expenseItemsArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expenseItemsArrayList = getArguments().getParcelableArrayList(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Expense App");

        FloatingActionButton addbutton = (FloatingActionButton) getActivity().findViewById(R.id.floatingActionButton);

        listView = (ListView)getActivity().findViewById(R.id.listview_expenselist);
        expenselinearlayout = (LinearLayout)getActivity().findViewById(R.id.linearlayout_expense);

        mExpenseRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                expenseItemsArrayList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    ExpenseItems expenseItems = postSnapshot.getValue(ExpenseItems.class);
                    expenseItemsArrayList.add(expenseItems);
                }
                if(expenseItemsArrayList.size()>0){
                    expenselinearlayout.removeAllViews();

                    Log.d("demohere2","eneterd here");
                    listviewAdapter adapter = new listviewAdapter(getActivity(),R.layout.listview_layout,android.R.id.text1,expenseItemsArrayList);
                    listView.setAdapter(adapter);
                    expenselinearlayout.addView(listView);


                }
                else{

                    Log.d("demohere","emterd hete");

                    expenselinearlayout.removeAllViews();
                    TextView noitems = new TextView(getActivity());
                    noitems.setText("There is no expense to show, Please add your expenses from the menu");
                    noitems.setGravity(Gravity.CENTER);
                    noitems.setTypeface(null, Typeface.BOLD);
                    expenselinearlayout.addView(noitems);


                }
            }

            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });




       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ExpenseItems expenseItems = expenseItemsArrayList.get(position);

               mListener.expenseitemsobjectdetails(expenseItems, position);
           }
       });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                mListener.removeExpenseObject(position);
                return true;
            }
        });
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.gotoAddFragment();
            }
        });










    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Expenseinterface) {
            mListener = (Expenseinterface) activity;
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
    public interface Expenseinterface {
        // TODO: Update argument type and name
        void expenseitemsobjectdetails(ExpenseItems expenseItems, Integer position);
        void removeExpenseObject(int position);
        void gotoAddFragment();
    }
}
