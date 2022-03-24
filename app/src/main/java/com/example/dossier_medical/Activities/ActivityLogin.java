package com.example.dossier_medical.Activities;

import androidx.appcompat.app.AppCompatActivity;
import customfonts.MyTextView_Roboto_Regular;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.dossier_medical.Entites.EServeur;
import com.example.dossier_medical.Memory.Keys;
import com.example.dossier_medical.Memory.Preferences;
import com.example.dossier_medical.R;
import com.example.dossier_medical.Utils.ETypeMessage;
import com.example.dossier_medical.Utils.UtilEServeur;
import com.example.dossier_medical.Utils.UtilsConnexionData;
import com.github.ybq.android.spinkit.SpinKitView;

import static com.example.dossier_medical.Utils.UtilsToast.showCFAlertDialog;

public class ActivityLogin extends AppCompatActivity {

    private MyTextView_Roboto_Regular Btconnecte, Btregister;

    private EditText edit_pass, edit_pseudo;
    private SpinKitView progress_load;
    private View base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseWidget();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void initialiseWidget() {
        Btconnecte=findViewById(R.id.Btconnect);
        Btregister=findViewById(R.id.BtRegister);
        edit_pass=findViewById(R.id.edit_pass);
        edit_pseudo =findViewById(R.id.edit_adress);
        base=findViewById(R.id.base);
        progress_load=findViewById(R.id.spin_kit_load);
        progress_load.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        event();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void event() {
    //
        Btconnecte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstConnection = Preferences.get(Keys.PreferencesKeys.FIRST_CONNECT);
                if(firstConnection == null) {
                    Btconnecte.setEnabled(false);//on desactive le temps de la requete
                    Boolean connect = UtilsConnexionData.isConnected(ActivityLogin.this);
                    //connexion existante
                    if(connect) {
                        remoteLogin();
                    }else {
                        showCFAlertDialog(ActivityLogin.this,"Pas de connexion",
                                "Problème de connexion survenu, veuillez la vérifier.", ETypeMessage.ERROR);
                        Btconnecte.setEnabled(true);//
                    }

                } else {
                    Btconnecte.setEnabled(false);//on desactive le temps de la requete
                    localLogin();
                }

            }

        }
        );
    //
        Btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstConnection = Preferences.get(Keys.PreferencesKeys.FIRST_CONNECT);
                if(firstConnection != null) {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityLogin.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec.")
                        .setCancelable(false)
                        .setMessage("Vous etes déjà inscrit, veuillez vous connectez.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Btconnecte.setEnabled(true);//
                            }
                        });

                builder.show(); // Show
                }else{
                    Intent signin = new Intent(ActivityLogin.this, ActivityRegisterMedecin.class);
                    startActivity(signin);
                }

            }

        }
        );
    }

    private void remoteLogin() {

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityLogin.this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setTitle("Echec.")
                .setCancelable(false)
                .setMessage("Veuillez vous inscrire tout d'abord.")
                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.show(); // Show
        Btconnecte.setEnabled(true);//
        /*progress_load.setVisibility(View.VISIBLE);
        String pseudo = edit_pseudo.getText().toString();
        final String pwd = edit_pass.getText().toString();
        //simuler auth
        Preferences.save(Keys.PreferencesKeys.USER_PSEUDO, pseudo);
        Preferences.save(Keys.PreferencesKeys.USER_PASS_WORD, pwd);
        //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
                progress_load.setVisibility(View.GONE);
                Intent signin = new Intent(ActivityLogin.this, ActivityHome.class);
                startActivity(signin);
                finish();
            }
        }, 3000);
        EServeur serveur = UtilEServeur.getServeur();*/
//        HttpRequest.loginUser(ActivityLogin.this, serveur, new String[]{pseudo, pwd}, new HttpCallbackString() {
//            @Override
//            public void onSuccess(String response) {
//                try {
//                    //logique du HTTP
//                    JSONObject jsonObject = new JSONObject(response);
//                //} catch (JSONException e) {
//                } catch (JSONException e) {
//                    progress_load.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onError(String message) {
//                progress_load.setVisibility(View.GONE);
//                Btconnecte.setEnabled(true);//on reactive le temps de la requete
//                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityLogin.this)
//                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
//                        .setTitle("Opération non aboutie.")
//                        .setCancelable(false)
//                        .setMessage("Veuillez vérifier la connexion et reprendre l'action.")
//                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builder.show(); // Show
//            }
//
//
//        });
    }
    private void localLogin(){
        progress_load.setVisibility(View.VISIBLE);
        String pseudo = edit_pseudo.getText().toString();
        String pwd = edit_pass.getText().toString();

        String pseudoIntern = Preferences.get(Keys.PreferencesKeys.USER_PSEUDO);
        String passwordIntern = Preferences.get(Keys.PreferencesKeys.USER_PASS_WORD);
        if(pseudo.equals(pseudoIntern) && pwd.equals(passwordIntern)){
            progress_load.setVisibility(View.GONE);
            Btconnecte.setEnabled(true);//on reactive le temps de la requete
            Intent signin = new Intent(ActivityLogin.this, ActivityHome.class);
            startActivity(signin);
            finish();
        }else {
            progress_load.setVisibility(View.GONE);
            Btconnecte.setEnabled(true);//on reactive le temps de la requete
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityLogin.this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                    .setTitle("Connexion echouée.")
                    .setCancelable(false)
                    .setMessage("Veuillez vérifier vos identifiants de connexion puis réessayer.")
                    .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.show(); // Show
        }
    }

    }