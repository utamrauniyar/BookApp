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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query = "INSERT INTO bookdata(BOOKNAME, BOOKEDITION, BOOKPRICE) VALUES(?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		get PrintWriter
		PrintWriter pw = res.getWriter();
//		set content type
		res.setContentType("text/html");

		/* Get the Book Info */
		String bookName = req.getParameter("bookname");
		String bookEdition = req.getParameter("bookedition");
		float bookPrice = Float.parseFloat(req.getParameter("bookprice"));

		/* Load jdbc driver */
		String jdbcDriver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver is not loaded");
			e.printStackTrace();
		}
		/* Establish the connection */
		String dbUrl = "jdbc:mysql:///book";
		String dbUname = "root";
		String dbPass = "root";
		try {
			Connection con = DriverManager.getConnection(dbUrl, dbUname, dbPass);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, bookName);
			ps.setString(2, bookEdition);
			ps.setFloat(3, bookPrice);
			int count = ps.executeUpdate();
			if (count == 1) {
				pw.println("<h2> Record Is Registered Successffully </h2>");
			} else {
				pw.println("<h2> Record Is NOT Registered Successffully </h2>");
			}
		} catch (SQLException se) {
			System.out.println("DB Connection failed!!!");
			se.printStackTrace();
			pw.println("<h2>" + se.getMessage() + "</h2");
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h2>" + e.getMessage() + "</h2");
		}
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList'>BookList</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
