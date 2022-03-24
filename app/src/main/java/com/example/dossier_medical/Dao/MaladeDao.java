package com.example.dossier_medical.Dao;


import com.example.dossier_medical.DataBases.DatabaseManager;
import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Entites.EMedecin;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaladeDao {

    public static boolean create(EMalade o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getMaladeDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(EMalade o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getMaladeDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EMalade get(String ref) {
        EMalade item = null;
        try {
            List<EMalade> dataList = DatabaseManager.getInstance().getHelper()
                    .getMaladeDao().queryForEq("_id", ref);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    public static EMalade get(int id) {
        EMalade item = null;
        try {
            List<EMalade> dataList = DatabaseManager.getInstance().getHelper()
                    .getMaladeDao().queryForEq("id", id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    public static List<EMalade> getAll() {

        List<EMalade> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getMaladeDao()
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
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getMaladeDao().deleteBuilder();
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
                    .getMaladeDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
