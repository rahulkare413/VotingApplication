package Demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/loginForm")
public class Login extends HttpServlet {
	static Connection con;
	static String tempUserData ;

	@Override
	public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/1ejm10","root","sql123");
            } catch ( SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId=req.getParameter("userId");
		String password1=req.getParameter("userPass");
				
		String query1 =" select * from voting where user_email = ? and user_pass = ?";
		String query2 =" select * from voting where user_contact = ? and user_pass = ?";
		
		PreparedStatement pstmt;
		ResultSet rs;
		PrintWriter pw=resp.getWriter();
		
		pw.print("<h1></h1>");
		
		if (userId.contains("@")) {
			try {
				pstmt = con.prepareStatement(query1);
				pstmt.setString(1, userId);
				pstmt.setString(2, password1);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(password1.equals(rs.getString(9))) {
						tempUserData=userId;
						pw.print("<h3>Login Successfully</h3>");
						resp.sendRedirect("home.html");
					}else {
						pw.print("<h3>INVALID Login</h3>");
					}
				}else {
					
					resp.sendRedirect("index.html");
					pw.print("<h3>INVALID Email or password</h3>");
					
					
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}else {
			try {
				pstmt = con.prepareStatement(query2);
				pstmt.setString(1, userId);
				pstmt.setString(2, password1);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(password1.equals(rs.getString(9))) {
						pw.print("<h3>Login Successfully</h3>");
					}else {
						pw.print("<h3>INVALID Login</h3>");
					}
				}else {
					pw.print("<h3>INVALID contact or password</h3>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				 resp.sendRedirect("index.html"); 
			}
		}
	}
}
		
		
	


