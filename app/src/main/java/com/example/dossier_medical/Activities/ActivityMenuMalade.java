package com.example.dossier_medical.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dossier_medical.Adaptaters.RecyclerAdapterMalade;
import com.example.dossier_medical.Adaptaters.RecyclerAdapterMenuMalade;
import com.example.dossier_medical.Dao.MaladeDao;
import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Fragments.FragActionVaccin;
import com.example.dossier_medical.Fragments.FragStoryVaccin;
import com.example.dossier_medical.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityMenuMalade extends AppCompatActivity implements RecyclerAdapterMenuMalade.ItemButtonListener {
    private TextView txt_title;
    private RecyclerView recycler_view;
    private ImageView bt_back;
    private RecyclerAdapterMenuMalade mAdapter;
    private List<Menu> menu_item;
    private EMalade eMalade;
    private int maladeid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_malade);
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            maladeid = bundle.getInt("maladeid");
        }
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        events();
    }

    private void initView(){
        txt_title = findViewById(R.id.txt_title);
        bt_back =  findViewById(R.id.bt_back);
        recycler_view = findViewById(R.id.generique_recyclerview);
        menu_item=new ArrayList<>();

        eMalade = MaladeDao.get(maladeid);
        txt_title.setText(eMalade.getNom());
        //
        mAdapter = new RecyclerAdapterMenuMalade(ActivityMenuMalade.this, menu_item,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityMenuMalade.this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
        //
        loadMenuList();
        //

    }

    private void events(){
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadMenuList(){
        //
        Menu allergie = new Menu(1,"Allergie",getResources().getDrawable(R.drawable.ic_allergy));
        menu_item.add(allergie);
        Menu antecedent = new Menu(2,"Antécédents médicaux",getResources().getDrawable(R.drawable.ic_medical_history));
        menu_item.add(antecedent);
        Menu rdv = new Menu(3,"Attribuer rendez-vous",getResources().getDrawable(R.drawable.ic_appointment));
        menu_item.add(rdv);
        Menu chirurgie = new Menu(4,"Chirurgie",getResources().getDrawable(R.drawable.ic_chirurgie));
        menu_item.add(chirurgie);
        Menu echo = new Menu(5,"Echographie",getResources().getDrawable(R.drawable.ic_ultrasound));
        menu_item.add(echo);
        Menu examen = new Menu(6,"Examen",getResources().getDrawable(R.drawable.ic_exam));
        menu_item.add(examen);
        Menu glycemie = new Menu(7,"Glycémie",getResources().getDrawable(R.drawable.ic_glycemie));
        menu_item.add(glycemie);
        Menu histoire = new Menu(8,"Histoire de famille",getResources().getDrawable(R.drawable.ic_family));
        menu_item.add(histoire);
        Menu ordo = new Menu(9,"Ordonnance",getResources().getDrawable(R.drawable.ic_ordonnance));
        menu_item.add(ordo);
        Menu pathologie = new Menu(10,"Pathologie",getResources().getDrawable(R.drawable.ic_virus));
        menu_item.add(pathologie);
        Menu radio = new Menu(11,"Radiologie",getResources().getDrawable(R.drawable.ic_x_ray));
        menu_item.add(radio);
        Menu saturation = new Menu(12,"Saturation en oxygiène",getResources().getDrawable(R.drawable.ic_oxy));
        menu_item.add(saturation);
        Menu tension = new Menu(13,"Tension artérielle",getResources().getDrawable(R.drawable.ic_blood_pressure));
        menu_item.add(tension);
        Menu test = new Menu(14,"Test laboratoire",getResources().getDrawable(R.drawable.ic_labo));
        menu_item.add(test);
        Menu vacin = new Menu(15,"Vaccins",getResources().getDrawable(R.drawable.ic_vaccin));
        menu_item.add(vacin);
        Menu visite = new Menu(16,"Visite médicale",getResources().getDrawable(R.drawable.ic_stethoscope));
        menu_item.add(visite);

    }

    @Override
    public void onUpdateClickListener(int position) {

    }

    @Override
    public void onItemClickListener(int position) {
        final Menu unMenu = menu_item.get(position);
        final int id = unMenu.getId();
        switch (id) {
            case 1: {
                break;
            }
            case 6: {

                Intent go = new Intent(ActivityMenuMalade.this, ActivityMaladeExamen.class);
                //touujours envoyé le malade
                go.putExtra("maladeid", maladeid);
                startActivity(go);
                break;
            }
            case 7: {

                Intent go = new Intent(ActivityMenuMalade.this, ActivityMaladeGlycemie.class);
                //touujours envoyé le malade
                go.putExtra("maladeid", maladeid);
                startActivity(go);
                break;
            }
            case 13: {

                Intent go = new Intent(ActivityMenuMalade.this, ActivityMaladeTension.class);
                //touujours envoyé le malade
                go.putExtra("maladeid", maladeid);
                startActivity(go);
                break;
            }
            case 14: {

                Intent go = new Intent(ActivityMenuMalade.this, ActivityMaladeLaboratoire.class);
                //touujours envoyé le malade
                go.putExtra("maladeid", maladeid);
                startActivity(go);
                break;
            }
            case 15: {

                Intent go = new Intent(ActivityMenuMalade.this, ActivityMaladeVaccin.class);
                //touujours envoyé le malade
                go.putExtra("maladeid", maladeid);
                startActivity(go);
                break;
            }
            case 16: {

                Intent go = new Intent(ActivityMenuMalade.this, ActivityMaladeVisite.class);
                //touujours envoyé le malade
                go.putExtra("maladeid", maladeid);
                startActivity(go);
                break;
            }

            default:
                break;
        }

    }

    public class Menu{
        int id;
        String name;
        Drawable photo;

        public Menu() {

        }
        public Menu(int id, String name, Drawable photo) {
            this.id = id;
            this.name = name;
            this.photo = photo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getPhoto() {
            return photo;
        }


    }
}