package com.example.dossier_medical.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.dossier_medical.Activities.ActivityMenuMalade;
import com.example.dossier_medical.Activities.ActivityRegisterMalade;
import com.example.dossier_medical.Adaptaters.RecyclerAdapterMalade;
import com.example.dossier_medical.Dao.MaladeDao;
import com.example.dossier_medical.Entites.EGlycemie;
import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Entites.ETension;
import com.example.dossier_medical.Entites.EVaccin;
import com.example.dossier_medical.Memory.Keys;
import com.example.dossier_medical.Memory.Preferences;
import com.example.dossier_medical.NetWork.HttpRequest;
import com.example.dossier_medical.R;
import com.example.dossier_medical.Utils.ETypeMessage;
import com.example.dossier_medical.Utils.HttpCallbackString;
import com.example.dossier_medical.Utils.UtilTimeStampToDate;
import com.example.dossier_medical.Utils.Utils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.dossier_medical.Utils.UtilsToast.showCFAlertDialog;
import static com.example.dossier_medical.Utils.UtilsToast.showCFAlertDialogFrag;


public class FragActionVaccin extends Fragment implements DatePickerDialog.OnDateSetListener  {

    View root;
    private FloatingActionButton BtAdd;
    private ImageButton bt_year;
    private EditText edt_name;
    private TextView txt_year;
    private SpinKitView progress;
    private String mYyear_select="";
    private EMalade eMalade;


    public FragActionVaccin() {
        // Required empty public constructor
    }
    public FragActionVaccin(EMalade eMalade) {
        //
        this.eMalade = eMalade;
    }


    // TODO: Rename and change types and number of parameters
    public static FragActionVaccin newInstance() {
        FragActionVaccin fragment = new FragActionVaccin();
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
        root = inflater.inflate(R.layout.frag_action_vaccin, container, false);

        initWidget();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        events();
    }

    void initWidget()
    {
        //ImageButton
        bt_year = root.findViewById(R.id.bt_year);
        //EditText
        edt_name = root.findViewById(R.id.edt_name);
        //Textview
        txt_year = root.findViewById(R.id.txt_year);
        //ButtonFloat
        BtAdd = root.findViewById(R.id.BtAdd);
        //Progress
        progress = root.findViewById(R.id.spin_kit_load_save);
        progress.setVisibility(View.GONE);
    }

