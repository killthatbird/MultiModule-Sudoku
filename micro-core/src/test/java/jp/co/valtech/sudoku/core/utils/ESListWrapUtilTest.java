package jp.co.valtech.sudoku.core.utils;

import jp.co.valtech.sudoku.core.config.CommonConstant;
import jp.co.valtech.sudoku.core.config.enums.Type;
import org.eclipse.collections.api.iterator.IntIterator;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ESListWrapUtilTest {

	final private String regexUp4 = "[A-D][0-4]";
	final private String regexUp9 = "[A-I][0-9]";
	final private String regexLow4 = "[a-d][0-4]";
	final private String regexLow9 = "[a-i][0-9]";

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void createCells() throws Exception {

		MutableList<String> list;
		MutableList<MutableList<String>> list2;

		// size4の時
		list = ESListWrapUtil.createCells(Type.SIZE4.getSize(), 0);
		ListIterator<String> iterator = list.listIterator();
		assertTrue(iterator.next().matches(regexUp4));

		// size4の時
		list2 = ESListWrapUtil.createCells(Type.SIZE4.getSize());
		for (MutableList<String> list3 : list2) {
			list3.each(s -> System.out.printf(s));
		}

		// size9の時
		list = ESListWrapUtil.createCells(Type.SIZE9.getSize(), 0);
		iterator = list.listIterator();
		assertTrue(iterator.next().matches(regexUp9));

		// size9の時
		list2 = ESListWrapUtil.createCells(Type.SIZE9.getSize());
		for (MutableList<String> list3 : list2) {
			list3.each(s -> System.out.printf(s));
		}
	}

	@Test
	public void getCellList() throws Exception {

		MutableList<String> list;

		list = ESListWrapUtil.getCellList(Type.SIZE4.getSize(), 0, CommonConstant.LOWER);
		assertTrue(list.size() == Type.SIZE4.getSize());
		assertTrue(list.get(0).matches(regexLow4));

		list = ESListWrapUtil.getCellList(Type.SIZE4.getSize(), 0, CommonConstant.UPPER);
		assertTrue(list.size() == Type.SIZE4.getSize());
		assertTrue(list.get(0).matches(regexUp4));

		list = ESListWrapUtil.getCellList(Type.SIZE9.getSize(), 0, CommonConstant.LOWER);
		assertTrue(list.size() == Type.SIZE9.getSize());
		assertTrue(list.get(0).matches(regexLow9));

		list = ESListWrapUtil.getCellList(Type.SIZE9.getSize(), 0, CommonConstant.UPPER);
		assertTrue(list.size() == Type.SIZE9.getSize());
		assertTrue(list.get(0).matches(regexUp9));
	}

	@Test
	public void getSelectNum() throws Exception {
		List<Integer> intList;

		intList = ESListWrapUtil.getSelectNum(Type.SIZE4.getSize());
		assertTrue(intList.size() == 4);

		intList = ESListWrapUtil.getSelectNum(Type.SIZE9.getSize());
		assertTrue(intList.size() == 9);

		intList = ESListWrapUtil.getSelectNum(7);
		assertTrue(intList.size() == 0);
		assertFalse(intList.size() == 7);
	}

	@Test
	public void getLinkedNum() throws Exception {
		List<Integer> intList;

		intList = ESListWrapUtil.getLinkedNum(Type.SIZE4.getSize());
		assertTrue(intList.size() == 4);

		intList = ESListWrapUtil.getLinkedNum(Type.SIZE9.getSize());
		assertTrue(intList.size() == 9);

		intList = ESListWrapUtil.getLinkedNum(7);
		assertTrue(intList.size() == 7);
	}

	@Test
	public void getRandList() {
		ImmutableIntList intList;

		// size4の時
		intList = ESListWrapUtil.getRandList(Type.SIZE4.getSize());
		IntIterator intIterator = intList.intIterator();
		assertTrue(intIterator.next() <= Type.SIZE4.getSize());
		// size9の時
		intList = ESListWrapUtil.getRandList(Type.SIZE9.getSize());
		intIterator = intList.intIterator();
		assertTrue(intIterator.next() <= Type.SIZE9.getSize());

		intList = ESListWrapUtil.getRandList(7);
		intIterator = intList.intIterator();
		assertFalse(intIterator.next() >= 8);
	}
}
