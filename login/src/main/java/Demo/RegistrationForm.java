package Demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/registration")
public class RegistrationForm extends HttpServlet {
static Connection connection;
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/1ejm10","root","sql123");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name=req.getParameter("userName");
		String email=req.getParameter("userEmail");
		String contactNo=req.getParameter("userContact");
		String gender=req.getParameter("gender");
		String state=req.getParameter("state");
		String idProof=req.getParameter("idProof");
		String idNo=req.getParameter("idNo");
		String password1=req.getParameter("pass");
		
		String insertData="INSERT INTO voting VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PrintWriter pw =resp.getWriter();
		PreparedStatement pstmt;
		
		try {
			pstmt= connection.prepareStatement(insertData);
			pstmt.setInt(1,0);
			pstmt.setString(2,name);
			pstmt.setString(3,email);
			pstmt.setString(4,contactNo);
			pstmt.setString(5,gender);
			pstmt.setString(6,state);
			pstmt.setString(7,idProof);
			pstmt.setString(8,idNo);
			pstmt.setString(9,password1);
			pstmt.setInt(10,0);
			
			
			int count=pstmt.executeUpdate();
			
			if(count>0) {
//				pw.print("<h1>Registration successfully Completed</h1>");
//				pw.print("<a href='index.html'>login</a>");
				RequestDispatcher rd = req.getRequestDispatcher("index.html");
				pw.print("<h1>Registration successfully Completed</h1>");
				rd.include(req, resp);
			}else {
				pw.print("<h1>Registration unsuccessfull</h1>");
//				pw.print("<a href='registration.html'>Registration</a>");
				RequestDispatcher rd = req.getRequestDispatcher("registration.html");
				rd.include(req, resp);
			}

			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	

}
