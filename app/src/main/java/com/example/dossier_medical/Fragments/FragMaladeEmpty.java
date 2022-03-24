package com.example.dossier_medical.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dossier_medical.Dao.MaladeDao;
import com.example.dossier_medical.R;

import androidx.fragment.app.Fragment;


public class FragMaladeEmpty extends Fragment {

    View root;
  /*  FirebaseDatabase database=null;
    DatabaseReference databaseReference=null;
    ValueEventListener postListener;*/
    private ProgressDialog progressDialog;
    private View contenaireHelp;
    public FragMaladeEmpty() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragMaladeEmpty newInstance() {
        FragMaladeEmpty fragment = new FragMaladeEmpty();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.empty_malade, container, false);
        initWidget();

   /*     database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference(Constant.EUser);*/

        long count= MaladeDao.count();

        if(count==0L)
        {
            contenaireHelp.setVisibility(View.VISIBLE);
            //loadData();
        }
        else
        {
            contenaireHelp.setVisibility(View.GONE);

        }


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    void initWidget()
    {
        contenaireHelp=root.findViewById(R.id.contenaireHelp);
    }


/*
    private void loadData()
    {
        showProgress();


        Query mQuery =  databaseReference.orderByChild("role").equalTo(Constant.ROLE_SUPER_ADMIN) ;
        postListener =  new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                boolean test=false;
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    EUser user = childSnapshot.getValue(EUser.class);
                    final   String Keys = childSnapshot.getKey();


                    EUser eUser= UserDao.getRef(Keys);

                    if(eUser==null)
                    {

                            eUser=new EUser();
                            eUser.setDateCreate(user.getDateCreate());
                            eUser.setUserParent(user.getUserParent());
                            eUser.setRole(user.getRole());
                            eUser.setConference(user.isConference());
                            eUser.setCount_invitation(user.getCount_invitation());
                            eUser.setEmail(user.getEmail());
                            eUser.setEtat(user.isEtat());
                            eUser.setRef(Keys);


                            UserDao.create(eUser) ;
                            test=true;

                    }
                    else
                    {
                        eUser=new EUser();
                        eUser.setDateCreate(user.getDateCreate());
                        eUser.setUserParent(user.getUserParent());
                        eUser.setRole(user.getRole());
                        eUser.setConference(user.isConference());
                        eUser.setCount_invitation(user.getCount_invitation());
                        eUser.setEmail(user.getEmail());
                        eUser.setEtat(user.isEtat());
                        eUser.setRef(Keys);

                        UserDao.update(eUser);
                        test=true;
                    }




                }



                if(test)
                {
                    Activity_Add_Client.transaction =getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragListUser.newInstance());
                    Activity_Add_Client.transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    Activity_Add_Client.transaction.commit();
                }
                else {
                    contenaireHelp.setVisibility(View.VISIBLE);
                }

                hiddenProgress();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // ...

                hiddenProgress();
                contenaireHelp.setVisibility(View.VISIBLE);
            }
        };

        mQuery.addListenerForSingleValueEvent(postListener);
    }
*/


    private void showProgress()
    {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Veuillez patienter...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }



    private void hiddenProgress()
    {
        progressDialog.dismiss();
    }


}