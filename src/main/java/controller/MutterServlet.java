package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.dao.MutterDAO;
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

		// 検索パラメータを取得 
		String user = request.getParameter("user"); // ユーザー名（完全一致） 
		String keyword = request.getParameter("keyword"); // 前方一致キーワード

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

		// JSP に渡す 
		request.setAttribute("mutterList", mutterList);
		// 画面表示 
		request.getRequestDispatcher("/WEB-INF/jsp/mutter.jsp").forward(request, response);

	}

	/*
	 * POSTリクエスト時の処理 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字化け防止（POST時は必須） 
		request.setCharacterEncoding("UTF-8");

		// フォームの入力値を取得 
		String userName = request.getParameter("username");
		String text = request.getParameter("text");

		// 入力チェック（textが空の場合は登録しない） 
		if (userName != null && text != null && !text.isEmpty()) {

			// DAOを利用してDBへ登録 
			MutterDAO dao = new MutterDAO();
			Mutter mutter = new Mutter(0, userName, text);
			dao.create(mutter);
		}

		response.sendRedirect("mutter");

	}

}
