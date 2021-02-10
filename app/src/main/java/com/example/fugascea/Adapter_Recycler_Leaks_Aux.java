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

public class Adapter_Recycler_Leaks_Aux extends RecyclerView.Adapter<Adapter_Recycler_Leaks_Aux.ViewHolderDatos> implements View.OnClickListener {


    //ArrayList<String> ListDatos;
    ArrayList<Element_Recycler_Leaks> ListDatos;
    private View.OnClickListener Listener;
    private Context context;

    String Status;

    AlertDialog DialogEditStatus;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Adapter_Recycler_Leaks_Aux (ArrayList<Element_Recycler_Leaks> listDatos,Context context) {
        this.ListDatos = listDatos;
        this.context=context;
    }



    @NonNull
    @Override
    public Adapter_Recycler_Leaks_Aux.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_recycler_leaks,parent,false);
        view.setOnClickListener(this);
        return new Adapter_Recycler_Leaks_Aux.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Recycler_Leaks_Aux.ViewHolderDatos holder, int position) {

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



}