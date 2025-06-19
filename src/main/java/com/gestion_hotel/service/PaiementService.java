package com.gestion_hotel.service;

import java.util.List;

import com.gestion_hotel.dao.PaiementDAO;
import com.gestion_hotel.entities.Paiment;

public class PaiementService {
    private PaiementDAO paiementDAO = new PaiementDAO();

    public void ajouterPaiement(Paiment paiement){
        if(paiement != null){paiementDAO.ajouterPaiement(paiement);
            System.out.println("paiement ajouter avec succes !");
        }else{
            System.out.println("erreur : le paiement ne peux pas etre null");
        }
    }
    
    public void supprimerPaiement(Long id){
        paiementDAO.supprimerPaiement(id);
        System.out.println("paiement supprimer avec succes !");
        
    }

    public Paiment trouverPaiement( Long id){
        return paiementDAO.trouverparId(id);
    }

    public List<Paiment> listerTousLesPaiements() {
        return paiementDAO.listerPaiements();
    }
    
    public void modifierPaiement(Paiment paiement ){
        paiementDAO.modifierPaiement(paiement);
        System.out.println("paiement modifier avec succes !");
    } 
    
    public void effectuerPaiement (Paiment paiement ){
        if(paiement == null || paiement.getReservation() == null)
        {System.out.println(" Paiement ou réservation invalide.");}
        
        Paiment paiementExistant = paiementDAO.trouverParReservation(paiement.getReservation());
        if (paiementExistant != null) {
            System.out.println(" Cette réservation a déjà été payée !");
            return;
        }

        boolean paiementEffectue =paiement.validerPaiment();
        if(paiementEffectue){
            paiementDAO.ajouterPaiement(paiement);
        }else{
            System.out.println("Paiement non validé. Vérifiez les montants.");
        }
    }
    
    public void rembourserPaiement(Long PaiementID){
        Paiment paiement = paiementDAO.trouverparId(PaiementID);
        if(paiement !=null ){
            paiement.rembourser();
            paiementDAO.modifierPaiement(paiement);
        } else {
            System.out.println("Paiement introuvable pour remboursement.");
        }
    }

}
