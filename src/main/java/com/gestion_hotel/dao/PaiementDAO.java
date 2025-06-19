package com.gestion_hotel.dao;

import java.util.List;

import com.gestion_hotel.entities.Paiment;
import com.gestion_hotel.entities.Reservation;
import com.gestion_hotel.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class PaiementDAO {
    public void ajouterPaiement(Paiment paiement ){
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(paiement);
            em.getTransaction().commit();
        } catch (Exception e ){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                System.err.println("erreur : lors de l'ajout de paiement :"+e.getMessage());
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }
    public void supprimerPaiement(Long id){
        EntityManager em =JPAUtil.getEntityManager();
        Paiment paiement =null;
        try{
            paiement =em.find(Paiment.class, id);
            if(paiement !=null){
                em.getTransaction().begin();
                em.remove(paiement);
                em.getTransaction().commit();
            }
        }catch (Exception e){
            if( em.getTransaction().isActive()){
                em.getTransaction().rollback();
                System.err.println("erreur : lors de la suppresion de paiement :"+e.getMessage());
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }

    public Paiment trouverparId( Long id){
        EntityManager em =JPAUtil.getEntityManager();
        Paiment paiement =null;
        try{
            
            paiement =em.createQuery("SELECT P FROM Paiment P WHERE P.id = :id",Paiment.class).setParameter("id",id).getSingleResult();
        }catch(Exception e){
            if( em.getTransaction().isActive()){
                em.getTransaction().rollback();
                System.err.println("aucun paiement  trouve avec cet id :"+id);
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
        return paiement;
    }
    public List<Paiment> listerPaiements(){
        EntityManager em =JPAUtil.getEntityManager();
        List<Paiment> paiements =null;
        try{
            em.getTransaction().begin();
            paiements =em.createQuery("SELECT P FROM Paiment P ORDER BY P.id ",Paiment.class).getResultList();
        }catch(Exception e){
            if( em.getTransaction().isActive()){
                em.getTransaction().rollback();
                System.out.println("aucun paiement   !");
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
        return paiements;
    }
    public void modifierPaiement(Paiment paiement){
        EntityManager em =JPAUtil.getEntityManager();
        try{
                em.getTransaction().begin();
                em.merge(paiement);
                em.getTransaction().commit();
                
        }catch( Exception e){
            if( em.getTransaction().isActive()){
                em.getTransaction().rollback();
                System.out.println("erreur : lors de modification de paiment   !");
                e.printStackTrace();
            }
            
        }finally{
            em.close();
        }
    }
    public Paiment trouverParReservation(Reservation reservation) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        return em.createQuery("SELECT p FROM Paiment p LE WHERE p.reservation = :reservation", Paiment.class).setParameter("reservation", reservation).getSingleResult();
    } catch (NoResultException e) {
        return null;
    } finally {
        em.close();
    }
}
}
