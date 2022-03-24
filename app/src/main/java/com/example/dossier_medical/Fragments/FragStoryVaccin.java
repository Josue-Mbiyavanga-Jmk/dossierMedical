package com.example.dossier_medical.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.dossier_medical.Activities.ActivityMaladeVaccin;
import com.example.dossier_medical.Activities.ActivityMenuMalade;
import com.example.dossier_medical.Adaptaters.RecyclerAdapterMalade;
import com.example.dossier_medical.Adaptaters.RecyclerAdapterMaladeVaccin;
import com.example.dossier_medical.Dao.MaladeDao;
import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Entites.EVaccin;
import com.example.dossier_medical.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragStoryVaccin extends Fragment implements RecyclerAdapterMaladeVaccin.ItemButtonListener {

    View root;
    private RecyclerView recycler_view;
    private RecyclerAdapterMaladeVaccin mAdapter;
    private RelativeLayout contenaireHelp;
    private ProgressDialog progressDialog;
    private List<EVaccin> list_items;
    private EMalade eMalade;


    public FragStoryVaccin() {
        // Required empty public constructor
    }

    public FragStoryVaccin(EMalade eMalade) {
        //
        this.eMalade = eMalade;
    }


    // TODO: Rename and change types and number of parameters
    public static FragStoryVaccin newInstance() {
        FragStoryVaccin fragment = new FragStoryVaccin();
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
        // Inflate the layout for this fragment  list_items = ProduitDao.getAll();
        root = inflater.inflate(R.layout.frag_story_vaccin, container, false);

        initWidget();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadListVaccin();
    }

    void initWidget()
    {

        list_items=new ArrayList<>();
        recycler_view = root. findViewById(R.id.generique_recyclerview);
        contenaireHelp = root. findViewById(R.id.contenaireHelp);
        //
        mAdapter = new RecyclerAdapterMaladeVaccin((AppCompatActivity)getActivity(), list_items,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }



    @Override
    public void onUpdateClickListener(int position) {

    }

    @Override
    public void onItemClickListener(int position) {


    }
    private void transformListVaccin(){
        String aslist = eMalade.getVaccin();//on recupere la liste dans la BD
        if(aslist == null){
            list_items=new ArrayList<>();
        }else {

           // List<EVaccin> list= Arrays.asList(new GsonBuilder().create().fromJson(aslist, EVaccin.class));
            list_items=new ArrayList<>();
            Gson gson = new Gson();
            List<EVaccin> list = gson.fromJson(aslist, new TypeToken<List<EVaccin>>(){}.getType());
            for(EVaccin vac : list){
                list_items.add(vac);
            }

        }

    }
    private void loadListVaccin(){
        String aslist = eMalade.getVaccin();//on recupere la liste dans la BD
        if(aslist == null){
            contenaireHelp.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
        }else{
            contenaireHelp.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            list_items.clear();
            List<EVaccin> list = gson.fromJson(aslist, new TypeToken<List<EVaccin>>(){}.getType());
            for(EVaccin vac : list){
                list_items.add(vac);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

}