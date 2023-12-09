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
import javax.servlet.http.HttpSession;
@WebServlet("/homeLink")
public class Home extends HttpServlet{
	static Connection con ;
	
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/1ejm10","root","sql123");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		String userId= Login.tempUserData;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		
		String doneVote1 = "Select done_voting from voting where user_email = ?";
		String doneVote2 = "Select done_voting from voting where user_contact = ?";

		PreparedStatement pstmt;
		ResultSet rs;
		HttpSession session = req.getSession(false);


		if (session != null) {
			String data = (String) session.getAttribute("user");
			if (data.contains("@")) {
			

				try {
					pstmt = con.prepareStatement(doneVote1);
					pstmt.setString(1, userId);
					rs = pstmt.executeQuery();

					if (rs.next() && rs.getInt(1) == 0) {
						String update = "update voting set done_voting = 1 where user_email=?";

						PreparedStatement pstmt1;
						session.setMaxInactiveInterval(10);

						pstmt1 = con.prepareStatement(update);
						pstmt1.setString(1, userId);

						int count = pstmt1.executeUpdate();

						if (count > 0) {
							int output = voting(req, resp);

							pw.print("<h1>Voting Done</h1>");
						} else {
							pw.print("<h1>Something went Wrong</h1>");
						}
					} else {
						pw.print("<h1>You have alredy voted</h1>");
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				try {
					pstmt = con.prepareStatement(doneVote1);
					pstmt.setString(1, userId);
					rs = pstmt.executeQuery();

					if (rs.next() && rs.getInt(1) == 0) {
						String update = "update voting set done_voting = 1 where user_email=?";

						PreparedStatement pstmt1;
						session.setMaxInactiveInterval(10);

						pstmt1 = con.prepareStatement(update);
						pstmt1.setString(1, userId);

						int count = pstmt1.executeUpdate();

						if (count > 0) {
							int output = voting(req, resp);

							pw.print("<h1>Voting Done</h1>");
						} else {
							pw.print("<h1>Something went Wrong</h1>");
						}
					} else {
						pw.print("<h1>You have alredy voted</h1>");
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			req.getRequestDispatcher("index.html").include(req, resp);
		}

	}

	private static int voting(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int count = 0;
		String vote = req.getParameter("party");

		String select = "select voting_count from party where party_name = ?";
		String update = " UPDATE party SET voting_count = voting_count + 1 WHERE party_name = ?";

		PreparedStatement pstmt, pstmt1;
		ResultSet rs;
		PrintWriter pw = resp.getWriter();

		try {

			pstmt = con.prepareStatement(select);
			pstmt.setString(1, vote);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pstmt1 = con.prepareStatement(update);
				pstmt1.setString(1, vote);

				count = pstmt1.executeUpdate();
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return count;
	}

	}
