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
 * つぶやき一覧・検索・投稿を扱うコントローラ
 * 
 * ・未ログイン時はログイン画面へリダイレクト
 * ・GET：一覧表示 / 検索
 * ・POST：新規投稿
 */
@WebServlet("/mutter")
public class MutterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	* GETリクエスト時の処理
	* 投稿一覧の表示、検索結果の表示を担当
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

		// ログイン中のユーザー名を取得 （本人の投稿一覧で使用）
		String loginName = loginUser.getUserName();

		// 検索パラメータを取得 
		String user = request.getParameter("user"); // ユーザー名（部分一致） 
		String keyword = request.getParameter("keyword"); // つぶやき本文（部分一致）

		//DAOを利用してデータを取得
		MutterDAO dao = new MutterDAO();
		List<Mutter> mutterList;

		// 検索条件が入力されているか判定
		boolean hasUser = (user != null && !user.isEmpty());
		boolean hasKeyword = (keyword != null && !keyword.isEmpty());

		if (hasUser || hasKeyword) {
			// 検索時：// NormalizeLogic で検索条件を正規化し、DAO に委譲
			NormalizeLogic logic = new NormalizeLogic();
			mutterList = logic.search(user, keyword);
		} else {
			// 検索条件が空のとき：全件取得 
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

	/**
	 * POSTリクエスト時の処理 
	 * 新規つぶやき投稿を担当
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

		request.setCharacterEncoding("UTF-8");

		// ログイン中のユーザー名を取得
		String userName = loginUser.getUserName();

		// フォームの入力値を取得（つぶやき本文）
		String text = request.getParameter("text");

		// 新規投稿をDBへ登録 
		MutterDAO dao = new MutterDAO();
		Mutter mutter = new Mutter(0, userName, text);
		dao.create(mutter);

		// 投稿後はつぶやき一覧へリダイレクト
		response.sendRedirect("mutter");

	}

}
