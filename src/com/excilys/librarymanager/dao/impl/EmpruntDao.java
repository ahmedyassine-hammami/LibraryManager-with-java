package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record;

import java.util.Date;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.utils.EstablishConnection;
import com.excilys.librarymanager.dao.IEmpruntDao;
import com.excilys.librarymanager.exception.DaoException;
public class EmpruntDao implements IEmpruntDao {
	
	private static EmpruntDao instance;
	private EmpruntDao() { }	
	public static EmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDao();
		}
		return instance;
	}
	
	
	private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n" + 
			"ORDER BY dateRetour DESC;";
	private static final String SELECT_NON_RENDU_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n" + 
			"WHERE dateRetour IS NULL;";
	
	
	
	private static final String SELECT_ONE_MEMBER_NON_RENDU_QUERY= "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n" + 
			"WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String SELECT_ONE_LIVRE_NON_RENDU_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n" + 
			"WHERE dateRetour IS NULL AND livre.id = ?;";
	private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,\r\n" + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" + 
			"dateRetour\r\n" + 
			"FROM emprunt AS e\r\n" + 
			"INNER JOIN membre ON membre.id = e.idMembre\r\n" + 
			"INNER JOIN livre ON livre.id = e.idLivre\r\n" + 
			"WHERE e.id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour)\r\n" + 
			"VALUES (?, ?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE emprunt\r\n" + 
			"SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ?\r\n" + 
			"WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";
	
	@Override
	
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emp = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				
				
				LocalDate d1 = res.getDate("dateEmprunt").toLocalDate() ;
				Date date = res.getDate("dateRetour");
				LocalDate d2 = null ;
				if(date!=null) d2 = res.getDate("dateRetour").toLocalDate() ;
				Emprunt f = new Emprunt(res.getInt("id"),res.getInt("idMembre"),res.getString("nom"),
						res.getString("prenom"),res.getString("adresse"),res.getString("email"),
						res.getString("telephone"),res.getString("abonnement"),res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),
						res.getString("isbn"),d1
						,d2);
				emp.add(f);
			}
			System.out.println("GET: " + emp);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
		}
		return emp;
	}
	
	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		List<Emprunt> emp = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NON_RENDU_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Date date = res.getDate("dateRetour");
				LocalDate d2 = null ;
				if(date!=null) d2 = res.getDate("dateRetour").toLocalDate() ;
				Emprunt f = new Emprunt(res.getInt("id"),res.getInt("idMembre"),res.getString("nom"),
						res.getString("prenom"),res.getString("adresse"),res.getString("email"),
						res.getString("telephone"),res.getString("abonnement"),res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),
						res.getString("isbn"),res.getDate("dateEmprunt").toLocalDate(),d2);
				emp.add(f);
			}
			System.out.println("GET: " + emp);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
		}
		return emp;
	}
	
	
	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		List<Emprunt> emp = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_MEMBER_NON_RENDU_QUERY);
			preparedStatement.setInt(1, idMembre);
			res = preparedStatement.executeQuery();

			while(res.next()) {
				Date date = res.getDate("dateRetour");
				LocalDate d2 = null ;
				if(date!=null) d2 = res.getDate("dateRetour").toLocalDate() ;
				Emprunt f = new Emprunt(res.getInt("id"),res.getInt("idMembre"),res.getString("nom"),
						res.getString("prenom"),res.getString("adresse"),res.getString("email"),
						res.getString("telephone"),res.getString("abonnement"),res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),
						res.getString("isbn"),res.getDate("dateEmprunt").toLocalDate(),d2);
				emp.add(f);
			}
			System.out.println("GET: " + emp);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du membre: id=" + idMembre, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emp ;
	}
	
	
	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		List<Emprunt> emp = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_LIVRE_NON_RENDU_QUERY);
			preparedStatement.setInt(1, idLivre);
			res = preparedStatement.executeQuery();

			while(res.next()) {
				Date date = res.getDate("dateRetour");
				LocalDate d2 = null ;
				if(date!=null) d2 = res.getDate("dateRetour").toLocalDate() ;
				Emprunt f = new Emprunt(res.getInt("id"),res.getInt("idMembre"),res.getString("nom"),
						res.getString("prenom"),res.getString("adresse"),res.getString("email"),
						res.getString("telephone"),res.getString("abonnement"),res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),
						res.getString("isbn"),res.getDate("dateEmprunt").toLocalDate(),d2);
				emp.add(f);
			}
			System.out.println("GET: " + emp);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du livre: id=" + idLivre, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emp ;
	}
	@Override
	public Emprunt getById(int id) throws DaoException {
		Emprunt f = new Emprunt();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				Date date = res.getDate("dateRetour");
				LocalDate d2 = null ;
				if(date!=null) d2 = res.getDate("dateRetour").toLocalDate() ;
				f = new Emprunt(res.getInt("id"),res.getInt("idMembre"),res.getString("nom"),
						res.getString("prenom"),res.getString("adresse"),res.getString("email"),
						res.getString("telephone"),res.getString("abonnement"),res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),
						res.getString("isbn"),res.getDate("dateEmprunt").toLocalDate(),d2);
			}
			
			System.out.println("GET: " + f);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de l'emprunt: id=" + id, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return f ;
	}
	
	
	@Override
	public void create(Membre m, Livre l, LocalDate dateEmprunt) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		java.sql.Date date = java.sql.Date.valueOf(dateEmprunt);
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY);
			preparedStatement.setInt(1, m.getId());
			preparedStatement.setInt(2,l.getId());
			preparedStatement.setDate(3,date);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException("Probléme lors de la création de l'emprunt: ", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public void update(Emprunt emprunt) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		java.sql.Date sqlDateEmprunt = java.sql.Date.valueOf(emprunt.getDateEmprunt());
		java.sql.Date sqlDateRetour = java.sql.Date.valueOf(emprunt.getDateRetour());
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setInt(1, emprunt.getMembre().getId());
			preparedStatement.setInt(2, emprunt.getLivre().getId());
			preparedStatement.setDate(3, sqlDateEmprunt);
			preparedStatement.setDate(4, sqlDateRetour);
			preparedStatement.setInt(7, emprunt.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour de l'emprunt: " + emprunt, e);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public int count() throws DaoException {
		int count =0 ;
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(COUNT_QUERY);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				count = res.getInt(1);
			}
			
			System.out.println("GET: " + count );
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération des emprunts", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	



	}

