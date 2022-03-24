package com.example.dossier_medical.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dossier_medical.Dao.MaladeDao;
import com.example.dossier_medical.Fragments.FragMaladeEmpty;
import com.example.dossier_medical.Fragments.FragMaladeList;
import com.example.dossier_medical.R;

public class ActivityHome extends AppCompatActivity {

    public  static FragmentTransaction transaction;
   // private FloatingActionButton BtFlotNew;
    private com.github.clans.fab.FloatingActionButton menu_new_malade,menu_search_malade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        event();

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadFocus();
    }
    private void initView() {
        setTitle("Les malades");
        //
        //  BtFlotNew =  findViewById(R.id.BtFlotNew);
        menu_new_malade =  findViewById(R.id.menu_item_new);
        menu_search_malade =  findViewById(R.id.menu_item_campagne);
    }
    private void event() {


        menu_new_malade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHome.this,ActivityRegisterMalade.class);
                startActivity(go);

            }
        });


        menu_search_malade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHome.this,ActivityMaladeSearch.class);
                startActivity(go);
            }
        });


    }

    private void loadFocus()
    {
        long count= MaladeDao.count();

        if(count== 0L)
        {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragMaladeEmpty.newInstance());
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
        else
        {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragMaladeList.newInstance());
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
    }
}