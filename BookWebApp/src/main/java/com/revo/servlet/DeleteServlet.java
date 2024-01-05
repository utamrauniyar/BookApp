package com.revo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteUrl")
public class DeleteServlet extends HttpServlet {
	private static final String query = "DELETE FROM bookdata WHERE id=?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		resp.setContentType("text/html");
		/* get the id of record */
		int id = Integer.parseInt(req.getParameter("id"));

		/* register driver */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String dbUrl = "jdbc:mysql:///book";
		String dbUname = "root";
		String dbPass = "root";
		/* generate connection */
		try {
			Connection con = DriverManager.getConnection(dbUrl, dbUname, dbPass);
			PreparedStatement ps = con.prepareStatement(query);

			ps.setInt(1, id);

			int count = ps.executeUpdate();
			if (count == 1) {
				pw.println("<h2>Record is deleted successfully</h2>");
			} else {
				pw.println("<h2>Record is not deleted successfully</h2>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h2>");
		}
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList'>Book List</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
