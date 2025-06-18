package Gestion;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
public class Chambre {
    protected Long id ;
    protected int numero;
    protected Type type; //souble,simple,suit
    protected double prix;
    protected boolean disponible;
    private static List <Reservation> reservations;
    protected Chambre(Long id, int numero, Type type, double prix, boolean disponible) {
        this.id = id;
        this.numero = numero;
        this.type = type;
        this.prix = prix;
        this.disponible = true;
    }
    public Chambre(){
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
