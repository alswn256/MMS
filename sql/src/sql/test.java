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
		String Csn = null;
		String Cname = null;
		String Crrn = null;
		String Csex = null;
		String Cphone = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.56.102:4567/MMS", "lmj", "1234");
			stmt = con.createStatement();// MMS 담김
		} catch (Exception e) {
			System.out.println(e);
		}

		status = menu(status);

		if (status == 111) {// 카테고리 및 담당 조회
			try {
				rs = stmt.executeQuery(
						"SELECT C.Casn, CategoryName, E.Esn, Name  FROM CATEGORY C LEFT JOIN MANAGE M ON C.Casn=M.Casn LEFT JOIN EMPLOYEE E ON E.Esn=M.Esn");
				System.out.println("카테고리 번호	카테고리명	담당 직원 사번	담당직원 이름");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	" + rs.getString(4));

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 112) {// 카테고리 추가
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

				System.out.print("▷ 카테고리명 : ");
				CategoryName = sc.next();
				stmt.executeUpdate("INSERT INTO CATEGORY VALUES('" + okCasn + "', '" + CategoryName + "')");
				System.out.println("◈ " + okCasn + " " + CategoryName + " 카테고리 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 121) {// 취급 물품조회
			try {
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("번호	물품 이름	가격	카테고리	공급업체");
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
		} else if (status == 122) {// 취금물품 추가
			try {
				System.out.print("▷ 취급 물품 번호(모델 번호) : ");
				HPsn = sc.next();
				System.out.print("▷ 취급 물품 이름(모델명) : ");

				Scanner scanner = new Scanner(System.in);
				HPname = scanner.nextLine();

				System.out.print("▷ 가격 : ");
				HPprice = sc.nextInt();

				rs = stmt.executeQuery("SELECT * FROM CATEGORY");
				System.out.println("\n카테고리 번호	카테고리명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n▷ 카테고리 : ");
				CategoryName = sc.next();

				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("\n공급업체 번호	공급업체명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n▷ 공급업체 : ");
				Ssn = sc.next();

				stmt.executeUpdate("INSERT INTO HANDLING_PRODUCT VALUES('" + HPsn + "', '" + HPname + "', " + HPprice
						+ ", '" + CategoryName + "', '" + Ssn + "')");
				System.out.println("\n◈ " + HPsn + " " + HPname + " - 취급 물품 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 131) {// 입고 물품 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM INCOME_PRODUCT");
				System.out.println("번호	유통기한	입고 품목 번호");
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
				System.out.println("◈ 취급 물품 목록, 카테고리, 공급업체 정보◈");
				rs = stmt.executeQuery(
						"SELECT HP.HPsn, HP.ProductName, HP.Price, C.CategoryName, S.SupplyerName FROM HANDLING_PRODUCT HP, SUPPLYER S, CATEGORY C WHERE HP.Ssn=S.Ssn AND HP.Casn=C.Casn");
				System.out.println("\n번호	물품 이름	가격	카테고리	공급업체");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));

				System.out.print("\n▷ 취급 물품 번호 : ");
				HPsn = sc.next();

				rs = stmt.executeQuery("SELECT Casn FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				String thisCasn = null;
				while (rs.next()) {
					thisCasn = rs.getString("Casn");
				}
				if (thisCasn.equals("CA001")) {// 카테고리가 식품일 경우
					System.out.print("▷ 유통기한 : ");
					SelfLife = sc.next();
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', '" + SelfLife + "', '"
							+ HPsn + "');");
					con.close();
				} else {
					System.out.println("\n※ 식품 카테고리가 아니므로 유통기한은 입력하지 않습니다.\n");
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', NULL, '" + HPsn + "')");
				}
				System.out.println("\n◈ " + HPsn + "의 입고 물품 번호가 " + newIPsn + "로 지정 및 추가 완료");
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
				System.out.println("◈ 취급 물품 목록 ◈");
				rs = stmt.executeQuery("SELECT * FROM HANDLING_PRODUCT");
				System.out.println("번호	물품 이름	가격	카테고리	공급업체");
				while (rs.next())
					System.out.println(
							String.format("%15s", rs.getString(1)) + "	" + String.format("%20s", rs.getString(2))
									+ "	" + rs.getString(3) + "	" + rs.getString(4) + "	" + rs.getString(5));
				System.out.print("\n▷ 취급 물품 번호 : ");
				HPsn = sc.next();
				rs = stmt.executeQuery("SELECT Ssn FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				String thisSsn = null;
				while (rs.next()) {
					thisSsn = rs.getString("Ssn");
				}
				System.out.print("▷ 사원 번호 : ");
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
		} else if (status == 31) {// 판매 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM BUY");
				System.out.println("취급 물품 번호	입고 물품 번호	카테고리 번호	날짜	시간");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 32) {// 판매 입력
			try {
				System.out.println("▶ 현재 입고 품목 조회");
				rs = stmt.executeQuery(
						"SELECT IP.IPsn, HP.ProductName FROM HANDLING_PRODUCT HP INNER JOIN INCOME_PRODUCT IP ON HP.HPsn=IP.HPsn");
				System.out.println("입고 품목(바코드) 번호	품목명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n▷ 판매 입고 품목(바코드) 번호 : ");
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
				System.out.println("\n◈ 해당 번호 정보\n취급 품목 번호	물품 이름 ");
				System.out.println(thisHPsn + "	" + thisHPname + "\n");
				System.out.print("▷ 구매 고객 번호 : ");
				Csn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO BUY VALUES('" + thisHPsn + "', '" + IPsn + "', '" + Csn + "', '"
						+ now_date + "', '" + now_time + "')");
				stmt.executeUpdate("DELETE FROM INCOME_PRODUCT WHERE IPsn='" + IPsn + "'");
				System.out.println("\n◈ " + thisHPname + " " + IPsn + " " + now_date + " " + now_time + " 판매 완료");
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
				rs = stmt.executeQuery(
						"SELECT E.Esn, Name, Certification FROM EMPLOYEE E, EMP_CERTIFICATION EC WHERE E.Esn=EC.Esn");
				System.out.println("사번	이름	소유 자격증");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 43) {// 직원 추가
			System.out.print("▷ 이름 : ");
			Ename = sc.next();
			System.out.print("▷ 주민등록번호 : ");
			Errn = sc.next();
			System.out.print("▷ 성별(남/여) : ");
			Esex = sc.next();
			System.out.print("▷ 휴대전화번호(-포함 입력) : ");
			Ephone = sc.next();

			try {
				System.out.print("\n◈ 참고 부서 정보 ◈\n");
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("부서 번호	부서명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n▷ 부서 번호 : ");
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
				System.out.println("◈ " + Ename + "의 사번을 '" + okEsn + "'으로 지정 및 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 51) {// 부서조회 및 부서 별 직원 조회
			try {
				System.out.println("◈ 부서 정보 ◈");
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("부서 번호	부서명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));

				System.out.println("\n◈ 부서별 소속 직원◈");
				rs = stmt.executeQuery(
						"SELECT D.Dsn, DepartmentName, Esn, Name FROM DEPARTMENT D RIGHT JOIN EMPLOYEE E ON D.Dsn=E.Dsn");
				System.out.println("부서 번호	부서명	사원번호	사원명");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	" + rs.getString(4));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 52) {// 부서 추가
			try {
				System.out.print("▷ 추가 부서명 :");
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
				System.out.println(okDsn + " 부서 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 61) {// 공급업체 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("업체 번호	업체명	전화번호");
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
				System.out.print("▷ 업체명 : ");
				Sname = sc.next();
				System.out.print("▷ 전화번호 : ");
				Stel = sc.next();

				stmt.executeUpdate("INSERT INTO SUPPLYER VALUES('" + okSsn + "', '" + Sname + "', '" + Stel + "')");
				System.out.println("\n◈ 공급업체 " + Sname + "을 '" + okSsn + "'번호로 할당해 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 71) {// 직원 신원 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
				System.out.println("고객번호	주민등록번호	이름	성별	전화번호");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 72) {// 고객 추가
			System.out.print("▷ 이름 : ");
			Cname = sc.next();
			System.out.print("▷ 주민등록번호 : ");
			Crrn = sc.next();
			System.out.print("▷ 성별(남/여) : ");
			Csex = sc.next();
			System.out.print("▷ 휴대전화번호(-포함 입력) : ");
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
				System.out.println(Cname+"을 ;"+okCsn + "'번호로 할당해 고객 추가 완료");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static int menu(int status) {
		status = 0;
		System.out.println("▼ Mart Management System\n\n" + "1. 물품 관리\n" + "2. 주문 관리\n" + "3. 판매 관리\n" + "4. 직원 관리\n"
				+ "5. 부서 관리\n" + "6. 업체 관리\n" + "7. 고객 관리\n");
		// sc = new Scanner(System.in);
		System.out.print("▷ 선택 : ");
		int input = sc.nextInt();
		if (input == 1) {// 1. 물품관리
			System.out.println("\n▼ 물품 관리");
			System.out.println("1. 카테고리 관리\n" + "2. 취급 물품 관리\n" + "3. 입고 물품 관리\n");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {// 1-1. 카테고리
				System.out.println("\n▼ 카테고리 관리");
				System.out.println("1. 카테고리 및 담당 조회\n2. 카테고리 추가\n");
				System.out.print("▷ 선택 : ");
				input = sc.nextInt();
				if (input == 1) {// 1-1-1
					System.out.println("\n▼ 카테고리 및 담당 조회");
					status = 111;
				} else if (input == 2) {// 1-1-2
					System.out.println("\n▼ 카테고리 추가");
					status = 112;
				}
			} else if (input == 2) {// 1-2. 취급 물품
				System.out.println("\n▼ 취급 물품 관리");
				System.out.println("1. 취급 물품 조회\n2. 취급 물품 추가\n");
				System.out.print("▷ 선택 : ");
				input = sc.nextInt();
				if (input == 1) {// 1-2-1
					System.out.println("\n▼ 취급 물품 조회");
					status = 121;
				} else if (input == 2) {// 1-2-2
					System.out.println("\n▼ 취급 물품 추가");
					status = 122;
				}
			} else if (input == 3) {// 1-3. 입고 물품
				System.out.println("\n▼ 입고 물품 관리");
				System.out.println("1. 입고 물품 조회\n2. 입고 물품 추가\n");
				System.out.print("▷ 선택 : ");
				input = sc.nextInt();
				if (input == 1) {// 1-3-1
					System.out.println("\n▼ 입고 물품 조회");
					status = 131;
				} else if (input == 2) {// 1-3-2
					System.out.println("\n▼ 입고 물품 추가 (바코드 번호 입력)");
					status = 132;
				}
			}
		} else if (input == 2) {// 2. 주문관리
			System.out.println("\n▼ 주문 관리");
			System.out.println("1. 주문 조회\n" + "2. 주문 추가\n");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {// 2-1
				System.out.println("\n▼ 주문 조회");
				status = 21;
			} else if (input == 2) {// 2-2
				System.out.println("\n▼ 주문 추가");
				status = 22;
			}
		} else if (input == 3) {// 3. 판매 관리
			System.out.println("\n▼ 판매 관리");
			System.out.println("1. 판매 조회\n2. 판매 입력\n");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {// 3-1
				System.out.println("\n▼ 판매 조회");
				status = 31;
			} else if (input == 2) {// 3-2
				System.out.println("\n▼ 판매 입력");
				status = 32;
			}
		} else if (input == 4) {// 4. 직원관리
			System.out.println("\n▼ 직원 관리");
			System.out.println("1. 직원 조회\n" + "2. 업무배치를 위한 직원 자격증 조회\n" + "3. 직원 추가\n");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {// 4-1. 직원조회
				System.out.println("\n▼ 직원 신원 조회");
				status = 41;
			} else if (input == 2) {// 4-2
				System.out.println("\n▼ 업무배치를 위한 직원 자격증 조회");
				status = 42;
			} else if (input == 3) {// 4-3
				System.out.println("\n▼ 직원 추가");
				status = 43;
			}
		} else if (input == 5) {// 5. 부서관리
			System.out.println("\n▼ 부서 관리");
			System.out.println("1. 부서 및 부서별 소속 직원 조회\n" + "2. 부서 추가\n");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {// 5-1. 부서조회
				System.out.println("\n▼ 부서 조회 및 부서별 직원 조회");
				status = 51;
			} else if (input == 2) {// 5-2
				System.out.println("\n▼ 부서 추가");
				status = 52;
			}
		} else if (input == 6) {// 6. 업체관리
			System.out.println("\n▼ 업체 관리");
			System.out.println("1. 업체 조회\n" + "2. 업체 추가\n");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {// 6-1. 업체조회
				System.out.println("\n▼ 업체 조회");
				status = 61;
			} else if (input == 2) {// 6-2
				System.out.println("\n▼ 업체 추가");
				status = 62;
			}
		} else if (input == 7) {// 7. 고객관리
			System.out.println("\n▼ 고객 관리");
			System.out.println("1. 고객 조회\n" + "2. 고객 추가\n");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {//
				System.out.println("\n▼ 고객 조회");
				status = 71;
			} else if (input == 2) {
				System.out.println("\n▼ 고객 추가");
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
