package com.excilys.librarymanager.model;


public class Membre {
	private Integer id;
	private String nom;
	private String prenom;
	private String adresse;
	private String email;
	private String telephone;
	private Abonnement abonnement ; 
	public Membre() {
		super();
	}
	
	public Membre(Integer id, String nom, String prenom, String adresse, String email, String telephone,
			String abonnement) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.telephone = telephone;
		this.abonnement = Abonnement.valueOf(abonnement);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAbonnement() {
		return abonnement.name();
	}

	public Abonnement getEnumAbonnement() {
		return abonnement;
	}
	
	public void setAbonnement(String abonnement) {
		this.abonnement =Abonnement.valueOf(abonnement);
	}

	@Override
	public String toString() {
		return "Membre [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", email=" + email
				+ ", telephone=" + telephone + ", abonnement=" + abonnement + "]";
	}
	
}
