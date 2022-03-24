package com.example.dossier_medical.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.dossier_medical.Dao.MedecinDao;
import com.example.dossier_medical.Entites.EMedecin;
import com.example.dossier_medical.Memory.Keys;
import com.example.dossier_medical.Memory.Parameters;
import com.example.dossier_medical.Memory.Preferences;
import com.example.dossier_medical.NetWork.HttpRequest;
import com.example.dossier_medical.R;
import com.example.dossier_medical.Utils.ETypeMessage;
import com.example.dossier_medical.Utils.HttpCallbackString;
import com.example.dossier_medical.Utils.UtilTimeStampToDate;
import com.github.ybq.android.spinkit.SpinKitView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.dossier_medical.Utils.UtilsToast.showCFAlertDialog;

public class ActivityRegisterMedecin extends AppCompatActivity {

    private View id_view_medecin_step_one,id_view_medecin_step_last;
    private ImageView circle1,circle2;
    private EditText edt_name,edt_postname,edt_firstname, edt_phone,edt_email,edt_num_ordre,edt_hopital,edit_pass,edit_pass_confirm;
    private SearchableSpinner spinner_genre,spinner_specialite;
    private TextView txt_year;
    private ImageButton bt_year;
    private Button BtnSave,BtnPreview;
    private RelativeLayout bottom;
    private SpinKitView progress_load;
    //pour le controle de position des étapes
    private int FocusActivity = 0;
    private String mGenre="",mYyear_select="",mSpecialite="";

