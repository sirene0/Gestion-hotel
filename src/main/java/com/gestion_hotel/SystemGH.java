package com.gestion_hotel;
import java.util.Scanner;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


import com.gestion_hotel.dao.ClientDAO;
import com.gestion_hotel.dao.AdminstrateurDAO;
import com.gestion_hotel.dao.ChambreDAO;
import com.gestion_hotel.dao.ReservationDAO;
import com.gestion_hotel.entities.*; 
import com.gestion_hotel.enums.*;
import com.gestion_hotel.service.AdminstrateurService;
import com.gestion_hotel.service.ChambreService;
import com.gestion_hotel.service.ClientService;
import com.gestion_hotel.service.ReservationService;
public class SystemGH {

    private static Scanner scanner =new Scanner(System.in);
    private static Adminstrateur ad =null;
    
    private static ClientService clientService = new ClientService();
    private static ChambreService chambreService = new ChambreService();
    private static ReservationService reservationService = new ReservationService();
    private static AdminstrateurService adminService = new AdminstrateurService();
    
    private static ClientDAO clientDAO = new ClientDAO();
    private static ChambreDAO chambreDAO = new ChambreDAO();
    private static ReservationDAO reservationDAO = new ReservationDAO();
    private static AdminstrateurDAO adminstrateurDAO = new AdminstrateurDAO();
    
    
    
    private static boolean adminConn;
    private static boolean clientConn;
    private static Client clientConnecte = null;
    private static boolean adminConnecte = false;

    public static void main(String[] args) throws ParseException {
        System.out.println(" DEMARRAGE SYSTEME DE GESTION HÔTELIÈRE");
        intialiserSysteme();
        int choix;
        do{
            affichermenuPrincipale();
            choix = lireChoix();

            switch (choix) {
                case 1:
                    menuInscription();
                    break;
                case(2):
                    menuConnexion();
                    break;
                case(3):
                    if(clientConnecte!= null){
                        menuclient();
                    }else{ System.out.println("Vous devez d'abord vous connecter comme client !");}
                    break;
                case(4):
                    if (adminConnecte) {
                        menuadmin();
                    }else{System.out.println("Vous devez d'abord vous connecter comme administrateur !");
                    }
                    break;
                case(5):
                    deconnexion();
                    break;
                case 0:
                    System.out.println(" Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide !");
                    break;
            }
            if (choix != 0) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        }while (choix !=0);
        scanner.close();
    }
    
    public static void intialiserSysteme(){
        ad=new Adminstrateur("Admin", "System", "admin@gmail.com","admin123");
        adminService.enregistrerAdmin(ad);

        try {
            chambreService.ajouterChambre(new Chambre( 100, Type.simple, 80.0, true));
            chambreService.ajouterChambre(new Chambre( 101, Type.simple, 80.0, true));
            chambreService.ajouterChambre(new Chambre( 200, Type.Double, 120.0, true));
            chambreService.ajouterChambre(new Chambre( 201, Type.Double, 120.0, true));
            chambreService.ajouterChambre(new Chambre( 300, Type.suite, 200.0, true));
            chambreService.ajouterChambre(new Chambre( 301, Type.suite, 200.0, true));

            if(ad !=null){
                List<Chambre> chambresCree =chambreService.listerChambres();
                ad.setChambres(chambresCree);
            }
            System.out.println("Systeme intialise avec succes !");
        } catch (Exception e) {
            System.out.println("Error lors la creation d'un admin ou bien des chambres ");
        }
    }
    
    
    public static void affichermenuPrincipale(){
        System.out.println("\n" +"\n" );
        System.out.println("1. S'inscrire \n" );
        System.out.println("2. Se connecter  \n" );
        System.out.println("3. Menu Client" + (clientConnecte != null ? " (" + clientConnecte.getNom() + ")" : "\n"));
        System.out.println("4. Menu Administrateur "+(adminConnecte ? "(connecte)" : "\n"));
        System.out.println("5. Se deconncter  \n" );
        System.out.println("0. Quiter  \n" );
        System.out.println("\n" +"\n" );
        System.out.println(" Votre Choix : \n" );
    }

