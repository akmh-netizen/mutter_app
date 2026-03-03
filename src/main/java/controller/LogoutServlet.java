package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションを取得 
		HttpSession session = request.getSession(false);
		// セッションがあれば破棄 
		if (session != null) {
			session.invalidate();
		}

		// ログアウトメッセージをセット 
		request.setAttribute("logoutMsg", "ログアウトしました"); 
		// login.jsp にフォワード 
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

}
