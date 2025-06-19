package com.gestion_hotel.dao;

import com.gestion_hotel.util.JPAUtil;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;


import com.gestion_hotel.entities.Client;
import jakarta.persistence.NoResultException;


public class ClientDAO {
    //enregistrer un client 
    public void enregistrerClient(Client client){
        EntityManager em =JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            System.err.println("erreur lors de l'enregistrement du client :"+e.getMessage());
        }finally{
            if(em !=null && em.isOpen()){
                em.close();
            }
        }
    }

    //rechercehe client par email
    public Client trouverparEmail(String email ){
        EntityManager em =JPAUtil.getEntityManager();
        Client client = null;
        try{
            client =em.createQuery("SELECT C FROM Client C WHERE C.email = :email ",Client.class).setParameter("email",email).getSingleResult();
        }catch(NoResultException e){
                System.err.println("aucun client trouve avec cet email :"+email);
        }finally{
            em.close();
        }
        return client;
    }
    
    //recherche par id 
    public Client trouverparId(Long id){
        EntityManager em =JPAUtil.getEntityManager();
        Client client =null;
        try{
            client= em.createQuery("SELECT C FROM Client  C WHERE C.id= :id  ",Client.class).setParameter("id",id).getSingleResult();
        }finally{
            em.close();
        }
        return client ;
    }

    //lister les clients
    public List<Client> listeClients(){
        EntityManager em= JPAUtil.getEntityManager();
        List<Client> clients =new ArrayList<>();
        try{
            clients=em.createQuery("SELECT C FROM Client C WHERE ORDER BY C.nom ",Client.class).getResultList();
        }catch(Exception e){
            System.err.println("Erreur lors de la récupération des clients : " + e.getMessage());
        }finally{
            em.close();
        }
        return clients;
    }


    public void supprimerClient(Long id){
        EntityManager em =JPAUtil.getEntityManager();
        Client client =null;
        try{
            em.getTransaction().begin();
            client=em.find(Client.class ,id);
            if(client!=null){
                em.remove(client);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            if (em.getTransaction().isActive()) 
            {
                em.getTransaction().rollback();
                System.err.println("erreur : lors de la suppression de client :"+e.getMessage());
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }
    
    public void modifierAdmin(Client client ){
        EntityManager em =JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(client);
            em.getTransaction().commit();
        }catch (Exception e){
            if (em.getTransaction().isActive()) 
            {
                em.getTransaction().rollback();
                System.err.println("erreur : lors de la modification de client :"+e.getMessage());
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }

    public Long compterClients() {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        return em.createQuery("SELECT COUNT(c) FROM Client c", Long.class).getSingleResult();
    } finally {
        em.close();
    }
}

}
