package com.gestion_hotel.service;

import java.util.Date;
import java.util.List;

import com.gestion_hotel.dao.ChambreDAO;
import com.gestion_hotel.entities.Chambre;

public class ChambreService {
    private ChambreDAO chambreDAO= new ChambreDAO();
    public void ajouterChambre(Chambre chambre) {
        if (chambre.getAdmin() == null) {
            throw new IllegalArgumentException("Une chambre doit avoir un administrateur associé");
        }
        chambreDAO.ajouterCh(chambre);
    }

    public Chambre chercherChambre(int numero) {
        return chambreDAO.trouverChambreparnum(numero);
    }

    public List<Chambre> listerChambres() {
        return chambreDAO.listerToutes();
    }

    public void modifierChambre(Chambre chambre) {
        chambreDAO.modifierChambre(chambre);
    }

    public void supprimerChambre(int numero) {
        chambreDAO.supprimerChambre(numero);
    }

    public List<Chambre> chambresDisponibles() {
        return chambreDAO.listeChambresDispo();
    }

    public boolean verifierDisponibilite (Chambre chambre, Date dateDebut, Date dateFin){
        return chambre.estDisponible(dateDebut,dateFin );
    }
    public void rendreChambreIndisponible(Chambre chambre) {
        chambre.setDisponible(false);
        chambreDAO.modifierChambre(chambre);
    }
    public void rendreChambreDisponible(Chambre chambre) {
        chambre.setDisponible(true);
        chambreDAO.modifierChambre(chambre);
    }
    public void modifierPrixChambre(Chambre chambre, double nouveauPrix) {
        chambre.setPrix(nouveauPrix);
        chambreDAO.modifierChambre(chambre);
    }
}
