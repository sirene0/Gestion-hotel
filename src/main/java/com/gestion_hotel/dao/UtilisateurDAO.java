package com.gestion_hotel.dao;

import java.util.ArrayList;
import java.util.List;
import com.gestion_hotel.entities.Utilisateur;
import com.gestion_hotel.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
public class UtilisateurDAO {
    //ENREGISTRE UN UTILISATEUR 
    public void enregistrerUser(Utilisateur user){
        EntityManager em= JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            System.out.println("utilisateur enregistrer avec succes !");
        }catch (Exception e){
            em.getTransaction().rollback();
            System.err.println("erreur lors de l'enregistrement de l'utilisateur :"+e.getMessage());
        }finally{
            em.close();
        }
    }
    //recherche par mail 
    public Utilisateur trouverparEmail (String email){
        EntityManager em =JPAUtil.getEntityManager();
        Utilisateur user= null;
        try{
            user =em.createNamedQuery("Utilisateur.trouverparEmail",Utilisateur.class).setParameter("email",email).getSingleResult();
        }catch(NoResultException e){
            System.err.println("aucun utilisateur trouve avec cet email :"+email);
        }finally{
            em.close();
        }
        return user ;
    }

    public List<Utilisateur> listUsers(){
        EntityManager em =JPAUtil.getEntityManager();
        List<Utilisateur> users =new ArrayList<>();
        try{
            users = em.createQuery("SELECT U FROM Utilisateur U ORDER BY U.nom",Utilisateur.class).getResultList();

        }catch (Exception e ){
            System.err.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }finally{
            em.close();
        }
        return users ;
    }
}

