package com.example.dossier_medical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dossier_medical.Dao.MaladeDao;
import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Fragments.FragActionVaccin;
import com.example.dossier_medical.Fragments.FragStoryVaccin;
import com.example.dossier_medical.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


public class ActivityMaladeVaccin extends AppCompatActivity {

    private static final int NUM_PAGES = 2;
    //The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    public static ViewPager2 viewPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private FragmentStateAdapter pagerAdapter;
    // tab titles
    private String[] titles = new String[]{"Nouveau", "Historique"};
    private TextView txt_title;
    private ImageView bt_back;
    private EMalade eMalade;
    private int maladeid;
    private boolean focusBackPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malade_vaccin);
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            maladeid = bundle.getInt("maladeid");
        }
        //
        init();
    }
    @Override
    protected void onResume() {
        super.onResume();
        events();
    }

    private void init(){
        txt_title = findViewById(R.id.txt_title);
        bt_back =  findViewById(R.id.bt_back);
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        //inflating tab layout
        TabLayout tabLayout =( TabLayout) findViewById(R.id.tabs);
        //displaying tabs
        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();
        //displaying name
        eMalade = MaladeDao.get(maladeid);
        txt_title.setText(eMalade.getNom());
    }

    private void events(){
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusBackPressed = true;
                onBackPressed();
            }
        });
    }

    private class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(FragmentActivity fa) {
            super(fa);
        }


        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return new FragActionVaccin(eMalade);
                }
                case 1: {

                    return new FragStoryVaccin(eMalade);
                }

                default:
                    return new FragActionVaccin(eMalade);
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0 && focusBackPressed == false) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.d
            //super.onBackPressed();
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

        } else {
            // Otherwise, select the previous step.
            //viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            super.onBackPressed();
        }
    }


}