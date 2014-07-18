package junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import user.parts.RegInfCheck;

public class RegInfCheckTest {
	//オブジェクトの生成
	RegInfCheck cn = new RegInfCheck();

	@Before
	public void setUp() throws Exception {
		
	}

	/**
	 * 名前
	 */
	@Test
	public void testCheckName001() {
		cn.checkName("0123456789");
		assertEquals("",cn.getErrMsg());
	}
	@Test
	public void testCheckName002() {
		cn.checkName("あいうえおかきくけこ");
		assertEquals("",cn.getErrMsg());
	}
	@Test
	public void testCheckName003() {
		cn.checkName("01234567890");
		assertEquals("名前は10桁以内で入力してください。<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckName004() {
		cn.checkName("あいうえおかきくけこさ");
		assertEquals("名前は10桁以内で入力してください。<br />",cn.getErrMsg());
	}

	/**
	 * 年齢
	 */
	@Test
	public void testCheckAge005() {
		cn.checkAge("16");
		assertEquals("",cn.getErrMsg());
	}
	@Test
	public void testCheckAge006() {
		cn.checkAge("60");
		assertEquals("",cn.getErrMsg());
	}
	@Test
	public void testCheckAge007() {
		cn.checkAge("15");
		assertEquals("年齢は(16-60)の範囲で入力してください。<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckAge008() {
		cn.checkAge("61");
		assertEquals("年齢は(16-60)の範囲で入力してください。<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckAge009() {
		cn.checkAge("１６");
		assertEquals("年齢は数値(半角)で入力してください。<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckAge010() {
		cn.checkAge("あいうえお");
		assertEquals("年齢は数値(半角)で入力してください。<br />"+"年齢は(16-60)の範囲で入力してください。<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckAge011() {
		cn.checkAge("16あいうえお");
		assertEquals("年齢は数値(半角)で入力してください。<br />"+"年齢は(16-60)の範囲で入力してください。<br />",cn.getErrMsg());
	}

	/**
	 * 番号
	 */
	@Test
	public void testCheckId012() {
		cn.checkId("999");
		assertEquals("",cn.getErrMsg());
	}
	@Test
	public void testCheckId013() {
		cn.checkId("1000");
		assertEquals("登録可能なID（999）を超えています。管理者に問い合わせてください。<br />",cn.getErrMsg());
	}


}
