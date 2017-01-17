package jp.co.valtech.sudoku.web.controller;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import jp.co.valtech.sudoku.web.WebApp;
import jp.co.valtech.sudoku.web.controller.page.ChoiceQuestionPage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PageControllerSelenideTest {

	@BeforeClass
	public static void setUpClass() {

		// テスト対象アプリの起動
		WebApp.main(new String[]{""});
		Configuration.browser = WebDriverRunner.SAFARI;
	}

	@AfterClass
	public static void tearDownClass() {
		WebDriverRunner.closeWebDriver();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void test() throws Exception {

		ChoiceQuestionPage page = ChoiceQuestionPage.open();

		Selenide.screenshot("init");
	}
}
