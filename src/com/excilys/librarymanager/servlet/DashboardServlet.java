package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.service.impl.MembreService;
import com.excilys.librarymanger.service.IEmpruntService;
import com.excilys.librarymanger.service.ILivreService;
import com.excilys.librarymanger.service.IMembreService;


public class DashboardServlet {
	
	@SuppressWarnings("unused")
	private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IEmpruntService empruntService = EmpruntService.getInstance();
		ILivreService livreService = LivreService.getInstance();
		IMembreService membreService = MembreService.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();
		int nbr_livre = 0 ;
		int nbr_membre = 0 ;
		int nbr_emprunt = 0 ;
		try {
			emprunts = empruntService.getListCurrent();
			nbr_livre=livreService.count();
			nbr_membre=membreService.count();
			nbr_emprunt=empruntService.count();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		request.setAttribute("nbr_membre",nbr_membre);
		request.setAttribute("nbr_livre",nbr_livre);
		request.setAttribute("nbr_emprunt",nbr_emprunt);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp");
		dispatcher.forward(request, response);
	}

}
