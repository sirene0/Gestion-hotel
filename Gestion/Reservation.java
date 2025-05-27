package Gestion;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Reservation {
    public Long id ;
    public Date dateDebut;
    public Date dateFin;
    public Statut statut;
    public Client c;
    public Chambre ch;
    
    public Reservation(Long id, Date dateDebut, Date dateFin, Statut statut, Client c, Chambre ch) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.c = c;
        this.ch = ch;
    }

    public long getId() {return id;}
    public void setId(long id) {    this.id = id; }
    public Date getDateDebut() {    return dateDebut;}
    public void setDateDebut(Date dateDebut) {    this.dateDebut = dateDebut;}
    public Date getDateFin() {     return dateFin;}
    public void setDateFin(Date dateFin) {    this.dateFin = dateFin;}
    public Statut getStatut() {    return statut;}
    public void setStatut(Statut statut) {    this.statut = statut;}
    public Client getC() {    return c;}
    public void setC(Client c) {     this.c = c;}
    public Chambre getCh() {    return ch;}
    public void setCh(Chambre ch) {    this.ch = ch;}

    public void confirmer(){
        this.statut=Statut.confirmée;
        ch.rendreindisponibe();
        System.out.println("Réservation confirmée !");
    }
    public void annuler(){
        this.statut=Statut.annulée;
        ch.rendreDisponible();
        System.out.println("Réservation annulée !");
    }
    public double calculerPrixTotal(){
        Long diff = dateFin.getTime() - dateDebut.getTime();
        Long nbrjour=diff /(1000*60*60*24);
        return nbrjour*ch.prix;
        }
}
