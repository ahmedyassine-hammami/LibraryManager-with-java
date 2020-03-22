package com.excilys.librarymanager.dao;
import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;

public interface IEmpruntDao {
	public List<Emprunt> getList() throws DaoException;
	public List<Emprunt> getListCurrent() throws DaoException;
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException;
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException;
	public Emprunt getById(int id) throws DaoException;
	public void create(Membre m,Livre l , LocalDate dateEmprunt) throws DaoException;
	public void update(Emprunt emprunt) throws DaoException;
	public int count() throws DaoException;
}
