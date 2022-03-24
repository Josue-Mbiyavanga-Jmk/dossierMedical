package com.example.dossier_medical.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.dossier_medical.R;
import com.example.dossier_medical.Utils.RequestCode;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityMaladeSearch extends AppCompatActivity {

    private String value_decode;
    private boolean checkScan=false;
    private EditText edt_Id_malade;
    private ImageButton bt_search_qr;
    private SpinKitView progress_load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malade_search);
        //à enlever
        setTitle("Recherche malade");
        initialiseWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        event();
    }

    private void initialiseWidget() {
        bt_search_qr=findViewById(R.id.bt_search_qr);
        edt_Id_malade=findViewById(R.id.edt_Id_malade);
        progress_load=findViewById(R.id.spin_kit_load);
        progress_load.setVisibility(View.GONE);
    }

    private void event()
    {
        bt_search_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkScan)
                {
                    //qrCode avec zxing-android-embedded library
                    IntentIntegrator integrator = new IntentIntegrator(ActivityMaladeSearch.this);
                    integrator.setCaptureActivity(ActivityCapture.class);
                    integrator.setOrientationLocked(false);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("scanner le code QR");
                    integrator.initiateScan();

                }
            }
        });

        edt_Id_malade.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String value=edt_Id_malade.getText().toString().trim();
                    if(value.equals("")){
                        edt_Id_malade.setError("Ce champ est obligatoire");
                    }else{
                        //appel de la méthode
                        searchByToken(value);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                //appel de la methode de recherche
                searchByQR(result.getContents()); //hors campagne
            }else{
                Toast.makeText(ActivityMaladeSearch.this, "aucune valeur scannée",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void searchByQR(String value){
        progress_load.setVisibility(View.VISIBLE);
        bt_search_qr.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
                progress_load.setVisibility(View.GONE);
                bt_search_qr.setEnabled(true);
                Toast.makeText(ActivityMaladeSearch.this, "valeur scannée = "+value,
                        Toast.LENGTH_LONG).show();

            }
        }, 3000);

//        EServeur serveur = UtilEServeur.getServeur();
//        HttpRequest.getByQr(ActivityDonneurSearch.this, serveur, new String[]{value}, new HttpCallbackString() {
//            @Override
//            public void onSuccess(String response) {
//                try{
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    int result = jsonObject.getInt("result");
//                    if(result == 200) {
//
//
//                        //
//                        EDonneur eDonneur=null;
//                        JSONArray array = jsonObject.getJSONArray("data");
//                        for(int i=0;i<array.length();i++){
//
//                            JSONObject object = array.getJSONObject(i);
//                            eDonneur =  new Gson().fromJson(object.toString(),EDonneur.class);
//                            eDonneur.setId_native(eDonneur.getId());
//
//                            EDonneur eDonnExist=DonneurDao.get(eDonneur.getId());
//                            if(eDonnExist!=null){
//
//                                DonneurDao.update(eDonneur);
//                            }
//                            else
//                            {
//                                //mettre le status à 0 pour prouver que c'est un donneur de recherche
//                                if(eDonneur.getStatus() == 1){
//                                    eDonneur.setStatus(0);
//                                }
//                                DonneurDao.create(eDonneur);
//                            }
//
//
//
//                        }
//                        //arret de la progression
//                        hud.dismiss();
//
//                        if(eDonneur!=null){
//                            Intent go = new Intent(ActivityDonneurSearch.this, ActivityDonneurDetail.class);
//                            go.putExtra("donneurid", eDonneur.getId_native());
//                            startActivity(go);
//                        }else {
//
//                            hud.dismiss();
//                            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityDonneurSearch.this)
//                                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
//                                    .setTitle("Infos")
//                                    .setCancelable(false)
//                                    .setMessage("Aucun donneur portant ce code QR n'a été trouvé!")
//                                    .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            onBackPressed();
//                                        }
//                                    });
//
//                            builder.show(); // Show
//                        }
//                    }
//                    else {
//                        //
//
//                        //
//                        hud.dismiss();
//                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityDonneurSearch.this)
//                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
//                                .setTitle("Infos")
//                                .setCancelable(false)
//                                .setMessage("Aucun donneur portant ce code QR n'a été trouvé!")
//                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        onBackPressed();
//                                    }
//                                });
//
//                        builder.show(); // Show
//                    }
//
//                } catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(String message) {
//                //
//
//                hud.dismiss();
//                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityDonneurSearch.this)
//                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
//                        .setTitle("Echec")
//                        .setCancelable(false)
//                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
//                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                onBackPressed();
//                            }
//                        });
//
//                builder.show(); // Show
//            }
//        });
    }

    private void searchByToken(String value){
        edt_Id_malade.setEnabled(false);
        progress_load.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
                progress_load.setVisibility(View.GONE);
                edt_Id_malade.setEnabled(true);
                Toast.makeText(ActivityMaladeSearch.this, "valeur trouvée = "+value,
                        Toast.LENGTH_LONG).show();

            }
        }, 3000);

//        EServeur serveur = UtilEServeur.getServeur();
//        HttpRequest.getByToken(ActivityDonneurSearch.this, serveur, new String[]{value}, new HttpCallbackString() {
//            @Override
//            public void onSuccess(String response) {
//                try{
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    int result = jsonObject.getInt("result");
//                    if(result == 200) {
//
//
//                        //
//                        EDonneur eDonneur=null;
//                        JSONArray array = jsonObject.getJSONArray("data");
//                        for(int i=0;i<array.length();i++){
//
//                            JSONObject object = array.getJSONObject(i);
//                            eDonneur =  new Gson().fromJson(object.toString(),EDonneur.class);
//                            eDonneur.setId_native(eDonneur.getId());
//
//                            EDonneur eDonnExist=DonneurDao.get(eDonneur.getId());
//                            if(eDonnExist!=null){
//
//                                DonneurDao.update(eDonneur);
//                            }
//                            else
//                            {
//                                //mettre le status à 0 pour prouver que c'est un donneur de recherche
//                                if(eDonneur.getStatus() == 1){
//                                    eDonneur.setStatus(0);
//                                }
//                                DonneurDao.create(eDonneur);
//                            }
//                        }
//                        //arret de la progression
//                        hud.dismiss();
//
//                        if(eDonneur!=null){
//                            edt_Id_sos.setText("");
//                            Intent go = new Intent(ActivityDonneurSearch.this, ActivityDonneurDetail.class);
//                            go.putExtra("donneurid", eDonneur.getId_native());
//                            startActivity(go);
//                        }else {
//                            edt_Id_sos.setText("");
//                            hud.dismiss();
//                            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityDonneurSearch.this)
//                                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
//                                    .setTitle("Infos")
//                                    .setCancelable(false)
//                                    .setMessage("Aucun donneur portant cet ID sOs makila n'a été trouvé!")
//                                    .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//
//                            builder.show(); // Show
//                        }
//                    }
//                    else {
//
//                        edt_Id_sos.setText("");
//                        hud.dismiss();
//                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityDonneurSearch.this)
//                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
//                                .setTitle("Infos")
//                                .setCancelable(false)
//                                .setMessage("Aucun donneur portant cet ID sOs makila n'a été trouvé!")
//                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//
//                        builder.show(); // Show
//                    }
//
//                } catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(String message) {
//                //
//                edt_Id_sos.setText("");
//                hud.dismiss();
//                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityDonneurSearch.this)
//                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
//                        .setTitle("Echec")
//                        .setCancelable(false)
//                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
//                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builder.show(); // Show
//            }
//        });
    }

}