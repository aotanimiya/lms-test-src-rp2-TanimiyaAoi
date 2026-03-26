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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// キーワードフォームに「助成金」と入力
		webDriver.findElement(By.id("form")).sendKeys("助成金");
		getEvidence(new Object() {}, "01");
		//検索ボタンを押下
		webDriver.findElement(By.cssSelector("input[value='検索']")).click();
		
		// 検索結果が表示されるまで最大10秒待機する
		visibilityTimeout(By.className("table"), 10);
		
		// よくある質問画面が正常に表示されているか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		//検索結果に「助成金」を含むデータが【ある】ことを検証
		assertTrue(webDriver.findElement(By.className("table")).getText().contains("助成金"));
		//検索結果に「助成金」を含まないデータが【ない】ことを検証
		assertTrue(webDriver.findElements(By.xpath("//tbody/tr[not(contains(., '助成金'))]")).isEmpty());
		//　エビデンスに検索結果が移るように画面をスクロール
		scrollTo("180");
		getEvidence(new Object() {}, "02");
		//スクロースした画面を初期位置に戻す
		scrollTo("0");

	}
	
	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//クリアボタンを押下
		webDriver.findElement(By.cssSelector("input[value='クリア']")).click();
		//キーワード欄が空欄になっているかを確認
		assertEquals("", webDriver.findElement(By.id("form")).getAttribute("value"));
		
		// よくある質問画面が正常に表示されているか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		getEvidence(new Object() {});
	}

}
