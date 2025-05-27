package Gestion;

import java.util.ArrayList;
import java.util.List;

public abstract class Utilisateur {
    protected static Long cptID= (long) 1; 
    protected Long id;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String motdepasse;
    protected boolean isInscrit;
    protected Typeutilisateur type;

    private static List<Utilisateur> utilisateurInscrits =new ArrayList<>();

    
    public Utilisateur( String nom, String prenom, String email, String motdepasse,Typeutilisateur type) {
        this.id = cptID++;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motdepasse = motdepasse;
        this.type=type;
    }
    
    public long getId() {return id;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}
    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) { this.prenom = prenom;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getMotdepasse() {return motdepasse;}
    public void setMotdepasse(String motdepasse) {this.motdepasse = motdepasse;}
    public Typeutilisateur getType() { return type;}
    public boolean sInscrire(){
        for (Utilisateur u : utilisateurInscrits){
            if(u.getEmail().equals(this.email)){
                System.out.println("Erreur : cet email est déjà utilisé !");
                return false ;
            }
        }
        if(getNom()== null || getNom().trim().isEmpty()){
            System.out.println("Erreur : le nom est obligatoire !");
            return false;
        }
        if(getPrenom()== null || getPrenom().trim().isEmpty()){
            System.out.println("Erreur : le prenom est obligatoire !");
            return false;
        }
        if(getEmail()== null || !getEmail().contains("@")){
            System.out.println("Erreur : l'email est invalide !");
            return false;
        }
        if(getMotdepasse()== null || getMotdepasse().length()<6){
            System.out.println("Erreur : le mot de passe doit contenir au moins 6 caractères !");
            return false;
        }
        this.isInscrit=true;
        utilisateurInscrits.add(this);
        System.out.println ("Utilisateur :" + getNom() +","+getPrenom()+ " inscrit avec succès !");
        return true;
    }
    public  boolean seConnecter(String email , String motdepasse){
        for(Utilisateur u :utilisateurInscrits){
            if( u.getEmail().equals(email)&& u.getMotdepasse().equals(motdepasse)){
                System.out.println(" Connexion réussie !");
                return true;
            }
        }
        System.out.println(" Échec de la connexion !");
        return false ;
    }
}
