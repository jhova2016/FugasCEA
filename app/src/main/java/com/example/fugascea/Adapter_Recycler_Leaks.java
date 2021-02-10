package com.example.fugascea;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Adapter_Recycler_Leaks extends RecyclerView.Adapter<Adapter_Recycler_Leaks.ViewHolderDatos> implements View.OnClickListener {


        //ArrayList<String> ListDatos;
        ArrayList<Element_Recycler_Leaks> ListDatos;
private View.OnClickListener Listener;
private Context context;

    String Status;

    AlertDialog DialogEditStatus;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

public Adapter_Recycler_Leaks (ArrayList<Element_Recycler_Leaks> listDatos,Context context) {
        this.ListDatos = listDatos;
        this.context=context;
        }



@NonNull
@Override
public Adapter_Recycler_Leaks.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_recycler_leaks,parent,false);
        view.setOnClickListener(this);
        return new Adapter_Recycler_Leaks.ViewHolderDatos(view);
        }

@Override
public void onBindViewHolder(@NonNull Adapter_Recycler_Leaks.ViewHolderDatos holder, int position) {

        holder.Id=(ListDatos.get(position).getId());
        holder.Locate.setText(ListDatos.get(position).getLocate());
        holder.References.setText(ListDatos.get(position).getReferences());
        holder.ReportDate.setText(ListDatos.get(position).getReportDate());
        holder.Inportance.setText(ListDatos.get(position).getImportance());
        //holder.Origin.setText(ListDatos.get(position).getOrigin());
        holder.Status.setText(ListDatos.get(position).getStatus());
        holder.Responsible.setText(ListDatos.get(position).getResponsible());
        switch (ListDatos.get(position).getStatus())
        {
            case "En reparacion":
                holder.ColStatus.setBackgroundResource(R.color.ColReparando);
                break;
            case "Pendiente":
                holder.ColStatus.setBackgroundResource(R.color.ColPendiente);
                break;

            case "Concluido":
                holder.ColStatus.setBackgroundResource(R.color.ColConcluido);
                break;


        }

        switch (ListDatos.get(position).getImportance())
        {
            case "Urgente":
                holder.ColInportance.setBackgroundResource(R.color.ColPendiente);
                holder.Inportance.setTextColor(Color.WHITE);

                break;
        }

    holder.LinearStatus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogEditLeak(ListDatos.get(position).getStatus(),ListDatos.get(position).getId());
            DialogEditStatus.show();

        }
    });

        }

@Override
public int getItemCount() {
        return ListDatos.size();
        }

public  void  setOnClickListener(View.OnClickListener listener)
        {
        this.Listener=listener;
        }

@Override
public void onClick(View v) {
        if(Listener!=null)
        {
        Listener.onClick(v);
        }

        }

public class ViewHolderDatos extends RecyclerView.ViewHolder {


    String Id;
    TextView Locate;
    TextView References;
    TextView ReportDate;
    TextView Inportance;
    //TextView Origin;
    TextView Status;
    TextView Responsible;
    LinearLayout ColStatus;
    LinearLayout ColInportance;
    LinearLayout LinearStatus;



    public ViewHolderDatos(@NonNull View itemView) {
        super(itemView);
        Locate=itemView.findViewById(R.id.Location);
        References=itemView.findViewById(R.id.References);
        ReportDate=itemView.findViewById(R.id.ReportDate);
        Inportance=itemView.findViewById(R.id.Inportance);
        //Origin=itemView.findViewById(R.id.Origin);
        Status=itemView.findViewById(R.id.Status);
        Responsible=itemView.findViewById(R.id.Responsible);
        ColStatus=itemView.findViewById(R.id.ColStatus);
        ColInportance=itemView.findViewById(R.id.ColImportance);
        LinearStatus=itemView.findViewById(R.id.LinearStatus);




    }


}

    private void DialogEditLeak(String status, String Id)
    {
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(context).setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.dialog_edit_status,null);

        Button BtnCancel=view.findViewById(R.id.BtnCancel);
        Button BtnSave=view.findViewById(R.id.BtnSave);

        Status=status;
        int AuxStatus=0;
        switch (Status) { case"Pendiente": AuxStatus=0;break; case"En reparacion": AuxStatus=1;break; case"Concluido": AuxStatus=2;break; }

        Spinner SpinnerStatus;
        ArrayList<String> listSpinnerStatus = new ArrayList<>();

        ArrayAdapter<String> AdapterStatus = new ArrayAdapter<>(context, R.layout.controlspinner, listSpinnerStatus);
        SpinnerStatus=view.findViewById(R.id.SpinnerStatus);
        listSpinnerStatus.add("Pendiente");
        listSpinnerStatus.add("En reparacion");
        listSpinnerStatus.add("Concluido");
        AdapterStatus.notifyDataSetChanged();
        SpinnerStatus.setAdapter(AdapterStatus);
        SpinnerStatus.setSelection(AuxStatus);

        SpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Status=listSpinnerStatus.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        BtnSave.setOnClickListener(v -> {


            Map<String, Object> Leak = new HashMap<>();

            Leak.put("Status", Status);




            DocumentReference washingtonRef = db.collection("Leaks").document(Id);

            washingtonRef
                    .update("Status",    Status)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");


                            SimpleDateFormat objSDFQuitar  = new SimpleDateFormat("HH:mm:ss");
                            Date date = new Date();

                            DocumentReference washingtonRef = db.collection("Updates").document("UpdateLeaks");

                            washingtonRef.update("Date",  objSDFQuitar .format(date));

                            Toast.makeText(context, " Editado", Toast.LENGTH_LONG).show();


                            DialogEditStatus.dismiss();
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

                DialogEditStatus.dismiss();
            }
        });


        builder.setView(view);
        DialogEditStatus =builder.create();
        DialogEditStatus.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


}
