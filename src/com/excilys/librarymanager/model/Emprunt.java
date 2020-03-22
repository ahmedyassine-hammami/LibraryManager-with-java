package com.excilys.librarymanager.model;

import java.time.LocalDate;

public class Emprunt {
	private int id;
	private Membre membre;
	private Livre livre ;
	private LocalDate dateEmprunt;
	private LocalDate dateRetour;
	public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
		super();
		this.id = id;
		this.membre = membre;
		this.livre = livre;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
	}

	public Emprunt() {
		// TODO Auto-generated constructor stub
	}
	public Emprunt(int id, int idMembre, String nom, String prenom, String adresse, String email, String telephone,
			String abonnement, int idLivre, String titre, String auteur, String isbn, LocalDate dateEmprunt,
			LocalDate dateRetour) {
		Membre membre=new Membre(idMembre,nom,prenom,adresse,email,telephone,abonnement);
		Livre livre=new Livre(idLivre,titre,auteur,isbn);
		this.id = id;
		this.membre = membre;
		this.livre = livre;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
	}
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	public Livre getLivre() {
		return livre;
	}
	public void setLivre(Livre livre) {
		this.livre = livre;
	}
	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	public LocalDate getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(LocalDate dateRetour) {
		this.dateRetour = dateRetour;
	}
	@Override
	public String toString() {
		return "Emprunt [id=" + id + ", membre=" + membre + ", livre=" + livre + ", dateEmprunt=" + dateEmprunt
				+ ", dateRetour=" + dateRetour + "]";
	}
	
	
}