    public static int lireChoix(){
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(" Veuillez entrer un nombre valide !");
            return -1;
        }
    }
    
    public static void menuInscription(){
        System.out.println(" INSCRIPTION \n" );
        System.out.println(" \n"+"\n" );

        System.out.println(" 1. Client  \n" );
        System.out.println(" 2. Adminstrateur  \n" );
        System.out.println(" \n"+"\n" );
        System.out.print("Type d'utilisateur : ");
        

        int type =lireChoix();
        switch (type){
            case 1:
                inscrireClient();
                break;
            case 2:
                inscrireAdmin();
            default:
                System.out.println(" Type invalide !");
        }
    }
    
    public static void inscrireClient(){
        System.out.println("Nom : ");
        String nom = scanner.nextLine();
        System.out.println("Prenom : ");
        String prenom = scanner.nextLine();
        System.out.println("Email : ");
        String email= scanner.nextLine();
        System.out.println("Mot de passe : ");
        String motdepasse = scanner.nextLine();

        Client newcl= new Client(nom, prenom, email, motdepasse);

        if (newcl !=null){
            clientService.enregistrerClient(newcl);
            System.out.println("Client inscrit avec succes !");
        }
    }
    
    public static void inscrireAdmin(){
        System.out.println("Nom : ");
        String nom = scanner.nextLine();
        System.out.println("Prenom : ");
        String prenom = scanner.nextLine();
        System.out.println("Email : ");
        String email= scanner.nextLine();
        System.out.println("Mot de passe : ");
        String motdepasse = scanner.nextLine();

        Adminstrateur newad=new Adminstrateur(nom, prenom, email, motdepasse);
        if(newad !=null){
            adminService.enregistrerAdmin(newad);
            System.out.println("Adminstrateur inscrit avec succes !");
        }
    }
    
    public static void menuConnexion(){
        System.out.println(" CONNEXION \n" );
        System.out.println(" \n"+"\n" );
        System.out.print("Type d'utilisateur : ");
        System.out.println(" 1. Client  \n" );
        System.out.println(" 2. Adminstrateur  \n" );
        System.out.println(" \n"+"\n" );

        int type =lireChoix();
        switch (type) {
            case 1:
                connexionClient();
                break;
            case 2:
                connexionadmin();
                break;

            default:
            System.out.println(" Type invalide !");
                break;
        }
    }
    
    public static void connexionClient(){
        System.out.println("---connexion Client ---");
        System.out.println("Email : ");
        String email= scanner.nextLine();
        System.out.println("Mot de passe : ");
        String motdepasse = scanner.nextLine();

        clientConn = clientService.seConnecter(email, motdepasse);
        if(clientConn){
            clientConnecte=clientService.getClientConnecte(email);
            adminConnecte=false;
            System.out.println("Connexion client reussie !");
        }else{
            System.out.println("Echec de connexion client ! ");
        System.out.println("  verifier l'email ou bien mot de passe   ");
        }
        System.out.println("Echec de connexion client ! ");
        System.out.println("  verifier l'email ou bien mot de passe   ");
    }

    public static void connexionadmin(){
        System.out.println("---connexion Adminstrateur ---");
        System.out.println("Email : ");
        String email= scanner.nextLine();
        System.out.println("Mot de passe : ");
        String motdepasse = scanner.nextLine();
        
        adminConn = adminService.seConnecter(email, motdepasse);
        if(adminConn){
            adminConnecte=true;
            clientConnecte=null;
            ad = adminService.getAdminConnecte(email);
            System.out.println("Connexion Adminstrateur reussie !");
        }else{System.out.println("Echec de connexion Adminstrateur !  ");}
        
    }
    
    public static void menuclient() throws ParseException{
        int choix;
        do{
            System.out.println("---menu client---");
            System.out.println("1. voir les chambrre disponible ");
            System.out.println("2. faire une reservation ");
            System.out.println("3. mes reservation  ");
            System.out.println("4. effectuer un paiment  ");
            System.out.println("0. retour menu principale  ");

            choix=lireChoix();
            switch (choix) {
                case 1 :
                    voirchambredispo(null, null);
                    break;
                case 2 :
                    fairereserv();
                    break;
                case 3 :
                    voirmesreservation();
                    break;
                case 4 :
                    effPaiment();
                    break;
                case 0 :
                    
                    break;
                default:
                    System.out.println(" Choix invalide !");
                    break;
            }
            if (choix != 0) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        }while(choix !=0);
    }
    
    public static void menuadmin(){
        int choix;
        do{
            System.out.println("---menu Adminstrateur---");
            System.out.println("1. gerer les chambrre  ");
            System.out.println("2. voir toutes les  reservation ");
            System.out.println("3. valider une reservation  ");
            System.out.println("4. annuler une reservation   ");
            System.out.println("5.  Statistiques");
            System.out.println("0. retour menu principale  ");

            choix=lireChoix();
            switch (choix) {
                case 1 :
                    gerechambre();
                    break;
                case 2 :
                    voirtoutesreservation();
                    break;
                case 3 :
                    validereserv();
                    break;
                case 4 :
                    annulereserv();
                    break;
                case 5 :
                    statistique();
                    break;
                case 0 :
                    
                    break;
                default:
                    System.out.println(" Choix invalide !");
                    break;
            }
            if (choix != 0) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        }while(choix !=0);
    }
    
    public static void voirchambredispo(Date dateDeb, Date dateFin){
        if (dateDeb == null || dateFin == null) {
            System.out.println(" Les dates de début et de fin doivent être spécifiées.");
            return;
        }
        
        System.out.println("les chambres disponibles entre " + dateDeb + " et " + dateFin + " :");

        List<Chambre> chambresdispo = chambreService.chambresDisponibles();
        boolean found =false;
        
        for (Chambre ch :chambresdispo){
            if (chambreService.verifierDisponibilite(ch, dateDeb, dateFin)) {
                System.out.println("Chambre n°" + ch.getNumero() + " | Type: " + ch.getType() + " | Prix: " + ch.getPrix() + " DA/nuit \n ");
                found = true;
            }
        }
        if (!found) {
        System.out.println("Aucune chambre disponible pour cette période.");
    }
    }
        
    public static void fairereserv() throws ParseException{
        System.out.println("---new reservation---");

        System.out.print(" enter la date de debut (yyyy-mm-dd) :");
        String  jourdeb=scanner.nextLine();
        System.out.print(" enter la date de fin  (yyyy-mm-dd) :");
        String  jourfin=scanner.nextLine();

        SimpleDateFormat s =new SimpleDateFormat("yyyy-mm-dd");
        Date datedeb = s.parse(jourdeb);
        Date datefin = s.parse(jourfin);


        voirchambredispo(datedeb,datefin);

        System.out.println("donne moi numero de chambre :");
        int numch= lireChoix();

        Chambre chambreChoisie = chambreService.chercherChambre(numch);

        if (chambreChoisie == null ||!chambreService.verifierDisponibilite(chambreChoisie,datedeb,datefin)) {
            System.out.println(" Chambre non trouvée ou non disponible !");
            return;
        }

    
        Reservation reservation=clientService.reserver(clientConnecte,chambreChoisie, datedeb, datefin);
        if (reservation != null) {
            System.out.println("Réservation réussie !");
        } else {
            System.out.println("Échec de la réservation !");
        }
    }
    
    public static void voirmesreservation(){
        System.out.println(" mes reservations ");
        clientService.consulterReservations(clientConnecte);
    }
    
    public static void effPaiment() {
        System.out.println("\n PAIEMENT ");

        List<Reservation> toutesReservations = clientService.consulterReservations(clientConnecte);
        List<Reservation> reservationsAPayer = new ArrayList<>();

    // Filtrer uniquement les réservations confirmées
        for (Reservation res : toutesReservations) {
            if (res.getStatut() == Statut.confirmée) {
                reservationsAPayer.add(res);
            }
        }

        if (reservationsAPayer.isEmpty()) {
            System.out.println(" Aucune réservation confirmée à payer !");
            return;
        }

        System.out.println("Réservations confirmées non payées :");
        for (int i = 0; i < reservationsAPayer.size(); i++) {
            Reservation res = reservationsAPayer.get(i);
            System.out.println((i + 1) + ". ID: " + res.getId() +
                    " - Montant: " + res.calculerPrixTotal() + "€");
        }

        System.out.print("Choisissez une réservation à payer : ");
        int choix = lireChoix() - 1;

        if (choix < 0 || choix >= reservationsAPayer.size()) {
            System.out.println(" Choix invalide !");
            return;
        }

        Reservation reservationChoisie = reservationsAPayer.get(choix);
        System.out.print("Méthode de paiement : ");
        System.out.println("1. Carte bancaire");
        System.out.println("2. PayPal");
    
        int methodeChoisie = lireChoix();

        MethodeP methode = (methodeChoisie == 1) ? MethodeP.CB : MethodeP.Paypal;

        double montant = reservationChoisie.calculerPrixTotal();
        System.out.println("Montant à payer : " + montant + "€");

        Paiment paiement = new Paiment(montant,new Date(),methode,EtatP.echoue );
        paiement.setReservation(reservationChoisie);

        if (paiement.validerPaiment()) {
            clientService.effectuerPaiement(paiement);
            System.out.println(" Paiement effectué avec succès !");
        } else {
            System.out.println(" Paiement échoué.");
        }
}

    /*public static void effPaiment(){
        System.out.println(" effectuer le paiment ");
        List<Reservation> myreserv= clientConnecte.consulterReservation();
        if(myreserv.isEmpty()){
            System.out.println("aucune reservation a payer !");
            return;
        }
        System.out.println(" reservation non  payees !");
        Iterator<Reservation> it = myreserv.iterator();
        int index =1 ;
        while(it.hasNext()){
            Reservation res =it.next();
            if(res.getStatut()==Statut.confirmée){
                System.out.println(index+".  id :"+res.getId()+" , montant :"+res.calculerPrixTotal()+"$");
                index++;
            }
        }
    }*/
    public static void gerechambre(){
        System.out.println("\n GESTION DES CHAMBRES ");
        System.out.println("1. Voir toutes les chambres");
        System.out.println("2. Ajouter une chambre");
        System.out.println("3. Modifier une chambre");
        System.out.println("4. Supprimer une chambre");
        System.out.print("Votre choix : ");

        int choix = lireChoix();
        switch (choix) {
            case 1:
                voirToutesChambres();
                break;
            case 2:
                ajouterChambre();
                break;
            case 3:
                modifierChambre();
                break;
            case 4 :
                supprimerChambre();
                break;
            default:
                System.out.println("❌ Choix invalide !");
        }

    }
    
    public static void voirToutesChambres(){
        List<Chambre> chambres = chambreService.listerChambres();
        System.out.println(" tout les chambres :");

        if (chambres == null || chambres.isEmpty()) {
        System.out.println("Aucune chambre trouvée !");
        return;
        }

        for(Chambre ch :chambres){
            String statut = ch.isDisponible() ? " disponible " : " occupee " ;
            System.out.println("chambre numero : "+ch.getNumero()+"("+ch.getType()+") ,prix :"+ch.getPrix()+" $/nuit -"+statut+"/n");
        }
    }
    
    public static void ajouterChambre(){
        System.out.println(" ajouter une chambre :");
        System.out.print("Numéro de chambre : ");
        int numero = lireChoix();

        System.out.println("Type de chambre :");
        System.out.println("1. Simple");
        System.out.println("2. Double");
        System.out.println("3. Suite");
        System.out.print("Votre choix : ");
        int typeChoix = lireChoix();
        
        Type type = null;
        switch (typeChoix) {
            case 1:
                type = Type.simple; 
                break;
            case 2: 
                type = Type.Double; 
                break;
            case 3: 
                type = Type.suite; 
                break;
            default:
                System.out.println(" Type invalide !");
                break;
        }
        System.out.print("Prix par nuit : ");
        double prix = Double.parseDouble(scanner.nextLine());
        try {
            Chambre newchmb =new Chambre(numero,type, prix, true);
            chambreService.ajouterChambre(newchmb);
            System.out.println(" Chambre ajoutée avec succès !");
        } catch (Exception e) {
            System.out.println(" Erreur lors de l'ajout de la chambre !");
        }
    }
    
    public static void modifierChambre(){
        System.out.println("numero de la chambre a modifier !");
        int numero =lireChoix();

        Chambre chambre = chambreService.chercherChambre(numero);

        if(chambre != null ){
            System.out.print("Nouveau prix par nuit : ");
            double nouveauPrix = Double.parseDouble(scanner.nextLine());
            chambre.setPrix(nouveauPrix);
            chambreService.modifierChambre(chambre);
            System.out.println("Chambre modifiée avec succès !");
        }else{
            System.out.println(" Chambre introuvable !");
        }
        
        
    }
    
    public static void supprimerChambre(){
        System.out.println("numero de la chambre a supprimer !");
        int numero=lireChoix();

        Chambre chambre = chambreService.chercherChambre(numero);
        if( chambre !=null){
            chambreService.supprimerChambre(numero);
            System.out.println("chambre supprimer !");
        }else{
            System.out.println(" chambre introuvable !");
        }
    }
    
    public static void voirtoutesreservation(){
        System.out.println(" toutes les reservations ");
    
        List<Reservation> reservations=adminService.voirToutesReservations(ad);
        if( reservations == null||reservations.isEmpty()){
            System.out.println(" aucune reservation trouvee ");
            return;
        }
        for(Reservation r :reservations ){
            if(r !=null){
                System.out.println(" ID: " + r.getId() + 
                            " , Client: " + r.getC().getNom() + " " + r.getC().getPrenom() +
                            " , Chambre: " + r.getCh().getNumero() +
                            " , Statut: " + r.getStatut() +
                            " , Prix: " + r.calculerPrixTotal() + "€");
            }
        }
    }
    
    public static void validereserv(){
        System.out.println("\n VALIDER RÉSERVATION ");
        voirtoutesreservation();

        System.out.print("ID de la réservation à valider : ");
        long id = Long.parseLong(scanner.nextLine());

        Reservation r= reservationService.chercherParId(id);
        if(r!=null && r.getStatut()==Statut.en_attente){
            reservationService.validerReservation(r);
            System.out.println("Réservation validée !");
        }else {
            System.out.println("Réservation non trouvée ou déjà validée !");
        }
    }
    
    public static void annulereserv(){
        System.out.println("\n ANNULER RÉSERVATION ");
        voirtoutesreservation();

        System.out.print("ID de la réservation à annuler : ");
        long id = Long.parseLong(scanner.nextLine());

        Reservation r = reservationService.chercherParId(id);
        if (r != null && r.getStatut() != Statut.annulée) {
            reservationService.annulerReserv(id);
            System.out.println("Réservation annulée avec succès !");
        } else {
            System.out.println("Réservation non trouvée ou déjà annulée !");
        }
        
    }
    
    public static void statistique(){
        Long nbclient = clientDAO.compterClients();
        Long nbchambre = chambreDAO.compterChambres();
        Long nbreservation =reservationDAO.compterReservations();
        Long nbchambreDispo = chambreDAO.compterChambresDisponibles();
        double chiffreAffaires = reservationDAO.calculerChiffreAffaires();
        Long nbadmin =adminstrateurDAO.compterAdminstrateur();
        System.out.println(" nombres des clients : "+nbclient+"\n");
        System.out.println(" nombres des chambres : "+nbchambre+"\n");
        System.out.println(" nombres des reservation : "+nbreservation+"\n");
        System.out.println(" nombres des adminstrateurs : "+nbadmin+"\n");
        System.out.println("nombres des chambres disponibles : "+nbchambreDispo+"\n");
        System.out.println(" chiffre d'affaires : "+chiffreAffaires+"$\n");
    }
    public static void deconnexion(){
        clientConnecte=null;
        adminConnecte=false;
        System.out.println("deconnexion reussie !");
    }
}