    private EMedecin eMedecin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_medecin);
        initWidget();

    }
    @Override
    protected void onResume() {
        super.onResume();
        event();
    }

    private void initWidget(){
        // circular steep
        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        //Les portion
        id_view_medecin_step_one = findViewById(R.id.id_view_medecin_step_one);
        id_view_medecin_step_last = findViewById(R.id.id_view_medecin_step_last);
        //Buttons
        BtnSave = findViewById(R.id.BtnSave);
        BtnPreview = findViewById(R.id.BtnPreview);
        BtnPreview.setVisibility(View.GONE);
        //ImageButton
        bt_year = findViewById(R.id.bt_year);
        //Spinner
        spinner_genre = findViewById(R.id.spinner_genre);
        spinner_specialite = findViewById(R.id.spinner_specialite);
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
        edt_num_ordre = findViewById(R.id.edt_num_ordre);
        edt_hopital = findViewById(R.id.edt_hopital);
        edit_pass = findViewById(R.id.edit_pass);
        edit_pass_confirm = findViewById(R.id.edit_pass_confirm);

        //initialiser les spinner
        loadList();

    }

    private void event(){
        txt_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geYear();
            }
        });

        bt_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geYear();
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

        spinner_specialite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpecialite=spinner_specialite.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                        boolean b = isEmptyFields(new Object[]{edt_name,edt_postname,edt_firstname, edt_phone,edt_email});
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
                        String pass = edit_pass.getText().toString();
                        String confirmPass = edit_pass_confirm.getText().toString();
                        //on vérifie si les champs sont non vides avant de passer
                        boolean b1 = isEmptyFields(new Object[]{edt_num_ordre,edt_hopital,edit_pass,edit_pass_confirm});
                        if(mGenre.equals(""))
                        {
                            showCFAlertDialog(ActivityRegisterMedecin.this,"Info","Vous devez sélectionner votre genre avant de continuer", ETypeMessage.ERROR);
                            return;
                        }

                        if(mYyear_select.equals(""))
                        {
                            showCFAlertDialog(ActivityRegisterMedecin.this,"Info","Vous devez renseigner votre année de naissance avant de continuer", ETypeMessage.ERROR);

                            return;
                        }
                        if (!b1) {
                            if(pass.length() < 6)
                            {
                                showCFAlertDialog(ActivityRegisterMedecin.this,"Info","Votre mot de passe doit avoir au moins 6 caractères", ETypeMessage.ERROR);
                                return;
                            }

                            if(confirmPass.length() < 6)
                            {
                                showCFAlertDialog(ActivityRegisterMedecin.this,"Info","Votre mot de passe de confirmation doit avoir au moins 6 caractères", ETypeMessage.ERROR);
                                return;

                            }

                            if (!pass.equals(confirmPass)) {

                                showCFAlertDialog(ActivityRegisterMedecin.this,"Info","Les deux mot de passes doivent être identiques", ETypeMessage.ERROR);
                            }
                            else {
                                //
                                progress_load.setVisibility(View.VISIBLE);
                                bottom.setVisibility(View.GONE);
                                //tout est bon
                                String nom = edt_name.getText().toString();
                                String postnom = edt_postname.getText().toString();
                                String prenom = edt_firstname.getText().toString();
                                String year = txt_year.getText().toString();
                                String tel = edt_phone.getText().toString();
                                String email = edt_email.getText().toString();

                                String numOdre = edt_num_ordre.getText().toString();
                                String hopital = edt_hopital.getText().toString();

                                eMedecin=new EMedecin();
                                //step 1
                                eMedecin.setNom(nom);
                                eMedecin.setPostnom(postnom);
                                eMedecin.setPrenom(prenom);
                                eMedecin.setAnnee_naissance(Integer.valueOf(year));
                                eMedecin.setTelephone("+243"+tel);
                                eMedecin.setEmail(email);
                                //raccourcir sexe
                                if(mGenre.equals("Homme")){
                                    eMedecin.setSexe("H");
                                }else if(mGenre.equals("Femme")){
                                    eMedecin.setSexe("F");
                                }
                                //Step 2
                                eMedecin.setNum_ordre(numOdre);
                                eMedecin.setSpecialite(mSpecialite);
                                eMedecin.setPassword(pass);
                                eMedecin.setHopital(hopital);
                                eMedecin.setDate_create(UtilTimeStampToDate.getTimeStamp());
                                eMedecin.setDate_update(UtilTimeStampToDate.getTimeStamp());
                                //prepare insertion
                                //insertLocal(eMedecin);
                                insertRemote(eMedecin);
                                //insert();


                            }


                        }

                        break;

                    default:
                        break;
                }
            }
        });

    }

    private void insert(){
        try {
        JSONObject src = new JSONObject("{\"countryCode\": \"CD\",\"currencyCode\": \"CDF\",\"phoneNumber\": \"+234810183549\",\"taxCode\": \"1234\",\"latitude\": 12.3456,\"longitude\": 12.3456}");
        JSONObject dest = new JSONObject("{\"countryCode\": \"CD\",\"currencyCode\": \"CDF\",\"phoneNumber\": \"+234810183549\",\"taxCode\": \"1234\",\"latitude\": 12.3456,\"longitude\": 12.3456}");
        JSONObject datasetSpecificFields = new JSONObject("{\"interest\": \"Value for interest\",\"details\": \"Value for details\",\"nom_patient\": \"Mamonekene\",\"sexe_patient\": \"masculin\"}");

        //create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = new JSONObject("{\"src\": \"Hello\"}");
        JSONObject strBody = new JSONObject ("{\"src\":\""+src+"\",\"dest\":\""+dest+"\",\"srcCryptoWallet\":\"XXX-XXX-XXX-XXX\",\"destCryptoWalletCode\":\"XXX\",\"amount\":1234.5678,\"datasetCode\":\"ID000\",\"datasetSpecificFields\":\""+datasetSpecificFields+"\"}");

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Parameters.URL_REQUEST_TRANSACTION, strBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress_load.setVisibility(View.GONE);
                bottom.setVisibility(View.VISIBLE);
                //now handle the response
                Toast.makeText(ActivityRegisterMedecin.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_load.setVisibility(View.GONE);
                bottom.setVisibility(View.VISIBLE);
                //handle the error
                Toast.makeText(ActivityRegisterMedecin.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-Key", Parameters.TOKEN);
                params.put("content-type", "application/json");
                return params;
            }
        };

// Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);
    } catch (JSONException e) {
        e.printStackTrace();
        }
    }

    private void insertRemote(EMedecin o){

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
                    "    \"datasetCode\": \"DSI06\",\n" +
                    "    \"datasetSpecificFields\": {\n" +
                    "        \"nom\": \""+o.getNom()+"\",\n" +
                    "        \"postnom\": \""+o.getPostnom()+"\",\n" +
                    "        \"prenom\": \""+o.getPrenom()+"\",\n" +
                    "        \"sexe\": \""+o.getSexe()+"\",\n" +
                    "        \"specialite\": \""+o.getSpecialite()+"\",\n" +
                    "        \"hopital\": \""+o.getHopital()+"\",\n" +
                    "        \"annee_naissance\": "+o.getAnnee_naissance()+",\n" +
                    "        \"phone\": \""+o.getTelephone()+"\",\n" +
                    "        \"email\": \""+o.getEmail()+"\",\n" +
                    "        \"password\": \""+o.getPassword()+"\",\n" +
                    "        \"num_ordre\": \""+o.getDate_update()+"\"\n" +
                    "    }\n" +
                    "}";

            HttpRequest.addTransaction(ActivityRegisterMedecin.this, new String[]{param}, new HttpCallbackString() {
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
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityRegisterMedecin.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Echec")
                            .setCancelable(false)
                            .setMessage("Votre inscription n'a pas abouti")
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


//            StringRequest stringRequest = new StringRequest(Request.Method.POST, Parameters.URL_REQUEST_TRANSACTION, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    progress_load.setVisibility(View.GONE);
//                    bottom.setVisibility(View.VISIBLE);
//                    Toast.makeText(ActivityRegisterMedecin.this, response, Toast.LENGTH_SHORT).show();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    progress_load.setVisibility(View.GONE);
//                    bottom.setVisibility(View.VISIBLE);
//                    Toast.makeText(ActivityRegisterMedecin.this, "error"+error, Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//               @Override
//               public Map<String, String> getHeaders() {
//             //  public Map<String, String> getParams() throws AuthFailureError{
//                   Map<String, String> params = new HashMap<String, String>();
//                     params.put("X-API-Key", Parameters.TOKEN);
//                   // params.put("X-API-Key", Parameters.TOKEN);
//                    //params.put("TOKEN", Parameters.TOKEN);
//                    params.put("content-type", "application/json ");
//                    //header.put("API-Key", Parameters.TOKEN);
//                    //header.put(TokenDM.AUTHORIZATION, TokenDM.BEARER + token.getToken());
//                   // return header;
//                    return params;
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                       // return strBody == null ? null : strBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                        return null;
//                    }
//
//                }
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//
//                        responseString = String.valueOf(response.statusCode);
//
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
//            };
//
//            //requestQueue.add(stringRequest);
//            Volley.newRequestQueue(this).add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

            //request a json object response
//            StringRequest strRequest = new StringRequest(Request.Method.POST, url, jsonBody, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                    //now handle the response
//                    Toast.makeText(ActivityRegisterMedecin.this, response, Toast.LENGTH_SHORT).show();
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LOG_VOLLEY", error.toString());
//                }
//            }) {    //this is the part, that adds the header to the request
//                @Override
//                public Map<String, String> getHeaders() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("x-vacationtoken", "secret_token");
//                    params.put("content-type", "application/json");
//                    return params;
//                }
//            };
//
//// Add the request to the queue
//            Volley.newRequestQueue(this).add(strRequest);
//        }catch (JSONException e){
//
//        }
    }

    private void finalInsertRemote(EMedecin o,String ref_insert){

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
                    "    \"datasetCode\": \"DSI03\",\n" +
                    "    \"datasetSpecificFields\": {\n" +
                    "        \"nom\": \""+o.getNom()+"\",\n" +
                    "        \"postnom\": \""+o.getPostnom()+"\",\n" +
                    "        \"prenom\": \""+o.getPrenom()+"\",\n" +
                    "        \"sexe\": \""+o.getSexe()+"\",\n" +
                    "        \"specialite\": \""+o.getSpecialite()+"\",\n" +
                    "        \"hopital\": \""+o.getHopital()+"\",\n" +
                    "        \"annee_naissance\": "+o.getAnnee_naissance()+",\n" +
                    "        \"phone\": \""+o.getTelephone()+"\",\n" +
                    "        \"email\": \""+o.getEmail()+"\",\n" +
                    "        \"password\": \""+o.getPassword()+"\",\n" +
                    "        \"num_ordre\": \""+o.getNum_ordre()+"\",\n" +
                    "        \"ref_medecin\": \""+ref_insert+"\"\n" +
                    "    }\n" +
                    "}";

            HttpRequest.addTransaction(ActivityRegisterMedecin.this, new String[]{param}, new HttpCallbackString() {
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
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityRegisterMedecin.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Echec")
                            .setCancelable(false)
                            .setMessage("Votre inscription n'a pas abouti")
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

    private void insertLocal(EMedecin o){
        Boolean b = MedecinDao.create(o);
        if(b){
            //enregistrer en ligne ou en local
            progress_load.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
            //
            Preferences.save(Keys.PreferencesKeys.USER_NAME, o.getNom());
            Preferences.save(Keys.PreferencesKeys.USER_NUM_ORDRE, o.getNum_ordre());
            Preferences.save(Keys.PreferencesKeys.USER_PSEUDO, o.getEmail());
            Preferences.save(Keys.PreferencesKeys.USER_PASS_WORD, o.getPassword());
            Preferences.save(Keys.PreferencesKeys.FIRST_CONNECT, "YES");
            //
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityRegisterMedecin.this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                    .setTitle("Info")
                    .setCancelable(false)
                    .setMessage("Votre inscription est effectuée avec succès")
                    .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent signin = new Intent(ActivityRegisterMedecin.this, ActivityHome.class);
                            startActivity(signin);
                            finish();
                        }
                    });
            builder.show(); // Show
        }

    }

    //utilitaire pour les spinner
    private void refresh(SearchableSpinner spinner, List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(ActivityRegisterMedecin.this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adp);
    }
    private void loadList(){
        List<String> genre=new ArrayList<>();
        genre.add("Homme");
        genre.add("Femme");
        refresh(spinner_genre,genre);
        //
        List<String> specialite=new ArrayList<>();
        specialite.add("Allergologie/Immunologie");
        specialite.add("Anesthésiologie");
        specialite.add("Andrologie");
        specialite.add("Cardiologie");
        specialite.add("Chirurgie");
        specialite.add("Dermatologie");
        specialite.add("Endocrinologie");
        specialite.add("Gynécologie");
        specialite.add("Hématologie");
        specialite.add("Hépatologie");
        specialite.add("Infectiologie");
        specialite.add("Médecine aiguë");
        specialite.add("Médecine du travail");
        specialite.add("Médecine générale");
        specialite.add("Médecine interne");
        specialite.add("Médecine nucléaire");
        specialite.add("Médecine palliative");
        specialite.add("Médecine physique");
        specialite.add("Médecine préventive");
        specialite.add("Néonatologie");
        specialite.add("Néphrologie");
        specialite.add("Neurologie");
        specialite.add("Odontologie");
        specialite.add("Oncologie");
        specialite.add("Obstétrique");
        specialite.add("Ophtalmologie");
        specialite.add("Orthopédie");
        specialite.add("Oto-rhino-laryngologie");
        specialite.add("Pédiatrie");
        specialite.add("Pneumologie");
        specialite.add("Psychiatrie");
        specialite.add("Radiologie");
        specialite.add("Radiothérapie");
        specialite.add("Rhumatologie");
        specialite.add("Urologie");

        refresh(spinner_specialite,specialite);
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
    ///méthodes pour controle des étapes
    //method custom visibility
    private void visibilityControl(int position)
    {
        switch (position)
        {
            case 0:
                //la portion à afficher et celles à cacher
                id_view_medecin_step_one.setVisibility(View.VISIBLE);
                id_view_medecin_step_last.setVisibility(View.GONE);

                break;

            case 1:

                //la portion à afficher et celles à cacher
                id_view_medecin_step_one.setVisibility(View.GONE);
                id_view_medecin_step_last.setVisibility(View.VISIBLE);

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
                circle1.setBackground(ActivityRegisterMedecin.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                circle2.setBackground(ActivityRegisterMedecin.this.getResources().getDrawable(R.drawable.circlebarre));
                FocusActivity=position;
                break;

            case 1:

                circle1.setBackground(ActivityRegisterMedecin.this.getResources().getDrawable(R.drawable.circlebarre));
                circle2.setBackground(ActivityRegisterMedecin.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                FocusActivity = position;
                break;

            default:

                break;

        }

    }
    private void geYear()
    {
        String[] date= UtilTimeStampToDate.getDate().split("-");

        int year=Integer.valueOf(date[0]);
        int month=Integer.valueOf(date[1]);
        int day=Integer.valueOf(date[0]);

        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ActivityRegisterMedecin.this,
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) { // on date set }

                    }
                }, 2090, 11);

        builder.setMinYear(1990)
                .setActivatedYear(year)
                .setMaxYear(2090)
                .setTitle("Année de naissance")
                .setYearRange(1900, 2090)
                .showYearOnly()
                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear)
                    {
                        mYyear_select=selectedYear+"";
                        txt_year.setText(String.format("%s", mYyear_select));
                    }
                })

                .build()
                .show();


    }
}