package com.gestion_hotel.dao;

import java.util.ArrayList;
import java.util.List;

import com.gestion_hotel.entities.Chambre;

import com.gestion_hotel.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class ChambreDAO {
    public void ajouterCh(Chambre chambre){
        EntityManager em =JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(chambre);
            em.getTransaction().commit();
        } catch (Exception e ) {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }
    
    public Chambre trouverChambreparnum( int numero){
        EntityManager em =JPAUtil.getEntityManager();
        Chambre chambre=null;
        try {
            chambre=em.find(Chambre.class,numero);
        } finally {
            em.close();
        }
        return chambre;
    }
    
    public List<Chambre> listerToutes() {
        EntityManager em =JPAUtil.getEntityManager();
        List<Chambre> chambres= new ArrayList<>();
        try {
            chambres= em.createQuery("SELECT C FROM Chambre C ORDER BY C.numero",Chambre.class).getResultList();

        } catch (Exception e) {
            System.err.println("erreur: lors de la recuperation de la listes des chambres :"+e.getMessage());
        }finally{
            em.close();
        }
        return chambres;
    }

    public void supprimerChambre(int numero){
        EntityManager em =JPAUtil.getEntityManager();
        Chambre ch=null;
        try{
            em.getTransaction().begin();
            ch= em.find(Chambre.class,numero);
            if ( ch != null){
                em.remove(ch);
                em.getTransaction().commit();
            }
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }

    public void modifierChambre(Chambre chambre){
        EntityManager em =JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(chambre);
            em.getTransaction().commit();
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }

    public Long compterChambres() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(c) FROM Chambre c", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public Long compterChambresDisponibles() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(c) FROM Chambre c WHERE c.disponible = true", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<Chambre> listeChambresDispo(){
        EntityManager em =JPAUtil.getEntityManager();
        List<Chambre> chDispo = new ArrayList<>();
        try {
            chDispo=em.createQuery("SELECT C FROM Chambre C WHERE C.disponible = true ORDER BY c.numero ",Chambre.class).getResultList();
        }catch(Exception e){
            System.err.println("erreur: lors de la recuperation de la liste des chambres disponible :"+e.getMessage());
        }finally{
            em.close();
        }
        return chDispo;
    }

}
