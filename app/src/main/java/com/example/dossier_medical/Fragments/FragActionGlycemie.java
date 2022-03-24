package com.example.dossier_medical.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.crowdfire.cfalertdialog.CFAlertDialog;
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
import com.google.gson.reflect.TypeToken;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.dossier_medical.Utils.UtilsToast.showCFAlertDialogFrag;


public class FragActionGlycemie extends Fragment implements DatePickerDialog.OnDateSetListener {

    View root;
    private FloatingActionButton BtAdd;
    private ImageButton bt_year,bt_heure_start;
    private EditText edt_resultat,edt_remarque;
    private TextView txt_year,txt_heure_start;
    private SpinKitView progress;
    private SearchableSpinner spinner_type,spinner_unite;
    private String mYyear_select="",mHstart="",mType="",mUnite="";
    private EMalade eMalade;
    TimePickerDialog picker;

    public FragActionGlycemie() {
        // Required empty public constructor
    }

    public FragActionGlycemie(EMalade eMalade) {
        //
        this.eMalade = eMalade;
    }

    // TODO: Rename and change types and number of parameters
    public static FragActionGlycemie newInstance() {
        FragActionGlycemie fragment = new FragActionGlycemie();
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
        root = inflater.inflate(R.layout.frag_action_glycemie, container, false);

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
        bt_heure_start = root.findViewById(R.id.bt_heure_start);
        //Textview
        txt_year = root.findViewById(R.id.txt_year);
        txt_heure_start = root.findViewById(R.id.txt_heure_start);
        //Editext
        edt_resultat = root.findViewById(R.id.edt_resultat);
        edt_remarque = root.findViewById(R.id.edt_remarque);
        //ButtonFloat
        BtAdd = root.findViewById(R.id.BtAdd);
        //Progress
        progress = root.findViewById(R.id.spin_kit_load_save);
        progress.setVisibility(View.GONE);
        //Spinner
        spinner_type = root.findViewById(R.id.spinner_type);
        spinner_unite = root.findViewById(R.id.spinner_unite);
        //initialiser les spinner
        loadList();
    }

