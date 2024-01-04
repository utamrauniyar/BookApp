package com.revo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
	private static final String query = "SELECT * FROM bookdata";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");

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
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			pw.println("<table border= '2' align='center'>");
			pw.println("<tr>");
			pw.println("<th>Book ID</th>");
			pw.println("<th>Book NAME</th>");
			pw.println("<th>Book EDITION</th>");
			pw.println("<th>Book PRICE</th>");
			pw.println("<th>EDIT</th>");
			pw.println("<th>DELETE</th>");

			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getString(2) + "</td>");
				pw.println("<td>" + rs.getString(3) + "</td>");
				pw.println("<td>" + rs.getFloat(4) + "</td>");
				pw.println("<td><a href='editScreen?id=" + rs.getInt(1) + "'>Edit</a></td>");
				pw.println("<td><a href='deleteUrl?id=" + rs.getInt(1) + "'>Delete</a></td>");
				pw.println("<tr>");
			}
			pw.println("</table>");
		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h2>");
		}
		pw.println("<a href='home.html'>Home</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
