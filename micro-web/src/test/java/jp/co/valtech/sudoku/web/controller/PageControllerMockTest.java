package jp.co.valtech.sudoku.web.controller;

import jp.co.valtech.sudoku.core.service.AnswerInfoService;
import jp.co.valtech.sudoku.core.service.ScoreInfoService;
import jp.co.valtech.sudoku.web.config.enums.Forward;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(webDriverEnabled = false)
public class PageControllerMockTest implements PageControllerTestInterface {

	private final String SLASH = "/";
	@MockBean
	AnswerInfoService answerInfoService;
	@MockBean
	ScoreInfoService scoreInfoService;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void after() {
	}

	@Test
	@Override
	public void testCreateAnswer() throws Exception {
		// Prepare get request
		String getSite = SLASH + Forward.CREATE_ANSWER.getPath();
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(getSite);
		this.mvc.perform(getRequest)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.view().name(Forward.CREATE_ANSWER.getPath()))
						.andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@Test
	public void testCompleteAnswer() throws Exception {
		// Prepare get request
		String getSite = SLASH + Forward.COMPLETE_ANSWER.getPath();
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(getSite);
		this.mvc.perform(getRequest)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.view().name(Forward.COMPLETE_ANSWER.getPath()))
						.andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@Test
	public void testChoiceQuestion() throws Exception {
		// Prepare get request
		String getSite = SLASH + Forward.CHOICE_QUESTION.getPath();
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(getSite);
		this.mvc.perform(getRequest)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.view().name(Forward.CHOICE_QUESTION.getPath()))
						.andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@Test
	public void testCreateQuestion() throws Exception {
		// Prepare get request
		String getSite = SLASH + Forward.CREATE_QUESTION.getPath();
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(getSite);
		this.mvc.perform(getRequest)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.view().name(Forward.CREATE_QUESTION.getPath()))
						.andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@Test
	public void testPlayNumberPlace() throws Exception {
		// Prepare get request
		String getSite = SLASH + Forward.PLAY_NUMBER_PLACE.getPath();
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(getSite);
		this.mvc.perform(getRequest)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.view().name(Forward.PLAY_NUMBER_PLACE.getPath()))
						.andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@Test
	public void testIsCheck() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/isCheck");
		this.mvc.perform(getRequest).andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Test
	public void testBestScore() throws Exception {
		// Prepare get request
		String getSite = SLASH + Forward.BEST_SCORE.getPath();
		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(getSite);
		this.mvc.perform(postRequest)
						.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testSearchAnswer() throws Exception {
		// Prepare get request
		String getSite = SLASH + Forward.SEARCH_ANSWER.getPath();
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(getSite);
		this.mvc.perform(getRequest).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testIsSearch() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/isSearch");
		this.mvc.perform(getRequest).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testDetail() throws Exception {
		// Prepare get request
		String getSite = SLASH + Forward.DETAIL.getPath();
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(getSite);
		this.mvc.perform(getRequest).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testPlayNumberPlaceDetail() throws Exception {
		// Prepare get request
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/playNumberPlaceDetail");
		this.mvc.perform(getRequest).andExpect(MockMvcResultMatchers.status().isOk());
	}
}


