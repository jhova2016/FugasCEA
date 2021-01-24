package com.example.fugascea;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Leaks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Leaks extends Fragment {


    RecyclerView RecyclerView;
    Adapter_Recycler_Leaks AdapterElementRecycler;
    final ArrayList<Element_Recycler_Leaks> ElementsRecycler = new ArrayList<Element_Recycler_Leaks>();

    FloatingActionButton FloatingButton;

    AlertDialog DialogNewLeaks;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Leaks() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fragment_Leaks newInstance(String param1, String param2) {
        Fragment_Leaks fragment = new Fragment_Leaks();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__leaks, container, false);
        FloatingButton=view.findViewById(R.id.floatingActionButton);

        RecyclerView = view.findViewById(R.id.RecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        DownDataBase();



        FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewLeak();
                DialogNewLeaks.show();

            }
        });

        return view;
    }

    private void NewLeak()
    {
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(getContext()).setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_leak,null);

        final ArrayList<String> listSpinner =new ArrayList<String>();
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.controlspinner, listSpinner);


        EditText Locate         =view.findViewById(R.id.EditLocate);
        EditText ReportDate     =view.findViewById(R.id.EditReportDate);
        EditText WhoReported    =view.findViewById(R.id.EditWhoReported);
        EditText Importance     =view.findViewById(R.id.EditImportance);
        EditText Origin         =view.findViewById(R.id.EditOrigin);
        EditText Status         =view.findViewById(R.id.EditEstatus);


        Button BtnSave=view.findViewById(R.id.BtnSave);

        Button BtnCancel=view.findViewById(R.id.BtnCancel);




        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String, Object> Leak = new HashMap<>();
                Leak.put("Locate", Locate.getText().toString());
                Leak.put("ReportDate", ReportDate.getText().toString());
                Leak.put("WhoReported", WhoReported.getText().toString());
                Leak.put("Importance", Importance.getText().toString());
                Leak.put("Origin", Origin.getText().toString());
                Leak.put("Status", Status.getText().toString());



                db.collection("Leaks")
                        .add(Leak)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getContext(),"Se agrego correctamente",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Error al agregar",Toast.LENGTH_LONG).show();
                            }
                        });



            }
        });

        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DialogNewLeaks.dismiss();
            }
        });


        builder.setView(view);
        DialogNewLeaks=builder.create();
        DialogNewLeaks.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void DownDataBase()
    {

        db.collection("Leaks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ElementsRecycler.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                ElementsRecycler.add(new Element_Recycler_Leaks(
                                        document.getId(),
                                        document.get("Locate").toString(),
                                        document.get("ReportDate").toString(),
                                        document.get("WhoReported").toString(),
                                        document.get("Origin").toString(),
                                        document.get("Importance").toString(),
                                        document.get("Status").toString()));



                            }

                            AdapterElementRecycler = new Adapter_Recycler_Leaks(ElementsRecycler,getContext());
                            AdapterElementRecycler.notifyDataSetChanged();
                            RecyclerView.setAdapter(AdapterElementRecycler);

                            AdapterElementRecycler.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {




                                }
                            });


                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }








}