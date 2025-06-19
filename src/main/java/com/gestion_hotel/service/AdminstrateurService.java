package com.gestion_hotel.service;

import java.util.Date;
import java.util.List;

import com.gestion_hotel.dao.AdminstrateurDAO;
import com.gestion_hotel.dao.ChambreDAO;
import com.gestion_hotel.dao.ReservationDAO;
import com.gestion_hotel.entities.Adminstrateur;
import com.gestion_hotel.entities.Chambre;
import com.gestion_hotel.entities.Client;
import com.gestion_hotel.entities.Reservation;

public class AdminstrateurService {
    private AdminstrateurDAO adminDAO = new AdminstrateurDAO();
    private ChambreDAO chambreDAO= new ChambreDAO();
    private ReservationDAO reservationDAO =new ReservationDAO();
    private ReservationService reservationService =new ReservationService();
    public boolean enregistrerAdmin(Adminstrateur admin ){

        if (adminDAO.trouverparEmail(admin.getEmail())!= null){
            System.out.println("Erreur : cet email est déjà utilisé !");
            return false;
        }
        if(admin.getNom() ==null || admin.getNom().trim().isEmpty()){
            System.out.println("Erreur : le nom est obligatoire !");
            return false;
        }
        if(admin.getPrenom() ==null || admin.getPrenom().trim().isEmpty()){
            System.out.println("Erreur : le prenom est obligatoire !");
            return false;
        }
        if(admin.getEmail() ==null || !admin.getEmail().contains("@")){
            System.out.println("Erreur : l'email est invalide !");
            return false;
        }
        if( admin.getMotdepasse() ==null || admin.getMotdepasse().length()<6){
            System.out.println("Erreur : le mot de passe doit contenir au moins 6 caractères !");
            return false;
        }
        adminDAO.enregistrerAdmin(admin);
        System.out.println("Administrateur : "+admin.getNom()+" , "+admin.getPrenom()+" ajouté avec succès.");
        return true;
    }

    public Adminstrateur trouvAdminParEmail(String email){
        Adminstrateur admint = adminDAO.trouverparEmail(email);
        if(admint != null && admint.isInscrit()){
            System.out.println(("adminstrateur trouve avec succes :"+admint.getNom()+" "+admint.getPrenom()));
        }else{
            System.out.println("aucun adminstrateur touve avec cette email :"+email);
        }
        
        return admint;
        
    }
    
    public Adminstrateur trouvAdminParId(Long id ){
        Adminstrateur admint = adminDAO.trouverparId(id);
        if(admint != null && admint.isInscrit()){
            System.out.println(("adminstrateur trouve avec succes :"+admint.getNom()+" "+admint.getPrenom()));
        }else{
            System.out.println("aucun adminstrateur touve avec cette id :"+id);
        }
        
        return admint;
        
    }
    
    public List<Adminstrateur> listeAllAdminstrateurs(){
        return adminDAO.listeAdminstrateurs();
    }
    
    public void supprimerAdmin(Long id ){
        if (adminDAO.trouverparId(id) != null){
        adminDAO.supprimerAdmin(id);
        System.out.println("Administrateur supprimé.");
        }else {
            System.out.println("Aucun admin trouvé avec cet ID !");
        }
    }
    
    public void modifierAdmin(Adminstrateur admin ){
        if (adminDAO.trouverparEmail(admin.getEmail())!= null){
            System.out.println("Erreur : cet email est déjà utilisé !");
            return;
        }
        if(admin.getNom() ==null || admin.getNom().trim().isEmpty()){
            System.out.println("Erreur : le nom est obligatoire !");
            return;
        }
        if(admin.getPrenom() ==null || admin.getPrenom().trim().isEmpty()){
            System.out.println("Erreur : le prenom est obligatoire !");
            return;
        }
        if(admin.getEmail() ==null || !admin.getEmail().contains("@")){
            System.out.println("Erreur : l'email est invalide !");
            return;
        }
        if( admin.getMotdepasse() ==null || admin.getMotdepasse().length()<6){
            System.out.println("Erreur : le mot de passe doit contenir au moins 6 caractères !");
            return;
        }
        adminDAO.modifierAdmin(admin);
        System.out.println("Administrateur modifié.");
    }

