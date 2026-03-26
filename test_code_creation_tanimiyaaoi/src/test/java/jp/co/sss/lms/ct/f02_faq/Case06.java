package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// カテゴリ検索欄の【研修関係】リンクをクリック
		webDriver.findElement(By.linkText("【研修関係】")).click();
		// 検索結果が表示されるまで最大10秒待機する
		WebDriverUtils.visibilityTimeout(By.className("table"), 10);
		getEvidence(new Object() {}, "01");
		
		// 表示された検索結果をリストに格納する
		List<WebElement> rows = webDriver.findElements(By.cssSelector(".table tbody tr"));
		//カテゴリ検索結果の2件が正しく表示されていることを検証
		assertEquals(2, rows.size());
		// 1件目、2件目のデータが正しく並んでいるか検証
		assertTrue(rows.get(0).getText().contains("キャンセル料・途中退校について"));
		assertTrue(rows.get(1).getText().contains("研修の申し込みはどのようにすれば良いですか"));
		
		//　エビデンスに検索結果が移るように画面をスクロール
		WebDriverUtils.scrollTo("180");
		getEvidence(new Object() {}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// 「Q.キャンセル料・途中退校について」の質問部分(dtタグ)をクリック
		webDriver.findElement(By.xpath("//span[contains(text(), 'キャンセル料・途中退校について')]")).click();
		WebDriverUtils.visibilityTimeout(By.id("answer-h[${status.index}]"), 5);
		
		// 回答が表示されたことを検証するため、該当の要素を特定
		WebElement answer = webDriver.findElement(By.id("answer-h[${status.index}]"));
		// 質問の回答が画面に表示されているかを検証
		assertTrue(answer.isDisplayed());
		// 回答内容が正しいか検証
		assertTrue(answer.getText().contains("受講者の退職や解雇等、"));
		
		//　エビデンスに検索結果が移るように画面をスクロール
		WebDriverUtils.scrollTo("220");
		getEvidence(new Object() {});
				
	}

}
