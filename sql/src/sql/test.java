package sql;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import com.mysql.cj.log.Log;

import java.util.Random;

public class test {
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	static Scanner sc = new Scanner(System.in);
	static int status = 0;// ������ �޴�

	static Random rnd;
	static int input=11;
	static String Esn = null;
	static String Errn = null;
	static String Ename = null;
	static String Esex = null;
	static String Ephone = null;
	static String Casn = null;
	static String CategoryName = null;
	static String HPsn = null;
	static String HPname = null;
	static int HPprice = 0;
	static String Ssn = null;
	static String Sname = null;
	static String Stel = null;
	static String IPsn = null;
	static String SelfLife = null;
	static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	static Calendar time = Calendar.getInstance();
	static String now_date;
	static String now_time;
	static String Dsn = null;
	static String Dname = null;
	static String Csn = null;
	static String Cname = null;
	static String Crrn = null;
	static String Csex = null;
	static String Cphone = null;

	public static void main(String args[]) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.56.102:4567/MMS", "lmj", "1234");
			stmt = con.createStatement();// MMS ���
		} catch (Exception e) {
			System.out.println(e);
		}

		while (input != 0) {
			function();
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void function() {
		status = menu(status);

		if (status == 111) {// ī�װ� �� ��� ��ȸ
			try {
				CATEGORYview();
				rs = stmt.executeQuery(
						"SELECT C.Casn, CategoryName, E.Esn, Name  FROM CATEGORY C LEFT JOIN MANAGE M ON C.Casn=M.Casn LEFT JOIN EMPLOYEE E ON E.Esn=M.Esn");
				System.out.println("\n��ȣ	ī�װ�	����� ���	����� �̸�");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	" + rs.getString(4));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 112) {// ī�װ� �߰�
			try {
				String thisCasn = null;
				rs = stmt.executeQuery("SELECT Casn FROM CATEGORY ORDER BY Casn DESC LIMIT 1");
				while (rs.next()) {
					thisCasn = rs.getString("Casn");
				}
				String numCasn = thisCasn.substring(2, 5);
				int tmpCasn = Integer.parseInt(numCasn) + 1;
				String okCasn = null;
				if (tmpCasn < 10)
					okCasn = "CA00" + Integer.toString(tmpCasn);
				else if (tmpCasn < 100)
					okCasn = "CA0" + Integer.toString(tmpCasn);
				else if (tmpCasn < 1000)
					okCasn = "CA" + Integer.toString(tmpCasn);

				System.out.print("�� ī�װ��� : ");
				CategoryName = sc.next();
				stmt.executeUpdate("INSERT INTO CATEGORY VALUES('" + okCasn + "', '" + CategoryName + "')");
				System.out.println("\n�� " + okCasn + " " + CategoryName + " ī�װ� �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 113) {// ī�װ� ����
			try {
				System.out.println("�� ī�װ� ��� ��");
				CATEGORYview();
				System.out.print("\n�� ������  ī�װ� ��ȣ : ");
				Casn = sc.next();
				rs = stmt.executeQuery("SELECT CategoryName FROM CATEGORY WHERE Casn='" + Casn + "'");// �ش� ī�װ� �̸�
																										// �޾ƿ�
				while (rs.next())
					CategoryName = rs.getString(1);
				stmt.executeUpdate("DELETE FROM CATEGORY WHERE Casn='" + Casn + "'");
				System.out.println("\n�� " + Casn + " " + CategoryName + " ī�װ� ���� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 121) {// ��� ��ǰ ��ȸ
			HPview();
		} else if (status == 122) {// ��ݹ�ǰ �߰�
			try {
				System.out.print("�� ��� ��ǰ ��ȣ(�� ��ȣ) : ");
				HPsn = sc.next();
				System.out.print("�� ��� ��ǰ �̸�(�𵨸�) : ");

				Scanner scanner = new Scanner(System.in);
				HPname = scanner.nextLine();

				System.out.print("�� ���� : ");
				HPprice = sc.nextInt();

				CATEGORYview();
				System.out.print("\n�� ī�װ� : ");
				CategoryName = sc.next();

				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("\n��ü ��ȣ	���޾�ü��");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n�� ���޾�ü : ");
				Ssn = sc.next();

				stmt.executeUpdate("INSERT INTO HANDLING_PRODUCT VALUES('" + HPsn + "', '" + HPname + "', " + HPprice
						+ ", '" + CategoryName + "', '" + Ssn + "')");
				System.out.println("\n�� " + HPsn + " " + HPname + " - ��� ��ǰ �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 123) {// ��� ��ǰ ����
			try {
				System.out.println("�� ��� ��ǰ ��� ��");
				HPview();
				System.out.println("\n1. ��� ��ǰ ��ȣ(�𵨸�) ����\n2. �̸� ����\n3. ���� ����\n4. ī�װ� ����\n5. ���޾�ü ����\n");
				System.out.print("�� �Է� : ");
				input = sc.nextInt();
				System.out.print("�� ������ ��� ��ǰ ��ȣ (�𵨸�) : ");
				String whereInput = sc.next();
				Scanner sc1 = new Scanner(System.in);
				if (input == 4) {
					CATEGORYview();
				} else if (input == 5) {
					System.out.println("\n��ȣ	���޾�ü��");
					rs = stmt.executeQuery("SELECT *  FROM SUPPLYER");
					while (rs.next())
						System.out.println(rs.getString(1) + "	" + rs.getString(2));
				}
				System.out.print("�� ����  ���� : ");
				String setInput = sc1.nextLine();// ������ ����
				String setCol = null;// ������ �÷�
				try {
					switch (input) {
					case 1:// ��� ��ǰ ��ȣ(�𵨸�) ����
						setCol = "HPsn";
						break;
					case 2:// �̸�����
						setCol = "ProductName";
						break;
					case 3:// ���� ����
						setCol = "Price";
						break;
					case 4:// ī�װ� ����
						setCol = "Casn";
						break;
					case 5:// ���޾�ü ����
						setCol = "Ssn";
						break;
					}
					stmt.executeUpdate("UPDATE HANDLING_PRODUCT SET " + setCol + "='" + setInput + "' WHERE HPsn='"
							+ whereInput + "'");
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("\n\n�� " + whereInput + "�� " + setInput + "���� ���� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 124) {// ��� ��ǰ ����
			try {
				System.out.println("�� �Ǹ� ������ �ִ� ��ǰ�� ������ �� �����ϴ�.");
				System.out.println("�� ��� ��ǰ ��� ��");
				HPview();
				System.out.print("\n�� ������  ��� ��ǰ ��ȣ : ");
				HPsn = sc.next();
				rs = stmt.executeQuery("SELECT ProductName FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				while (rs.next())
					HPname = rs.getString(1);
				stmt.executeUpdate("DELETE FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				System.out.println("\n�� " + HPsn + " " + HPname + " ��� ��ǰ���� ���� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 131) {// �԰� ��ǰ ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM INCOME_PRODUCT");
				System.out.println("��ȣ	�������	�԰� ǰ�� ��ȣ");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + String.format("%10s", rs.getString(2)) + "	" + rs.getString(3));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 132) {// �԰� ��ǰ �߰�
			rnd = new Random();
			StringBuffer newIPsn = new StringBuffer();
			for (int i = 0; i < 5; i++) {
				if (rnd.nextBoolean()) {
					newIPsn.append((char) ((int) (rnd.nextInt(26)) + 65));
				} else {
					newIPsn.append((rnd.nextInt(10)));
				}
			}
			try {
				System.out.println("�� ��� ��ǰ ���, ī�װ�, ���޾�ü ������");
				HPview();
				System.out.print("\n�� ��� ��ǰ ��ȣ : ");
				HPsn = sc.next();

				rs = stmt.executeQuery("SELECT Casn FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				String thisCasn = null;
				while (rs.next()) {
					thisCasn = rs.getString("Casn");
				}
				if (thisCasn.equals("CA001")) {// ī�װ��� ��ǰ�� ���
					System.out.print("�� ������� : ");
					SelfLife = sc.next();
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', '" + SelfLife + "', '"
							+ HPsn + "');");
				} else {
					System.out.println("�� ��ǰ ī�װ��� �ƴϹǷ� ��������� �Է����� �ʽ��ϴ�.\n");
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', NULL, '" + HPsn + "')");
				}
				System.out.println("\n�� " + HPsn + "�� �԰� ��ǰ ��ȣ�� " + newIPsn + "�� ���� �� �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 133) {// �԰� ��ǰ ����
			try {
				System.out.println("�� �Ǹ� ������ �ִ� ��ǰ�� ������ �� �����ϴ�.");
				System.out.println("�� �԰� ��ǰ ��� ��");
				rs = stmt.executeQuery("SELECT * FROM INCOME_PRODUCT");
				System.out.println("��ȣ	�������	�԰� ǰ�� ��ȣ");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + String.format("%10s", rs.getString(2)) + "	" + rs.getString(3));

				System.out.print("\n�� ������  �԰� ��ǰ ��ȣ : ");
				IPsn = sc.next();
				stmt.executeUpdate("DELETE FROM INCOME_PRODUCT WHERE IPsn='" + IPsn + "'");
				System.out.println("\n�� " + IPsn + " �԰� ��ǰ���� ���� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 21) {// �ֹ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM ORDERS");
				System.out.println("��� ��ǰ ��ȣ	��ü ��ȣ	��� ��ȣ	��¥	�ð�");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 22) {// �ֹ� �߰�
			try {
				System.out.println("�� ��� ��ǰ ��� ��");
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("��ȣ	��ǰ �̸�	����	ī�װ�	���޾�ü");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));
				System.out.print("\n�� ��� ��ǰ ��ȣ : ");
				HPsn = sc.next();
				rs = stmt.executeQuery("SELECT Ssn FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				String thisSsn = null;
				while (rs.next()) {
					thisSsn = rs.getString("Ssn");
				}
				EMPview();
				System.out.print("�� ��� ��ȣ : ");
				Esn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO ORDERS VALUES('" + HPsn + "', '" + thisSsn + "', '" + Esn + "', '"
						+ now_date + "', '" + now_time + "');");
				System.out.println(HPsn + " �ֹ� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 31) {// �Ǹ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM BUY");
				System.out.println("��� ��ǰ ��ȣ	���ڵ� 	�� ��ȣ	��¥	�ð�");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 32) {// �Ǹ� �Է�
			try {
				System.out.println("�� ���� �԰� ǰ�� ��ȸ");
				rs = stmt.executeQuery(
						"SELECT IP.IPsn, HP.ProductName FROM HANDLING_PRODUCT HP INNER JOIN INCOME_PRODUCT IP ON HP.HPsn=IP.HPsn");
				System.out.println("�԰� ǰ��(���ڵ�) ��ȣ	ǰ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n�� �Ǹ� �԰� ǰ��(���ڵ�) ��ȣ : ");
				IPsn = sc.next();
				rs = stmt.executeQuery(
						"SELECT HP.HPsn, HP.ProductName FROM HANDLING_PRODUCT HP LEFT JOIN INCOME_PRODUCT IP ON HP.HPsn=IP.HPsn WHERE IPsn='"
								+ IPsn + "'");
				String thisHPsn = null;
				String thisHPname = null;
				while (rs.next()) {
					thisHPsn = rs.getString(1);
					thisHPname = rs.getString(2);
				}
				System.out.println("\n�� �ش� ��ȣ ����\n��� ǰ�� ��ȣ	��ǰ �̸� ");
				System.out.println(thisHPsn + "	" + thisHPname + "\n");
				CUSTOMERview();
				System.out.print("\n�� ���� �� ��ȣ : ");
				Csn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO BUY VALUES('" + thisHPsn + "', '" + IPsn + "', '" + Csn + "', '"
						+ now_date + "', '" + now_time + "')");
				stmt.executeUpdate("DELETE FROM INCOME_PRODUCT WHERE IPsn='" + IPsn + "'");
				System.out.println("\n�� " + thisHPname + " " + IPsn + " " + now_date + " " + now_time + " �Ǹ� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}

		} else if (status == 41) {// ���� �ſ� ��ȸ
			EMPview();
		} else if (status == 42) {// ���� �ڰ��� ��ȸ
			try {
				rs = stmt.executeQuery(
						"SELECT E.Esn, Name, Certification FROM EMPLOYEE E, EMP_CERTIFICATION EC WHERE E.Esn=EC.Esn");
				System.out.println("���	�̸�	���� �ڰ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 43) {// ���� �߰�
			System.out.print("�� �̸� : ");
			Ename = sc.next();
			System.out.print("�� �ֹε�Ϲ�ȣ : ");
			Errn = sc.next();
			System.out.print("�� ����(��/��) : ");
			Esex = sc.next();
			System.out.print("�� �޴���ȭ��ȣ(-���� �Է�) : ");
			Ephone = sc.next();
			try {
				System.out.print("\n�� ���� �μ� ���� ��\n");
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("�μ� ��ȣ	�μ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n�� �μ� ��ȣ : ");
				Dsn = sc.next();
				String thisEsn = null;
				rs = stmt.executeQuery("SELECT Esn FROM EMPLOYEE ORDER BY Esn DESC LIMIT 1");
				while (rs.next()) {
					thisEsn = rs.getString("Esn");
				}
				String numEsn = thisEsn.substring(1, 5);
				int tmpEsn = Integer.parseInt(numEsn) + 1;
				String okEsn = null;
				if (tmpEsn < 10)
					okEsn = "E000" + Integer.toString(tmpEsn);
				else if (tmpEsn < 100)
					okEsn = "E00" + Integer.toString(tmpEsn);
				else if (tmpEsn < 1000)
					okEsn = "E0" + Integer.toString(tmpEsn);
				else if (tmpEsn < 10000)
					okEsn = "E" + Integer.toString(tmpEsn);

				stmt.executeUpdate("INSERT INTO EMPLOYEE VALUES('" + okEsn + "', '" + Errn + "', '" + Ename + "', '"
						+ Esex + "', '" + Ephone + "', '" + Dsn + "')");
				System.out.println("�� " + Ename + "�� ����� '" + okEsn + "'���� ���� �� �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 44) {// ���� �ڰ��� �߰�
			EMPview();
			System.out.print("�� ��� : ");
			Esn = sc.next();
			System.out.print("�� �ڰ��� : ");
			Scanner sc1 = new Scanner(System.in);
			String certification = sc1.nextLine();
			try {
				stmt.executeUpdate("INSERT INTO EMP_CERTIFICATION VALUES('" + Esn + "', '" + certification + "')");
				System.out.println("�� " + Esn + "�� �ڰ��� ������ '" + certification + "' �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 45) {// ���� ����
			EMPview();
			try {
				System.out.println("�� �ֹ� ������ �ִ� ����� ������ �� �����ϴ�.");
				System.out.println("�� ���� ��� ��");
				rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
				System.out.println("�����ȣ	�ֹε�Ϲ�ȣ	�̸�	����	��ȭ��ȣ		�μ�");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5) + "	" + rs.getString(6));
				System.out.print("\n�� ������  ��� ��ȣ : ");
				Esn = sc.next();
				rs = stmt.executeQuery("SELECT Name FROM EMPLOYEE WHERE Esn='" + Esn + "'");
				while (rs.next())
					Ename = rs.getString(1);
				stmt.executeUpdate("DELETE FROM EMPLOYEE WHERE Esn='" + Esn + "'");
				System.out.println("\n�� " + Esn + " " + Ename + " ���� ���� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 51) {// �μ���ȸ �� �μ� �� ���� ��ȸ
			try {
				System.out.println("�� �μ� ���� ��");
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("�μ� ��ȣ	�μ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));

				System.out.println("\n�� �μ��� �Ҽ� ������");
				rs = stmt.executeQuery(
						"SELECT D.Dsn, DepartmentName, Esn, Name FROM DEPARTMENT D RIGHT JOIN EMPLOYEE E ON D.Dsn=E.Dsn");
				System.out.println("�μ� ��ȣ	�μ���	�����ȣ	�����");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	" + rs.getString(4));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 52) {// �μ� �߰�
			try {
				System.out.print("�� �߰� �μ��� :");
				Dname = sc.next();
				String thisDsn = null;
				rs = stmt.executeQuery("SELECT Dsn FROM DEPARTMENT ORDER BY Dsn DESC LIMIT 1");
				while (rs.next()) {
					thisDsn = rs.getString("Dsn");
				}
				String numEsn = thisDsn.substring(1, 5);
				int tmpEsn = Integer.parseInt(numEsn) + 1;
				String okDsn = null;
				if (tmpEsn < 10)
					okDsn = "D000" + Integer.toString(tmpEsn);
				else if (tmpEsn < 100)
					okDsn = "D00" + Integer.toString(tmpEsn);
				else if (tmpEsn < 1000)
					okDsn = "D0" + Integer.toString(tmpEsn);
				else if (tmpEsn < 10000)
					okDsn = "D" + Integer.toString(tmpEsn);

				stmt.executeUpdate("INSERT INTO DEPARTMENT VALUES('" + okDsn + "', '" + Dname + "')");
				System.out.println("��" + okDsn + "���� �μ� �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 53) {// �μ� ����
			try {
				System.out.println("�� �μ� ��� ��");
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("�μ� ��ȣ	�μ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n�� ������  �μ� ��ȣ : ");
				Dsn = sc.next();
				rs = stmt.executeQuery("SELECT DepartmentName FROM DEPARTMENT WHERE Dsn='" + Dsn + "'");
				while (rs.next())
					Dname = rs.getString(1);
				stmt.executeUpdate("DELETE FROM DEPARTMENT WHERE Dsn='" + Dsn + "'");
				System.out.println("\n�� " + Dsn + " " + Dname + " �μ� ���� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 61) {// ���޾�ü ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("��ü ��ȣ	��ü��	��ȭ��ȣ");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 62) {// ���޾�ü �߰�
			try {
				String thisSsn = null;
				rs = stmt.executeQuery("SELECT Ssn FROM SUPPLYER ORDER BY Ssn DESC LIMIT 1");
				while (rs.next()) {
					thisSsn = rs.getString("Ssn");
				}
				String numSsn = thisSsn.substring(1, 5);
				int tmpSsn = Integer.parseInt(numSsn) + 1;
				String okSsn = null;
				if (tmpSsn < 10)
					okSsn = "S000" + Integer.toString(tmpSsn);
				else if (tmpSsn < 100)
					okSsn = "S00" + Integer.toString(tmpSsn);
				else if (tmpSsn < 1000)
					okSsn = "S0" + Integer.toString(tmpSsn);
				else if (tmpSsn < 10000)
					okSsn = "S" + Integer.toString(tmpSsn);
				System.out.print("�� ��ü�� : ");
				Sname = sc.next();
				System.out.print("�� ��ȭ��ȣ : ");
				Stel = sc.next();

				stmt.executeUpdate("INSERT INTO SUPPLYER VALUES('" + okSsn + "', '" + Sname + "', '" + Stel + "')");
				System.out.println("\n�� ���޾�ü " + Sname + "��(��) '" + okSsn + "'��ȣ�� �Ҵ��� �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 63) {// ���޾�ü ����
			try {
				System.out.println("�� ��ü ������ �ش� ��ü�� �����ϴ� ǰ�� �����˴ϴ�.");
				System.out.println("�� ���޾�ü ��� ��");
				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("��ü ��ȣ	�μ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n�� ������  ��ü ��ȣ : ");
				Ssn = sc.next();
				rs = stmt.executeQuery("SELECT SupplyerName FROM SUPPLYER WHERE Ssn='" + Ssn + "'");
				while (rs.next())
					Sname = rs.getString(1);
				stmt.executeUpdate("DELETE FROM SUPPLYER WHERE Ssn='" + Ssn + "'");
				System.out.println("\n�� " + Ssn + " " + Sname + " ���޾�ü ���� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 71) {// �� ��ȸ
			CUSTOMERview();
		} else if (status == 72) {// �� �߰�
			System.out.print("�� �̸� : ");
			Cname = sc.next();
			System.out.print("�� �ֹε�Ϲ�ȣ : ");
			Crrn = sc.next();
			System.out.print("�� ����(��/��) : ");
			Csex = sc.next();
			System.out.print("�� �޴���ȭ��ȣ(-���� �Է�) : ");
			Cphone = sc.next();

			try {
				String thisCsn = null;
				rs = stmt.executeQuery("SELECT Csn FROM CUSTOMER ORDER BY Csn DESC LIMIT 1");
				while (rs.next()) {
					thisCsn = rs.getString("Csn");
				}
				String numCsn = thisCsn.substring(1, 5);
				int tmpCsn = Integer.parseInt(numCsn) + 1;
				String okCsn = null;
				if (tmpCsn < 10)
					okCsn = "C000" + Integer.toString(tmpCsn);
				else if (tmpCsn < 100)
					okCsn = "C00" + Integer.toString(tmpCsn);
				else if (tmpCsn < 1000)
					okCsn = "C0" + Integer.toString(tmpCsn);
				else if (tmpCsn < 10000)
					okCsn = "C" + Integer.toString(tmpCsn);

				stmt.executeUpdate("INSERT INTO CUSTOMER VALUES('" + okCsn + "', '" + Crrn + "', '" + Cname + "', '"
						+ Csex + "', '" + Cphone + "')");
				System.out.println(Cname + "��(��) '" + okCsn + "'��ȣ�� �Ҵ��� �� �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 73) {// �� ����
			try {
				System.out.println("�� �ŷ� ������ �ִ� ���� �������� �ʽ��ϴ�.");
				System.out.println("�� �� ��� ��");
				rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
				System.out.println("�� ��ȣ	�ֹε�Ϲ�ȣ	�̸�	����	��ȭ��ȣ");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
				System.out.print("\n�� ������  �� ��ȣ : ");
				Csn = sc.next();
				rs = stmt.executeQuery("SELECT Name FROM CUSTOMER WHERE Csn='" + Csn + "'");
				while (rs.next())
					Cname = rs.getString(1);
				stmt.executeUpdate("DELETE FROM CUSTOMER WHERE Csn='" + Csn + "'");
				System.out.println("\n�� " + Csn + " " + Cname + " �� ���� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static int menu(int status) {
		status = 0;
		System.out.println("\n�� Mart Management System\n" + "1. ��ǰ ����\n" + "2. �ֹ� ����\n" + "3. �Ǹ� ����\n" + "4. ���� ����\n"
				+ "5. �μ� ����\n" + "6. ��ü ����\n" + "7. �� ����\n"+"0. ����\n");
		System.out.print("�� ���� : ");
		input = sc.nextInt();
		if (input == 1) {// 1. ��ǰ����
			System.out.println("\n�� ��ǰ ����");
			System.out.println("1. ī�װ� ����\n" + "2. ��� ��ǰ ����\n" + "3. �԰� ��ǰ ����\n");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {// 1-1. ī�װ�
				System.out.println("\n�� ī�װ� ����");
				System.out.println("1. ī�װ� �� ��� ��ȸ\n2. ī�װ� �߰�\n3. ī�װ� ����");
				System.out.print("\n�� ���� : ");
				input = sc.nextInt();
				if (input == 1) {// 1-1-1
					System.out.println("\n�� ī�װ� �� ��� ��ȸ");
					status = 111;
				} else if (input == 2) {// 1-1-2
					System.out.println("\n�� ī�װ� �߰�");
					status = 112;
				} else if (input == 3) {// 1-1-3
					System.out.println("\n�� ī�װ� ����");
					status = 113;
				}
			} else if (input == 2) {// 1-2. ��� ��ǰ
				System.out.println("\n�� ��� ��ǰ ����");
				System.out.println("1. ��� ��ǰ ��ȸ\n2. ��� ��ǰ �߰�\n3. ��� ��ǰ ����\n4. ��� ��ǰ ����\n");
				System.out.print("�� ���� : ");
				input = sc.nextInt();
				if (input == 1) {// 1-2-1
					System.out.println("\n�� ��� ��ǰ ��ȸ");
					status = 121;
				} else if (input == 2) {// 1-2-2
					System.out.println("\n�� ��� ��ǰ �߰�");
					status = 122;
				} else if (input == 3) {// 1-2-3
					System.out.println("\n�� ��� ��ǰ ����");
					status = 123;
				} else if (input == 4) {// 1-2-3
					System.out.println("\n�� ��� ��ǰ ����");
					status = 124;
				}
			} else if (input == 3) {// 1-3. �԰� ��ǰ
				System.out.println("\n�� �԰� ��ǰ ����");
				System.out.println("1. �԰� ��ǰ ��ȸ\n2. �԰� ��ǰ �߰�\n3. �԰� ��ǰ ����");
				System.out.print("�� ���� : ");
				input = sc.nextInt();
				if (input == 1) {// 1-3-1
					System.out.println("\n�� �԰� ��ǰ ��ȸ");
					status = 131;
				} else if (input == 2) {// 1-3-2
					System.out.println("\n�� �԰� ��ǰ �߰� (���ڵ� ��ȣ �Է�)");
					status = 132;
				} else if (input == 3) {// 1-3-3
					System.out.println("\n�� �԰� ��ǰ ����");
					status = 133;
				}
			}
		} else if (input == 2) {// 2. �ֹ�����
			System.out.println("\n�� �ֹ� ����");
			System.out.println("1. �ֹ� ��ȸ\n" + "2. �ֹ� �߰�\n");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {// 2-1
				System.out.println("\n�� �ֹ� ��ȸ");
				status = 21;
			} else if (input == 2) {// 2-2
				System.out.println("\n�� �ֹ� �߰�");
				status = 22;
			}
		} else if (input == 3) {// 3. �Ǹ� ����
			System.out.println("\n�� �Ǹ� ����");
			System.out.println("1. �Ǹ� ��ȸ\n2. �Ǹ� �Է�\n");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {// 3-1
				System.out.println("\n�� �Ǹ� ��ȸ");
				status = 31;
			} else if (input == 2) {// 3-2
				System.out.println("\n�� �Ǹ� �Է�");
				status = 32;
			}
		} else if (input == 4) {// 4. ��������
			System.out.println("\n�� ���� ����");
			System.out.println("1. ���� ��ȸ\n" + "2. ������ġ�� ���� ���� �ڰ��� ��ȸ\n" + "3. ���� �߰�\n4. ���� �ڰ��� �߰�\n5. ���� ����");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {// 4-1. ������ȸ
				System.out.println("\n�� ���� �ſ� ��ȸ");
				status = 41;
			} else if (input == 2) {// 4-2
				System.out.println("\n�� ������ġ�� ���� ���� �ڰ��� ��ȸ");
				status = 42;
			} else if (input == 3) {// 4-3
				System.out.println("\n�� ���� �߰�");
				status = 43;
			} else if (input == 4) {// 4-4
				System.out.println("\n�� ���� �ڰ��� �߰�");
				status = 44;
			} else if (input == 5) {// 4-5
				System.out.println("\n�� ���� ����");
				status = 45;
			}
		} else if (input == 5) {// 5. �μ�����
			System.out.println("\n�� �μ� ����");
			System.out.println("1. �μ� �� �μ��� �Ҽ� ���� ��ȸ\n" + "2. �μ� �߰�\n3. �μ� ����");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {// 5-1. �μ���ȸ
				System.out.println("\n�� �μ� ��ȸ �� �μ��� ���� ��ȸ");
				status = 51;
			} else if (input == 2) {// 5-2
				System.out.println("\n�� �μ� �߰�");
				status = 52;
			} else if (input == 3) {// 5-2
				System.out.println("\n�� �μ� ����");
				status = 53;
			}
		} else if (input == 6) {// 6. ��ü����
			System.out.println("\n�� ��ü ����");
			System.out.println("1. ��ü ��ȸ\n" + "2. ��ü �߰�\n3. ��ü ����");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {
				System.out.println("\n�� ��ü ��ȸ");
				status = 61;
			} else if (input == 2) {
				System.out.println("\n�� ��ü �߰�");
				status = 62;
			} else if (input == 3) {
				System.out.println("\n�� ��ü ����");
				status = 63;
			}
		} else if (input == 7) {// 7. ������
			System.out.println("\n�� �� ����");
			System.out.println("1. �� ��ȸ\n" + "2. �� �߰�\n3. �� ����");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {//
				System.out.println("\n�� �� ��ȸ");
				status = 71;
			} else if (input == 2) {
				System.out.println("\n�� �� �߰�");
				status = 72;
			} else if (input == 3) {
				System.out.println("\n�� �� ����");
				status = 73;
			}
		}

		return status;
	}

	public static void CUSTOMERview() {
		try {
			rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
			System.out.println("�� ��ȣ	�ֹε�Ϲ�ȣ	�̸�	����	��ȭ��ȣ");
			while (rs.next())
				System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
						+ rs.getString(4) + "	" + rs.getString(5));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void CATEGORYview() {
		try {
			System.out.println("��ȣ	ī�װ���");
			rs = stmt.executeQuery("SELECT *  FROM CATEGORY");
			while (rs.next())
				System.out.println(rs.getString(1) + "	" + rs.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void HPview() {// ��� ���� ��ȸ �ٸ���
		try {
			rs = stmt.executeQuery(
					"SELECT HP.HPsn, HP.ProductName, HP.Price, C.Casn, C.CategoryName, S.Ssn, S.SupplyerName FROM HANDLING_PRODUCT HP, SUPPLYER S, CATEGORY C WHERE HP.Ssn=S.Ssn AND HP.Casn=C.Casn");
			System.out.println("��ȣ	                  ��ǰ �̸�	                 ����	ī�װ� ��ȣ	ī�װ���	��ü ��ȣ	��ü��");
			while (rs.next())
				if (rs.getString(1).length() < 5 && rs.getString(2).length() < 5)
					System.out.println(rs.getString(1) + "       	" + rs.getString(2) + "       	" + rs.getString(3)
							+ "	" + rs.getString(4) + "   	" + rs.getString(5) + "	" + rs.getString(6) + "	"
							+ rs.getString(7));
				else if (rs.getString(1).length() < 5 && rs.getString(2).length() < 10)
					System.out.println(rs.getString(1) + "       	" + rs.getString(2) + "     	" + rs.getString(3)
							+ "	" + rs.getString(4) + "   	" + rs.getString(5) + "	" + rs.getString(6) + "	"
							+ rs.getString(7));
				else if (rs.getString(1).length() < 5 && rs.getString(2).length() >= 10)
					System.out.println(rs.getString(1) + "       	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "   	" + rs.getString(5) + "	" + rs.getString(6) + "	"
							+ rs.getString(7));

				else if (rs.getString(1).length() < 10 && rs.getString(2).length() < 5)
					System.out.println(rs.getString(1) + "     	" + rs.getString(2) + "       	" + rs.getString(3)
							+ "	" + rs.getString(4) + "   	" + rs.getString(5) + "	" + rs.getString(6) + "	"
							+ rs.getString(7));
				else if (rs.getString(1).length() < 10 && rs.getString(2).length() < 10)
					System.out.println(rs.getString(1) + "     	" + rs.getString(2) + "     	" + rs.getString(3)
							+ "	" + rs.getString(4) + "   	" + rs.getString(5) + "	" + rs.getString(6) + "	"
							+ rs.getString(7));
				else if (rs.getString(1).length() < 10 && rs.getString(2).length() >= 10)
					System.out.println(rs.getString(1) + "     	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "   	" + rs.getString(5) + "	" + rs.getString(6) + "	"
							+ rs.getString(7));

				else if (rs.getString(1).length() >= 10 && rs.getString(2).length() < 5)
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "       	" + rs.getString(3) + "	"
							+ rs.getString(4) + "   	" + rs.getString(5) + "	" + rs.getString(6) + "	"
							+ rs.getString(7));
				else if (rs.getString(1).length() >= 10 && rs.getString(2).length() < 10)
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "     	" + rs.getString(3) + "	"
							+ rs.getString(4) + "   	" + rs.getString(5) + "	" + rs.getString(6) + "	"
							+ rs.getString(7));
				else if (rs.getString(1).length() >= 10 && rs.getString(2).length() < 5)
					System.out.println(
							rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	" + rs.getString(4)
									+ "   	" + rs.getString(5) + "	" + rs.getString(6) + "	" + rs.getString(7));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void EMPview() {
		try {
			rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
			System.out.println("�����ȣ	�ֹε�Ϲ�ȣ	�̸�	����	��ȭ��ȣ		�μ�");
			while (rs.next())
				System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
						+ rs.getString(4) + "	" + rs.getString(5) + "	" + rs.getString(6));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

// systemctl disable firewalld
// systemctl enable iptables
// systemctl restart iptables
