package model.data;

/**
 * ログインユーザー情報を保持するエンティティクラス
 */
public class Login {

	private int loginId; //ログイン番号 
	private String userName; //ユーザ名
	private String password; //パスワード
	
	/*
	 * 引数なしコンストラクタ
	 */
	public Login() {
		super();
	}
	
	/*
	 * 全フィールドを受け取るコンストラクタ
	 */
	public Login(int loginId, String userName, String password) {
		super();
		this.loginId = loginId;
		this.userName = userName;
		this.password = password;
	}

	/*
	 * アクセサ（getter/setter）
	 */
	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
}
