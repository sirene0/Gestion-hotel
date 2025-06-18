package com.gestion_hotel.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME ="gestion_hotelPU";
    private static EntityManagerFactory emf ;
    
    public JPAUtil(){}

    static {
        try {
            emf=Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e){
            System.err.println("erreur lors de la creation de l'entitymanagerfactory :"+e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }
    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public static String getPersistenceUnitName() {return PERSISTENCE_UNIT_NAME;}
    public static EntityManagerFactory getEmf() {return emf;}
    public static void setEmf(EntityManagerFactory emf) {JPAUtil.emf = emf;}

    public static void close(){
        if(emf !=null && emf.isOpen()){
            emf.close();
        }
    }
}
