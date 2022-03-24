package com.example.dossier_medical.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dossier_medical.Adaptaters.RecyclerAdapterMalade;
import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.R;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


public class FragActionLaboratoire extends Fragment  {

    View root;
    private RecyclerView recycler_view;
    private RecyclerAdapterMalade mAdapter;
    private ProgressDialog progressDialog;
    private List<EMalade> list_items;

    public FragActionLaboratoire() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragActionLaboratoire newInstance() {
        FragActionLaboratoire fragment = new FragActionLaboratoire();
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
        root = inflater.inflate(R.layout.frag_action_examen, container, false);

        initWidget();
        return root;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    void initWidget()
    {

    }



}