<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>つぶやきアプリ</title>
<body>

	<h1>つぶやきアプリ</h1>

	<!-- 投稿フォーム -->
	<div class="form-area">
		<h2>新規投稿</h2>
		<form action="mutter" method="post">
			<p>
				ユーザー名：<br> <input type="text" name="username" required>
			</p>
			<p>
				つぶやき：<br>
				<textarea name="text" rows="3" cols="40" required></textarea>
			</p>
			<p>
				<button type="submit">投稿する</button>
			</p>
		</form>
	</div>

	<!-- 検索フォーム -->
	<div class="form-area">
		<h2>検索</h2>

		<form action="mutter" method="get">
			<p>
				ユーザー名（部分一致）：<br> <input type="text" name="user">
			</p>
			<p>
				つぶやき（部分一致）：<br> <input type="text" name="keyword">
			</p>
			<p>
				<button type="submit">検索する</button>
			</p>
		</form>
	</div>

	<!-- つぶやき一覧表示 -->
	<div class="list-area">
		<h2>投稿一覧</h2>
		<c:forEach var="m" items="${mutterList}">
			<div class="mutter-item">
				<span class="username">${m.userName}</span>： <span>${m.text}</span><br>
			</div>
		</c:forEach>

		<!-- 投稿が0件の場合のメッセージ -->
		<c:if test="${empty mutterList}">
			<p>まだ投稿がありません。</p>
		</c:if>
	</div>


</body>
</html>
