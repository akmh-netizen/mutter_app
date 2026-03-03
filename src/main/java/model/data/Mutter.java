package model.data;

import java.io.Serializable;

/**
 * muttersテーブルの1レコードを表すEntityクラス。
 */
public class Mutter implements Serializable {
	
	/* 
	 * フィールド
	 */
	private int id; // 主キー（SERIAL）
	private String userName; // ユーザー名（VARCHAR(20)）
	private String text; // つぶやき本文（VARCHAR(255)）

	/*
	 * 引数なしコンストラクタ
	 */
	public Mutter() {
		super();
	}

	/*
	 * 全フィールドを受け取るコンストラクタ
	 */
	public Mutter(int id, String userName, String text) {
		super();
		this.id = id;
		this.userName = userName;
		this.text = text;
	}

	/*
	 * アクセサ（getter/setter）
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
