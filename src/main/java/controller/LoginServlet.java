package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.data.Login;
import model.logic.LoginLogic;

/**
 * ログイン処理を担当するサーブレット
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}


	/**
	 * POST メソッドでログイン処理を行う 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字コード設定 
		request.setCharacterEncoding("UTF-8");

		// login.jsp のフォームから値を取得 
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		// ログイン処理
		LoginLogic logic = new LoginLogic();
		Login login = logic.login(userName, password);

		if (login != null) {
			// ログイン成功  
			// セッションを取得し、ログインユーザー情報を保存 
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", login);

			// メイン画面へリダイレクト（再読み込み対策） 
			response.sendRedirect("mutter");
		} else {
			// ログイン失敗 
			// エラーメッセージをリクエストにセット
			
			request.setAttribute("errorMsg", "ユーザー名またはパスワードが違います");
			// login.jsp にフォワード（入力値を保持したまま戻れる） 
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			
			
		}
	}
}
