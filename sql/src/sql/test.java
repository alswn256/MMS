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
			stmt = con.createStatement();// MMS 담김
		} catch (Exception e) {
			System.out.println(e);
		}

		// con.close();

		status = menu(status);// 메뉴 받아옴
		/*
		 * 111 : 카테고리 조회, 112 : 카테고리 추가
		 * 
		 * 121 : 취급 품목 조회 , 122 : 취급 품목 추가
		 * 
		 * 131 : 입고 품목 조회 , 132 : 입고 품목 추가
		 * 
		 * 21 : 주문조회, 22 : 주문 추가
		 * 
		 * 3 : 판매 조회
		 * 
		 * 41 : 직원 조회 , 42 : 직원 추가
		 * 
		 * 51 : 업체 관리 52 : 업체 조회
		 */
		if (status == 111) {// 카테고리 조회
			try {
				stmt = con.createStatement();// MMS 담김
				rs = stmt.executeQuery("SELECT * FROM CATEGORY");// 쿼리문으로 받아온 결과 담김
				System.out.println("번호	이름");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 112) {// 카테고리 추가
			String Casn = sc.next();
			String CategoryName = sc.next();
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO CATEGORY VALUES('" + Casn + "', '" + CategoryName + "');");
				System.out.println(Casn +" 카테고리 등록 완료");
				
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 41) {
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
				System.out.println("사원번호	주민등록번호	이름	성별	전화번호		부서");
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
		System.out.println("<< Mart Management System >>\n" + "1. 물품 관리\n" + "2. 주문 관리\n" + "3. 판매 조회\n" + "4. 직원 관리\n"
				+ "5. 업체 관리\n");
		sc = new Scanner(System.in);
		int input = sc.nextInt();

		if (input == 1) {// 1. 물품관리
			System.out.println("1. 카테고리\n" + "2. 취급 품목\n" + "3. 입고 품목\n");
			input = sc.nextInt();
			if (input == 1) {// 1-1. 카테고리
				System.out.println("1. 카테고리 조회\n2. 카테고리 추가\n");
				input = sc.nextInt();
				if (input == 1) {// 1-1-1
					System.out.println("카테고리 조회");
					status = 111;
				} else if (input == 2) {// 1-1-2
					System.out.println("카테고리 추가");
					status = 112;
				}
			} else if (input == 2) {// 1-2. 취급 품목
				System.out.println("1. 취급 품목 조회\n2. 취급 품목 추가\n");
				input = sc.nextInt();
				if (input == 1) {// 1-2-1
					System.out.println("취급 품목 조회");
					status = 121;
				} else if (input == 2) {// 1-2-2
					System.out.println("취급 품목 추가");
					status = 122;
				}
			} else if (input == 3) {// 1-3. 입고 품목
				System.out.println("1. 입고 품목 조회\n2. 입고 품목 추가\n");
				input = sc.nextInt();
				if (input == 1) {// 1-3-1
					System.out.println("입고 품목 조회");
					status = 131;
				} else if (input == 2) {// 1-3-2
					System.out.println("입고 품목 추가");
					status = 132;
				}
			}
		} else if (input == 2) {// 2. 주문관리
			System.out.println("1. 주문 조회\n" + "2. 주문 추가\n");
			input = sc.nextInt();
			if (input == 1) {// 2-1
				System.out.println("주문 조회");
				status = 21;
			} else if (input == 2) {// 2-2
				System.out.println("주문 추가");
				status = 22;
			}
		} else if (input == 3) {// 3. 판매조회
			System.out.println("판매 조회");
			status = 3;
		} else if (input == 4) {// 4. 직원관리
			System.out.println("1. 직원 조회\n" + "2. 직원 추가\n");
			input = sc.nextInt();
			if (input == 1) {// 4-1. 직원조회
				System.out.println("직원 조회");
				status = 41;
			} else if (input == 2) {// 4-2
				System.out.println("직원 추가");
				status = 42;
			}
		} else if (input == 5) {// 5. 업체관리
			System.out.println("1. 업체 조회\n" + "2. 업체 추가\n");
			input = sc.nextInt();
			if (input == 1) {// 5-1. 업체조회
				System.out.println("업체 조회");
				status = 51;
			} else if (input == 2) {// 5-2
				System.out.println("업체 추가");
				status = 52;
			}
		}
		return status;

	}
}

// systemctl disable firewalld
// systemctl enable iptables
// systemctl restart iptables
