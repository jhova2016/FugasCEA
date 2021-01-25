package com.example.fugascea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Recycler_Leaks extends RecyclerView.Adapter<Adapter_Recycler_Leaks.ViewHolderDatos> implements View.OnClickListener {


        //ArrayList<String> ListDatos;
        ArrayList<Element_Recycler_Leaks> ListDatos;
private View.OnClickListener Listener;
private Context context;

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
        holder.WhoReported.setText(ListDatos.get(position).getWhoReported());
        holder.Origin.setText(ListDatos.get(position).getOrigin());
        holder.Status.setText(ListDatos.get(position).getStatus());
        holder.Responsible.setText(ListDatos.get(position).getResponsible());



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
    TextView WhoReported;
    TextView Origin;
    TextView Status;
    TextView Responsible;



    public ViewHolderDatos(@NonNull View itemView) {
        super(itemView);
        Locate=itemView.findViewById(R.id.Location);
        References=itemView.findViewById(R.id.References);
        ReportDate=itemView.findViewById(R.id.ReportDate);
        WhoReported=itemView.findViewById(R.id.WhoReported);
        Origin=itemView.findViewById(R.id.Origin);
        Status=itemView.findViewById(R.id.Status);
        Responsible=itemView.findViewById(R.id.Responsible);




    }


}
}
