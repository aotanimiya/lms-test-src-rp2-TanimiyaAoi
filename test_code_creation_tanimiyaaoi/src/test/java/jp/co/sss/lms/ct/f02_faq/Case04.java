package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		getEvidence(new Object() {});
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

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 「機能」と表示のドロップダウンを押下
		webDriver.findElement(By.className("dropdown")).click();
		// ドロップダウン内の「ヘルプ」を押下
		webDriver.findElement(By.linkText("ヘルプ")).click();
		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		getEvidence(new Object() {});
		
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// よくある質問リンクを押下する
		webDriver.findElement(By.linkText("よくある質問")).click();
		// 新しいタブに操作権限を移す
		for (String handle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(handle);
		}
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		getEvidence(new Object() {});
	}

}
