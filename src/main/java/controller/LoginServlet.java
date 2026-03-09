package controller;

import java.io.IOException;

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
 *
 * ・GET：ログイン画面の表示
 * ・POST：ログイン認証処理
 * ・認証成功時はセッションにユーザー情報を保存し、/mutter へ遷移
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** 
	 * ログイン画面の表示（GET） 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログイン画面へフォワード
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);

	}

	/** 
	 * ログイン認証処理（POST） 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// login.jsp のフォームから値を取得 
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		// ログイン処理
		LoginLogic logic = new LoginLogic();
		Login login = logic.login(userName, password);

		if (login != null) {
			// ログイン成功  :
			// セッションを取得し、ログインユーザー情報を保存 
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", login);

			// ログイン成功メッセージをセット 
			String msg = login.getUserName() + " さん\nようこそ、つぶやきアプリへ";
			session.setAttribute("welcomeMsg", msg);

			//ログイン後はつぶやき一覧へリダイレクト(フォワードだと投稿一覧が出てこないため)
			response.sendRedirect("mutter");
			return;

		} else {
			// ログイン失敗 ：
			// エラーメッセージをリクエストにセット
			request.setAttribute("errorMsg", "ユーザー名またはパスワードが違います");
			
			// ログイン画面へフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);

		}
		
	}
	
}
