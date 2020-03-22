package com.excilys.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.ILivreDao;
import com.excilys.librarymanager.dao.impl.LivreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanger.service.ILivreService;

public class LivreService implements ILivreService {
	private static LivreService instance = new LivreService();
	private LivreService() { }	
	public static LivreService getInstance() {		
		return instance;
	}
	@Override
	public List<Livre> getList() throws ServiceException {
		LivreDao livreDao = LivreDao.getInstance();
		List<Livre> livre = new ArrayList<>();
		try {
			livre = livreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return livre;
	}
	@Override
	public List<Livre> getListDispo() throws ServiceException {
		LivreDao livreDao = LivreDao.getInstance();
		List<Livre> livre = new ArrayList<>();
		try {
			livre = livreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		EmpruntService esi=EmpruntService.getInstance();
		for (int i=0;i<livre.size();i++) {
			if (!esi.isLivreDispo(livre.get(i).getId()))
				livre.remove(i);
		}
		return livre;
	}
	@Override
	public Livre getById(int id) throws ServiceException {
		LivreDao livreDao = LivreDao.getInstance();
		Livre livre = new Livre();
		try {
			livre = livreDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return livre;
	}
	@Override
	public int create(String titre, String auteur, String isbn) throws ServiceException {
		if (titre==null) {
			throw new ServiceException("Il faut indiquer un titre");
		}
		else {
		LivreDao livreDao = LivreDao.getInstance();
		int i = -1;
		try {
			i = livreDao.create(titre,auteur,isbn);
		}catch (DaoException e2) {
			System.out.println(e2.getMessage());			
		} 
		return i;
		}
		
	}
	@Override
	public void update(Livre livre) throws ServiceException {
		if (livre.getTitre()==null) {
			throw new ServiceException("Il faut indiquer un titre");
		}
		
		else {
			LivreDao livreDao = LivreDao.getInstance();
			try {
				livreDao.update(livre);
			} catch (DaoException e1) {
				System.out.println(e1.getMessage());			
			}
		}

	}
	@Override
	public void delete(int id) throws ServiceException {
		LivreDao livreDao = LivreDao.getInstance();
		try {
			livreDao.delete(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
	}
	@Override
	public int count() throws ServiceException {
		LivreDao livreDao = LivreDao.getInstance();
		int i = -1;
		try {
			i = livreDao.count();
		}catch (DaoException e2) {
			System.out.println(e2.getMessage());			
		} 
		return i;
	}
}