    void events() {
        //
        BtAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean b = isEmptyFields(new Object[]{edt_resultat,edt_remarque});
                if (mType.equals("")  ) {
                    showCFAlertDialogFrag(FragActionGlycemie.this, "Info", "Vous devez renseigner le type de prelevement", ETypeMessage.ERROR);

                    return;
                }
                if (mUnite.equals("") ) {
                    showCFAlertDialogFrag(FragActionGlycemie.this, "Info", "Vous devez renseigner l'unité", ETypeMessage.ERROR);

                    return;
                }
                if (mYyear_select.equals("") || mYyear_select.equals("Date prelevement*") || mHstart.equals("") || mHstart.equals("Heure*")) {
                    showCFAlertDialogFrag(FragActionGlycemie.this, "Info", "Vous devez renseigner la date et/ou l'heure avant de continuer", ETypeMessage.ERROR);

                    return;
                }
                if (!b) {
                    insertLocal();
                }


            }
        });
        //date
        bt_year.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String[] date = UtilTimeStampToDate.getDate().split("-");

                int day = Integer.valueOf(date[2].split(" ")[0]);
                int month = Integer.valueOf(date[1]);
                int year = Integer.valueOf(date[0]);

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), R.style.MyDialogTheme, FragActionGlycemie.this, year, month - 1, day);

                datePickerDialog.show();

            }
        });
        txt_year.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String[] date = UtilTimeStampToDate.getDate().split("-");

                int day = Integer.valueOf(date[2].split(" ")[0]);
                int month = Integer.valueOf(date[1]);
                int year = Integer.valueOf(date[0]);

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), R.style.MyDialogTheme, FragActionGlycemie.this, year, month - 1, day);

                datePickerDialog.show();

            }
        });
        //heure
        txt_heure_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeure("heure*");
            }
        });

        bt_heure_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeure("heure*");
            }
        });
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType=spinner_type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUnite=spinner_unite.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void insertLocal(){
        progress.setVisibility(View.VISIBLE);
        BtAdd.setEnabled(false);
        EGlycemie obj = new EGlycemie();
        obj.setResultat(edt_resultat.getText().toString());
        obj.setRemarque(edt_remarque.getText().toString());
        obj.setType(mType);
        obj.setUnite(mUnite);
        obj.setDate(mYyear_select);
        obj.setHeure(mHstart);
        //on peut remplacer par reference docteur
        obj.setMedecin_create(Preferences.get(Keys.PreferencesKeys.USER_PSEUDO));
        obj.setDate_create(UtilTimeStampToDate.getTimeStamp());
        obj.setDate_update(UtilTimeStampToDate.getTimeStamp());
        //
        Gson gson = new Gson();
        List<EGlycemie> listFinal = new ArrayList<>();
        String aslist = eMalade.getGlycemie();//on recupere la liste dans la BD
        if(aslist == null){
            listFinal.add(obj);
        }else{
            //List<EVaccin> list = Arrays.asList(new GsonBuilder().create().fromJson(aslist, EVaccin.class));
            List<EGlycemie> list = gson.fromJson(aslist, new TypeToken<List<EGlycemie>>(){}.getType());
            for(EGlycemie glycemie : list){
                listFinal.add(glycemie);
            }
            //à la fin on ajoute la nouvelle tension
            listFinal.add(obj);

        }
        //
        String glycemieList = gson.toJson(listFinal); //save list produit as String in the DB
        eMalade.setGlycemie(glycemieList);
        //encode
        String encodeGlycemie = Utils.convertStringToBase64(glycemieList);
        //pour la tension
        List<ETension> listFinal1 = new ArrayList<>();
        String encodeTension = "";
        String aslist1 = eMalade.getTension();//on recupere la liste dans la BD
        if(aslist1 != null){
            List<ETension> list1 = gson.fromJson(aslist1, new TypeToken<List<ETension>>(){}.getType());
            for(ETension tension : list1){
                listFinal1.add(tension);
            }
            String tensionList = gson.toJson(listFinal1); //save list produit as String in the DB
            eMalade.setTension(tensionList);
            //encode
            encodeTension =Utils.convertStringToBase64(tensionList);
        }

        //pour le vaccin
        List<EVaccin> listFinal2 = new ArrayList<>();
        String encodeVaccin = "";
        String aslist2 = eMalade.getVaccin();//on recupere la liste dans la BD
        if(aslist2 != null){
            List<EVaccin> list2 = gson.fromJson(aslist2, new TypeToken<List<EVaccin>>(){}.getType());
            for(EVaccin vac : list2){
                listFinal2.add(vac);
            }
            //
            String vaccinList = gson.toJson(listFinal2); //save list produit as String in the DB
            eMalade.setVaccin(vaccinList);
            //encode
            encodeVaccin =Utils.convertStringToBase64(vaccinList);
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
                                .setMessage("La glycémie prelévée a été enregistrée")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        edt_resultat.setText("");
                                        edt_resultat.requestFocus();
                                        edt_remarque.setText("");
                                        txt_year.setText("Date prelevement*");
                                        txt_heure_start.setText("Heure*");

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
                            .setMessage("La glycémie prelévée n'a été enregistrée")
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
    private void setHeure(final String focus)
    {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        if(focus.equals("heure*"))
                        {
                            txt_heure_start.setText(sHour + ":" + sMinute);
                            mHstart=sHour + ":" + sMinute;
                        }

                    }
                }, hour, minutes, true);
        picker.show();
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
    //utilitaire pour les spinner
    private void refresh(SearchableSpinner spinner, List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adp);
    }
    private void loadList(){
        List<String> types=new ArrayList<>();
        types.add("A jeun");
        types.add("Au hasard");
        types.add("Post prandial");

        refresh(spinner_type,types);
        //
        List<String> unites=new ArrayList<>();
        unites.add("mg/dL");
        unites.add("mmol/L");

        refresh(spinner_unite,unites);
    }


}