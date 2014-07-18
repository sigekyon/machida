package junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import user.parts.RegInfCheck;

public class RegInfCheckTest {
	//�I�u�W�F�N�g�̐���
	RegInfCheck cn = new RegInfCheck();

	@Before
	public void setUp() throws Exception {
		
	}

	/**
	 * ���O
	 */
	@Test
	public void testCheckName001() {
		cn.checkName("0123456789");
		assertEquals("",cn.getErrMsg());
	}
	@Test
	public void testCheckName002() {
		cn.checkName("��������������������");
		assertEquals("",cn.getErrMsg());
	}
	@Test
	public void testCheckName003() {
		cn.checkName("01234567890");
		assertEquals("���O��10���ȓ��œ��͂��Ă��������B<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckName004() {
		cn.checkName("����������������������");
		assertEquals("���O��10���ȓ��œ��͂��Ă��������B<br />",cn.getErrMsg());
	}

	/**
	 * �N��
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
		assertEquals("�N���(16-60)�͈̔͂œ��͂��Ă��������B<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckAge008() {
		cn.checkAge("61");
		assertEquals("�N���(16-60)�͈̔͂œ��͂��Ă��������B<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckAge009() {
		cn.checkAge("�P�U");
		assertEquals("�N��͐��l(���p)�œ��͂��Ă��������B<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckAge010() {
		cn.checkAge("����������");
		assertEquals("�N��͐��l(���p)�œ��͂��Ă��������B<br />"+"�N���(16-60)�͈̔͂œ��͂��Ă��������B<br />",cn.getErrMsg());
	}
	@Test
	public void testCheckAge011() {
		cn.checkAge("16����������");
		assertEquals("�N��͐��l(���p)�œ��͂��Ă��������B<br />"+"�N���(16-60)�͈̔͂œ��͂��Ă��������B<br />",cn.getErrMsg());
	}

	/**
	 * �ԍ�
	 */
	@Test
	public void testCheckId012() {
		cn.checkId("999");
		assertEquals("",cn.getErrMsg());
	}
	@Test
	public void testCheckId013() {
		cn.checkId("1000");
		assertEquals("�o�^�\��ID�i999�j�𒴂��Ă��܂��B�Ǘ��҂ɖ₢���킹�Ă��������B<br />",cn.getErrMsg());
	}


}
