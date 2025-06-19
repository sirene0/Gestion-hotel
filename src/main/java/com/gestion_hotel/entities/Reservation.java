package com.gestion_hotel.entities;

import java.util.Date;

import jakarta.persistence.TemporalType;

import com.gestion_hotel.enums.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;

@Entity
@Table(name="Reservations")

public class Reservation {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id ;
    /**
     *
     */
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Enumerated(EnumType.STRING)
    private Statut statut;
    @ManyToOne
    @JoinColumn(name="client_id",nullable = false)
    private Client c;
    @ManyToOne
    @JoinColumn(name="chambre_id",nullable = false)
    private Chambre ch;
    
    @OneToOne(mappedBy ="reservation",cascade = CascadeType.ALL)
    private Paiment paiement;
    
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true )
    private Adminstrateur admin;

    public Reservation() {
    }


    public Reservation( Date dateDebut, Date dateFin, Statut statut, Client c, Chambre ch) {

        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.c = c;
        this.ch = ch;
        this.admin =admin;
    }

    public long getId() {return id;}
    public void setId(long id) {    this.id = id; }
    public Date getDateDebut() {    return dateDebut;}
    public void setDateDebut(Date dateDebut) {    this.dateDebut = dateDebut;}
    public Date getDateFin() {     return dateFin;}
    public void setDateFin(Date dateFin) {    this.dateFin = dateFin;}
    public Statut getStatut() {    return statut;}
    public void setStatut(Statut statut) {    this.statut = statut;}
    public Client getC() {    return c;}
    public void setC(Client c) {     this.c = c;}
    public Chambre getCh() {    return ch;}
    public void setCh(Chambre ch) {    this.ch = ch;}

    public void confirmer(){
        this.statut=Statut.confirmee;
        ch.rendreindisponibe();
        System.out.println("Réservation confirmée !");
    }
    public void annuler(){
        this.statut=Statut.annulee;
        ch.rendreDisponible();
        System.out.println("Réservation annulée !");
    }
    public double calculerPrixTotal(){  
        Long diff = dateFin.getTime() - dateDebut.getTime();
        Long nbrjour=diff /(1000*60*60*24);
        return nbrjour*ch.getPrix();
        }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((dateDebut == null) ? 0 : dateDebut.hashCode());
        result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
        result = prime * result + ((statut == null) ? 0 : statut.hashCode());
        result = prime * result + ((c == null) ? 0 : c.hashCode());
        result = prime * result + ((ch == null) ? 0 : ch.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reservation other = (Reservation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (dateDebut == null) {
            if (other.dateDebut != null)
                return false;
        } else if (!dateDebut.equals(other.dateDebut))
            return false;
        if (dateFin == null) {
            if (other.dateFin != null)
                return false;
        } else if (!dateFin.equals(other.dateFin))
            return false;
        if (statut == null) {
            if (other.statut != null)
                return false;
        } else if (!statut.equals(other.statut))
            return false;
        if (c == null) {
            if (other.c != null)
                return false;
        } else if (!c.equals(other.c))
            return false;
        if (ch == null) {
            if (other.ch != null)
                return false;
        } else if (!ch.equals(other.ch))
            return false;
        return true;
    }
    
}
