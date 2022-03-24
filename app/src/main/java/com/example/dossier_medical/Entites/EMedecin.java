package com.example.dossier_medical.Entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_medecin")
public class EMedecin {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "ref")
    private String ref;
    @DatabaseField(columnName = "nom")
    private String nom;
    @DatabaseField(columnName = "postnom")
    private String postnom;
    @DatabaseField(columnName = "prenom")
    private String prenom;
    @DatabaseField(columnName = "sexe")
    private String sexe;
    @DatabaseField(columnName = "specialite")
    private String specialite;
    @DatabaseField(columnName = "annee_naissance")
    private int annee_naissance;
    @DatabaseField(columnName = "telephone")
    private String telephone;
    @DatabaseField(columnName = "email")
    private String email;
    @DatabaseField(columnName = "num_ordre")
    private String num_ordre;
    @DatabaseField(columnName = "hopital")
    private String hopital;
    @DatabaseField(columnName = "password")
    private String password;
    @DatabaseField(columnName = "date_create")
    private Long date_create;
    @DatabaseField(columnName = "date_update")
    private Long date_update;
   // @DatabaseField(columnName = "version")
    //private int __v;

    public EMedecin() {
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

    public String getPostnom() {
        return postnom;
    }

    public void setPostnom(String postnom) {
        this.postnom = postnom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public int getAnnee_naissance() {
        return annee_naissance;
    }

    public void setAnnee_naissance(int annee_naissance) {
        this.annee_naissance = annee_naissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum_ordre() {
        return num_ordre;
    }

    public void setNum_ordre(String num_ordre) {
        this.num_ordre = num_ordre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getHopital() {
        return hopital;
    }

    public void setHopital(String hopital) {
        this.hopital = hopital;
    }
}
