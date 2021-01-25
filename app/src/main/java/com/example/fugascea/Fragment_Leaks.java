package com.example.fugascea;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    final ArrayList<Element_Recycler_Leaks> ElementsRecycler = new ArrayList<>();

    FloatingActionButton FloatingButton;

    AlertDialog DialogNewLeaks;
    AlertDialog DialogEditLeaks;

    String Importance;
    String Status;
    String Origin;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__leaks, container, false);
        FloatingButton=view.findViewById(R.id.floatingActionButton);

        RecyclerView = view.findViewById(R.id.RecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        UpdateDataBase();



        FloatingButton.setOnClickListener(v -> {

            DialogNewLeak();
            DialogNewLeaks.show();

        });

        return view;
    }



    private void DialogNewLeak()
    {
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(getContext()).setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_leak,null);




        Spinner SpinnerImportance;
        Spinner SpinnerStatus;
        Spinner SpinnerOrigin;

        EditText Locate         =view.findViewById(R.id.EditLocate);
        EditText References     =view.findViewById(R.id.EditReferences);
        EditText WhoReported    =view.findViewById(R.id.EditWhoReported);
        //importance
        //Origin
        //Status
        EditText Responsible    =view.findViewById(R.id.EditResponsible);
        String Date;

        ArrayList<String> listSpinnerImportance = new ArrayList<>();
        ArrayList<String> listSpinnerStatus = new ArrayList<>();
        ArrayList<String> listSpinnerOrigin = new ArrayList<>();

        ArrayAdapter<String> AdapterImportance = new ArrayAdapter<>(getContext(), R.layout.controlspinner, listSpinnerImportance);
        SpinnerImportance=view.findViewById(R.id.SpinnerImportance);
        listSpinnerImportance.add("Normal");
        listSpinnerImportance.add("Urgente");
        AdapterImportance.notifyDataSetChanged();
        SpinnerImportance.setAdapter(AdapterImportance);

        ArrayAdapter<String> AdapterStatus = new ArrayAdapter<>(getContext(), R.layout.controlspinner, listSpinnerStatus);
        SpinnerStatus=view.findViewById(R.id.SpinnerStatus);
        listSpinnerStatus.add("Pendiente");
        listSpinnerStatus.add("En reparacion");
        listSpinnerStatus.add("Concluido");
        AdapterStatus.notifyDataSetChanged();
        SpinnerStatus.setAdapter(AdapterStatus);

        ArrayAdapter<String> AdapterOrigin = new ArrayAdapter<>(getContext(), R.layout.controlspinner, listSpinnerOrigin);
        SpinnerOrigin=view.findViewById(R.id.SpinnerOrigin);
        listSpinnerOrigin.add("Linea conduccion");
        listSpinnerOrigin.add("toma de usuario");
        AdapterOrigin.notifyDataSetChanged();
        SpinnerOrigin.setAdapter(AdapterOrigin);




        SpinnerImportance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Importance=listSpinnerImportance.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        SpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Status=listSpinnerStatus.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        SpinnerOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Origin=listSpinnerOrigin.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });




        Calendar Calendario= Calendar.getInstance();
        int Mes=Calendario.get(Calendar.MONTH);
        Mes=Mes+1;
        Date=Calendario.get(Calendar.DAY_OF_MONTH)+"/"+Mes+"/"+Calendario.get(Calendar.YEAR);

        Button BtnSave=view.findViewById(R.id.BtnSave);

        Button BtnCancel=view.findViewById(R.id.BtnCancel);


        String finalDate = Date;
        BtnSave.setOnClickListener(v -> {


            Map<String, Object> Leak = new HashMap<>();
            Leak.put("Locate", Locate.getText().toString());
            Leak.put("References", References.getText().toString());
            Leak.put("ReportDate", finalDate);
            Leak.put("WhoReported", WhoReported.getText().toString());
            Leak.put("Importance", Importance);
            Leak.put("Origin", Origin);
            Leak.put("Status", Status);
            Leak.put("Responsible", Responsible.getText().toString());



            db.collection("Leaks")
                    .add(Leak)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getContext(),"Se agrego correctamente",Toast.LENGTH_LONG).show();

                        SimpleDateFormat objSDFQuitar  = new SimpleDateFormat("HH:mm:ss");
                        Date date = new Date();

                        DocumentReference washingtonRef = db.collection("Updates").document("UpdateLeaks");

                        washingtonRef.update("Date",  objSDFQuitar .format(date));




                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(),"Error al agregar",Toast.LENGTH_LONG).show());



        });

        BtnCancel.setOnClickListener(v -> DialogNewLeaks.dismiss());


        builder.setView(view);
        DialogNewLeaks=builder.create();
        DialogNewLeaks.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    private void DialogEditLeak(String id,String locate,String references, String whoreported,String importance, String origin,String status,String responsible)
    {
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(getContext()).setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_leak,null);

        final ArrayList<String> listSpinner = new ArrayList<>();
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.controlspinner, listSpinner);


        EditText Locate         =view.findViewById(R.id.EditLocate);
        EditText References     =view.findViewById(R.id.EditReferences);
        EditText WhoReported    =view.findViewById(R.id.EditWhoReported);
        //importance
        //Origin
        //Status
        EditText Responsible    =view.findViewById(R.id.EditResponsible);
        String Date = null;

        Locate.setText(locate);
        References.setText(references);
        WhoReported.setText(whoreported);
        Importance=importance;
        Origin=origin;
        Status=status;
        Responsible.setText(responsible);

        int AuxImportance = 0;
        int AuxOrigin=0;
        int AuxStatus=0;
        switch (Importance) { case"Normal": AuxImportance=0;break; case"Urgente": AuxImportance=1;break; }
        switch (Origin) { case"Linea conduccion": AuxOrigin=0;break; case"toma de usuario": AuxOrigin=1;break; }
        switch (Status) { case"Pendiente": AuxStatus=0;break; case"En reparacion": AuxStatus=1;break; case"Concluido": AuxStatus=2;break; }



        Spinner SpinnerImportance;
        Spinner SpinnerStatus;
        Spinner SpinnerOrigin;


        ArrayList<String> listSpinnerImportance = new ArrayList<>();
        ArrayList<String> listSpinnerStatus = new ArrayList<>();
        ArrayList<String> listSpinnerOrigin = new ArrayList<>();

        ArrayAdapter<String> AdapterImportance = new ArrayAdapter<>(getContext(), R.layout.controlspinner, listSpinnerImportance);
        SpinnerImportance=view.findViewById(R.id.SpinnerImportance);
        listSpinnerImportance.add("Normal");
        listSpinnerImportance.add("Urgente");
        AdapterImportance.notifyDataSetChanged();
        SpinnerImportance.setAdapter(AdapterImportance);
        SpinnerImportance.setSelection(AuxImportance);

        ArrayAdapter<String> AdapterStatus = new ArrayAdapter<>(getContext(), R.layout.controlspinner, listSpinnerStatus);
        SpinnerStatus=view.findViewById(R.id.SpinnerStatus);
        listSpinnerStatus.add("Pendiente");
        listSpinnerStatus.add("En reparacion");
        listSpinnerStatus.add("Concluido");
        AdapterStatus.notifyDataSetChanged();
        SpinnerStatus.setAdapter(AdapterStatus);
        SpinnerStatus.setSelection(AuxStatus);

        ArrayAdapter<String> AdapterOrigin = new ArrayAdapter<>(getContext(), R.layout.controlspinner, listSpinnerOrigin);
        SpinnerOrigin=view.findViewById(R.id.SpinnerOrigin);
        listSpinnerOrigin.add("Linea conduccion");
        listSpinnerOrigin.add("toma de usuario");
        AdapterOrigin.notifyDataSetChanged();
        SpinnerOrigin.setAdapter(AdapterOrigin);
        SpinnerOrigin.setSelection(AuxOrigin);

        SpinnerImportance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Importance=listSpinnerImportance.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        SpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Status=listSpinnerStatus.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        SpinnerOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Origin=listSpinnerOrigin.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });





        Button BtnSave=view.findViewById(R.id.BtnSave);

        Button BtnCancel=view.findViewById(R.id.BtnCancel);



        BtnSave.setOnClickListener(v -> {


            Map<String, Object> Leak = new HashMap<>();
            Leak.put("Locate", Locate.getText().toString());
            Leak.put("References", References.getText().toString());
            Leak.put("WhoReported", WhoReported.getText().toString());
            Leak.put("Importance", Importance);
            Leak.put("Origin", Origin);
            Leak.put("Status", Status);
            Leak.put("Responsible", Responsible.getText().toString());



            DocumentReference washingtonRef = db.collection("Leaks").document(id);

            washingtonRef
                    .update("Locate",  Locate.getText().toString(),
                            "References",     References.getText().toString(),
                            "WhoReported",         WhoReported.getText().toString(),
                            "Importance",   Importance,
                            "Origin", Origin,
                            "Status",    Status,
                            "Responsible",     Responsible.getText().toString()

                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");


                            SimpleDateFormat objSDFQuitar  = new SimpleDateFormat("HH:mm:ss");
                            Date date = new Date();

                            DocumentReference washingtonRef = db.collection("Updates").document("UpdateLeaks");

                            washingtonRef.update("Date",  objSDFQuitar .format(date));

                            Toast.makeText(getContext(), " Editado", Toast.LENGTH_LONG).show();


                            DialogEditLeaks.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                        }
                    });


        });

        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DialogEditLeaks.dismiss();
            }
        });


        builder.setView(view);
        DialogEditLeaks=builder.create();
        DialogEditLeaks.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void UpdateDataBase()
    {

        DocumentReference docRef = db.collection("Updates").document("UpdateLeaks");

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                DownDataBase();

            }
        });

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
                                                document.get("References").toString(),
                                                document.get("ReportDate").toString(),
                                                document.get("WhoReported").toString(),
                                                document.get("Origin").toString(),
                                                document.get("Importance").toString(),
                                                document.get("Status").toString(),
                                                document.get("Responsible").toString())

                                        );



                            }

                            AdapterElementRecycler = new Adapter_Recycler_Leaks(ElementsRecycler,getContext());
                            AdapterElementRecycler.notifyDataSetChanged();
                            RecyclerView.setAdapter(AdapterElementRecycler);

                            AdapterElementRecycler.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    DialogEditLeak(ElementsRecycler.get(RecyclerView.getChildAdapterPosition(v)).getId(),
                                            ElementsRecycler.get(RecyclerView.getChildAdapterPosition(v)).getLocate(),
                                            ElementsRecycler.get(RecyclerView.getChildAdapterPosition(v)).getReferences(),
                                            ElementsRecycler.get(RecyclerView.getChildAdapterPosition(v)).getWhoReported(),
                                            ElementsRecycler.get(RecyclerView.getChildAdapterPosition(v)).getImportance(),
                                            ElementsRecycler.get(RecyclerView.getChildAdapterPosition(v)).getOrigin(),
                                            ElementsRecycler.get(RecyclerView.getChildAdapterPosition(v)).getStatus(),
                                            ElementsRecycler.get(RecyclerView.getChildAdapterPosition(v)).getResponsible()
                                            );
                                    DialogEditLeaks.show();

                                }
                            });


                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }








}