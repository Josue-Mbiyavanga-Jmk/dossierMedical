package com.example.dossier_medical.Dao;


import com.example.dossier_medical.DataBases.DatabaseManager;
import com.example.dossier_medical.Entites.EMedecin;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedecinDao {

    public static boolean create(EMedecin o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getMedecinDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(EMedecin o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getMedecinDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EMedecin get(String ref) {
        EMedecin item = null;
        try {
            List<EMedecin> dataList = DatabaseManager.getInstance().getHelper()
                    .getMedecinDao().queryForEq("_id", ref);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    public static EMedecin get(int id) {
        EMedecin item = null;
        try {
            List<EMedecin> dataList = DatabaseManager.getInstance().getHelper()
                    .getMedecinDao().queryForEq("id", id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    public static List<EMedecin> getAll() {

        List<EMedecin> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getMedecinDao()
                    .queryBuilder().orderBy("id", true)
                    //.where().eq("", "")
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }



    public static boolean delete(int Id) {
        boolean b = true;
        try {
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getMedecinDao().deleteBuilder();
            deleteBuilder.
                    where().eq("id",Id);
            deleteBuilder.delete();
        } catch (Exception e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }

    public static long count() {
        long count = 0;
        try {
            count = DatabaseManager.getInstance().getHelper()
                    .getMedecinDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
