package Gestion;
import java.util.Date;
public class Paiment {
    protected Long id;
    protected double montant;
    protected Date datePaiment;
    protected MethodeP methode;
    protected EtatP statut;
    private Reservation reservation;
    private double totalres;
    public Paiment(Long id, double montant, Date datePaiment, MethodeP methode, EtatP statut) {
        this.id = id;
        this.montant = montant;
        this.datePaiment = datePaiment;
        this.methode = methode;
        this.statut = statut;
        this.reservation=reservation;
        this.totalres=totalres;
    }

    public Long getId() {    return id;}
    public void setId(Long id) {    this.id = id;}
    public double getMontant() {    return montant;}
    public void setMontant(double montant) {     this.montant = montant;}
    public Date getDatePaiment() {    return datePaiment;}
    public void setDatePaiment(Date datePaiment) {    this.datePaiment = datePaiment;}
    public MethodeP getMethode() {     return methode;}
    public void setMethode(MethodeP methode) {    this.methode = methode;}
    public EtatP getStatut() {    return statut;}
    public void setStatut(EtatP statut) {    this.statut = statut;}
    public Reservation getReservation() {    return reservation;}
    public void setReservation(Reservation reservation) {    this.reservation = reservation;}
    public double getTotalres() {return totalres;}
    public void setTotalres(double totalres) {    this.totalres = totalres;}
    //a revoir 
    public boolean validerPaiment(){
        if((this.montant - reservation.calculerPrixTotal())==0){
            this.statut =EtatP.reussi;
            return true;
        }else if((  this.montant - reservation.calculerPrixTotal()) > 0){
            totalres =this.montant - reservation.calculerPrixTotal();
            setMontant(totalres);
            System.out.println("il vous reste a paye :"+getMontant()+"$");
            return false;
        }
        return false;
    }
    //a revoir 
    public void rembourser(){
        if ("réussi".equals(statut)) {
            this.statut = EtatP.echoue;
            reservation.annuler();
            System.out.println("Remboursement effectué");
        }
    }


}
