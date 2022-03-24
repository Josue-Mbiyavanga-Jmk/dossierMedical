package com.example.dossier_medical.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.dossier_medical.Dao.MaladeDao;
import com.example.dossier_medical.Dao.MedecinDao;
import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Entites.EMedecin;
import com.example.dossier_medical.Memory.Keys;
import com.example.dossier_medical.Memory.Preferences;
import com.example.dossier_medical.NetWork.HttpRequest;
import com.example.dossier_medical.R;
import com.example.dossier_medical.Utils.ETypeMessage;
import com.example.dossier_medical.Utils.HttpCallbackString;
import com.example.dossier_medical.Utils.UtilTimeStampToDate;
import com.github.ybq.android.spinkit.SpinKitView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.example.dossier_medical.Utils.UtilsToast.showCFAlertDialog;

public class ActivityRegisterMalade extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private View id_view_malade_step_one,id_view_malade_step_last;
    private ImageView circle1,circle2;
    private EditText edt_name,edt_postname,edt_firstname, edt_phone,edt_email,edt_adresse,edt_remarque;
    private SearchableSpinner spinner_genre,spinner_groupe,spinner_civil;
    private TextView txt_year;
    private ImageButton bt_year;
    private Button BtnSave,BtnPreview;
    private RelativeLayout bottom;
    private SpinKitView progress_load;
    //pour le controle de position des étapes
    private int FocusActivity = 0;
    private String mGenre="",mYyear_select="",mGroupe="",mCivil="";

    private EMalade eMalade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_malade);
        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        events();
    }

    private void initWidget(){
        // circular steep
        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        //Les portion
        id_view_malade_step_one = findViewById(R.id.id_view_malade_step_one);
        id_view_malade_step_last = findViewById(R.id.id_view_malade_step_last);
        //Buttons
        BtnSave = findViewById(R.id.BtnSave);
        BtnPreview = findViewById(R.id.BtnPreview);
        BtnPreview.setVisibility(View.GONE);
        //ImageButton
        bt_year = findViewById(R.id.bt_year);
        //Spinner
        spinner_genre = findViewById(R.id.spinner_genre);
        spinner_groupe = findViewById(R.id.spinner_groupe);
        spinner_civil = findViewById(R.id.spinner_civil);
        //RelativeLayout
        bottom = findViewById(R.id.bottom);
        //Progress
        progress_load = findViewById(R.id.spin_kit_load_save);
        progress_load.setVisibility(View.GONE);
        //Textview
        txt_year = findViewById(R.id.txt_year);
        //EditText
        edt_name = findViewById(R.id.edt_name);
        edt_postname = findViewById(R.id.edt_postname);
        edt_firstname = findViewById(R.id.edt_firstname);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        edt_adresse = findViewById(R.id.edt_adresse);
        edt_remarque = findViewById(R.id.edt_remarque);
        //initialiser les spinner
        loadList();

    }

    private void events(){

        //Précédent
        BtnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (FocusActivity) {
                    case 1:
                        //si c'est 1, alors aller à 0
                        changeFocus(0);
                        visibilityControl(0);

                        BtnPreview.setVisibility(View.GONE);
                        FocusActivity=0;
                        BtnSave.setText("Suivant");

                        break;


                    default:
                        break;
                }
            }
        });

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                switch (FocusActivity) {
                    case 0:
                        //si c'est O, alors aller à 1
                        //on vérifie si les champs sont non vides avant de passer
                        boolean b = isEmptyFields(new Object[]{edt_name,edt_postname,edt_firstname});
                        if(mGenre.equals(""))
                        {
                            showCFAlertDialog(ActivityRegisterMalade.this,"Info","Vous devez sélectionner votre genre avant de continuer", ETypeMessage.ERROR);
                            return;
                        }
                        if(mGroupe.equals(""))
                        {
                            showCFAlertDialog(ActivityRegisterMalade.this,"Info","Vous devez sélectionner votre groupe sanguin avant de continuer", ETypeMessage.ERROR);
                            return;
                        }

                        if(mYyear_select.equals(""))
                        {
                            showCFAlertDialog(ActivityRegisterMalade.this,"Info","Vous devez renseigner votre date de naissance avant de continuer", ETypeMessage.ERROR);

                            return;
                        }
                        if (!b) {

                            changeFocus(1);
                            visibilityControl(1);

                            BtnPreview.setVisibility(View.VISIBLE);
                            FocusActivity = 1;
                            BtnSave.setText("Enregistrer");
                        }
                        break;

                    case 1:
                        //si c'est 1,conclure
                        //on vérifie si les champs sont non vides avant de passer
                        boolean b1 = isEmptyFields(new Object[]{edt_phone,edt_email,edt_adresse,edt_remarque});
                        if(mCivil.equals(""))
                        {
                            showCFAlertDialog(ActivityRegisterMalade.this,"Info","Vous devez sélectionner votre état-civil", ETypeMessage.ERROR);
                            return;
                        }
                        if (!b1) {
                                //
                                progress_load.setVisibility(View.VISIBLE);
                                bottom.setVisibility(View.GONE);
                                //tout est bon
                                String nom = edt_name.getText().toString();
                                String postnom = edt_postname.getText().toString();
                                String prenom = edt_firstname.getText().toString();
                                String date = txt_year.getText().toString();
                                String tel = edt_phone.getText().toString();
                                String email = edt_email.getText().toString();
                                String adresse = edt_adresse.getText().toString();
                                String remarque = edt_remarque.getText().toString();

                                eMalade=new EMalade();
                                //step 1
                                eMalade.setNom(nom);
                                eMalade.setPostnom(postnom);
                                eMalade.setPrenom(prenom);
                                eMalade.setDate_naissance(date);
                                eMalade.setTelephone("+243"+tel);
                                eMalade.setEmail(email);
                                eMalade.setSexe(mGenre);
                                eMalade.setGroupe_sanguin(mGroupe);
                                eMalade.setEtat_civil(mCivil);
                                    //raccourcir sexe
                                    /*if(mGenre.equals("Homme")){
                                        eMalade.setSexe("H");
                                    }else if(mGenre.equals("Femme")){
                                        eMalade.setSexe("F");
                                    }*/
                                    //Step 2
                                eMalade.setAdresse(adresse);
                                eMalade.setRemarque(remarque);
                                eMalade.setDate_create(UtilTimeStampToDate.getTimeStamp());
                                eMalade.setDate_update(UtilTimeStampToDate.getTimeStamp());

                                //prepare insertion
                                insertRemote(eMalade);



                            }

                        break;

                    default:
                        break;
                }
            }
        });


        bt_year.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               String[] date= UtilTimeStampToDate.getDate().split("-");

                int day=Integer.valueOf(date[2].split(" ")[0]);
                int month=Integer.valueOf(date[1]);
                int year=Integer.valueOf(date[0]);

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(ActivityRegisterMalade.this, R.style.MyDialogTheme, ActivityRegisterMalade.this, year, month-1, day);

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
                        new DatePickerDialog(ActivityRegisterMalade.this, R.style.MyDialogTheme, ActivityRegisterMalade.this, year, month-1, day);

                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);

            }
        });

        spinner_genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGenre=spinner_genre.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_civil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCivil=spinner_civil.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_groupe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGroupe=spinner_groupe.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void insertRemote(EMalade o){

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
                    "    \"datasetCode\": \"DSI02\",\n" +
                    "    \"datasetSpecificFields\": {\n" +
                    "        \"nom\": \""+o.getNom()+"\",\n" +
                    "        \"postnom\": \""+o.getPostnom()+"\",\n" +
                    "        \"prenom\": \""+o.getPrenom()+"\",\n" +
                    "        \"sexe\": \""+o.getSexe()+"\",\n" +
                    "        \"groupe_sanguin\": \""+o.getGroupe_sanguin()+"\",\n" +
                    "        \"date_naissance\": \""+o.getDate_naissance()+"\",\n" +
                    "        \"phone\": \""+o.getTelephone()+"\",\n" +
                    "        \"email\": \""+o.getEmail()+"\",\n" +
                    "        \"adresse\": \""+o.getAdresse()+"\",\n" +
                    "        \"etat_civil\": \""+o.getEtat_civil()+"\",\n" +
                    "        \"remarque\": \""+o.getRemarque()+"\"\n" +
                    "    }\n" +
                    "}";

            HttpRequest.addTransaction(ActivityRegisterMalade.this, new String[]{param}, new HttpCallbackString() {
                @Override
                public void onSuccess(String response) {
                    String value =response;
                    finalInsertRemote(o,value);//deuxième http
                }

                @Override
                public void onError(String message) {
                    String value =message;
                    progress_load.setVisibility(View.GONE);
                    bottom.setVisibility(View.VISIBLE);
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityRegisterMalade.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Echec")
                            .setCancelable(false)
                            .setMessage("L'enregistrement n'a pas abouti")
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
            progress_load.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
        }

    }

    private void finalInsertRemote(EMalade o, String ref_insert){
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
                    "        \"nom\": \""+o.getNom()+"\",\n" +
                    "        \"postnom\": \""+o.getPostnom()+"\",\n" +
                    "        \"prenom\": \""+o.getPrenom()+"\",\n" +
                    "        \"sexe\": \""+o.getSexe()+"\",\n" +
                    "        \"groupe_sanguin\": \""+o.getGroupe_sanguin()+"\",\n" +
                    "        \"date_naissance\": \""+o.getDate_naissance()+"\",\n" +
                    "        \"phone\": \""+o.getTelephone()+"\",\n" +
                    "        \"email\": \""+o.getEmail()+"\",\n" +
                    "        \"adresse\": \""+o.getAdresse()+"\",\n" +
                    "        \"etat_civil\": \""+o.getEtat_civil()+"\",\n" +
                    "        \"remarque\": \""+o.getRemarque()+"\",\n" +
                    "        \"ref_malade\": \""+ref_insert+"\",\n" +
                    "        \"glycemies\": \"\",\n" +
                    "        \"tensions\": \"\",\n" +
                    "        \"vaccins\": \"\"\n" +
                    "    }\n" +
                    "}";

            HttpRequest.addTransaction(ActivityRegisterMalade.this, new String[]{param}, new HttpCallbackString() {
                @Override
                public void onSuccess(String response) {
                    String value =response;
                    //insertion réussie
                    o.setRef(value);//la valeur de la table update
                    insertLocal(o);

                }

                @Override
                public void onError(String message) {
                    String value =message;
                    progress_load.setVisibility(View.GONE);
                    bottom.setVisibility(View.VISIBLE);
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityRegisterMalade.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Echec")
                            .setCancelable(false)
                            .setMessage("L'enregistrement n'a pas abouti")
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
            progress_load.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
        }
    }

    private void insertLocal(EMalade o){
        Boolean b = MaladeDao.create(o);
        if(b){
            //enregistrer en ligne ou en local
            progress_load.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
            //
            //
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityRegisterMalade.this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                    .setTitle("Info")
                    .setCancelable(false)
                    .setMessage("Enregistrement du patient effectué avec succès")
                    .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            builder.show(); // Show
        }
    }

    //date
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

    //utilitaire pour les spinner
    private void refresh(SearchableSpinner spinner, List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(ActivityRegisterMalade.this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adp);
    }
    private void loadList(){
        List<String> genre=new ArrayList<>();
        genre.add("Homme");
        genre.add("Femme");
        refresh(spinner_genre,genre);
        //
        List<String> groupe=new ArrayList<>();
        groupe.add("A+");
        groupe.add("A-");
        groupe.add("AB+");
        groupe.add("AB-");
        groupe.add("B+");
        groupe.add("B-");
        groupe.add("O+");
        groupe.add("O-");

        refresh(spinner_groupe,groupe);

        List<String> civil=new ArrayList<>();
        civil.add("Celibataire");
        civil.add("Marié(e)");
        civil.add("Divorcé(e)");
        civil.add("Veuf(ve)");
        refresh(spinner_civil,civil);
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
    //method custom visibility
    private void visibilityControl(int position)
    {
        switch (position)
        {
            case 0:
                //la portion à afficher et celles à cacher
                id_view_malade_step_one.setVisibility(View.VISIBLE);
                id_view_malade_step_last.setVisibility(View.GONE);

                break;

            case 1:

                //la portion à afficher et celles à cacher
                id_view_malade_step_one.setVisibility(View.GONE);
                id_view_malade_step_last.setVisibility(View.VISIBLE);

                break;

            default:

                break;

        }

    }
    //method custom focus
    private void changeFocus(int position)
    {
        switch (position)
        {
            case 0:
                circle1.setBackground(ActivityRegisterMalade.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                circle2.setBackground(ActivityRegisterMalade.this.getResources().getDrawable(R.drawable.circlebarre));
                FocusActivity=position;
                break;

            case 1:

                circle1.setBackground(ActivityRegisterMalade.this.getResources().getDrawable(R.drawable.circlebarre));
                circle2.setBackground(ActivityRegisterMalade.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                FocusActivity = position;
                break;

            default:

                break;

        }

    }
}