package com.excilys.librarymanager.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.IEmpruntDao;
import com.excilys.librarymanager.dao.ILivreDao;
import com.excilys.librarymanager.dao.IMembreDao;
import com.excilys.librarymanager.dao.impl.EmpruntDao;
import com.excilys.librarymanager.dao.impl.*;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanger.service.IEmpruntService;

public class EmpruntService implements IEmpruntService {
	private static EmpruntService instance;
	
	private EmpruntService() { }	
	public static EmpruntService getInstance() {
		if(instance == null) {
			instance = new EmpruntService();
		}
		return instance;
	}
	@Override
	public List<Emprunt> getList() throws ServiceException{
		IEmpruntDao empruntDao = EmpruntDao.getInstance();
		List<Emprunt> liste_emprunt = new ArrayList<>();
		
			
			try {
				liste_emprunt = empruntDao.getList();
			} catch (DaoException e1) {
				System.out.println(e1.getMessage());			
			}
			return liste_emprunt;
		}
	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		IEmpruntDao empruntDao = EmpruntDao.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();
		try {
			emprunts = empruntDao.getListCurrent();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}
	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		IEmpruntDao empruntDao = EmpruntDao.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();
		try {
			emprunts = empruntDao.getListCurrentByMembre(idMembre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}
	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		IEmpruntDao empruntDao = EmpruntDao.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();
		try {
			emprunts = empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}
	
	@Override
	
	public Emprunt getById(int id) throws ServiceException{
		IEmpruntDao empruntDao = EmpruntDao.getInstance();
		Emprunt emprunt = new Emprunt();
		try {
			emprunt = empruntDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunt;
	}
	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{
		
		IMembreDao membreDao = MembreDao.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(idMembre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		ILivreDao livreDao = LivreDao.getInstance();
		Livre livre = new Livre();
		try {
			livre = livreDao.getById(idLivre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		IEmpruntDao empruntDao = EmpruntDao.getInstance();
		try {
			empruntDao.create(membre,livre,dateEmprunt);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
	}
	@Override
	public void returnBook(int id) throws ServiceException{
			IEmpruntDao empruntDao = EmpruntDao.getInstance();
			
			
			try {Emprunt emprunt=empruntDao.getById(id);
				emprunt.setDateRetour(LocalDate.now());
				empruntDao.update(emprunt);
			} 
			catch (DaoException e1) {
				System.out.println(e1.getMessage());			
			}

	}
	@Override
	public int count() throws ServiceException {
		IEmpruntDao empruntDao = EmpruntDao.getInstance();
		int i = -1;
		try {
			i = empruntDao.count();
		}catch (DaoException e2) {
			System.out.println(e2.getMessage());			
		} 
		return i;
	}
	
	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		List<Emprunt> liste= getListCurrentByLivre(idLivre);
		return (liste==null);
	}

	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		List<Emprunt> liste= getListCurrentByMembre(membre.getId());
		return (liste.size()<membre.getEnumAbonnement().quota);
	}
		
	
	
}
