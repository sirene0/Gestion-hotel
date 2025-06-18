package com.gestion_hotel.dao;

import java.util.ArrayList;
import java.util.List;

import com.gestion_hotel.entities.Reservation;
import com.gestion_hotel.enums.Statut;
import com.gestion_hotel.util.JPAUtil;
import jakarta.persistence.EntityManager;
public class ReservationDAO {
    //ajouter une reservation 
    public void AjouterReservation(Reservation reserv ){
        EntityManager em =JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(reserv);
            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }

    //trouver une reservation par id 
    public Reservation trouverRerservparId(Long id){
        EntityManager em =JPAUtil.getEntityManager();
        Reservation reserv=null;
        try {
            reserv=em.find(Reservation.class,id);
        } finally {
            em.close();
        }
        return reserv;
    }

    //liste des reservations
    public List<Reservation> listeReserv(){
        EntityManager em =JPAUtil.getEntityManager();
        List<Reservation> reservations= new ArrayList<>();
        try {
            reservations= em.createQuery("SELECT R FROM Reservation R ORDER BY R.dateDebut",Reservation.class).getResultList();

        } catch (Exception e) {
            System.err.println("erreur: lors de la recuperation de la listes des reservations :"+e.getMessage());
        }finally{
            em.close();
        }
        return reservations;
    }

    //supprimer une reservation 
    public void supprimerReservation(Long id){
        EntityManager em =JPAUtil.getEntityManager();
        Reservation reserv=null;
        try{
            em.getTransaction().begin();
            reserv= em.find(Reservation.class,id);
            if ( reserv != null){
                em.remove(reserv);
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
    //modifier une reservation 
    public void modifierReserv(Reservation reserv){
        EntityManager em =JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(reserv);
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

    public Long compterReservations() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(r) FROM Reservation r", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public double calculerChiffreAffaires() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Reservation> reservations = em.createQuery("SELECT r FROM Reservation r WHERE r.statut = :statut", Reservation.class).setParameter("statut", Statut.confirmée).getResultList();

            double total = 0;
            for (Reservation r : reservations) {
                total += r.calculerPrixTotal();
            }
            return total;
        } finally {
            em.close();
        }
    }

}