    public void ajouterChambre(Adminstrateur admin , Chambre chambre){
        if(!admin.getChambres().contains(chambre)){
            chambre.setAdmin(admin);
            chambreDAO.ajouterCh(chambre);
            admin.getChambres().add(chambre);
            System.out.println("Chambre ajoutée avec succès !");
        }else {
            System.out.println("Cette chambre existe déjà !");
        }
    }
    
    public void supprimerChambre(Adminstrateur admin , int numero ){
        Chambre aSupprimer = null;
        for (Chambre ch : admin.getChambres()){
            if( ch.getNumero() == numero){
                aSupprimer =ch;
                break;
            }
        }
        if(aSupprimer !=null){
            chambreDAO.supprimerChambre(numero);
            admin.getChambres().remove(aSupprimer);
            System.out.println("Chambre supprimée avec succès !");
        }else {
            System.out.println("Chambre introuvable !");
        }
    }

    public void modifierChambre(Adminstrateur admin ,Chambre chambre ){
        boolean found = false ;
        for (Chambre ch :admin.getChambres()){
            if(ch.getId().equals(chambre.getId())){
                chambre.setAdmin(admin);
                chambreDAO.modifierChambre(chambre);
                found =true;
                System.out.println("Chambre modifiée avec succès !");
                break;
            }
        }
        if(!found){
            System.out.println("Chambre introuvable !");
        }
    }

    public void  listeChambres(Adminstrateur admin ){
        for (Chambre ch : admin.getChambres()) {
            System.out.println(ch);
        }
        
    }

    public List<Chambre>listChambresDispo(){
        List<Chambre> chambredispo =chambreDAO.listeChambresDispo();
        if(chambredispo.isEmpty()){
            System.out.println ("aucune chambre disponible !");
        }else{
            System.out.println (" chambre(s) disponible(s) :");
        }
        return chambredispo;
    }
    
    public List<Reservation> voirToutesReservations(Adminstrateur admin) {
        return reservationDAO.listeReserv();
    }
    
    public void validerResevation(Adminstrateur admin ,Reservation reserv ){
        if(reserv !=null){
            reserv.confirmer();
            if(!admin.getAllreservation().contains(reserv)){
                admin.getAllreservation().add(reserv);
            }
            reservationDAO.modifierReserv(reserv);
            System.out.println("Réservation validée par l'administrateur.");
        }else {
            System.out.println("Réservation introuvable.");
        }
    }
    
    public Adminstrateur getAdminConnecte(String email) {
        return adminDAO.trouverparEmail(email);
    }
    
    public Reservation reserver(Client client ,Chambre chambre ,Date datedeb,Date datefin,Adminstrateur admin){
        return reservationService.reserveradmin(client, chambre, datedeb, datefin, admin);
    }
    
    public void annulerResevation(Adminstrateur admin ,Reservation reserv ){
        if(reserv !=null){
            reserv.annuler();
            if(!admin.getAllreservation().contains(reserv)){
                admin.getAllreservation().remove(reserv);
            }
            reservationDAO.modifierReserv(reserv);
            System.out.println("Réservation annulée par l'administrateur.");
        }else {
            System.out.println("Réservation introuvable.");
        }
    }
    
    public boolean seConnecter(String email , String motdepasse){
        Adminstrateur admin = adminDAO.trouverparEmail(email);
        if(admin == null ){
            System.out.println("Aucun administrateur avec cet email.");
            return false;
        }
        if(!admin.isInscrit()){
            System.out.println("Erreur : vous devez d'abord vous inscrire !");
            return false;
        }

        if (!admin.getMotdepasse().equals(motdepasse)) {
        System.out.println("Mot de passe incorrect.");
        return false;
        }

        System.out.println("Connexion réussie. Bienvenue " + admin.getNom() +" , "+admin.getPrenom()+ " !");
        return true;
    }

}