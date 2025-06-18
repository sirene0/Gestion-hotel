package com.gestion_hotel.service;


import java.util.List;

import com.gestion_hotel.dao.UtilisateurDAO;
import com.gestion_hotel.entities.Utilisateur;

public class UtilisateurService {
    //private static List<Utilisateur> utilisateurInsrits = new ArrayList<>();
    
    private UtilisateurDAO userDAO = new UtilisateurDAO();

    //public List<Utilisateur> getUtilisateurInscrits(){return utilisateurInsrits;}
    
    public boolean sInscrire(Utilisateur user){
        
        if(userDAO.trouverparEmail(user.getEmail()) !=null){
            System.out.println("Erreur : cet email est déjà utilisé !");
            return false ;
        }
        /*for (Utilisateur u : utilisateurInsrits){
            if(u.getEmail().equals(user.getEmail())){
                System.out.println("Erreur : cet email est déjà utilisé !");
                return false ;
            }
        }*/
        if(user.getNom()== null || user.getNom().trim().isEmpty()){
            System.out.println("Erreur : le nom est obligatoire !");
            return false;
        }
        if(user.getPrenom()== null || user.getPrenom().trim().isEmpty()){
            System.out.println("Erreur : le prenom est obligatoire !");
            return false;
        }
        if(user.getEmail()== null || !user.getEmail().contains("@")){
            System.out.println("Erreur : l'email est invalide !");
            return false;
        }
        if(user.getMotdepasse()== null || user.getMotdepasse().length()<6){
            System.out.println("Erreur : le mot de passe doit contenir au moins 6 caractères !");
            return false;
        }
        user.setInscrit(true);
        //utilisateurInsrits.add(user);
        System.out.println ("Utilisateur :" + user.getNom() +","+user.getPrenom()+ " inscrit avec succès !");
        userDAO.enregistrerUser(user);
        return true;
    }
    
    public boolean seConnecter(String email ,String motdepasse){
        /*for(Utilisateur u : utilisateurInsrits){
            if(u.getEmail().equals(email) && u.getMotdepasse().equals(motdepasse)){
                System.out.println(" Connexion réussie !");
                return true;
            }
        }
        System.out.println("connexion echouee !");
        return false;*/


        Utilisateur user = userDAO.trouverparEmail(email);
        if(user != null &&user.getMotdepasse().equals(motdepasse)){
            System.out.println(" Connexion réussie !");
                return true;
        }
        System.out.println("connexion echouee !");
        return false;
    }

    public List<Utilisateur> listeUsers(){
        return userDAO.listUsers();
    }
}
