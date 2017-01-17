package jp.co.valtech.sudoku.core.utils;

import jp.co.valtech.sudoku.core.config.enums.Type;
import org.eclipse.collections.api.map.ImmutableMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ESMapWrapUtilTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void getSelectTypes() throws Exception {
		ImmutableMap<String, Integer> map = ESMapWrapUtil.getSelectTypes();
		assertTrue(map.containsKey("2*2"));
		assertTrue(map.containsKey("3*3"));
		assertFalse(map.containsKey("4*4"));
		assertTrue(map.containsValue(Type.SIZE4.getSize()));
		assertTrue(map.containsValue(Type.SIZE9.getSize()));
		assertFalse(map.containsValue(16));
	}

	@Test
	public void getSelectLevels() throws Exception {
		ImmutableMap<String, String> map = ESMapWrapUtil.getSelectLevels();
		assertTrue(map.containsKey("イージー"));
		assertTrue(map.containsKey("ノーマル"));
		assertTrue(map.containsKey("ハード"));
		assertFalse(map.containsKey("ウルトラ"));
		assertTrue(map.containsValue("easy"));
		assertTrue(map.containsValue("normal"));
		assertTrue(map.containsValue("hard"));
		assertFalse(map.containsValue("ultra"));
	}

}
