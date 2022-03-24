package com.example.dossier_medical.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
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
import com.example.dossier_medical.Utils.UtilsToast;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.dossier_medical.Utils.UtilsToast.showCFAlertDialogFrag;


public class FragActionTension extends Fragment implements DatePickerDialog.OnDateSetListener  {

    View root;
    private FloatingActionButton BtAdd;
    private ImageButton bt_year,bt_heure_start;
    private TextView txt_year,txt_heure_start;
    private SpinKitView progress;
    private String mYyear_select="",mHstart="",mSystolique="",mDiastolique="",mPouls="";
    private EMalade eMalade;
    private NumberPicker numberPicker,numberPicker2,numberPicker3;
    TimePickerDialog picker;

    final String[] data = {
            "040", "041", "042", "043", "044", "045", "046", "047", "048", "049",
            "050", "051", "052", "053", "054", "055", "056", "057", "058", "059",
            "060", "061", "062", "063", "064", "065", "066", "067", "068", "069",
            "070", "071", "072", "073", "074", "075", "076", "077", "078", "079",
            "080", "081", "082", "083", "084", "085", "086", "087", "088", "089",
            "090", "091", "092", "093", "094", "095", "096", "097", "098", "099",
            "100", "101", "102", "103", "104", "105", "106", "107", "108", "109",
            "110", "111", "112", "113", "114", "115", "116", "117", "118", "119",
            "120", "121", "122", "123", "124", "125", "126", "127", "128", "129",
            "130", "131", "132", "133", "134", "135", "136", "137", "138", "139",
            "140", "141", "142", "143", "144", "145", "146", "147", "148", "149",
            "150", "151", "152", "153", "154", "155", "156", "157", "158", "159",
            "160", "161", "162", "163", "164", "165", "166", "167", "168", "169",
            "170", "171", "172", "173", "174", "175", "176", "177", "178", "179",
            "180", "181", "182", "183", "184", "185", "186", "187", "188", "189",
            "190", "191", "192", "193", "194", "195", "196", "197", "198", "199",
            "200", "201", "202", "203", "204", "205", "206", "207", "208", "209",
            "210", "211", "212", "213", "214", "215", "216", "217", "218", "219",
            "220", "221", "222", "223", "224", "225", "226", "227", "228", "229",
            "230", "231", "232", "233", "234", "235", "236", "237", "238", "239",
            "240", "241", "242", "243", "244", "245", "246", "247", "248", "249",
            "250", "251", "252", "253", "254", "255", "256", "257", "258", "259",
            "260", "261", "262", "263", "264", "265", "266", "267", "268", "269",
            "270", "271", "272", "273", "274", "275", "276", "277", "278", "279",
            "280", "281", "282", "283", "284", "285", "286", "287", "288", "289",
            "290", "291", "292", "293", "294", "295", "296", "297", "298", "299",
            "300"};
    //


    public FragActionTension() {
        // Required empty public constructor
    }
    public FragActionTension(EMalade eMalade) {
        //
        this.eMalade = eMalade;
    }


    // TODO: Rename and change types and number of parameters
    public static FragActionTension newInstance() {
        FragActionTension fragment = new FragActionTension();
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
        root = inflater.inflate(R.layout.frag_action_tension, container, false);

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
        //ButtonFloat
        BtAdd = root.findViewById(R.id.BtAdd);
        //Progress
        progress = root.findViewById(R.id.spin_kit_load_save);
        progress.setVisibility(View.GONE);
        //
        numberPicker = (NumberPicker) root.findViewById(R.id.number_picker);
        numberPicker2 = (NumberPicker) root.findViewById(R.id.number_picker2);
        numberPicker3 = (NumberPicker) root.findViewById(R.id.number_picker3);
        //
        numberPicker.setMinValue(1);
        numberPicker2.setMinValue(1);
        numberPicker3.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker2.setMaxValue(data.length);
        numberPicker3.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker2.setDisplayedValues(data);
        numberPicker3.setDisplayedValues(data);

    }

