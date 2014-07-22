package junitrv;

import static org.junit.Assert.*;

//�ڑ�
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



//�G���[
import java.sql.SQLException;

//
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import user.bean.RegistrantInfo;
import user.parts.RegInfDAO;

public class RegInfDAOrvTest {
	//����
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
	 * ���߂Ɉ����s����
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
	 * DB�ɃA�^�b�`
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
	 * DB�Ƀf�^�b�`
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
	 * �e�[�u�����̒��g���폜���ăf�[�^��o�^����
	 */
	public void firsttable(){
		//�O�����
		try{
			//�f�[�^�x�[�X�ɐڑ�
			conect();
			//�f�[�^�e�[�u�����S���폜
			ResultSet rs = null;
			int count = 0;
			
			/**
			 * SQL���őS�s���J�E���g���폜
			 */
			this.stmt = con.prepareStatement("SELECT * FROM registrants");
			rs = stmt.executeQuery();
			rs.last();
			count = rs.getRow();
			
			this.stmt = con.prepareStatement("DELETE FROM task.registrants LIMIT ?");
			this.stmt.setInt(1,count);
			this.stmt.executeUpdate();

			//�e�L�X�g�ǉ�
			stmt = con.prepareStatement("INSERT INTO `registrants` VALUES ('001','��ؑ��Y','35'),('002','Tommy','25'),('003','�R�c�Ԏq','30')");//PreparedStatement�I�u�W�F�N�g�̐���
			stmt.executeUpdate();//�s�쐬���̎��s
		}
		catch(SQLException e){
			//�f�[�^�x�[�X�Ɋւ���G���[���擾
			System.out.println("�G���[�ԍ��F"+e.getErrorCode());
			System.out.println("�����F"+e.getMessage());
		}
	}
	
	@Test
	public void testInsert() {
		//�O�����
		firsttable();
		
		//���\�b�h���s
		dao = new RegInfDAO();
		dao.insert("004", "�����H����", "28");
		
		//�m�F
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
			//�f�[�^�x�[�X�Ɋւ���G���[���擾
			System.out.println("�G���[�ԍ��F"+e.getErrorCode());
			System.out.println("�����F"+e.getMessage());
		}
		
		//�z����쐬����
		String[] id = {"001","002","003","004"};
		String[] name = {"��ؑ��Y","Tommy","�R�c�Ԏq","�����H����"};
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
		//�O�����
		firsttable();
		
		//���\�b�h���s
		dao = new RegInfDAO();
		dao.update("002", "Michael", "29");
		
		//�m�F
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
			//�f�[�^�x�[�X�Ɋւ���G���[���擾
			System.out.println("�G���[�ԍ��F"+e.getErrorCode());
			System.out.println("�����F"+e.getMessage());
		}
		
		//�z����쐬����
		String[] id = {"001","002","003"};
		String[] name = {"��ؑ��Y","Michael","�R�c�Ԏq"};
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
		//�O�����
		firsttable();
		
		//���\�b�h���s
		dao = new RegInfDAO();
		dao.delete("001");
		
		//�m�F
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
			//�f�[�^�x�[�X�Ɋւ���G���[���擾
			System.out.println("�G���[�ԍ��F"+e.getErrorCode());
			System.out.println("�����F"+e.getMessage());
		}
		
		//�z����쐬����
		String[] id = {"002","003"};
		String[] name = {"Tommy","�R�c�Ԏq"};
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
		//���\�b�h�̎��s
		ArrayList<RegistrantInfo> arrayRegInfo0 = new ArrayList<RegistrantInfo>();
		dao = new RegInfDAO();
		arrayRegInfo0 = dao.getReglist();
		
		//�m�F����
		//�z����쐬����
		String[] id = {"001","002","003"};
		String[] name = {"��ؑ��Y", "Tommy","�R�c�Ԏq"};
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
		//�O�����
		try{
			//�f�[�^�x�[�X�ɐڑ�
			conect();
			//�f�[�^�e�[�u�����S���폜
			ResultSet rs = null;
			String[] str = new String[10];
			int count = 0;
			stmt = con.prepareStatement("SELECT * FROM registrants");//PreparedStatement�I�u�W�F�N�g�̐���
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
			//�f�[�^�x�[�X�Ɋւ���G���[���擾
			System.out.println("�G���[�ԍ��F"+e.getErrorCode());
			System.out.println("�����F"+e.getMessage());
		}
		
		//���\�b�h���s
		dao = new RegInfDAO();
		String str = dao.getNextId();
		
		//�m�F
		assertEquals("001", str);
		
		closelocal();
	}
	
	/**
	 * �Ō�Ɉ����s����
	 */
	@After
	public void tearDown(){
	    dao.close();
	}
}
