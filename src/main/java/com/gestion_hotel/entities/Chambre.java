package com.gestion_hotel.entities;
import java.util.Date;
import java.util.List;



import java.util.ArrayList;
import com.gestion_hotel.enums.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name ="Chambres")
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private int numero;

    @Enumerated(EnumType.STRING)
    private Type type; //souble,simple,suit
    
    private double prix;
    private boolean disponible;
    
    @OneToMany(mappedBy = "ch",cascade = CascadeType.ALL,orphanRemoval = true,fetch=FetchType.LAZY)
    private  List <Reservation> reservations;

    @ManyToMany
    @JoinColumn(name ="admin_id", nullable = false )
    private Adminstrateur admin;
    
    public Chambre() {
    }

    public Chambre( int numero, Type type, double prix, boolean disponible) {
        this.numero = numero;
        this.type = type;
        this.prix = prix;
        this.disponible = disponible;
        this.reservations=new ArrayList<>();
    }
    

    
    public Long getId() {return id;}
    public int getNumero() {return numero;}
    public void setNumero(int numero) {this.numero = numero;}
    public Type getType() {return type;}
    public void setType(Type type) {this.type = type;}
    public double getPrix() {return prix;}
    public void setPrix(double prix) {this.prix = prix;}
    public boolean isDisponible() {return disponible;}
    public void setDisponible(boolean disponible) {this.disponible = disponible;}
    public void setReservations(List<Reservation> reservations) {this.reservations = reservations;}
    public Adminstrateur getAdmin() {return admin;}
    public void setAdmin(Adminstrateur admin) {this.admin = admin;}
    public List<Reservation> getReservations(){return reservations;}
    


    public boolean estDisponible(Date dateDebut, Date dateFin ){
        if(!this.disponible){
            return false;
        }
        
        for(Reservation r :reservations){
            if(!r.getStatut().equals("annulée")){
                if(datesSeChevauchtent(r.getDateDebut(),r.getDateFin(),dateDebut,dateFin)){return false ;}
            }
        
        }
        return true;
    }

    public boolean datesSeChevauchtent(Date datedeb1,Date datefin1 ,Date datedeb2,Date datefin2){
        return !datefin1.before(datedeb2)&&!datedeb1.after(datefin2);
    }

    public void ajouterReservation( Reservation reserv){
        if(!reservations.contains(reserv)){
            reservations.add(reserv);
        }
    }
    public void modifierPrix(double newprix,Type type){
        this.prix=newprix;
    }
    public void rendreDisponible(){
        this.disponible=true;
    }
    public void rendreindisponibe(){
        this.disponible=false;
    }
}
