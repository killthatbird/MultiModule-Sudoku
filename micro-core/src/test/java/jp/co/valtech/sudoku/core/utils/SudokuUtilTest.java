package jp.co.valtech.sudoku.core.utils;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by uratamanabu on 2016/08/15.
 */
public class SudokuUtilTest {
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void convertSquareRoot() throws Exception {
		int squareRoot = SudokuUtils.convertSquareRoot(4);
		assertTrue(squareRoot == 2);
	}

	@Test
	public void calculationScore() throws Exception {

	}

	@Test
	public void answerInfoTblConvertBean() throws Exception {

	}

	@Test
	public void setCell() throws Exception {

	}

}