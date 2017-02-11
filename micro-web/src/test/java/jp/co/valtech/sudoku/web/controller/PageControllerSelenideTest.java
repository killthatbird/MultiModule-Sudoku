package jp.co.valtech.sudoku.web.controller;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import jp.co.valtech.sudoku.web.WebApp;
import jp.co.valtech.sudoku.web.controller.page.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PageControllerSelenideTest implements PageControllerTestInterface {

	@BeforeClass
	public static void setUpClass() {

		// テスト対象アプリの起動
		WebApp.main(new String[]{""});
		Configuration.browser = WebDriverRunner.SAFARI;

		// タイムアウトの時間を5000ミリ秒にする(デフォルト:4000ミリ秒)
		Configuration.timeout = 2000;

		// ベースURLを変更する (デフォルト:http://localhost:8080)
		Configuration.baseUrl = "https://localhost:8081/Sudoku";

		// テスト実行後にブラウザを開いたままにする
		Configuration.holdBrowserOpen = false;
	}

	@AfterClass
	public static void tearDownClass() {
		WebDriverRunner.closeWebDriver();
	}

	@After
	public void tearDown() {
	}

	@Test
	@Override
	public void testCreateAnswer() {
		CreateAnswerPage page = CreateAnswerPage.open();
		assertThat(CreateAnswerPage.title(), is("createAnswer"));

		Selenide.screenshot("createAnswer");
		Selenide.close();
	}

	@Test
	public void testCompleteAnswer() {
		CompleteAnswerPage page = CompleteAnswerPage.open();
		//assertThat(CreateAnswerPage.title(), is("completeAnswer"));

		Selenide.screenshot("completeAnswer");
		Selenide.close();
	}

	@Test
	public void testChoiceQuestion() {
		ChoiceQuestionPage page = ChoiceQuestionPage.open();
		assertThat(CreateAnswerPage.title(), is("choiceQuestion"));

		Selenide.screenshot("choiceQuestion");
		Selenide.close();
	}

	@Test
	public void testCreateQuestion() {
		CreateQuestionPage page = CreateQuestionPage.open();
		assertThat(CreateAnswerPage.title(), is("createQuestion"));

		Selenide.screenshot("createQuestion");
		Selenide.close();
	}

	@Test
	public void testPlayNumberPlace() {
		PlayNumberPlacePage page = PlayNumberPlacePage.open();
		//assertThat(CreateAnswerPage.title(), is("playNumberPlace"));

		Selenide.screenshot("playNumberPlace");
		Selenide.close();
	}

	@Test
	public void testIsCheck() {

	}

	@Test
	public void testBestScore() {
		BestScorePage page = BestScorePage.open();
		//assertThat(CreateAnswerPage.title(), is("bestScore"));

		Selenide.screenshot("bestScore");
		Selenide.close();
	}

	@Test
	public void testSearchAnswer() {
		SearchAnswerPage page = SearchAnswerPage.open();
		assertThat(CreateAnswerPage.title(), is("searchAnswer"));

		Selenide.screenshot("searchAnswer");
		Selenide.close();
	}

	@Test
	public void testIsSearch() {

	}

	@Test
	public void testDetail() {

	}

	@Test
	public void testPlayNumberPlaceDetail() {

	}
}
