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


public class FragStoryLaboratoire extends Fragment implements RecyclerAdapterMalade.ItemButtonListener {

    View root;
    private RecyclerView recycler_view;
    private RecyclerAdapterMalade mAdapter;
    private ProgressDialog progressDialog;
    private List<EMalade> list_items;

    public FragStoryLaboratoire() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragStoryLaboratoire newInstance() {
        FragStoryLaboratoire fragment = new FragStoryLaboratoire();
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
        root = inflater.inflate(R.layout.frag_story_examen, container, false);

        initWidget();
        return root;
    }

    @Override
    public void onResume() {
        /*list_items.clear();
        list_items.addAll(MaladeDao.getAll());
        mAdapter.notifyDataSetChanged();*/
        super.onResume();
    }

    void initWidget()
    {
        /*list_items=new ArrayList<>();
        recycler_view = root. findViewById(R.id.generique_recyclerview);

        mAdapter = new RecyclerAdapterMalade((AppCompatActivity)getActivity(), list_items,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);*/
    }



    @Override
    public void onUpdateClickListener(int position) {

    }

    @Override
    public void onItemClickListener(int position) {

        /*final EMalade malade = list_items.get(position);
        final int id = malade.getId();
        Intent go = new Intent(getActivity(), ActivityMenuMalade.class);
        go.putExtra("maladeid", id);
        startActivity(go);*/

    }

}