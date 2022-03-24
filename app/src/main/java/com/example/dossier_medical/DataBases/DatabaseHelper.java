package com.example.dossier_medical.DataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Entites.EMedecin;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


/**
 * Created by kevin lukanga 01/05/2019.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    //DatabaseHelper
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "internGeoEntreprise.db";
    private static final int DATABASE_VERSION = 1;

    //Data Access Object
    private Dao<EMedecin, String> eMedecinDao = null;
    private Dao<EMalade, String> eMaladeDao = null;




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, EMedecin.class);
            TableUtils.createTable(connectionSource, EMalade.class);


        } catch (Exception e) {
            Log.e(TAG, "BD n'a pas été créée", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

        }catch (Exception e) {
            Log.e(TAG, "BD n'a pas été créée", e);
            throw new RuntimeException(e);
        }
    }

    //customize dao action

    public Dao<EMedecin, String> getMedecinDao() {
        if (null == eMedecinDao) {
            try {
                eMedecinDao = getDao(EMedecin.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eMedecinDao;
    }
    public Dao<EMalade, String> getMaladeDao() {
        if (null == eMaladeDao) {
            try {
                eMaladeDao = getDao(EMalade.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eMaladeDao;
    }


}
