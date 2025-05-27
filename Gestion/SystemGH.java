package Gestion;
import java.util.Scanner;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
public class SystemGH {

    private static Scanner scanner =new Scanner(System.in);
    private static Adminstrateur ad =null;
    private static List<Client> clients =new ArrayList<>(); 
    private static List<Chambre> chambres =new ArrayList<>(); 
    private static List<Reservation> allReservations =new ArrayList<>();
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
        ad =Adminstrateur.creeAdminstrateur("Admin", "System", "admin@gmail.com","admin123");
        try {
            chambres.add(new Chambre(1L, 100, Type.simple, 80.0, true));
            chambres.add(new Chambre(2L, 101, Type.simple, 80.0, true));
            chambres.add(new Chambre(3L, 200, Type.Double, 120.0, true));
            chambres.add(new Chambre(4L, 201, Type.Double, 120.0, true));
            chambres.add(new Chambre(5L, 300, Type.suite, 200.0, true));
            chambres.add(new Chambre(6L, 301, Type.suite, 200.0, true));

            if(ad !=null){
                ad.setChambres(chambres);
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

        Client newcl= Client.creernewClient(nom, prenom, email, motdepasse);
        if (newcl !=null){
            clients.add(newcl);
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

        Adminstrateur newad=Adminstrateur.creeAdminstrateur(nom, prenom, email, motdepasse);
        if(newad !=null){
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
        for(Client cl :clients){
            if(cl.seConnecter(email,motdepasse)){
                clientConnecte=cl;
                adminConnecte=false;
                System.out.println("Connexion client reussie !");
                return;
            }
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
        if(ad !=null && ad.seConnecter(email, motdepasse)){
            adminConnecte=true;
            clientConnecte=null;
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
                    voirchambredispo();
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
    public static void voirchambredispo(){
        System.out.println("les chambres disponibles :");
        boolean found =false;
        for (Chambre ch :chambres){
            if (ch.isDisponible()) {
                System.out.println("chambre numero "+ch.getNumero()+" type ("+ch.getType()+") - prix"+ch.getPrix()+"$/nuit");
                found=true;
            }
        }
        if(!found ){System.out.println("aucune chambre disponible actuellement !");}

    }
    public static void fairereserv() throws ParseException{
        System.out.println("---new reservation---");
        voirchambredispo();
        System.out.println("donne moi numero de chambre :");
        int numch= lireChoix();
        Chambre chambrechoisie =null;
        for (Chambre ch :chambres){
            if(ch.getNumero()== numch &&ch.isDisponible()){
                chambrechoisie=ch;
                break;
            }
        }
        if (chambrechoisie == null) {
            System.out.println(" Chambre non trouvée ou non disponible !");
            return;
        }

        System.out.print(" enter la date de debut (yyyy-mm-dd) :");
        String  jourdeb=scanner.nextLine();
        System.out.print(" enter la date de fin  (yyyy-mm-dd) :");
        String  jourfin=scanner.nextLine();
        SimpleDateFormat s =new SimpleDateFormat("yyyy-mm-dd");
        Date datedeb = s.parse(jourdeb);
        Date datefin = s.parse(jourfin);
        Reservation reservation=clientConnecte.reserver(chambrechoisie, datedeb, datefin, allReservations);
        if(reservation!=null){
            allReservations.add(reservation);
            if(ad!=null){
                ad.getAllreservation().add(reservation);
            }
        }
    }
    public static void voirmesreservation(){
        System.out.println(" mes reservations ");
        clientConnecte.consulterReservation();
    }
    public static void effPaiment() {
        System.out.println("\n PAIEMENT ");

        List<Reservation> toutesReservations = clientConnecte.consulterReservation();
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

        Paiment paiement = new Paiment(
            System.currentTimeMillis(),montant,new Date(),methode,EtatP.echoue );
        paiement.setReservation(reservationChoisie);

        if (paiement.validerPaiment()) {
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
            default:
                System.out.println("❌ Choix invalide !");
        }

    }
    public static void voirToutesChambres(){
        System.out.println(" tout les chambres :");
        for(Chambre ch :chambres){
            String statut = ch.isDisponible() ? " disponible " : " occupee " ;
            System.out.println("chambre numero : "+ch.getNumero()+"("+ch.getType()+") ,prix :"+ch.getPrix()+" $/nuit -"+statut);
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
            Chambre newchmb =new Chambre((long)(chambres.size() +1), numero,type, prix, true);
            chambres.add(newchmb);
            System.out.println(" Chambre ajoutée avec succès !");
        } catch (Exception e) {
            System.out.println(" Erreur lors de l'ajout de la chambre !");
        }
    }
    public static void modifierChambre(){}
    public static void supprimerChambre(){}
    public static void voirtoutesreservation(){
        System.out.println(" toutes les reservations ");
        List<Reservation> reservations=ad.voirToutesReservations();
        if( reservations.isEmpty()){
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
        for(Reservation r :ad.voirToutesReservations()){
            if(r!=null && r.getId() ==id &&r.getStatut()==Statut.en_attente){
                ad.validerResevation(r);
                return;
            }
        }
        System.out.println(" Réservation non trouvée ou déjà validée !");
    }
    public static void annulereserv(){
        System.out.println("\n ANNULER RÉSERVATION ");
        voirtoutesreservation();
        System.out.print("ID de la réservation à annuler : ");
        long id = Long.parseLong(scanner.nextLine());
        for(Reservation r :ad.voirToutesReservations()){
            if(r!=null && r.getId() ==id &&r.getStatut()!=Statut.annulée){
                ad.annulerResevation(r);
                return;
            }
        }
        System.out.println(" Réservation non trouvée ou déjà annulée !");
    }
    public static void statistique(){
        System.out.println(" nombres des clients : "+clients.size()+"\n");
        System.out.println(" nombres des chambres : "+chambres.size()+"\n");
        System.out.println(" nombres des reservation : "+allReservations.size()+"\n");
        int chdisp =0;
        for(Chambre ch :chambres ){
            if(ch.isDisponible()){chdisp++;}
        }
        System.out.println("nombres des chambres disponibles : "+chdisp+"\n");

        double chiffaff=0;
        for(Reservation r: allReservations){
            if(r!=null && r.getStatut()==Statut.confirmée){
                chiffaff+=r.calculerPrixTotal();
            }
        }
        System.out.println(" chiffre d'affaires : "+chiffaff+"$\n");
    }
    public static void deconnexion(){
        clientConnecte=null;
        adminConnecte=false;
        System.out.println("deconnexion reussie !");
    }
}
