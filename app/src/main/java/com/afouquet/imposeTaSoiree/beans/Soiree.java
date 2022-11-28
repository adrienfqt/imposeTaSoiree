package com.afouquet.imposeTaSoiree.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Soiree implements Serializable {
    private int id;
    private String libCourt;
    private String descriptif;
    private String adresse;
    private Double lat;
    private Double lng;
    private Date dateDebut;
    private Date heureDebut;
    private String organisateur;

    public Soiree(int id,String libCourt, String descriptif, String adresse, Double lat, Double lng, Date dateDebut, Date heureDebut,String organisateur) {
        this.id = id;
        this.libCourt = libCourt;
        this.descriptif = descriptif;
        this.adresse = adresse;
        this.lat = lat;
        this.lng = lng;
        this.dateDebut = dateDebut;
        this.heureDebut = heureDebut;
        this.organisateur = organisateur;
    }

    public String getOrganisateur() {
        return organisateur;
    }

    public String getLibCourt() {
        return libCourt;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public String getAdresse() {
        return adresse;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdff = new SimpleDateFormat("dd-MM-yyyy");
        return this.libCourt +" ("+sdff.format(dateDebut)+")";
    }

    public int getId() {
        return id;
    }
}
