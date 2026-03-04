package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.LoginDAO;

/**
 * ユーザー登録処理を担当するサーブレット
 *
 * ・GET：ユーザー登録画面の表示
 * ・POST：新規ユーザー登録処理
 * ・登録成功時はログイン画面へリダイレクト
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** 
	 * ユーザー登録画面の表示（GET）
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);

	}

	/** 
	 * 新規ユーザー登録処理（POST） 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// register.jsp のフォームから値を取得 
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		// ユーザー登録処理
		LoginDAO dao = new LoginDAO();
		boolean result = dao.insertUser(userName, password);

		// 登録成功：
		if (result) {

			// セッションに登録完了メッセージを保存
			HttpSession session = request.getSession();
			session.setAttribute("registerMsg", "ユーザー登録が完了しました");

			// ログイン画面へリダイレクト 
			response.sendRedirect("login");

			// 登録失敗
		} else {

			// エラーメッセージをセット 
			request.setAttribute("errorMsg", "登録に失敗しました。ユーザー名の形式や重複を確認してください。");

			// ユーザー登録画面へフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);

		}

	}

}
