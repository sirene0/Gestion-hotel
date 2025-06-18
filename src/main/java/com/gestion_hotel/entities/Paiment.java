package com.gestion_hotel.entities;
import java.util.Date;



import com.gestion_hotel.enums.*;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table (name ="Paiement")

public class Paiment {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;

    private double montant;
    
    @Temporal(TemporalType.DATE)
    private Date datePaiment;

    @Enumerated(EnumType.STRING)
    private MethodeP methode;

    @Enumerated(EnumType.STRING) 
    private EtatP statut;

    @OneToOne
    @JoinColumn(name ="reservation_id",nullable = true)
    private Reservation reservation;

    private double totalres;

    
    public Paiment() {
    }

    public Paiment( double montant, Date datePaiment, MethodeP methode, EtatP statut) {
        this.montant = montant;
        this.datePaiment = datePaiment;
        this.methode = methode;
        this.statut = statut;
    }

    public Long getId() {    return id;}
    public void setId(Long id) {    this.id = id;}
    public double getMontant() {    return montant;}
    public void setMontant(double montant) {     this.montant = montant;}
    public Date getDatePaiment() {    return datePaiment;}
    public void setDatePaiment(Date datePaiment) {    this.datePaiment = datePaiment;}
    public MethodeP getMethode() {     return methode;}
    public void setMethode(MethodeP methode) {    this.methode = methode;}
    public EtatP getStatut() {    return statut;}
    public void setStatut(EtatP statut) {    this.statut = statut;}
    public Reservation getReservation() {    return reservation;}
    public void setReservation(Reservation reservation) {    this.reservation = reservation;}
    public double getTotalres() {return totalres;}
    public void setTotalres(double totalres) {    this.totalres = totalres;}
    //a revoir 
    public boolean validerPaiment(){
        if(reservation ==null){
            System.out.println("aucune reservation associee !");
            return false;
        }
        double montantTotal = reservation.calculerPrixTotal();

        if(this.montant >=montantTotal){
            this.statut =EtatP.reussi;
            System.out.println("paiment reussi ! ");
            if(this.montant>montantTotal){
                double reste = this.montant-montantTotal;
                System.out.println("Montant en trop :"+reste+"$");
            }
            return true;
        }else {
            totalres =montantTotal - this.montant ;
            
            System.out.println("Paiement insuffisant. Il manque"+totalres+"$");
            this.statut=EtatP.en_attente;
            return false;
        }
        
    }
    //a revoir 
    public void rembourser(){
        if (statut== EtatP.reussi) {
            this.statut = EtatP.echoue;
            if (reservation != null) {
                reservation.annuler();
            }
            System.out.println("Remboursement effectué");
        }
    }


}
