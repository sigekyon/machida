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
	//定数
	final static private File file = new File("C:/test/log/log.txt");
	static PrintWriter pw;
	//オブジェクトの宣言
	BufferedReader br = null;
	StringBuilder sb = new StringBuilder();
	OutLog ol = new OutLog();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOutLogDmpString() throws IOException {
		//前提条件(ファイルの中身の削除)
		FileWriter fw = new FileWriter(file);
		fw.flush();
		fw.close();

		//テストの実行
		ol.outLogDmp("sample：サンプル");

		//文字列の作成
		sb.append(DateString.getDate14());
	    sb.append(":sample：サンプル");
	    
		br = new BufferedReader(new FileReader(file));
		String str1;
		if((str1 = br.readLine()) != null);
		br.close();
			
		String bun = new String(sb);
		assertEquals(bun,str1);

	}

	@Test
	public void testOutLogDmpInteger() throws IOException{
		//前提条件(ファイルの中身の削除)
		FileWriter fw = new FileWriter(file);
		fw.flush();
		fw.close();

		//テストの実行
		ol.outLogDmp("sample：12345");

		//文字列の作成
		sb.append(DateString.getDate14());
	    sb.append(":sample：12345");
	    
		br = new BufferedReader(new FileReader(file));
		String str1;
		if((str1 = br.readLine()) != null);
		br.close();
			
		String bun = new String(sb);
		assertEquals(bun,str1);
	}

}