    void events(){
        //
        BtAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(mSystolique.equals("") || mDiastolique.equals("") || mPouls.equals(""))
                {
                    showCFAlertDialogFrag(FragActionTension.this,"Info","Vous devez renseigner les valeurs de la tension prelevée", ETypeMessage.ERROR);

                    return;
                }
                else if(mYyear_select.equals("") || mYyear_select.equals("Date prelevement*") || mHstart.equals("") || mHstart.equals("Heure*"))
                {
                    showCFAlertDialogFrag(FragActionTension.this,"Info","Vous devez renseigner la date et/ou l'heure avant de continuer", ETypeMessage.ERROR);

                    return;
                }
                else {
                    insertLocal();
                }


            }
        });
        //date
        bt_year.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String[] date= UtilTimeStampToDate.getDate().split("-");

                int day=Integer.valueOf(date[2].split(" ")[0]);
                int month=Integer.valueOf(date[1]);
                int year=Integer.valueOf(date[0]);

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), R.style.MyDialogTheme, FragActionTension.this, year, month-1, day);

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
                        new DatePickerDialog(getActivity(), R.style.MyDialogTheme,FragActionTension.this, year, month-1, day);

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
        // OnClickListener
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });

        // OnScrollListener
        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    try{

                        int a = picker.getValue();
                        mSystolique = data[a-1];
                       /* Toast.makeText(getActivity(), "value="+data[a-1],
                                Toast.LENGTH_LONG).show();*/
                    } catch (Exception e){

                    }


                }
            }
        });


    // OnClickListener
        numberPicker2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });

    // OnValueChangeListener
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        }
    });

    // OnScrollListener
        numberPicker2.setOnScrollListener(new NumberPicker.OnScrollListener() {
        @Override
        public void onScrollStateChange(NumberPicker picker, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE) {
                try{

                    int a = picker.getValue();
                    mDiastolique = data[a-1];

                } catch (Exception e){

                }


            }
        }
    });
        // OnClickListener
        numberPicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // OnValueChangeListener
        numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });

        // OnScrollListener
        numberPicker3.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    try{

                        int a = picker.getValue();
                        mPouls = data[a-1];
                    } catch (Exception e){

                    }


                }
            }
        });


}

    void insertLocal(){
        progress.setVisibility(View.VISIBLE);
        BtAdd.setEnabled(false);
        ETension obj = new ETension();
        obj.setSystolique(mSystolique);
        obj.setDiastolique(mDiastolique);
        obj.setPouls(mPouls);
        obj.setDate(mYyear_select);
        obj.setHeure(mHstart);
        //on peut remplacer par reference docteur
        obj.setMedecin_create(Preferences.get(Keys.PreferencesKeys.USER_PSEUDO));
        obj.setDate_create(UtilTimeStampToDate.getTimeStamp());
        obj.setDate_update(UtilTimeStampToDate.getTimeStamp());
        //
        Gson gson = new Gson();
        List<ETension> listFinal = new ArrayList<>();
        String aslist = eMalade.getTension();//on recupere la liste dans la BD
        if(aslist == null){
            listFinal.add(obj);
        }else{
            List<ETension> list = gson.fromJson(aslist, new TypeToken<List<ETension>>(){}.getType());
            for(ETension tension : list){
                listFinal.add(tension);
            }
            //à la fin on ajoute la nouvelle tension
            listFinal.add(obj);

        }

        //
        String tensionList = gson.toJson(listFinal); //save list produit as String in the DB
        eMalade.setTension(tensionList);
        //encode
        String encodeTension = Utils.convertStringToBase64(tensionList);
        //pour le vaccin
        List<EVaccin> listFinal1 = new ArrayList<>();
        String encodeVaccin = "";
        String aslist1 = eMalade.getVaccin();//on recupere la liste dans la BD
        if(aslist1 != null){
            List<EVaccin> list1 = gson.fromJson(aslist1, new TypeToken<List<EVaccin>>(){}.getType());
            for(EVaccin vac : list1){
                listFinal1.add(vac);
            }
            //
            String vaccinList = gson.toJson(listFinal1); //save list produit as String in the DB
            eMalade.setVaccin(vaccinList);
            //encode
            encodeVaccin = Utils.convertStringToBase64(vaccinList);
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
            encodeGlycemie = Utils.convertStringToBase64(glycemieList);
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
                                .setMessage("La tension prelévée a été enregistrée")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
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
                            .setMessage("La tension prelévée n'a été enregistrée")
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

}