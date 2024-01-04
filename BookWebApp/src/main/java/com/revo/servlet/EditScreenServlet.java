package com.revo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {

	private static final String query = "SELECT bookname, bookedition, bookprice FROM bookdata WHERE id=?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		resp.setContentType("text/html");
		/* get the id of record */
		int id = Integer.parseInt(req.getParameter("id"));

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String dbUrl = "jdbc:mysql:///book";
		String dbUname = "root";
		String dbPass = "root";

		try {
			Connection con = DriverManager.getConnection(dbUrl, dbUname, dbPass);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			pw.println("<form action='editUrl?id=" + id + "' method='post'>");
			pw.println("<table align='center'>");
			pw.println("<tr>");
			pw.println("<td>Book Name</td>");
			pw.println("<td><input type='text' name='bookName' value='" + rs.getString(1) + "'></td>");
			pw.println("</tr>");

			pw.println("<tr>");
			pw.println("<td>Book Edition</td>");
			pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString(2) + "'></td>");
			pw.println("</tr>");

			pw.println("<tr>");
			pw.println("<td>Book Price</td>");
			pw.println("<td><input type='text' name='bookPrice' value='" + rs.getFloat(3) + "'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><input type='submit' value='Edit'></td>");
			pw.println("<td><input type='reset' value='cancel'></td>");
			pw.println("</tr>");
			pw.println("</table>");
			pw.println("</form>");
		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h2>");
		}
		pw.println("<a href='home.html'>Home</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
