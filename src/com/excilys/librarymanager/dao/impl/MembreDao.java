package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.utils.EstablishConnection;
import com.excilys.librarymanager.dao.IMembreDao;
import com.excilys.librarymanager.exception.DaoException;
public class MembreDao implements IMembreDao {
	
	private static MembreDao instance;
	private MembreDao() { }	
	public static MembreDao getInstance() {
		if(instance == null) {
			instance = new MembreDao();
		}
		return instance;
	}
	
	
	private static final String CREATE_QUERY = "INSERT INTO membre(nom, prenom, adresse, email, telephone,abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SELECT_ONE_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
	private static final String SELECT_ALL_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
	private static final String UPDATE_QUERY = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
	private static final String DELETE_QUERY = "DELETE FROM membre WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM membre;";
	

	@Override
	public int create(String nom, String prenom, String adresse, 
			String email, String telephone,String abonnement) throws DaoException {

		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1,nom);
			preparedStatement.setString(2,prenom);
			preparedStatement.setString(3,adresse);
			preparedStatement.setString(4,email);
			preparedStatement.setString(5,telephone);
			preparedStatement.setString(6,abonnement);
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

		} catch (SQLException e) {
			throw new DaoException("Probléme lors de la création du membre: ", e);
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
		return id;
	}

	@Override
	public Membre getById(int id) throws DaoException {
		Membre membre = new Membre();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				membre.setId(res.getInt("id"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(res.getString("abonnement"));
			}
			
			System.out.println("GET: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du membre: id=" + id, e);
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
		return membre ;
	}

	@Override
	public List<Membre> getList() throws DaoException {
		List<Membre> membres = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Membre f = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"),
						res.getString("adresse"),res.getString("email"),res.getString("telephone"),
						res.getString("abonnement"));
				membres.add(f);
			}
			System.out.println("GET: " +membres);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des membres", e);
		}
		return membres;
	}

	@Override
	public void update(Membre m) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setString(1, m.getNom());
			preparedStatement.setString(2, m.getPrenom() );
			preparedStatement.setString(3, m.getAdresse());
			preparedStatement.setString(4, m.getEmail());
			preparedStatement.setString(5, m.getTelephone());
			preparedStatement.setString(6, m.getAbonnement());
			preparedStatement.setInt(7, m.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + m);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du membre: " + m, e);
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
	public void delete(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			System.out.println("DELETE: " + id);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la suppression du membre: " + id, e);
		}  finally {
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
				throw new DaoException("Problème lors de la récupération des membres", e);
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