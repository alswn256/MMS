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
	static Scanner sc;

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

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.56.102:4567/MMS", "lmj", "1234");
			stmt = con.createStatement();// MMS ���
		} catch (Exception e) {
			System.out.println(e);
		}

		status = menu(status);// �޴� �޾ƿ�
		/*
		 * 111 : ī�װ� ��ȸ, 112 : ī�װ� �߰�
		 * 
		 * 121 : ��� ��ǰ ��ȸ , 122 : ��� ��ǰ �߰�
		 * 
		 * 131 : �԰� ��ǰ ��ȸ , 132 : �԰� ��ǰ �߰�
		 * 
		 * 21 : �ֹ���ȸ, 22 : �ֹ� �߰�
		 * 
		 * 3 : �Ǹ� ��ȸ
		 * 
		 * 41 : ���� ��ȸ, 42 : ������ġ�� ���� �ڰ��� ��ȸ 43 : ���� �߰� 51 : �μ� ��ȸ, 52 : �μ� �߰� 61 : ��ü ��ȸ
		 * 
		 * 51 : �μ� ��ȸ, 51 : �μ� �߰�
		 * 
		 * 62 : ��ü �߰�
		 */
		if (status == 111) {// ī�װ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM CATEGORY");// ���������� �޾ƿ� ��� ���
				System.out.println("��ȣ	�̸�");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 112) {// ī�װ� �߰�
			Casn = sc.next();
			CategoryName = sc.next();
			try {
				stmt.executeUpdate("INSERT INTO CATEGORY VALUES('" + Casn + "', '" + CategoryName + "')");
				System.out.println(Casn + " ī�װ� �߰� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 121) {// ��� ��ǰ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("��ȣ	��ǰ �̸�	����	ī�װ�	���޾�ü");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 122) {// ��ݹ�ǰ �߰�
			HPsn = sc.next();
			HPname = sc.next();
			HPprice = sc.nextInt();
			CategoryName = sc.next();
			Ssn = sc.next();
			try {
				stmt.executeUpdate("INSERT INTO HANDLING_PRODUCT VALUES('" + HPsn + "', '" + HPname + "', " + HPprice
						+ ", '" + CategoryName + "', '" + Ssn + "')");
				System.out.println(HPsn + " ��� ��ǰ �߰� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 131) {// �԰� ��ǰ ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM INCOME_PRODUCT");
				System.out.println("��ȣ	�������	��� ��ǰ ��ȣ");
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
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("��ȣ	��ǰ �̸�	����	ī�װ�	���޾�ü");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));

				System.out.print("��� ��ǰ ��ȣ : ");
				HPsn = sc.next();

				rs = stmt.executeQuery("SELECT Casn FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				String thisCasn = null;
				while (rs.next()) {
					thisCasn = rs.getString("Casn");
				}
				if (thisCasn.equals("C0001")) {// ī�װ��� ��ǰ�� ���
					System.out.println("������� : ");
					SelfLife = sc.next();
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', '" + SelfLife + "', '"
							+ HPsn + "');");
					con.close();
				} else {
					System.out.println("��ǰ ī�װ��� �ƴϹǷ� ��������� �Է����� �ʽ��ϴ�.");
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', NULL, '" + HPsn + "')");
				}
				System.out.println(newIPsn + " �԰� ��ǰ �߰� �Ϸ�");
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
				System.out.println("< ��� ��ǰ ��� >");
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("��ȣ	��ǰ �̸�	����	ī�װ�	���޾�ü");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));
				System.out.print("��� ��ǰ ��ȣ : ");
				HPsn = sc.next();
				rs = stmt.executeQuery("SELECT Ssn FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				String thisSsn = null;
				while (rs.next()) {
					thisSsn = rs.getString("Ssn");
				}
				System.out.print("��� ��ȣ : ");
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
		} else if (status == 3) {// �Ǹ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM BUY");
				System.out.println("��� ��ǰ ��ȣ	�԰� ��ǰ ��ȣ	ī�װ�	��¥	�ð�");
				while (rs.next())
					System.out.println(String.format("%15s", rs.getString(1)) + "	" + rs.getString(2) + "	"
							+ rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));
				System.out.print("��� ��ǰ ��ȣ : ");
				HPsn = sc.next();
				System.out.print("���޾�ü ��ȣ : ");
				Ssn = sc.next();
				System.out.print("��� ��ȣ : ");
				Esn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO ORDERS VALUES('" + HPsn + "', '" + Ssn + "', '" + Esn + "', '"
						+ now_date + "', '" + now_time + "')");
				System.out.println(HPsn + " �ֹ� �Ϸ�");
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
				rs = stmt.executeQuery("SELECT * FROM EMP_CERTIFICATION");
				System.out.println("���	�ڰ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 43) {// ���� �߰�
			System.out.print("�̸� : ");
			Ename = sc.next();
			System.out.print("�ֹε�Ϲ�ȣ : ");
			Errn = sc.next();
			System.out.print("����(��/��) : ");
			Esex = sc.next();
			System.out.print("�޴���ȭ��ȣ(-���� �Է�) : ");
			Ephone = sc.next();

			try {
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("��ȣ	�μ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.print("�μ�");
			Dsn = sc.next();

			String thisEsn = null;
			try {
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
				System.out.println(okEsn + " ���� �߰� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 51) {// �μ� ��ȸ
			try {
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("�μ� ��ȣ	�μ���");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 52) {// �μ� �߰�
			String Dname = sc.next();
			try {
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
				System.out.println("���޾�ü ��ȣ	���޾�ü��	��ȭ��ȣ");
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
				System.out.print("���޾�ü�� : ");
				Stel = sc.next();
				System.out.print("��ȭ��ȣ : ");
				Stel = sc.next();
				
				stmt.executeUpdate("INSERT INTO SUPPLYER VALUES('" + okSsn + "', '" + Sname + "', '" + Stel + "')");
				System.out.println(okSsn + " ���޾�ü �߰� �Ϸ�");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static int menu(int status) {
		status = 0;
		System.out.println("<< Mart Management System >>\n" + "1. ��ǰ ����\n" + "2. �ֹ� ����\n" + "3. �Ǹ� ��ȸ\n" + "4. ���� ����\n"
				+ "5. �μ� ����\n" + "6. ��ü ����\n");
		sc = new Scanner(System.in);
		int input = sc.nextInt();

		if (input == 1) {// 1. ��ǰ����
			System.out.println("1. ī�װ�\n" + "2. ��� ��ǰ\n" + "3. �԰� ��ǰ\n");
			input = sc.nextInt();
			if (input == 1) {// 1-1. ī�װ�
				System.out.println("1. ī�װ� ��ȸ\n2. ī�װ� �߰�\n");
				input = sc.nextInt();
				if (input == 1) {// 1-1-1
					System.out.println("ī�װ� ��ȸ");
					status = 111;
				} else if (input == 2) {// 1-1-2
					System.out.println("ī�װ� �߰�");
					status = 112;
				}
			} else if (input == 2) {// 1-2. ��� ��ǰ
				System.out.println("1. ��� ��ǰ ��ȸ\n2. ��� ��ǰ �߰�\n");
				input = sc.nextInt();
				if (input == 1) {// 1-2-1
					System.out.println("��� ��ǰ ��ȸ");
					status = 121;
				} else if (input == 2) {// 1-2-2
					System.out.println("��� ��ǰ �߰�");
					status = 122;
				}
			} else if (input == 3) {// 1-3. �԰� ��ǰ
				System.out.println("1. �԰� ��ǰ ��ȸ\n2. �԰� ��ǰ �߰�\n");
				input = sc.nextInt();
				if (input == 1) {// 1-3-1
					System.out.println("�԰� ��ǰ ��ȸ");
					status = 131;
				} else if (input == 2) {// 1-3-2
					System.out.println("�԰� ��ǰ �߰�");
					status = 132;
				}
			}
		} else if (input == 2) {// 2. �ֹ�����
			System.out.println("1. �ֹ� ��ȸ\n" + "2. �ֹ� �߰�\n");
			input = sc.nextInt();
			if (input == 1) {// 2-1
				System.out.println("�ֹ� ��ȸ");
				status = 21;
			} else if (input == 2) {// 2-2
				System.out.println("�ֹ� �߰�");
				status = 22;
			}
		} else if (input == 3) {// 3. �Ǹ���ȸ
			System.out.println("�Ǹ� ��ȸ");
			status = 3;
		} else if (input == 4) {// 4. ��������
			System.out.println("1. ���� ��ȸ\n" + "2. ������ġ�� ���� ���� �ڰ��� ��ȸ\n" + "3. ���� �߰�\n");
			input = sc.nextInt();
			if (input == 1) {// 4-1. ������ȸ
				System.out.println("���� �ſ� ��ȸ");
				status = 41;
			} else if (input == 2) {// 4-2
				System.out.println("������ġ�� ���� ���� �ڰ��� ��ȸ");
				status = 42;
			} else if (input == 3) {// 4-3
				System.out.println("���� �߰�");
				status = 43;
			}
		} else if (input == 5) {// 5. �μ�����
			System.out.println("1. �μ� ��ȸ\n" + "2. �μ� �߰�\n");
			input = sc.nextInt();
			if (input == 1) {// 5-1. �μ���ȸ
				System.out.println("< �μ� ��ȸ >");
				status = 51;
			} else if (input == 2) {// 5-2
				System.out.println("< �μ� �߰� >");
				status = 52;
			}

		} else if (input == 6) {// 6. ��ü����
			System.out.println("1. ��ü ��ȸ\n" + "2. ��ü �߰�\n");
			input = sc.nextInt();
			if (input == 1) {// 6-1. ��ü��ȸ
				System.out.println("< ��ü ��ȸ >");
				status = 61;
			} else if (input == 2) {// 6-2
				System.out.println("< ��ü �߰� >");
				status = 62;
			}
		}
		return status;

	}
}

// systemctl disable firewalld
// systemctl enable iptables
// systemctl restart iptables
