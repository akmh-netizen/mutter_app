package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログアウト処理を担当するサーブレット
 *
 * ・セッションを破棄してログアウト状態にする
 * ・ログアウト完了メッセージを表示し、ログイン画面へ戻す
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** 
	 * GET リクエストでログアウト処理を実行 
	 */
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
		
		// ログイン画面にフォワード 
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

}
