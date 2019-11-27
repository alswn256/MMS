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
		int status = 0;// 선택한 메뉴

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
			stmt = con.createStatement();// MMS 담김
		} catch (Exception e) {
			System.out.println(e);
		}

		status = menu(status);// 메뉴 받아옴
		/*
		 * 111 : 카테고리 조회, 112 : 카테고리 추가
		 * 
		 * 121 : 취급 물품 조회 , 122 : 취급 물품 추가
		 * 
		 * 131 : 입고 물품 조회 , 132 : 입고 물품 추가
		 * 
		 * 21 : 주문조회, 22 : 주문 추가
		 * 
		 * 3 : 판매 조회
		 * 
		 * 41 : 직원 조회, 42 : 업무배치를 위한 자격증 조회 43 : 직원 추가 51 : 부서 조회, 52 : 부서 추가 61 : 업체 조회
		 * 
		 * 51 : 부서 조회, 51 : 부서 추가
		 * 
		 * 62 : 업체 추가
		 */
		if (status == 111) {// 카테고리 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM CATEGORY");// 쿼리문으로 받아온 결과 담김
				System.out.println("번호	이름");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 112) {// 카테고리 추가
			Casn = sc.next();
			CategoryName = sc.next();
			try {
				stmt.executeUpdate("INSERT INTO CATEGORY VALUES('" + Casn + "', '" + CategoryName + "')");
				System.out.println(Casn + " 카테고리 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 121) {// 취급 물품조회
			try {
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("번호	물품 이름	가격	카테고리	공급업체");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 122) {// 취금물품 추가
			HPsn = sc.next();
			HPname = sc.next();
			HPprice = sc.nextInt();
			CategoryName = sc.next();
			Ssn = sc.next();
			try {
				stmt.executeUpdate("INSERT INTO HANDLING_PRODUCT VALUES('" + HPsn + "', '" + HPname + "', " + HPprice
						+ ", '" + CategoryName + "', '" + Ssn + "')");
				System.out.println(HPsn + " 취급 물품 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 131) {// 입고 물품 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM INCOME_PRODUCT");
				System.out.println("번호	유통기한	취급 물품 번호");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + String.format("%10s", rs.getString(2)) + "	" + rs.getString(3));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 132) {// 입고 물품 추가
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
				System.out.println("번호	물품 이름	가격	카테고리	공급업체");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));

				System.out.print("취급 물품 번호 : ");
				HPsn = sc.next();

				rs = stmt.executeQuery("SELECT Casn FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				String thisCasn = null;
				while (rs.next()) {
					thisCasn = rs.getString("Casn");
				}
				if (thisCasn.equals("C0001")) {// 카테고리가 식품일 경우
					System.out.println("유통기한 : ");
					SelfLife = sc.next();
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', '" + SelfLife + "', '"
							+ HPsn + "');");
					con.close();
				} else {
					System.out.println("식품 카테고리가 아니므로 유통기한은 입력하지 않습니다.");
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', NULL, '" + HPsn + "')");
				}
				System.out.println(newIPsn + " 입고 물품 추가 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 21) {// 주문 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM ORDERS");
				System.out.println("취급 물품 번호	공급업체 번호	사원 번호	날짜	시간");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 22) {// 주문 추가
			try {
				System.out.println("< 취급 물품 목록 >");
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("번호	물품 이름	가격	카테고리	공급업체");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));
				System.out.print("취급 물품 번호 : ");
				HPsn = sc.next();
				rs = stmt.executeQuery("SELECT Ssn FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				String thisSsn = null;
				while (rs.next()) {
					thisSsn = rs.getString("Ssn");
				}
				System.out.print("사원 번호 : ");
				Esn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO ORDERS VALUES('" + HPsn + "', '" + thisSsn + "', '" + Esn + "', '"
						+ now_date + "', '" + now_time + "');");
				System.out.println(HPsn + " 주문 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 3) {// 판매 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM BUY");
				System.out.println("취급 물품 번호	입고 물품 번호	카테고리	날짜	시간");
				while (rs.next())
					System.out.println(String.format("%15s", rs.getString(1)) + "	" + rs.getString(2) + "	"
							+ rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));
				System.out.print("취급 물품 번호 : ");
				HPsn = sc.next();
				System.out.print("공급업체 번호 : ");
				Ssn = sc.next();
				System.out.print("사원 번호 : ");
				Esn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO ORDERS VALUES('" + HPsn + "', '" + Ssn + "', '" + Esn + "', '"
						+ now_date + "', '" + now_time + "')");
				System.out.println(HPsn + " 주문 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 41) {// 직원 신원 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
				System.out.println("사원번호	주민등록번호	이름	성별	전화번호		부서");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5) + "	" + rs.getString(6));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 42) {// 직원 자격증 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM EMP_CERTIFICATION");
				System.out.println("사번	자격증");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 43) {// 직원 추가
			System.out.print("이름 : ");
			Ename = sc.next();
			System.out.print("주민등록번호 : ");
			Errn = sc.next();
			System.out.print("성별(남/여) : ");
			Esex = sc.next();
			System.out.print("휴대전화번호(-포함 입력) : ");
			Ephone = sc.next();

			try {
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("번호	부서명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.print("부서");
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
				System.out.println(okEsn + " 직원 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 51) {// 부서 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("부서 번호	부서명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 52) {// 부서 추가
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
				System.out.println(okDsn + " 부서 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 61) {// 공급업체 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("공급업체 번호	공급업체명	전화번호");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 62) {// 공급업체 추가
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
				System.out.print("공급업체명 : ");
				Stel = sc.next();
				System.out.print("전화번호 : ");
				Stel = sc.next();
				
				stmt.executeUpdate("INSERT INTO SUPPLYER VALUES('" + okSsn + "', '" + Sname + "', '" + Stel + "')");
				System.out.println(okSsn + " 공급업체 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static int menu(int status) {
		status = 0;
		System.out.println("<< Mart Management System >>\n" + "1. 물품 관리\n" + "2. 주문 관리\n" + "3. 판매 조회\n" + "4. 직원 관리\n"
				+ "5. 부서 관리\n" + "6. 업체 관리\n");
		sc = new Scanner(System.in);
		int input = sc.nextInt();

		if (input == 1) {// 1. 물품관리
			System.out.println("1. 카테고리\n" + "2. 취급 물품\n" + "3. 입고 물품\n");
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
			} else if (input == 2) {// 1-2. 취급 물품
				System.out.println("1. 취급 물품 조회\n2. 취급 물품 추가\n");
				input = sc.nextInt();
				if (input == 1) {// 1-2-1
					System.out.println("취급 물품 조회");
					status = 121;
				} else if (input == 2) {// 1-2-2
					System.out.println("취급 물품 추가");
					status = 122;
				}
			} else if (input == 3) {// 1-3. 입고 물품
				System.out.println("1. 입고 물품 조회\n2. 입고 물품 추가\n");
				input = sc.nextInt();
				if (input == 1) {// 1-3-1
					System.out.println("입고 물품 조회");
					status = 131;
				} else if (input == 2) {// 1-3-2
					System.out.println("입고 물품 추가");
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
			System.out.println("1. 직원 조회\n" + "2. 업무배치를 위한 직원 자격증 조회\n" + "3. 직원 추가\n");
			input = sc.nextInt();
			if (input == 1) {// 4-1. 직원조회
				System.out.println("직원 신원 조회");
				status = 41;
			} else if (input == 2) {// 4-2
				System.out.println("업무배치를 위한 직원 자격증 조회");
				status = 42;
			} else if (input == 3) {// 4-3
				System.out.println("직원 추가");
				status = 43;
			}
		} else if (input == 5) {// 5. 부서관리
			System.out.println("1. 부서 조회\n" + "2. 부서 추가\n");
			input = sc.nextInt();
			if (input == 1) {// 5-1. 부서조회
				System.out.println("< 부서 조회 >");
				status = 51;
			} else if (input == 2) {// 5-2
				System.out.println("< 부서 추가 >");
				status = 52;
			}

		} else if (input == 6) {// 6. 업체관리
			System.out.println("1. 업체 조회\n" + "2. 업체 추가\n");
			input = sc.nextInt();
			if (input == 1) {// 6-1. 업체조회
				System.out.println("< 업체 조회 >");
				status = 61;
			} else if (input == 2) {// 6-2
				System.out.println("< 업체 추가 >");
				status = 62;
			}
		}
		return status;

	}
}

// systemctl disable firewalld
// systemctl enable iptables
// systemctl restart iptables
