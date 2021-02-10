package com.example.fugascea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fugascea.Login.SingIN;
import com.example.fugascea.controlador.PagerControler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    TextView Identificador;
    TextView Correo;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    FirebaseAuth mAuth;

    AlertDialog DialogOpciones;
    ImageView BtnOpciones;

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tabP,tabC;
    PagerControler pagerControler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnOpciones=findViewById(R.id.ImageBtnOpciones);

        tabLayout=findViewById(R.id.TapLayout);
        viewPager=findViewById(R.id.viewPager);
        tabP=findViewById(R.id.tabPendientes);
        tabC=findViewById(R.id.tabConcluidos);

        pagerControler=new PagerControler(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerControler);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0)
                {
                    pagerControler.notifyDataSetChanged();
                }
                if(tab.getPosition()==1)
                {
                    pagerControler.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FrameMain, new Fragment_Leaks());
        fragmentTransaction.commit();*/

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Identificador=findViewById(R.id.Identificador);
        Correo=findViewById(R.id.Correo);

        Correo.setText(user.getEmail());

        db.collection("Users").document(user.getEmail()).collection("Acceso").document(user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.getResult().get("Admin").toString().equals("true"))
                {
                    Identificador.setText("Administrador");

                }
                else
                {
                    Identificador.setText("Usuario");
                }


            }
        });


        BtnOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOpcionesFuncion();
                DialogOpciones.show();
            }
        });


    }


    public void DialogOpcionesFuncion ()
    {
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_opciones,null);


        Button BtnCerrarSesion;
        BtnCerrarSesion=view.findViewById(R.id.BtnOpcionesDialog2);

        BtnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SingIN.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                stopService(intent);
                finish();
            }
        });

        builder.setView(view);
        DialogOpciones=builder.create();
        DialogOpciones.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


}