    private void events(){
        bt_year.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String[] date= UtilTimeStampToDate.getDate().split("-");

                int day=Integer.valueOf(date[2].split(" ")[0]);
                int month=Integer.valueOf(date[1]);
                int year=Integer.valueOf(date[0]);

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), R.style.MyDialogTheme, FragActionVaccin.this, year, month-1, day);

                datePickerDialog.show();

            }
        });
        txt_year.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String[] date= UtilTimeStampToDate.getDate().split("-");

                int day=Integer.valueOf(date[2].split(" ")[0]);
                int month=Integer.valueOf(date[1]);
                int year=Integer.valueOf(date[0]);

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), R.style.MyDialogTheme,FragActionVaccin.this, year, month-1, day);

                datePickerDialog.show();

            }
        });

        BtAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean b = isEmptyFields(new Object[]{edt_name});
                if(mYyear_select.equals("") || mYyear_select.equals("Date de vaccination *"))
                {
                    showCFAlertDialogFrag(FragActionVaccin.this,"Info","Vous devez renseigner la date de vaccination avant de continuer", ETypeMessage.ERROR);

                    return;
                }
                if (!b) {
                    insertLocal();
                }


               //ici on va faire l'insertion
               // String aslist = eMalade.getVaccin();//on recupere la liste dans la BD
              //  list_items= Arrays.asList(new GsonBuilder().create().fromJson(aslist, EVaccin.class));

            }
        });

    }

    private void insertLocal(){
        progress.setVisibility(View.VISIBLE);
        BtAdd.setEnabled(false);
        String nom = edt_name.getText().toString();
        EVaccin obj = new EVaccin();
        obj.setNom(nom);
        obj.setDate(mYyear_select);
        //on peut remplacer par reference docteur
        obj.setMedecin_create(Preferences.get(Keys.PreferencesKeys.USER_PSEUDO));
        obj.setDate_create(UtilTimeStampToDate.getTimeStamp());
        obj.setDate_update(UtilTimeStampToDate.getTimeStamp());
        //Pour le vaccin
        Gson gson = new Gson();
        List<EVaccin> listFinal = new ArrayList<>();
        String aslist = eMalade.getVaccin();//on recupere la liste dans la BD
        if(aslist == null){
            listFinal.add(obj);
        }else{
            //List<EVaccin> list = Arrays.asList(new GsonBuilder().create().fromJson(aslist, EVaccin.class));
            List<EVaccin> list = gson.fromJson(aslist, new TypeToken<List<EVaccin>>(){}.getType());
            for(EVaccin vac : list){
                listFinal.add(vac);
            }
            //à la fin on ajoute le nouveau vacin
            listFinal.add(obj);

        }
        //
        String vaccinList = gson.toJson(listFinal); //save list produit as String in the DB
        eMalade.setVaccin(vaccinList);
        //encode
        String encodeVaccin = Utils.convertStringToBase64(vaccinList);
        //pour la tension
        List<ETension> listFinal1 = new ArrayList<>();
        String encodeTension = "";
        String aslist1 = eMalade.getTension();//on recupere la liste dans la BD
        if(aslist1 != null){
            List<ETension> list1 = gson.fromJson(aslist1, new TypeToken<List<ETension>>(){}.getType());
            for(ETension tension : list1){
                listFinal1.add(tension);
            }
            //
            String tensionList = gson.toJson(listFinal1); //save list produit as String in the DB
            eMalade.setTension(tensionList);
            //encode
            encodeTension =Utils.convertStringToBase64(tensionList);
        }

        //pour la glycémie
        List<EGlycemie> listFinal2 = new ArrayList<>();
        String encodeGlycemie = "";
        String aslist2 = eMalade.getGlycemie();//on recupere la liste dans la BD
        if(aslist2 != null){
            List<EGlycemie> list2 = gson.fromJson(aslist2, new TypeToken<List<EGlycemie>>(){}.getType());
            for(EGlycemie glycemie : list2){
                listFinal2.add(glycemie);
            }
            //
            String glycemieList = gson.toJson(listFinal2); //save list produit as String in the DB
            eMalade.setGlycemie(glycemieList);
            //encode
            encodeGlycemie =Utils.convertStringToBase64(glycemieList);
        }

        //
        try {

            String param="{\n" +
                    "    \"src\": {\n" +
                    "        \"countryCode\": \"CD\",\n" +
                    "        \"currencyCode\": \"CDF\",\n" +
                    "        \"phoneNumber\": \"+234810183549\",\n" +
                    "        \"taxCode\": \"1234\",\n" +
                    "        \"latitude\": 12.3456,\n" +
                    "        \"longitude\": 12.3456\n" +
                    "    },\n" +
                    "    \"dest\": {\n" +
                    "        \"countryCode\": \"CD\",\n" +
                    "        \"currencyCode\": \"CDF\",\n" +
                    "        \"phoneNumber\": \"+234810183549\",\n" +
                    "        \"taxCode\": \"1234\",\n" +
                    "        \"latitude\": 12.3456,\n" +
                    "        \"longitude\": 12.3456\n" +
                    "    },\n" +
                    "    \"srcCryptoWallet\": \"XXX-XXX-XXX-XXX\",\n" +
                    "    \"destCryptoWalletCode\": \"XXX\",\n" +
                    "    \"amount\": 1234.5678,\n" +
                    "    \"datasetCode\": \"DSI04\",\n" +
                    "    \"datasetSpecificFields\": {\n" +
                    "        \"nom\": \""+eMalade.getNom()+"\",\n" +
                    "        \"postnom\": \""+eMalade.getPostnom()+"\",\n" +
                    "        \"prenom\": \""+eMalade.getPrenom()+"\",\n" +
                    "        \"sexe\": \""+eMalade.getSexe()+"\",\n" +
                    "        \"groupe_sanguin\": \""+eMalade.getGroupe_sanguin()+"\",\n" +
                    "        \"date_naissance\": \""+eMalade.getDate_naissance()+"\",\n" +
                    "        \"phone\": \""+eMalade.getTelephone()+"\",\n" +
                    "        \"email\": \""+eMalade.getEmail()+"\",\n" +
                    "        \"adresse\": \""+eMalade.getAdresse()+"\",\n" +
                    "        \"etat_civil\": \""+eMalade.getEtat_civil()+"\",\n" +
                    "        \"remarque\": \""+eMalade.getRemarque()+"\",\n" +
                    "        \"ref_malade\": \""+eMalade.getRef()+"\",\n" +
                    "        \"vaccins\": \""+encodeVaccin+"\",\n" +
                    "        \"tensions\": \""+encodeTension+"\",\n" +
                    "        \"glycemies\": \""+encodeGlycemie+"\"\n" +
                    "    }\n" +
                    "}";

            HttpRequest.addTransaction(getActivity(), new String[]{param}, new HttpCallbackString() {
                @Override
                public void onSuccess(String response) {
                    String value =response;
                    eMalade.setRef(value);
                    //
                    Boolean b = MaladeDao.update(eMalade);
                    if(b){
                        progress.setVisibility(View.GONE);
                        BtAdd.setEnabled(true);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Info")
                                .setCancelable(false)
                                .setMessage("La vaccination a été enregistrée")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        edt_name.setText("");
                                        edt_name.requestFocus();
                                        txt_year.setText("Date de vaccination *");

                                    }
                                });

                        builder.show(); // Show
                    }
                }

                @Override
                public void onError(String message) {
                    String value =message;
                    progress.setVisibility(View.GONE);
                    BtAdd.setEnabled(true);
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Echec")
                            .setCancelable(false)
                            .setMessage("La vaccination n'a été enregistrée")
                            .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    builder.show(); // Show
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            progress.setVisibility(View.GONE);
            BtAdd.setEnabled(true);
        }

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        try {
            String m,y,j;

            if(month+1<10)
            {
                m="0"+(month+1);
            }
            else
            {
                m=(month+1)+"";
            }

            if(dayOfMonth+1<10)
            {
                j="0"+(dayOfMonth);
            }
            else
            {
                j=dayOfMonth+"";
            }

            txt_year.setText(""+j+" / "+ m +" / "+year);
            mYyear_select = ""+j+" / "+ m +" / "+year;
            txt_year.setBackgroundResource(R.drawable.square_background);


        }
        catch (Exception e)
        {

        }
    }

    //utilitaire pour des champs vides
    private Boolean isEmptyFields(Object[] objects){
        boolean b=false;
        for (Object o:objects)
        {
            if(o instanceof EditText )
            {
                String text= ((EditText)o).getText().toString().trim();
                if(text.isEmpty()){
                    ((EditText) o).setError("Remplir ce champ!");
                    b=true;
                }

            }
        }
        return b;
    }
}