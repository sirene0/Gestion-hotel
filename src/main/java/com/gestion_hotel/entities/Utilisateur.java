package com.gestion_hotel.entities;

import com.gestion_hotel.enums.*;

import jakarta.persistence.*;

@NamedQuery(name ="Utilisateur.trouverparEmail",
            query =" SELECT U FROM Utilisateur U WHERE U.email = :email")
@Entity
@Table (name ="Utilisateurs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type" ,discriminatorType = DiscriminatorType.STRING)
public abstract class Utilisateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String motdepasse;

    private boolean isInscrit;

    @Column( insertable = false, updatable=false)
    @Enumerated( EnumType.STRING)
    private Typeutilisateur type;
    
    public Utilisateur() {
    }

    public Utilisateur( String nom, String prenom, String email, String motdepasse,Typeutilisateur type) {
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
    public boolean isInscrit() {return isInscrit;}
    public void setInscrit(boolean isInscrit) {this.isInscrit = isInscrit;}

    
}
