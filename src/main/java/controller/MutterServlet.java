package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.MutterDAO;
import model.data.Login;
import model.data.Mutter;
import model.logic.NormalizeLogic;

/**
 * つぶやき画面のコントローラ
 */
@WebServlet("/mutter")
public class MutterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	* GETリクエスト時の処理
	*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログインチェック
		HttpSession session = request.getSession(false);
		Login loginUser = (session != null) ? (Login) session.getAttribute("loginUser") : null;

		if (loginUser == null) {
			response.sendRedirect("login");
			return;
		}
		
		// ログイン中のユーザー名（本人一覧で使用） 
		String loginName = loginUser.getUserName();

		// 検索パラメータを取得 
		String user = request.getParameter("user"); // ユーザー名（完全一致） 
		String keyword = request.getParameter("keyword"); // 部分一致キーワード

		//DAOを利用してデータを取得
		MutterDAO dao = new MutterDAO();
		List<Mutter> mutterList;

		/* 
		 * 検索条件が入力されているか判定 
		 * user または keyword のどちらかが null でなく、かつ空文字でない場合 
		 * → 検索モードに切り替える 
		 */
		boolean hasUser = (user != null && !user.isEmpty());
		boolean hasKeyword = (keyword != null && !keyword.isEmpty());

		if (hasUser || hasKeyword) {
			// 検索時：ユーザー名 OR 前方一致で検索 
			NormalizeLogic logic = new NormalizeLogic();
			mutterList = logic.search(user, keyword);
		} else {
			// 検索条件が空：全件取得 
			mutterList = dao.findAll();
		}
		
		// 本人の投稿一覧を取得（削除・編集リンク用） 
		List<Mutter> myList = dao.findByLoginUser(loginName);

		// JSP に渡す 
		request.setAttribute("mutterList", mutterList);
		request.setAttribute("myList", myList);
		
		// 画面表示 
		request.getRequestDispatcher("/WEB-INF/jsp/mutter.jsp").forward(request, response);

	}

	/*
	 * POSTリクエスト時の処理 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログインチェック
		HttpSession session = request.getSession(false);
		Login loginUser = (session != null) ? (Login) session.getAttribute("loginUser") : null;

		if (loginUser == null) {
			response.sendRedirect("login");
			return;
		}

		// 文字化け防止（POST時は必須） 
		request.setCharacterEncoding("UTF-8");
		
		// ログイン中のユーザー名を取得
		String userName = loginUser.getUserName();
		// フォームの入力値を取得
		String text = request.getParameter("text");

			// DAOを利用してDBへ登録 
			MutterDAO dao = new MutterDAO();
			Mutter mutter = new Mutter(0, userName, text);
			dao.create(mutter);

		response.sendRedirect("mutter");

	}

}
