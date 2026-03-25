package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// ログイン画面を開く
		goTo("http://localhost:8080/lms");
		// 検証
		assertEquals("ログイン | LMS",webDriver.getTitle());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//IDを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		// パスワードを入力
		webDriver.findElement(By.id("password")).sendKeys("StudentAA0101");
		// ログイン前の画面を撮影
		getEvidence(new Object() {} ,"01");
		// ログインボタンを押下
		webDriver.findElement(By.className("btn-primary")).click();
		
		// 現在のURLを取得して、詳細画面のURLと一致するか検証
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());
		// 詳細画面が開いているかの検証
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		// 詳細画面の撮影
		getEvidence(new Object() {}, "02");
	}

}
