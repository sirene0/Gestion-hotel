package com.gestion_hotel.entities;

import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("Client")
public  class Client extends Utilisateur{
    @OneToMany(mappedBy = "c",cascade = CascadeType.ALL ,orphanRemoval = true ,fetch = FetchType.LAZY)
    private List<Reservation> reservations =new ArrayList<>();
    
    public Client(){
        super();
    }
    public Client( String nom, String prenom, String email, String motdepasse) {
        super( nom, prenom, email, motdepasse);
        this.reservations = new ArrayList<>();
    }
    
    /* *
    public static Client creernewClient(String nom, String prenom ,String email, String motDePasse){
        Client newClient=new Client(nom,prenom,email,motDePasse);
        if(newClient.sInscrire()){return newClient;}
        return null;}


    public boolean seConnecter(String email ,String motdepasse){
        if(!isInscrit){
            System.out.println("erreur: vous devez d'abord vous inscrire !");
            return false;
        }
        return super.seConnecter(email, motdepasse);
    }
    public Reservation reserver(Chambre chambre, Date dateDebut,Date dateFin, List<Reservation> toutesreservations){
        if(!isInscrit){
            System.out.println("Erreur :vous devez d'abord inscrit pour reserver !");
            return null;
        }
        //validation de la date 
        Date aujourdHui = new Date();
        if (dateDebut.before(aujourdHui)){
            System.out.println("Erreur vous devez mettre une date valide !");
            return null;
        }else{System.out.println("la date est valide !");}

        if (dateFin.before(dateDebut)||dateFin.equals(dateDebut)){
            System.out.println("Erreur: La date de fin doit être après la date de début!");
            return null;}
        //verfier disponibilite de la chambre 
        if(!verifierDisponibiliteChambre(chambre, dateDebut, dateFin, toutesreservations)){
            System.out.println("la chambre numero "+chambre.getNumero()+"n'est pas disponible du "+dateDebut+"au"+dateFin);
            return null;
        }
        //creer reservation 
        long reservationid = System.currentTimeMillis()+ new Random().nextInt(1000);
        Reservation reserv =new Reservation(reservationid, dateDebut, dateFin, Statut.confirmée,this, chambre);
        reservations.add(reserv);
        System.out.println(" Réservation créée avec succès!");
        System.out.println("Chambre :"+chambre.getNumero()+"("+chambre.getType()+")");
        System.out.println("Du: " + dateDebut + " au: " + dateFin);
        System.out.println("Prix total :"+reserv.calculerPrixTotal()+"$");
        return reserv;
    }

    public void annulerResevation(Reservation res){
        if(reservations.contains(res)){
            res.annuler();
            System.out.println("reservation annulee !");
        }else{ 
            System.out.println("reservation introuvable!");
        }
    }

    public boolean verifierDisponibiliteChambre(Chambre chambre, Date dateDebut, Date dateFin, List<Reservation> toutesReservations){
        if(chambre ==null || dateDebut ==null || dateFin== null || dateDebut.after(dateFin) || !chambre.isDisponible()){
            return false;
        }
        if (toutesReservations == null || toutesReservations.isEmpty()) {
        return true;
        }
        for(Reservation reserv : toutesReservations){
            if( reserv != null 
            && reserv.getStatut() !=Statut.annulée 
            && reserv.getCh() != null 
            && reserv.getCh().getId() !=null 
            && reserv.getCh().getId().equals(chambre.getId()) 
            && datesSeChevauchtent(dateDebut, dateFin, reserv.getDateDebut(), reserv.getDateFin())){
                return false;
            }
        }
        return true;
    }
    
    public boolean datesSeChevauchtent(Date datedeb1,Date datefin1 ,Date datedeb2,Date datefin2){
        return !datefin1.before(datedeb2)&&!datedeb1.after(datefin2);
    }
    
    public List<Reservation> consulterReservation(){
        if (!isInscrit) {
            System.out.println("Vous devez être inscrit pour consulter vos réservations!");
            return new ArrayList<>();
        }else if (reservations.isEmpty()) {
            System.out.println("Votre n'avez aucune reservation pour le moment .");
        } else {
            System.out.println("Vos reservation :");
            for(Reservation r :reservations){
                System.out.println(" Réservation id  :" + r.getId() +"\n");
                System.out.println("Chambre numero : "+r.getCh().getNumero()+"\n");
                System.out.println("Du : "+r.getDateDebut()+"  au : "+r.getDateFin() );
            }
        }
        return new ArrayList<>(reservations);
    }

*/
    public List<Reservation> getReservations() {return reservations;}
    public void setReservations(List<Reservation> reservations) {this.reservations = reservations;}
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reservations == null) ? 0 : reservations.hashCode());
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
        Client other = (Client) obj;
        if (reservations == null) {
            if (other.reservations != null)
                return false;
        } else if (!reservations.equals(other.reservations))
            return false;
        return true;
    }

    
}