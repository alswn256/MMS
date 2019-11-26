package sql;

import java.sql.*;
import java.util.Scanner;

public class test {
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	static Scanner sc;

	public static void main(String args[]) {
		int status = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.56.102:4567/MMS", "lmj", "1234");
			stmt = con.createStatement();// MMS ���
		} catch (Exception e) {
			System.out.println(e);
		}

		// con.close();

		status = menu(status);// �޴� �޾ƿ�
		/*
		 * 111 : ī�װ� ��ȸ, 112 : ī�װ� �߰�
		 * 
		 * 121 : ��� ǰ�� ��ȸ , 122 : ��� ǰ�� �߰�
		 * 
		 * 131 : �԰� ǰ�� ��ȸ , 132 : �԰� ǰ�� �߰�
		 * 
		 * 21 : �ֹ���ȸ, 22 : �ֹ� �߰�
		 * 
		 * 3 : �Ǹ� ��ȸ
		 * 
		 * 41 : ���� ��ȸ , 42 : ���� �߰�
		 * 
		 * 51 : ��ü ���� 52 : ��ü ��ȸ
		 */
		if (status == 111) {// ī�װ� ��ȸ
			try {
				stmt = con.createStatement();// MMS ���
				rs = stmt.executeQuery("SELECT * FROM CATEGORY");// ���������� �޾ƿ� ��� ���
				System.out.println("��ȣ	�̸�");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 112) {// ī�װ� �߰�
			String Casn = sc.next();
			String CategoryName = sc.next();
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO CATEGORY VALUES('" + Casn + "', '" + CategoryName + "');");
				System.out.println(Casn +" ī�װ� ��� �Ϸ�");
				
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 41) {
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
				System.out.println("�����ȣ	�ֹε�Ϲ�ȣ	�̸�	����	��ȭ��ȣ		�μ�");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5) + "	" + rs.getString(6));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	public static int menu(int status) {
		status = 0;
		System.out.println("<< Mart Management System >>\n" + "1. ��ǰ ����\n" + "2. �ֹ� ����\n" + "3. �Ǹ� ��ȸ\n" + "4. ���� ����\n"
				+ "5. ��ü ����\n");
		sc = new Scanner(System.in);
		int input = sc.nextInt();

		if (input == 1) {// 1. ��ǰ����
			System.out.println("1. ī�װ�\n" + "2. ��� ǰ��\n" + "3. �԰� ǰ��\n");
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
			} else if (input == 2) {// 1-2. ��� ǰ��
				System.out.println("1. ��� ǰ�� ��ȸ\n2. ��� ǰ�� �߰�\n");
				input = sc.nextInt();
				if (input == 1) {// 1-2-1
					System.out.println("��� ǰ�� ��ȸ");
					status = 121;
				} else if (input == 2) {// 1-2-2
					System.out.println("��� ǰ�� �߰�");
					status = 122;
				}
			} else if (input == 3) {// 1-3. �԰� ǰ��
				System.out.println("1. �԰� ǰ�� ��ȸ\n2. �԰� ǰ�� �߰�\n");
				input = sc.nextInt();
				if (input == 1) {// 1-3-1
					System.out.println("�԰� ǰ�� ��ȸ");
					status = 131;
				} else if (input == 2) {// 1-3-2
					System.out.println("�԰� ǰ�� �߰�");
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
			System.out.println("1. ���� ��ȸ\n" + "2. ���� �߰�\n");
			input = sc.nextInt();
			if (input == 1) {// 4-1. ������ȸ
				System.out.println("���� ��ȸ");
				status = 41;
			} else if (input == 2) {// 4-2
				System.out.println("���� �߰�");
				status = 42;
			}
		} else if (input == 5) {// 5. ��ü����
			System.out.println("1. ��ü ��ȸ\n" + "2. ��ü �߰�\n");
			input = sc.nextInt();
			if (input == 1) {// 5-1. ��ü��ȸ
				System.out.println("��ü ��ȸ");
				status = 51;
			} else if (input == 2) {// 5-2
				System.out.println("��ü �߰�");
				status = 52;
			}
		}
		return status;

	}
}

// systemctl disable firewalld
// systemctl enable iptables
// systemctl restart iptables
