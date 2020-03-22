package com.excilys.librarymanager.model;

public class Livre {
	private int id;
	private String titre;
	private String auteur;
	private String isbn ;
	public Livre(int id, String titre, String auteur, String isbn) {
		super();
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
		this.isbn = isbn;
	}
	public Livre() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	@Override
	public String toString() {
		return "Livre [id=" + id + ", titre=" + titre + ", auteur=" + auteur + ", isbn=" + isbn + "]";
	}
	

}
