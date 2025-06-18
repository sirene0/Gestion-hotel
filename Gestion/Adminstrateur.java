package Gestion;
import java.util.ArrayList;
import java.util.List;

public class Adminstrateur extends Utilisateur{

    private List<Chambre> chambres;
    private List <Reservation> allreservation;
    private boolean isInscrit;

    public Adminstrateur( String nom, String prenom, String email, String motdepasse) {
        super( nom, prenom, email, motdepasse,Typeutilisateur.Adminstrateur);
        this.chambres=new ArrayList<>();
        this.allreservation=new ArrayList<>();
    }
    
    public List<Chambre> getChambres() {    return chambres;}
    public void setChambres(List<Chambre> chambres) {    this.chambres = chambres;}
    public List<Reservation> getAllreservation() {    return allreservation;}
    public void setAllreservation(List<Reservation> allreservation) {    this.allreservation = allreservation;}

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


    public void ajouterChambre(Chambre chambre){}
    public void modifierChambre(Chambre chambre){}
    public void supprimerChambre(long id){}
    
    
    
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

}
