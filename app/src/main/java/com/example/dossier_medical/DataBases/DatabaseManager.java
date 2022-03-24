package com.example.dossier_medical.DataBases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Entites.EMedecin;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by kevin lukanga 01/05/2019.
 */
@SuppressLint("NewApi")
public class DatabaseManager<T> {
    private static Context context;
    @SuppressWarnings("rawtypes")
    static private DatabaseManager instance;
    private DatabaseHelper helper;

    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    @SuppressWarnings("rawtypes")
    static public void init(Context ctx) {
        context = ctx;
        if (null == instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    @SuppressWarnings("rawtypes")
    static public DatabaseManager getInstance() {
        if (null == instance) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

   /* public static boolean isFileExiste() {
        try {
            new FileOutputStream(DatabaseHelper.DB_PATH
                    + DatabaseHelper.DATABASE_NAME);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }*/

  /*  public static boolean isFileExist() {
        try {
            new FileOutputStream(DatabaseHelper.DB_PATH
                    + DatabaseHelper.DATABASE_NAME);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }*/

    public static void clearMedecin() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EMedecin.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearMalade() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EMalade.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







    public DatabaseHelper getHelper() {
        return helper;
    }

    public SQLiteDatabase getSQDatabase() {
        SQLiteDatabase sqDatabase = null;
        try {

            sqDatabase = helper.getWritableDatabase();
        } catch (Exception e) {
            Log.w("Erreur",  e + "");
        }
        return sqDatabase;
    }

    public void viderTable(Class<T> entity) {
        ConnectionSource connectionSource = getHelper().getConnectionSource();
        try {
            TableUtils.clearTable(connectionSource, entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionSource getConnectionSource() {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = getHelper().getConnectionSource();
        } catch (Exception e) {
        }
        return connectionSource;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        SQLiteDatabase sqDatabase = null;
        try {

            sqDatabase = helper.getWritableDatabase();
        } catch (Exception e) {
            Log.w("Erreur",  e + "");
        }
        return sqDatabase;
    }

    public void clearTable(Class<T> entity) {
        ConnectionSource connectionSource = getHelper().getConnectionSource();
        try {
            TableUtils.clearTable(connectionSource, entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
