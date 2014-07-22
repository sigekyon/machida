package junit;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import user.bean.RegistrantInfo;
import employ.DateString;
import employ.OutLog;

public class OutLogTest {
	//�萔
	final static private File file = new File("C:/test/log/log.txt");
	static PrintWriter pw;
	//�I�u�W�F�N�g�̐錾
	BufferedReader br = null;
	StringBuilder sb = new StringBuilder();
	OutLog ol = new OutLog();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOutLogDmpString() throws IOException {
		//�O�����(�t�@�C���̒��g�̍폜)
		FileWriter fw = new FileWriter(file);
		fw.flush();
		fw.close();

		//�e�X�g�̎��s
		ol.outLogDmp("sample�F�T���v��");

		//������̍쐬
		sb.append(DateString.getDate14());
	    sb.append(":sample�F�T���v��");
	    
		br = new BufferedReader(new FileReader(file));
		String str1;
		if((str1 = br.readLine()) != null);
		br.close();
			
		String bun = new String(sb);
		assertEquals(bun,str1);

	}

	@Test
	public void testOutLogDmpInteger() throws IOException{
		//�O�����(�t�@�C���̒��g�̍폜)
		FileWriter fw = new FileWriter(file);
		fw.flush();
		fw.close();

		//�e�X�g�̎��s
		ol.outLogDmp("sample�F12345");

		//������̍쐬
		sb.append(DateString.getDate14());
	    sb.append(":sample�F12345");
	    
		br = new BufferedReader(new FileReader(file));
		String str1;
		if((str1 = br.readLine()) != null);
		br.close();
			
		String bun = new String(sb);
		assertEquals(bun,str1);
	}

}
