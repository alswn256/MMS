package sql;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Random;

public class test {
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	static Scanner sc = new Scanner(System.in);;

	public static void main(String args[]) {
		int status = 0;// ������ �޴�

		Random rnd;

		String Esn = null;
		String Errn = null;
		String Ename = null;
		String Esex = null;
		String Ephone = null;
		String Casn = null;
		String CategoryName = null;
		String HPsn = null;
		String HPname = null;
		int HPprice = 0;
		String Ssn = null;
		String Sname = null;
		String Stel = null;
		String IPsn = null;
		String SelfLife = null;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String now_date;
		String now_time;
		String Dsn = null;
		String Csn = null;
		String Cname = null;
		String Crrn = null;
		String Csex = null;
		String Cphone = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.56.102:4567/MMS", "lmj", "1234");
			stmt = con.createStatement();// MMS ���
		} catch (Exception e) {
			System.out.println(e);
		}

		status = menu(status);

		if (status == 111) {// ī�װ� �� ��� ��ȸ
			try {
				rs = stmt.executeQuery(
						"SELECT C.Casn, CategoryName, E.Esn, Name  FROM CATEGORY C LEFT JOIN MANAGE M ON C.Casn=M.Casn LEFT JOIN EMPLOYEE E ON E.Esn=M.Esn");
				System.out.println("ī�װ� ��ȣ	ī�װ���	��� ���� ���	������� �̸�");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	" + rs.getString(4));

				con.close();
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
				System.out.println("�� " + okCasn + " " + CategoryName + " ī�װ� �߰� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 121) {// ��� ��ǰ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("��ȣ	��ǰ �̸�	����	ī�װ�	���޾�ü");
				while (rs.next())
					// System.out.println(getLPad(rs.getString(1), 20, " ")+"
					// "+getLPad(rs.getString(2), 20, " ")+ " " + rs.getString(3) + " " +
					// rs.getString(4) + " " + rs.getString(5));

					System.out.println(getRPad(rs.getString(1), 15, " ") + "	" + rs.getString(2) + ""// getRPad(rs.getString(2),
																										// 40, " ")
							+ getLPad(rs.getString(3), 10, " ") + "	" + rs.getString(4) + "	" + rs.getString(5));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 122) {// ��ݹ�ǰ �߰�
			try {
				System.out.print("�� ��� ��ǰ ��ȣ(�� ��ȣ) : ");
				HPsn = sc.next();
				System.out.print("�� ��� ��ǰ �̸�(�𵨸�) : ");

				Scanner scanner = new Scanner(System.in);
				HPname = scanner.nextLine();

				System.out.print("�� ���� : ");
				HPprice = sc.nextInt();

				rs = stmt.executeQuery("SELECT * FROM CATEGORY");
				System.out.println("\nī�װ� ��ȣ	ī�װ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n�� ī�װ� : ");
				CategoryName = sc.next();

				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("\n���޾�ü ��ȣ	���޾�ü��");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n�� ���޾�ü : ");
				Ssn = sc.next();

				stmt.executeUpdate("INSERT INTO HANDLING_PRODUCT VALUES('" + HPsn + "', '" + HPname + "', " + HPprice
						+ ", '" + CategoryName + "', '" + Ssn + "')");
				System.out.println("\n�� " + HPsn + " " + HPname + " - ��� ��ǰ �߰� �Ϸ�");
				con.close();
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
				con.close();
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
				rs = stmt.executeQuery(
						"SELECT HP.HPsn, HP.ProductName, HP.Price, C.CategoryName, S.SupplyerName FROM HANDLING_PRODUCT HP, SUPPLYER S, CATEGORY C WHERE HP.Ssn=S.Ssn AND HP.Casn=C.Casn");
				System.out.println("\n��ȣ	��ǰ �̸�	����	ī�װ�	���޾�ü");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));

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
					con.close();
				} else {
					System.out.println("\n�� ��ǰ ī�װ��� �ƴϹǷ� ��������� �Է����� �ʽ��ϴ�.\n");
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', NULL, '" + HPsn + "')");
				}
				System.out.println("\n�� " + HPsn + "�� �԰� ��ǰ ��ȣ�� " + newIPsn + "�� ���� �� �߰� �Ϸ�");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 21) {// �ֹ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM ORDERS");
				System.out.println("��� ��ǰ ��ȣ	���޾�ü ��ȣ	��� ��ȣ	��¥	�ð�");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
				con.close();
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
				System.out.print("�� ��� ��ȣ : ");
				Esn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO ORDERS VALUES('" + HPsn + "', '" + thisSsn + "', '" + Esn + "', '"
						+ now_date + "', '" + now_time + "');");
				System.out.println(HPsn + " �ֹ� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 31) {// �Ǹ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM BUY");
				System.out.println("��� ��ǰ ��ȣ	�԰� ��ǰ ��ȣ	ī�װ� ��ȣ	��¥	�ð�");
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
				System.out.print("�� ���� �� ��ȣ : ");
				Csn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO BUY VALUES('" + thisHPsn + "', '" + IPsn + "', '" + Csn + "', '"
						+ now_date + "', '" + now_time + "')");
				stmt.executeUpdate("DELETE FROM INCOME_PRODUCT WHERE IPsn='" + IPsn + "'");
				System.out.println("\n�� " + thisHPname + " " + IPsn + " " + now_date + " " + now_time + " �Ǹ� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		} else if (status == 41) {// ���� �ſ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
				System.out.println("�����ȣ	�ֹε�Ϲ�ȣ	�̸�	����	��ȭ��ȣ		�μ�");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5) + "	" + rs.getString(6));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 42) {// ���� �ڰ��� ��ȸ
			try {
				rs = stmt.executeQuery(
						"SELECT E.Esn, Name, Certification FROM EMPLOYEE E, EMP_CERTIFICATION EC WHERE E.Esn=EC.Esn");
				System.out.println("���	�̸�	���� �ڰ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3));
				con.close();
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
				con.close();
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
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 52) {// �μ� �߰�
			try {
				System.out.print("�� �߰� �μ��� :");
				String Dname = sc.next();
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
				System.out.println(okDsn + " �μ� �߰� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 61) {// ���޾�ü ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("��ü ��ȣ	��ü��	��ȭ��ȣ");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3));
				con.close();
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
				System.out.println("\n�� ���޾�ü " + Sname + "�� '" + okSsn + "'��ȣ�� �Ҵ��� �߰� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 71) {// ���� �ſ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
				System.out.println("����ȣ	�ֹε�Ϲ�ȣ	�̸�	����	��ȭ��ȣ");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
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
				System.out.println(Cname+"�� ;"+okCsn + "'��ȣ�� �Ҵ��� �� �߰� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static int menu(int status) {
		status = 0;
		System.out.println("�� Mart Management System\n\n" + "1. ��ǰ ����\n" + "2. �ֹ� ����\n" + "3. �Ǹ� ����\n" + "4. ���� ����\n"
				+ "5. �μ� ����\n" + "6. ��ü ����\n" + "7. �� ����\n");
		// sc = new Scanner(System.in);
		System.out.print("�� ���� : ");
		int input = sc.nextInt();
		if (input == 1) {// 1. ��ǰ����
			System.out.println("\n�� ��ǰ ����");
			System.out.println("1. ī�װ� ����\n" + "2. ��� ��ǰ ����\n" + "3. �԰� ��ǰ ����\n");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {// 1-1. ī�װ�
				System.out.println("\n�� ī�װ� ����");
				System.out.println("1. ī�װ� �� ��� ��ȸ\n2. ī�װ� �߰�\n");
				System.out.print("�� ���� : ");
				input = sc.nextInt();
				if (input == 1) {// 1-1-1
					System.out.println("\n�� ī�װ� �� ��� ��ȸ");
					status = 111;
				} else if (input == 2) {// 1-1-2
					System.out.println("\n�� ī�װ� �߰�");
					status = 112;
				}
			} else if (input == 2) {// 1-2. ��� ��ǰ
				System.out.println("\n�� ��� ��ǰ ����");
				System.out.println("1. ��� ��ǰ ��ȸ\n2. ��� ��ǰ �߰�\n");
				System.out.print("�� ���� : ");
				input = sc.nextInt();
				if (input == 1) {// 1-2-1
					System.out.println("\n�� ��� ��ǰ ��ȸ");
					status = 121;
				} else if (input == 2) {// 1-2-2
					System.out.println("\n�� ��� ��ǰ �߰�");
					status = 122;
				}
			} else if (input == 3) {// 1-3. �԰� ��ǰ
				System.out.println("\n�� �԰� ��ǰ ����");
				System.out.println("1. �԰� ��ǰ ��ȸ\n2. �԰� ��ǰ �߰�\n");
				System.out.print("�� ���� : ");
				input = sc.nextInt();
				if (input == 1) {// 1-3-1
					System.out.println("\n�� �԰� ��ǰ ��ȸ");
					status = 131;
				} else if (input == 2) {// 1-3-2
					System.out.println("\n�� �԰� ��ǰ �߰� (���ڵ� ��ȣ �Է�)");
					status = 132;
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
			System.out.println("1. ���� ��ȸ\n" + "2. ������ġ�� ���� ���� �ڰ��� ��ȸ\n" + "3. ���� �߰�\n");
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
			}
		} else if (input == 5) {// 5. �μ�����
			System.out.println("\n�� �μ� ����");
			System.out.println("1. �μ� �� �μ��� �Ҽ� ���� ��ȸ\n" + "2. �μ� �߰�\n");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {// 5-1. �μ���ȸ
				System.out.println("\n�� �μ� ��ȸ �� �μ��� ���� ��ȸ");
				status = 51;
			} else if (input == 2) {// 5-2
				System.out.println("\n�� �μ� �߰�");
				status = 52;
			}
		} else if (input == 6) {// 6. ��ü����
			System.out.println("\n�� ��ü ����");
			System.out.println("1. ��ü ��ȸ\n" + "2. ��ü �߰�\n");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {// 6-1. ��ü��ȸ
				System.out.println("\n�� ��ü ��ȸ");
				status = 61;
			} else if (input == 2) {// 6-2
				System.out.println("\n�� ��ü �߰�");
				status = 62;
			}
		} else if (input == 7) {// 7. ������
			System.out.println("\n�� �� ����");
			System.out.println("1. �� ��ȸ\n" + "2. �� �߰�\n");
			System.out.print("�� ���� : ");
			input = sc.nextInt();
			if (input == 1) {//
				System.out.println("\n�� �� ��ȸ");
				status = 71;
			} else if (input == 2) {
				System.out.println("\n�� �� �߰�");
				status = 72;
			}
		}
		return status;

	}

	public static String getLPad(String str, int size, String strFillText) { // Fill string blanks
		for (int i = (str.getBytes()).length; i < size; i++) {
			str = strFillText + str;
		}
		return str;
	}

	public static String getRPad(String str, int size, String strFillText) { // Fill string blanks
		for (int i = (str.getBytes()).length; i < size; i++) {
			str += strFillText;
		}
		return str;
	}

	public static String appendSpace(String str, int len) {
		int length = str.getBytes().length;
		String tempStr = str;

		if (length < len) {
			int endCount = len - length;
			for (int i = 0; i < endCount; i++) {
				str = str + " ";
			}
		} else if (length > len) {
			byte[] temp = new byte[len];
			System.arraycopy(str.getBytes(), 0, temp, 0, len);
			str = new String(temp);
		} else {
		}

		if (str.length() == 0) {
			byte[] temp = new byte[len];
			System.arraycopy(tempStr.getBytes(), 0, temp, 0, len - 1);
			str = new String(temp);
		}

		return str;
	}

}

// systemctl disable firewalld
// systemctl enable iptables
// systemctl restart iptables
