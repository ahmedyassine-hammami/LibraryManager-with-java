package com.excilys.librarymanager.test;

import java.util.List;

import com.excilys.librarymanager.dao.ILivreDao;
import com.excilys.librarymanager.dao.impl.EmpruntDao;
import com.excilys.librarymanager.dao.impl.LivreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;

public class main {

	public static void main(String[] args) {
		EmpruntDao a = EmpruntDao.getInstance();
		ILivreDao livre = LivreDao.getInstance();
		try {
			List<Emprunt> l = a.getListCurrentByLivre(1);
			System.out.println();
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}

	}

}
