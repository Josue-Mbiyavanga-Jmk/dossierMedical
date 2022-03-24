package com.example.dossier_medical.Entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_glycemie")
public class EGlycemie {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "ref")
    private String ref;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "unite")
    private String unite;
    @DatabaseField(columnName = "resultat")
    private String resultat;
    @DatabaseField(columnName = "remarque")
    private String remarque;
    @DatabaseField(columnName = "date")
    private String date;
    @DatabaseField(columnName = "heure")
    private String heure;
    @DatabaseField(columnName = "status")
    private int status;
    @DatabaseField(columnName = "medecin_create")
    private String medecin_create;
    @DatabaseField(columnName = "date_create")
    private Long date_create;
    @DatabaseField(columnName = "date_update")
    private Long date_update;


    public EGlycemie() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMedecin_create() {
        return medecin_create;
    }

    public void setMedecin_create(String medecin_create) {
        this.medecin_create = medecin_create;
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
