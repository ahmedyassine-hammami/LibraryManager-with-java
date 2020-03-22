package com.excilys.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.impl.MembreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanger.service.IMembreService;

public class MembreService implements IMembreService {
	private static MembreService instance = new MembreService();
	private MembreService() { }	
	public static MembreService getInstance() {		
		return instance;
	}

	@Override
	public List<Membre> getList() throws ServiceException {
		MembreDao membreDao = MembreDao.getInstance();
		List<Membre> membres = new ArrayList<>();
		try {
			membres = membreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return membres;
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		MembreDao membreDao = MembreDao.getInstance();
		List<Membre> membres = new ArrayList<>();
		try {
			membres = membreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		EmpruntService esi=EmpruntService.getInstance();
		for (int i=0;i<membres.size();i++) {
			if (!esi.isEmpruntPossible(membres.get(i)))
				membres.remove(i);
		}
		return membres;
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		MembreDao membreDao = MembreDao.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return membre;
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone,Abonnement abonnement)
			throws ServiceException {
		if ((nom==null)||(prenom==null)) {
			throw new ServiceException("Il faut indiquer nom et prenom");
		}
		else {
		MembreDao membreDao = MembreDao.getInstance();
		int i = -1;
		try {
			i = membreDao.create(nom.toUpperCase(),prenom,adresse,email,telephone,abonnement.name());
		}catch (DaoException e2) {
			System.out.println(e2.getMessage());			
		} 
		return i;
		}
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		if ((membre.getNom()==null)||(membre.getPrenom()==null)) {
			throw new ServiceException("Il faut indiquer nom et prenom");
		}
		
		else {
			membre.setNom(membre.getNom().toUpperCase());
			MembreDao membreDao = MembreDao.getInstance();
			try {
				membreDao.update(membre);
			} catch (DaoException e1) {
				System.out.println(e1.getMessage());			
			}
		}
		
	}

	@Override
	public void delete(int id) throws ServiceException {
		MembreDao membreDao = MembreDao.getInstance();
		try {
			membreDao.delete(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
	}

	@Override
	public int count() throws ServiceException {
		MembreDao membreDao = MembreDao.getInstance();
		int i = -1;
		try {
			i = membreDao.count();
		}catch (DaoException e2) {
			System.out.println(e2.getMessage());			
		} 
		return i;
	}

}
