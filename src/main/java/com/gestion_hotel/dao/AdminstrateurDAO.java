package com.gestion_hotel.dao;

import java.util.ArrayList;
import java.util.List;

import com.gestion_hotel.entities.Adminstrateur;
import com.gestion_hotel.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class AdminstrateurDAO {
    //enregistrer un adminstrateur 
    public void enregistrerAdmin(Adminstrateur admin){
        EntityManager em =JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(admin);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            System.err.println("erreur lors de l'enregistrement d' adminstrateur :"+e.getMessage());
        }finally{
            if(em !=null && em.isOpen()){
                em.close();
            }
        }
    }

    //rechercehe adminstrateur par email
    public Adminstrateur trouverparEmail(String email ){
        EntityManager em =JPAUtil.getEntityManager();
        Adminstrateur admin = null;
        try{
            admin =em.createQuery("SELECT A FROM Adminstrateur A WHERE A.email = :email",Adminstrateur.class).setParameter("email",email).getSingleResult();
        }catch(NoResultException e){
                System.err.println("aucun adminstrateur trouve avec cet email :"+email);
        }finally{
            em.close();
        }
        return admin;
    }
    
    //recherche par id 
    public Adminstrateur trouverparId(Long id){
        EntityManager em =JPAUtil.getEntityManager();
        Adminstrateur admin =null;
        try{
            admin= em.createQuery("SELECT A FROM Adminstrateur A WHERE A.id= :id",Adminstrateur.class).setParameter("id",id).getSingleResult();
        }finally{
            em.close();
        }
        return admin ;
    }

    //lister les clients
    public List<Adminstrateur> listeAdminstrateurs(){
        EntityManager em= JPAUtil.getEntityManager();
        List<Adminstrateur> admins =new ArrayList<>();
        try{
            admins=em.createQuery("SELECT A FROM Adminstrateur A ORDER BY A.nom",Adminstrateur.class).getResultList();
        }catch(Exception e){
            System.err.println("Erreur lors de la récupération des adminstrateurs : " + e.getMessage());
        }finally{
            em.close();
        }
        return admins;
    }
    
    public void supprimerAdmin(Long id){
        EntityManager em =JPAUtil.getEntityManager();
        Adminstrateur admin =null;
        try{
            em.getTransaction().begin();
            admin=em.find(Adminstrateur.class ,id);
            if(admin!=null){
                em.remove(admin);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            if (em.getTransaction().isActive()) 
            {
                em.getTransaction().rollback();
                System.err.println("erreur : lors de la suppression de l'adminstrateur :"+e.getMessage());
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }
    
    public void modifierAdmin(Adminstrateur admin ){
        EntityManager em =JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(admin);
            em.getTransaction().commit();
        }catch (Exception e){
            if (em.getTransaction().isActive()) 
            {
                em.getTransaction().rollback();
                System.err.println("erreur : lors de la modification de l'adminstrateur :"+e.getMessage());
                e.printStackTrace();
            }
        }finally{
            em.close();
        }
    }

    public Long compterAdminstrateur(){
        EntityManager em = JPAUtil.getEntityManager();
        try{
            return em.createQuery("SELECT A FROM Adminstrateur A ",Long.class).getSingleResult();
        } finally{
            em.close();
        }
    }
}
