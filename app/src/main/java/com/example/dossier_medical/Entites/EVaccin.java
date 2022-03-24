package com.example.dossier_medical.Entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_vaccin")
public class EVaccin {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "ref")
    private String ref;
    @DatabaseField(columnName = "nom")
    private String nom;
    @DatabaseField(columnName = "date")
    private String date;
    @DatabaseField(columnName = "medecin_create")
    private String medecin_create;
    @DatabaseField(columnName = "status")
    private int status;
    @DatabaseField(columnName = "date_create")
    private Long date_create;
    @DatabaseField(columnName = "date_update")
    private Long date_update;
   // @DatabaseField(columnName = "version")
    //private int __v;

    public EVaccin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedecin_create() {
        return medecin_create;
    }

    public void setMedecin_create(String medecin_create) {
        this.medecin_create = medecin_create;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getDate_create() {
        return date_create;
    }

    public void setDate_create(Long date_create) {
        this.date_create = date_create;
    }

    public Long getDate_update() {
        return date_update;
    }

    public void setDate_update(Long date_update) {
        this.date_update = date_update;
    }

}
