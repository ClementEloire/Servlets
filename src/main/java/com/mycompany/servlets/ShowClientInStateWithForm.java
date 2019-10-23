/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author pedago
 */
@WebServlet(name = "ShowClientInStateWithForm", urlPatterns = {"/ShowClientInStateWithForm"})
public class ShowClientInStateWithForm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowClientInStateWithForm</title>");
            out.println("<style>");
            out.println("table, th, td {\n" +"  border: 1px solid black;\n" +"}");
            out.println("</style>");            
            out.println("</head>");
            out.println("<body>");
            try {
                out.println("<form action=\"ShowClientInStateWithForm\" id=\"state\">");
                out.println("<select name=\"state\" form=\"state\">");
                DAO dao = new DAO(DataSourceFactory.getDataSource());
                List <String> State = dao.listState();
                    if (State == null) {
                        throw new Exception("State inconnu");
                    }

                for(int i = 0; i<State.size(); i++){
                    out.printf("<option value=\""+ State.get(i) +"\"> "+ State.get(i) + "</option>");
                }

                out.println(" </select>");
                out.println( "<input type=\"submit\">");
                out.println(" </form>");

            } catch (Exception e) {
				out.printf("Erreur : %s", e.getMessage());
			}
            
            try {	// Trouver la valeur du paramètre HTTP customerID
				String val = request.getParameter("state");
				if (val == null) {
					throw new Exception("La paramètre State n'a pas été transmis");
				}
				// on doit convertir cette valeur en entier (attention aux exceptions !)
				String state = val;

				DAO dao = new DAO(DataSourceFactory.getDataSource());
				List <CustomerEntity> customer = dao.customersInState(state);
				if (customer == null) {
					throw new Exception("Client inconnu");
				}
                                out.println("<table style=\"width:100%\">");
                                out.println( "<tr> ");
                                out.println("<th>ID</th>" +"    <th>NOM</th>" +"    <th>ADRESSE</th><br/>");   
                                out.println( "</tr> ");
				// Afficher les propriétés du client	
                                for(int i = 0; i<customer.size(); i++){
                                    out.println( "<tr> ");
                                    out.printf(" <td>%s</td>" +"    <td>%s</td>" +"    <td>%s</td>",
					customer.get(i).getCustomerId(),
					customer.get(i).getName(),
					customer.get(i).getAddressLine1());
                                    out.println( "</tr> ");
                                }
                                out.println("</table>");
			} catch (Exception e) {
				out.printf("Erreur : %s", e.getMessage());
			}
            
            out.printf("<hr><a href='%s'>Retour au menu</a>", request.getContextPath());
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
