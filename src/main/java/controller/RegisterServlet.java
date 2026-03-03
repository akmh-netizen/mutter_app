package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.LoginDAO;

/**
 * ログインユーザー登録処理を担当するサーブレット
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // 文字コード設定（日本語対応） 
		request.setCharacterEncoding("UTF-8");

		// register.jsp から送られた値を取得 
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		// 登録処理
		LoginDAO dao = new LoginDAO();
		boolean result = dao.insertUser(userName, password);

		if (result) {
			// 登録成功 	
			HttpSession session = request.getSession(); session.setAttribute("registerMsg", "ユーザー登録が完了しました");
			// ログイン画面へリダイレクト 
			response.sendRedirect("login");
		} else {
			// 登録失敗 
			// エラーメッセージをセット 
			request.setAttribute("errorMsg", "登録に失敗しました。ユーザー名の形式や重複を確認してください。");
			// register.jsp に戻す（入力値を保持したまま） 
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
			dispatcher.forward(request, response);

		}

	}

}
