# mutter_app

Java（Servlet/JSP）と PostgreSQL を使用して作成した、シンプルなつぶやき投稿アプリです。  
ユーザー登録・ログイン・つぶやき投稿・検索・一覧表示ができます。

---

## 🚀 機能一覧

- **ユーザー登録**
- **ログイン / ログアウト**
- **つぶやき一覧表示**
- **つぶやき投稿**
- **つぶやき検索**
  - ユーザー名で検索（部分一致）
  - つぶやき本文で検索（部分一致）

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
          MutterServlet.java
          RegisterServlet.java
          
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



