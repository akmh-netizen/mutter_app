<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<a href="logout">ログアウト</a>
<br>
<meta charset="UTF-8">
<title>つぶやきアプリ</title>

<%
String welcomeMsg = (String) session.getAttribute("welcomeMsg");
if (welcomeMsg != null) {
%>
<script> 
alert("<%=welcomeMsg.replace("\n", "\\n")%>");
</script>
<%
session.removeAttribute("welcomeMsg"); // 1回だけ表示
}
%>

<%
String editMsg = (String) session.getAttribute("editMsg");
if (editMsg != null) {
%>
<script>
    alert("<%=editMsg%>");
</script>
<%
session.removeAttribute("editMsg"); // 1回だけ表示
}
%>

<body>

	<h1>つぶやきアプリ</h1>

	<!-- 投稿フォーム -->
	<div class="form-area">
		<h2>新規投稿</h2>
		<form action="mutter" method="post">
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
				<span class="username">${m.userName}</span>： <span>${m.text}</span>
			</div>
		</c:forEach>

		<!-- 投稿が0件の場合のメッセージ -->
		<c:if test="${empty mutterList}">
			<p>まだ投稿がありません。</p>
		</c:if>
	</div>

	<!-- ログインユーザーの投稿一覧 -->
	<div class="list-area">
		<h2>あなたの投稿</h2>

		<!-- ログインユーザー本人の投稿だけを表示 -->
		<c:forEach var="m" items="${myList}">
			<div class="mutter-item">
				<span>${m.text}</span>

				<!-- 編集リンク（mode=edit は後で実装） -->
				<a href="edit?id=${m.id}">編集</a>

				<!-- 削除リンク（本人だけ削除可能） -->
				<a href="delete?id=${m.id}" onclick="return confirm('本当に削除しますか？');">
					削除 </a>

			</div>
		</c:forEach>

		<!-- 投稿が0件の場合 -->
		<c:if test="${empty myList}">
			<p>あなたの投稿はまだありません。</p>
		</c:if>
	</div>


</body>
</html>
