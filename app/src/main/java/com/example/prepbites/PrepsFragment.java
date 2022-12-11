package com.example.prepbites;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class PrepsFragment extends Fragment {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount account;
    private DatabaseReference mDatabase;
    Button newBtn;
    int year, month, day;
    TextInputEditText name, subject, date, repeatDays, task;
    String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    public PrepsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PrepsFragment newInstance(String param1, String param2) {
        PrepsFragment fragment = new PrepsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(getContext(),gso);

        account = GoogleSignIn.getLastSignedInAccount(getContext());

        View root = inflater.inflate(R.layout.fragment_preps, container, false);
        // Inflate the layout for this fragment
        Button newPrepBtn = root.findViewById(R.id.newBtn);
//        return inflater.inflate(R.layout.fragment_preps, container, false);   ORIGINAL

        newPrepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBottomSheet();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void toggleBottomSheet(){
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet, null);
        TextView selectDate = view.findViewById(R.id.dateSelect);
        Button save = view.findViewById(R.id.saveNewBtn);

        name = (TextInputEditText) view.findViewById(R.id.prepName);
        subject = (TextInputEditText) view.findViewById(R.id.subjectName);
        task = (TextInputEditText) view.findViewById(R.id.taskName);
        //repeatDays


        final Calendar c = Calendar.getInstance();

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity()
                        , (datePicker, year, month, day) -> {
                            month = month +1;
                            String date = getMonth(month)+" "+day+" "+ year;
                            selectDate.setText(date);
                        },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prep prep = new Prep("name","subj","10202022","MO,WE,FR","test"
//                        name.getText().toString(),
//                        subject.getText().toString(),
//                        selectDate.getText().toString(),
//                        "MO,WE,FR",
//                        task.getText().toString()
                );

                mDatabase.child("users").child(account.getId()).child("preps").push().setValue(prep);

            }
        });

        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();
    }

    public String getMonth(int m){
        return months[m-1];
    }
}