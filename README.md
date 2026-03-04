# mutter_app

Java（Servlet/JSP）とPostgreSQL を使用して作成した、シンプルなつぶやき投稿アプリです。  
※ アプリの画面イメージは GitHub の Issue に掲載しています。


---

## 🚀 機能一覧

- **ユーザー登録**  
  新規ユーザーを作成し、登録完了ダイアログを表示。

- **ログイン / ログアウト**  
  セッションでログイン状態を管理。  
  **未ログイン時に `/mutter` へアクセスするとログイン画面へリダイレクト。**

- **つぶやき投稿**  
  ログインユーザーのみ投稿可能。  
  投稿者名はセッションから自動取得。

- **つぶやき一覧表示**  
  全ユーザーの投稿を新しい順に表示。  
  **ログインユーザー本人の投稿だけをまとめて表示（編集・削除リンク付き）。**

- **つぶやき検索**  
  - ユーザー名（部分一致）  
  - つぶやき本文（部分一致）

- **つぶやき編集**  
  ログインユーザー本人の投稿のみ編集可能。  
  編集完了後にダイアログを表示。

- **つぶやき削除**  
  ログインユーザー本人の投稿のみ削除可能。  
  削除後は `/mutter` にリダイレクト。

---

## 🛠 使用技術

- **Java 25**
- **Apache Tomcat 11**
- **PostgreSQL**
- **JSP / Servlet**
- **JDBC（PostgreSQL ドライバ）**

---

## 📂 ディレクトリ構成

    mutter_app/
      src/
        controller/
          LoginServlet.java
          LogoutServlet.java
          MutterServlet.java
          RegisterServlet.java
          DeleteMutterServlet.java
          EditMutterServlet.java
          
        model/
          dao/
            LoginDAO.java
            MutterDAO.java
          data/
            Login.java
            Mutter.java
          logic/
            LoginLogic.java
            NormalizeLogic.java
          util/
            DButil.java
            
      webapp/
        WEB-INF/
          jsp/
            login.jsp
            mutter.jsp
            register.jsp
            edit.jsp
            


---

## 🗄 データベース構造

### login テーブル

| カラム名   | 型               | 説明 |
|-----------|------------------|------|
| loginid   | integer (PK)     | ユーザーID |
| username  | varchar          | ユーザー名 |
| password  | varchar          | パスワード |

---

### mutters テーブル

| カラム名   | 型               | 説明 |
|-----------|------------------|------|
| id        | integer (PK)     | 投稿ID |
| username  | varchar          | 投稿者名 |
| text      | varchar          | つぶやき本文 |

---



