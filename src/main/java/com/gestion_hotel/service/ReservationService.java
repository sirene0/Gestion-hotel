package com.gestion_hotel.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Random;

import com.gestion_hotel.dao.ReservationDAO;
import com.gestion_hotel.entities.Adminstrateur;
import com.gestion_hotel.entities.Chambre;
import com.gestion_hotel.entities.Client;
import com.gestion_hotel.entities.Reservation;
import com.gestion_hotel.enums.Statut;

public class ReservationService {
    private ReservationDAO reserDAO = new ReservationDAO();

    public Reservation reserver(Client client ,Chambre chambre ,Date datedeb,Date datefin){
        if(! client.isInscrit()){
            System.out.println("Erreur :vous devez d'abord inscrit pour reserver !");
            return null;
        }
        Date ajd =new Date();
        if(datedeb.before(ajd)){
            System.out.println("Erreur vous devez mettre une date valide !");
            return null;
        }
        if (datefin.before(datedeb)||datefin.equals(datedeb)){
            System.out.println("Erreur: La date de fin doit être après la date de début!");
            return null;
        }
        List<Reservation> toutesReservations =reserDAO.listeReserv();
        if(!verifierDisponibiliteChambre(chambre,datedeb,datefin,toutesReservations)){
            System.out.println("la chambre numero "+chambre.getNumero()+"n'est pas disponible entre "+datedeb+"et"+datefin);
            return null;
        }
        //long idReservation = System.currentTimeMillis() + new Random().nextInt(1000);
        Reservation reservation = new Reservation(datedeb, datefin, Statut.confirmee, client, chambre);
        
        reserDAO.AjouterReservation(reservation);
        System.out.println("Réservation créée avec succès !");
        return reservation;
    }

    public Reservation reserveradmin(Client client ,Chambre chambre ,Date datedeb,Date datefin,Adminstrateur admin){
        if(! client.isInscrit()){
            System.out.println("Erreur :vous devez d'abord inscrit pour reserver !");
            return null;
        }
        Date ajd =new Date();
        if(datedeb.before(ajd)){
            System.out.println("Erreur vous devez mettre une date valide !");
            return null;
        }
        if (datefin.before(datedeb)||datefin.equals(datedeb)){
            System.out.println("Erreur: La date de fin doit être après la date de début!");
            return null;
        }
        List<Reservation> toutesReservations =reserDAO.listeReserv();
        if(!verifierDisponibiliteChambre(chambre,datedeb,datefin,toutesReservations)){
            System.out.println("la chambre numero "+chambre.getNumero()+"n'est pas disponible entre "+datedeb+"et"+datefin);
            return null;
        }
        //long idReservation = System.currentTimeMillis() + new Random().nextInt(1000);
        Reservation reservation = new Reservation(datedeb, datefin, Statut.confirmee, client, chambre);
        
        reserDAO.AjouterReservation(reservation);
        System.out.println("Réservation créée avec succès !");
        return reservation;
    }

    public boolean verifierDisponibiliteChambre(Chambre chambre, Date datedeb , Date datefin,List<Reservation> reservations){
        if (chambre == null || datedeb == null || datefin == null || datedeb.after(datefin) || !chambre.isDisponible()) {
            return false;
        }
        if (reservations ==null || reservations.isEmpty()){
            return true;
        }
        for (Reservation reserv : reservations){
            if (reserv != null 
            && reserv.getStatut() !=Statut.annulee 
            && reserv.getCh() != null 
            && reserv.getCh().getId() !=null 
            && reserv.getCh().getId().equals(chambre.getId()) 
            && datesSeChevauchtent(datedeb, datefin, reserv.getDateDebut(), reserv.getDateFin())){
                return false;
            }
        }
        return true;
    }
    
    public boolean datesSeChevauchtent(Date datedeb1,Date datefin1 ,Date datedeb2,Date datefin2){
        return !datefin1.before(datedeb2)&&!datedeb1.after(datefin2);
    }
    
    public void annulerReserv(Long idreserv ){
        Reservation reserv =reserDAO.trouverRerservparId(idreserv);
        if( reserv !=null){
            reserv.annuler();
            reserDAO.modifierReserv(reserv);
            System.out.println("reservation annulee !");
        }else{
            System.out.println("reservation introuvable !");
        }
    }
    
    public double afficherPrixTotalReservation(Long idReserv) {
    Reservation reserv = reserDAO.trouverRerservparId(idReserv);
    if (reserv != null) {
        double total = reserv.calculerPrixTotal();
        System.out.println(" Prix total de la réservation : " + total + " DA");
        return total;
    } else {
        System.out.println("Réservation introuvable !");
        return 0;
    }
}

    public List<Reservation> consulterReservation(Client client ){
        List<Reservation> reservations= new ArrayList<>();
        for(Reservation reserv : reserDAO.listeReserv()){
            if(reserv.getC() !=null && (reserv.getC().getId()==client.getId())){
                reservations.add(reserv);
            }
        }
        if (reservations.isEmpty()){
            System.out.println(" Vous n’avez aucune réservation.");
        }else{
            for (Reservation r : reservations) {
                System.out.println(" Réservation ID: " + r.getId()
                        + " | Chambre: " + r.getCh().getNumero()
                        + " | Du: " + r.getDateDebut()
                        + " au: " + r.getDateFin()
                        + " | Statut: " + r.getStatut());
            }
        }
        return reservations;
    }

    public List<Reservation> touteslesReservation (){
        return reserDAO.listeReserv();
    }

    public Reservation chercherParId(long id) {
        return reserDAO.trouverRerservparId(id);
    }

    public void validerReservation(Reservation reservation) {
        if(reservation != null){
        reservation.setStatut(Statut.confirmee);
        reserDAO.modifierReserv(reservation);
        System.out.println("reservation annulee !");
        }else{
            System.out.println("reservation introuvable !");
        }
    }
}


