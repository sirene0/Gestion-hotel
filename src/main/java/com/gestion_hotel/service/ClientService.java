package com.gestion_hotel.service;


import java.util.Date;
import java.util.List;

import com.gestion_hotel.dao.PaiementDAO;
import com.gestion_hotel.dao.ClientDAO;
import com.gestion_hotel.dao.ReservationDAO;
import com.gestion_hotel.entities.*;


public class ClientService {
    private ClientDAO clientDAO= new ClientDAO();
    private ReservationDAO reservationDAO =new ReservationDAO();
    private ReservationService reservationService =new ReservationService();
    private ChambreService chambreService = new ChambreService();
    private PaiementDAO paimentDAO = new PaiementDAO();

    public boolean enregistrerClient(Client client){
        if(clientDAO.trouverparEmail(client.getEmail())!=null){
            System.out.println("erreur : email deja utilise !");
            return false ;
        }
        if(client.getNom()== null || client.getNom().trim().isEmpty()){
            System.out.println("Erreur : le nom est obligatoire !");
            return false;
        }
        if(client.getPrenom()== null || client.getPrenom().trim().isEmpty()){
            System.out.println("Erreur : le prenom est obligatoire !");
            return false;
        }
        if(client.getEmail()== null || !client.getEmail().contains("@")){
            System.out.println("Erreur : l'email est invalide !");
            return false;
        }
        if(client.getMotdepasse()== null || client.getMotdepasse().length()<6){
            System.out.println("Erreur : le mot de passe doit contenir au moins 6 caractères !");
            return false;
        }

        

        clientDAO.enregistrerClient(client);
        System.out.println ("Client :" + client.getNom() +","+client.getPrenom()+ " inscrit avec succès !");
        return true;
    }

    public boolean seConnecter(String email,String motdepasse){
        Client client= clientDAO.trouverparEmail(email);
        if(client == null ){
            System.out.println("Aucun client avec cet email.");
            return false;
        }
        if(!client.isInscrit()){
            System.out.println("Erreur : vous devez d'abord vous inscrire !");
            return false;
        }
        if (!client.getMotdepasse().equals(motdepasse)) {
        System.out.println("Mot de passe incorrect.");
        return false;
        }
        System.out.println("Connexion réussie. Bienvenue " +client.getNom() + " , "+client.getPrenom() + "!");
        return true;
    }

    public void validerResevation(Client client ,Reservation reserv ){
        if(reserv !=null){
            reserv.confirmer();
            if(!client.getReservations().contains(reserv)){
                client.getReservations().add(reserv);
            }
            reservationDAO.modifierReserv(reserv);
            System.out.println("Réservation validée par client .");
        }else {
            System.out.println("Réservation introuvable.");
        }
    }

    
    public Reservation reserver(Client client,Chambre chambre ,Date datedeb ,Date datefin){
        return reservationService.reserver(client, chambre, datedeb, datefin);
    }
    
    public void annulerResevation(Client client ,Reservation reserv ){
        if(reserv !=null){
            reserv.annuler();
            if(!client.getReservations().contains(reserv)){
                client.getReservations().remove(reserv);
            }
            reservationDAO.modifierReserv(reserv);
            System.out.println("Réservation annulée par client.");
        }else {
            System.out.println("Réservation introuvable.");
        }
    }

    public void listChambresDispo(){
        List<Chambre> chambresdispo = chambreService.listerChambres();
        for (Chambre ch : chambresdispo){
            System.out.println("Chambre n°\n" + ch.getNumero());
        }
    }
    
    public void effectuerPaiement(Paiment paiement) {
        paimentDAO.ajouterPaiement(paiement);
    }
    
    public Client getClientConnecte(String email) {
        return clientDAO.trouverparEmail(email);
    }
    
    public List<Reservation> consulterReservations(Client client){
        List<Reservation> reservations= null;
        reservations= reservationService.consulterReservation(client);
        if(reservations.isEmpty()){
            System.out.println("Vous n’avez aucune réservation.");
        }else{
            System.out.println("Vos réservations :");
        for (Reservation r : reservations) {
            System.out.println("----------------------------------------");
            System.out.println(" Réservation ID     : " + r.getId());
            System.out.println(" Chambre numéro     : " + r.getCh().getNumero());
            System.out.println(" Date début         : " + r.getDateDebut());
            System.out.println(" Date fin           : " + r.getDateFin());
            System.out.println(" Statut             : " + r.getStatut());
        }
        }
        return reservations;

    }
}

        