# mutter_app

Java（Servlet/JSP）とPostgreSQL を使用して作成した、シンプルなつぶやき投稿アプリです。  
※ アプリの画面イメージは GitHub の Issue に掲載しています。


---

## 🚀 機能一覧

### 📘 授業で実装した機能
- **ログイン / ログアウト**
  - セッションでログイン状態を管理

- **つぶやき投稿**
  - ユーザー名を都度入力し投稿

- **つぶやき一覧表示**
  - 全ユーザーの投稿を新しい順に表示


### 🛠 追加・変更した機能

- **ログイン / ログアウト**
  - セッションでログイン状態を管理
  - 未ログイン時に `/mutter` へアクセスするとログイン画面へリダイレクト
  
- **つぶやき検索機能**
  - ユーザー名（部分一致）
  - つぶやき本文（部分一致）
  - 全角・半角を正規化して検索

  - **つぶやき投稿**
  - ログインユーザー名で投稿
  - 投稿者名はセッションから自動取得

- **つぶやき編集機能**
  - ログインユーザー本人の投稿のみ編集可能
  - 編集完了後にダイアログを表示

- **つぶやき削除機能**
  - ログインユーザー本人の投稿のみ削除可能
  - 削除後は `/mutter` にリダイレクト

- **ログインユーザーの投稿一覧表示**
  - ログインユーザー本人の投稿だけをまとめて表示
  - 編集・削除リンクを表示

---

## 🛠 使用技術

- **Java**
- **PostgreSQL**
- **JSP / Servlet**

---

## 💻 開発環境

- **Pleiades**
- **Apache Tomcat**
  - Eclipse（Pleiades）

---

## 📂 ディレクトリ構成

    mutter_app/
      src/
        controller/                # サーブレット
          LoginServlet.java        # ログイン処理（認証・セッション保存）
          LogoutServlet.java       # ログアウト処理（セッション破棄）
          MutterServlet.java       # つぶやき一覧表示・検索・投稿処理
          RegisterServlet.java     # 新規ユーザー登録処理
          DeleteMutterServlet.java # つぶやき削除処理
          EditMutterServlet.java   # つぶやき編集処理
    
        model/
          dao/                     # DAO
            LoginDAO.java          # ログイン認証・ユーザー登録
            MutterDAO.java         # つぶやきの取得・検索・投稿・更新・削除
    
          data/                    # DTO
            Login.java             # ログインユーザー情報（loginid, username, password）
            Mutter.java            # つぶやき情報（id, username, text）
    
          logic/                   # ビジネスロジック層
            LoginLogic.java        # ログイン認証処理
            NormalizeLogic.java    # 検索用の文字列正規化（全角→半角など）
    
          util/                    # 共通処理
            DButil.java            # DB接続管理（Connection取得）
    
      webapp/
        WEB-INF/
          jsp/                     # 画面（View）
            login.jsp              # ログイン画面
            mutter.jsp             # つぶやき一覧・検索・投稿画面
            register.jsp           # 新規ユーザー登録画面
            edit.jsp               # 投稿編集画面

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



