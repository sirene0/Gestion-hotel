package com.gestion_hotel.entities;
import java.util.ArrayList;
import java.util.List;

import com.gestion_hotel.enums.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;


@Entity
@DiscriminatorValue("Adminstrateur")
public class Adminstrateur extends Utilisateur{

    @OneToMany(mappedBy="admin",cascade =CascadeType.ALL , orphanRemoval = true )
    private List<Chambre> chambres  = new ArrayList<>();
    
    @OneToMany(mappedBy="admin",cascade =CascadeType.ALL , orphanRemoval = true )
    private List <Reservation> allreservation =new ArrayList<>();
    

    public Adminstrateur() {
        super();
    }

    public Adminstrateur(String nom, String prenom, String email, String motdepasse, Typeutilisateur type) {
        super(nom, prenom, email, motdepasse, type);
    }

    public Adminstrateur( String nom, String prenom, String email, String motdepasse) {
        super( nom, prenom, email, motdepasse,Typeutilisateur.Adminstrateur);
        this.chambres=new ArrayList<>();
        this.allreservation=new ArrayList<>();
    }
    
    public List<Chambre> getChambres() {    return chambres;}
    public void setChambres(List<Chambre> chambres) {    this.chambres = chambres;}
    public List<Reservation> getAllreservation() {    return allreservation;}
    public void setAllreservation(List<Reservation> allreservation) {    this.allreservation = allreservation;}
/* 
    public static Adminstrateur creeAdminstrateur(String nom ,String prenom,String email,String motdepasse){
        Adminstrateur newadmin =new Adminstrateur(nom, prenom, email, motdepasse);
        if(newadmin.sInscrire()){return newadmin;}
        return null;
    }
     
    public boolean seConnecter(String email , String motdepasse){
        if(!isInscrit){
            System.out.println("Erreur : vous devez d'abord vous inscrire !");
            return false;
        }
        return super.seConnecter(email, motdepasse);
    }


    public void ajouterChambre(Chambre chambre){
        if(!chambres.contains(chambre)){
            chambres.add(chambre);
            System.out.println(" chambre ajoutee  avec succes !");
        }else{
            System.out.println("cette chambre existe  Disponible!");
        }
    }
    public void modifierChambre(Chambre chambre){
        for (int i = 0;i<chambres.size();i++ ){
            if(chambres.get(i).getId().equals(chambre.getId())){
                chambres.set(i,chambre);
                System.out.println(" chambre modifier avec succes !");
                return;
            }
        }
        System.out.println("chambre introuvable !");
    }
    public void supprimerChambre(long id){
        Chambre chambresupprimer =null;
        for (Chambre ch :chambres){
            if(ch.getId().equals(id)){
                chambresupprimer=ch;
                break;
            }
        }
        if(chambresupprimer !=null){
            chambres.remove(chambresupprimer);
            System.out.println(" chambre supprimee avec succes !");
        } else {
            System.out.println("chambre introuvable !");
        }
    }
    
    
    
    public List<Reservation> voirToutesReservations(){
        return new ArrayList<>(allreservation);}
    public void validerResevation(Reservation reservation){
        reservation.confirmer();
        if(!allreservation.contains(reservation)){
            allreservation.add(reservation);
        }
        System.out.println(" Réservation validée par l'administrateur");
    }
    public void annulerResevation(Reservation reservation){
        reservation.annuler();
        System.out.println(" Réservation annulée par l'administrateur");
    }
*/
}
