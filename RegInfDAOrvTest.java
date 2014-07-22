package junitrv;

import static org.junit.Assert.*;

//接続
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



//Junit
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;



//エラー
import java.sql.SQLException;

//
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import user.bean.RegistrantInfo;
import user.parts.RegInfDAO;

public class RegInfDAOrvTest {
	//属性
	RegInfDAO dao;
	private InitialContext initCon;
	private DataSource ds;
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	/**
	 * 
	 * @throws Exception
	 * 
	 * 初めに一回実行する
	 */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	    System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
	    System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");


	    InitialContext ic = new InitialContext();
	    ic.createSubcontext("java:");
	    ic.createSubcontext("java:comp");
	    ic.createSubcontext("java:comp/env");
	    ic.createSubcontext("java:comp/env/jdbc");

	    MysqlDataSource mds = new MysqlDataSource();
	    mds.setUser("root");
	    mds.setPassword("root");
	    mds.setURL("jdbc:mysql://localhost/Task");

	    ic.bind("java:comp/env/jdbc/Task", mds);

	}
	
	/**
	 * DBにアタッチ
	 */
	public void conect(){
		try{
			this.initCon = new InitialContext();
			this.ds = (DataSource)this.initCon.lookup("java:comp/env/jdbc/Task"); 
			this.con = ds.getConnection();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * DBにデタッチ
	 */
	public void closelocal(){
		try {
			if(this.rs != null){
				this.rs.close();
			}
			if(this.stmt != null){
				this.stmt.close();
			}
			this.con.close();
			ds = null;
			this.initCon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * テーブル内の中身を削除してデータを登録する
	 */
	public void firsttable(){
		//前提条件
		try{
			//データベースに接続
			conect();
			//データテーブル内全権削除
			ResultSet rs = null;
			int count = 0;
			
			/**
			 * SQL文で全行数カウント＆削除
			 */
			this.stmt = con.prepareStatement("SELECT * FROM registrants");
			rs = stmt.executeQuery();
			rs.last();
			count = rs.getRow();
			
			this.stmt = con.prepareStatement("DELETE FROM task.registrants LIMIT ?");
			this.stmt.setInt(1,count);
			this.stmt.executeUpdate();

			//テキスト追加
			stmt = con.prepareStatement("INSERT INTO `registrants` VALUES ('001','鈴木太郎','35'),('002','Tommy','25'),('003','山田花子','30')");//PreparedStatementオブジェクトの生成
			stmt.executeUpdate();//行作成文の実行
		}
		catch(SQLException e){
			//データベースに関するエラー情報取得
			System.out.println("エラー番号："+e.getErrorCode());
			System.out.println("説明："+e.getMessage());
		}
	}
	
	@Test
	public void testInsert() {
		//前提条件
		firsttable();
		
		//メソッド実行
		dao = new RegInfDAO();
		dao.insert("004", "佐藤路未央", "28");
		
		//確認
		RegistrantInfo regInfo0;
		ArrayList<RegistrantInfo> arrayRegInfo0 = new ArrayList<RegistrantInfo>();
		try{
			stmt = con.prepareStatement("SELECT * FROM registrants");
			rs = stmt.executeQuery();
			while(rs.next()){
				regInfo0 = new RegistrantInfo(rs.getString("registrant_id"),
											rs.getString("registrant_name"),
											rs.getString("registrant_age"));
				arrayRegInfo0.add(regInfo0);				
			}
		}
		catch(SQLException e){
			//データベースに関するエラー情報取得
			System.out.println("エラー番号："+e.getErrorCode());
			System.out.println("説明："+e.getMessage());
		}
		
		//配列を作成する
		String[] id = {"001","002","003","004"};
		String[] name = {"鈴木太郎","Tommy","山田花子","佐藤路未央"};
		String[] age = {"35","25","30","28"};
	
		int count = 0;
		for(RegistrantInfo ri:arrayRegInfo0){
			assertEquals(id[count], ri.getrId());
			assertEquals(name[count], ri.getrName());
			assertEquals(age[count], ri.getrAge());
			count++;
		}
		closelocal();
		
	}

	@Test
	public void testUpdate() {
		//前提条件
		firsttable();
		
		//メソッド実行
		dao = new RegInfDAO();
		dao.update("002", "Michael", "29");
		
		//確認
		RegistrantInfo regInfo0;
		ArrayList<RegistrantInfo> arrayRegInfo0 = new ArrayList<RegistrantInfo>();
		try{
			stmt = con.prepareStatement("SELECT * FROM registrants");
			rs = stmt.executeQuery();
			while(rs.next()){
				regInfo0 = new RegistrantInfo(rs.getString("registrant_id"),
											rs.getString("registrant_name"),
											rs.getString("registrant_age"));
				arrayRegInfo0.add(regInfo0);				
			}
		}
		catch(SQLException e){
			//データベースに関するエラー情報取得
			System.out.println("エラー番号："+e.getErrorCode());
			System.out.println("説明："+e.getMessage());
		}
		
		//配列を作成する
		String[] id = {"001","002","003"};
		String[] name = {"鈴木太郎","Michael","山田花子"};
		String[] age = {"35","29","30"};
	
		int count = 0;
		for(RegistrantInfo ri:arrayRegInfo0){
			assertEquals(id[count], ri.getrId());
			assertEquals(name[count], ri.getrName());
			assertEquals(age[count], ri.getrAge());
			count++;
		}
		closelocal();
		
	}

	@Test
	public void testDelete() {
		//前提条件
		firsttable();
		
		//メソッド実行
		dao = new RegInfDAO();
		dao.delete("001");
		
		//確認
		RegistrantInfo regInfo0;
		ArrayList<RegistrantInfo> arrayRegInfo0 = new ArrayList<RegistrantInfo>();
		try{
			stmt = con.prepareStatement("SELECT * FROM registrants");
			rs = stmt.executeQuery();
			while(rs.next()){
				regInfo0 = new RegistrantInfo(rs.getString("registrant_id"),
											rs.getString("registrant_name"),
											rs.getString("registrant_age"));
				arrayRegInfo0.add(regInfo0);				
			}
		}
		catch(SQLException e){
			//データベースに関するエラー情報取得
			System.out.println("エラー番号："+e.getErrorCode());
			System.out.println("説明："+e.getMessage());
		}
		
		//配列を作成する
		String[] id = {"002","003"};
		String[] name = {"Tommy","山田花子"};
		String[] age = {"25","30"};
	
		int count = 0;
		for(RegistrantInfo ri:arrayRegInfo0){
			assertEquals(id[count], ri.getrId());
			assertEquals(name[count], ri.getrName());
			assertEquals(age[count], ri.getrAge());
			count++;
		}
		closelocal();
	}

	@Test
	public void testGetReglist() {	
		//メソッドの実行
		ArrayList<RegistrantInfo> arrayRegInfo0 = new ArrayList<RegistrantInfo>();
		dao = new RegInfDAO();
		arrayRegInfo0 = dao.getReglist();
		
		//確認する
		//配列を作成する
		String[] id = {"001","002","003"};
		String[] name = {"鈴木太郎", "Tommy","山田花子"};
		String[] age = {"35","25","30"};
	
		int count = 0;
		for(RegistrantInfo ri:arrayRegInfo0){
			assertEquals(id[count], ri.getrId());
			assertEquals(name[count], ri.getrName());
			assertEquals(age[count], ri.getrAge());
			count++;
		}
		
		

		
	}

	@Test
	public void testGetNextId() {
		//前提条件
		try{
			//データベースに接続
			conect();
			//データテーブル内全権削除
			ResultSet rs = null;
			String[] str = new String[10];
			int count = 0;
			stmt = con.prepareStatement("SELECT * FROM registrants");//PreparedStatementオブジェクトの生成
			rs = stmt.executeQuery();
			while(rs.next()){
				str[count] = rs.getString("registrant_id");
				count = count+1;
			}
			for(int j=0; j<count; j++){
				this.stmt = con.prepareStatement("DELETE FROM task.registrants WHERE registrant_id=?");
				this.stmt.setString(1,str[j]);
				this.stmt.executeUpdate();
			}
		}
		catch(SQLException e){
			//データベースに関するエラー情報取得
			System.out.println("エラー番号："+e.getErrorCode());
			System.out.println("説明："+e.getMessage());
		}
		
		//メソッド実行
		dao = new RegInfDAO();
		String str = dao.getNextId();
		
		//確認
		assertEquals("001", str);
		
		closelocal();
	}
	
	/**
	 * 最後に一回実行する
	 */
	@After
	public void tearDown(){
	    dao.close();
	}
}
