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
	static int status = 0;// 선택한 메뉴

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
			stmt = con.createStatement();// MMS 담김
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

		if (status == 111) {// 카테고리 및 담당 조회
			try {
				CATEGORYview();
				rs = stmt.executeQuery(
						"SELECT C.Casn, CategoryName, E.Esn, Name  FROM CATEGORY C LEFT JOIN MANAGE M ON C.Casn=M.Casn LEFT JOIN EMPLOYEE E ON E.Esn=M.Esn");
				System.out.println("\n번호	카테고리	담당자 사번	담당자 이름");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	" + rs.getString(4));
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
				System.out.println("\n◈ " + okCasn + " " + CategoryName + " 카테고리 추가 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 113) {// 카테고리 삭제
			try {
				System.out.println("◈ 카테고리 목록 ◈");
				CATEGORYview();
				System.out.print("\n▷ 삭제할  카테고리 번호 : ");
				Casn = sc.next();
				rs = stmt.executeQuery("SELECT CategoryName FROM CATEGORY WHERE Casn='" + Casn + "'");// 해당 카테고리 이름
																										// 받아옴
				while (rs.next())
					CategoryName = rs.getString(1);
				stmt.executeUpdate("DELETE FROM CATEGORY WHERE Casn='" + Casn + "'");
				System.out.println("\n◈ " + Casn + " " + CategoryName + " 카테고리 삭제 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 121) {// 취급 물품 조회
			HPview();
		} else if (status == 122) {// 취금물품 추가
			try {
				System.out.print("▷ 취급 물품 번호(모델 번호) : ");
				HPsn = sc.next();
				System.out.print("▷ 취급 물품 이름(모델명) : ");

				Scanner scanner = new Scanner(System.in);
				HPname = scanner.nextLine();

				System.out.print("▷ 가격 : ");
				HPprice = sc.nextInt();

				CATEGORYview();
				System.out.print("\n▷ 카테고리 : ");
				CategoryName = sc.next();

				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("\n업체 번호	공급업체명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n▷ 공급업체 : ");
				Ssn = sc.next();

				stmt.executeUpdate("INSERT INTO HANDLING_PRODUCT VALUES('" + HPsn + "', '" + HPname + "', " + HPprice
						+ ", '" + CategoryName + "', '" + Ssn + "')");
				System.out.println("\n◈ " + HPsn + " " + HPname + " - 취급 물품 추가 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 123) {// 취급 물품 수정
			try {
				System.out.println("◈ 취급 물품 목록 ◈");
				HPview();
				System.out.println("\n1. 취급 물품 번호(모델명) 수정\n2. 이름 수정\n3. 가격 수정\n4. 카테고리 수정\n5. 공급업체 수정\n");
				System.out.print("▷ 입력 : ");
				input = sc.nextInt();
				System.out.print("▷ 수정할 취급 물품 번호 (모델명) : ");
				String whereInput = sc.next();
				Scanner sc1 = new Scanner(System.in);
				if (input == 4) {
					CATEGORYview();
				} else if (input == 5) {
					System.out.println("\n번호	공급업체명");
					rs = stmt.executeQuery("SELECT *  FROM SUPPLYER");
					while (rs.next())
						System.out.println(rs.getString(1) + "	" + rs.getString(2));
				}
				System.out.print("▷ 변경  정보 : ");
				String setInput = sc1.nextLine();// 변경할 정보
				String setCol = null;// 변경할 컬럼
				try {
					switch (input) {
					case 1:// 취급 물품 번호(모델명) 수정
						setCol = "HPsn";
						break;
					case 2:// 이름수정
						setCol = "ProductName";
						break;
					case 3:// 가격 수정
						setCol = "Price";
						break;
					case 4:// 카테고리 수정
						setCol = "Casn";
						break;
					case 5:// 공급업체 수정
						setCol = "Ssn";
						break;
					}
					stmt.executeUpdate("UPDATE HANDLING_PRODUCT SET " + setCol + "='" + setInput + "' WHERE HPsn='"
							+ whereInput + "'");
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("\n\n◈ " + whereInput + "을 " + setInput + "으로 수정 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 124) {// 취급 물품 삭제
			try {
				System.out.println("※ 판매 내역이 있는 물품은 삭제할 수 없습니다.");
				System.out.println("◈ 취급 물품 목록 ◈");
				HPview();
				System.out.print("\n▷ 삭제할  취급 물품 번호 : ");
				HPsn = sc.next();
				rs = stmt.executeQuery("SELECT ProductName FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				while (rs.next())
					HPname = rs.getString(1);
				stmt.executeUpdate("DELETE FROM HANDLING_PRODUCT WHERE HPsn='" + HPsn + "'");
				System.out.println("\n◈ " + HPsn + " " + HPname + " 취급 물품에서 삭제 완료");
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
				HPview();
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
				} else {
					System.out.println("※ 식품 카테고리가 아니므로 유통기한은 입력하지 않습니다.\n");
					stmt.executeUpdate("INSERT INTO INCOME_PRODUCT VALUES('" + newIPsn + "', NULL, '" + HPsn + "')");
				}
				System.out.println("\n◈ " + HPsn + "의 입고 물품 번호가 " + newIPsn + "로 지정 및 추가 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 133) {// 입고 물품 삭제
			try {
				System.out.println("※ 판매 내역이 있는 물품은 삭제할 수 없습니다.");
				System.out.println("◈ 입고 물품 목록 ◈");
				rs = stmt.executeQuery("SELECT * FROM INCOME_PRODUCT");
				System.out.println("번호	유통기한	입고 품목 번호");
				while (rs.next())
					System.out.println(
							rs.getString(1) + "	" + String.format("%10s", rs.getString(2)) + "	" + rs.getString(3));

				System.out.print("\n▷ 삭제할  입고 물품 번호 : ");
				IPsn = sc.next();
				stmt.executeUpdate("DELETE FROM INCOME_PRODUCT WHERE IPsn='" + IPsn + "'");
				System.out.println("\n◈ " + IPsn + " 입고 물품에서 삭제 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 21) {// 주문 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM ORDERS");
				System.out.println("취급 물품 번호	업체 번호	사원 번호	날짜	시간");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
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
				EMPview();
				System.out.print("▷ 사원 번호 : ");
				Esn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO ORDERS VALUES('" + HPsn + "', '" + thisSsn + "', '" + Esn + "', '"
						+ now_date + "', '" + now_time + "');");
				System.out.println(HPsn + " 주문 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 31) {// 판매 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM BUY");
				System.out.println("취급 물품 번호	바코드 	고객 번호	날짜	시간");
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
				CUSTOMERview();
				System.out.print("\n▷ 구매 고객 번호 : ");
				Csn = sc.next();

				now_date = format1.format(time.getTime());
				now_time = format2.format(time.getTime());

				stmt.executeUpdate("INSERT INTO BUY VALUES('" + thisHPsn + "', '" + IPsn + "', '" + Csn + "', '"
						+ now_date + "', '" + now_time + "')");
				stmt.executeUpdate("DELETE FROM INCOME_PRODUCT WHERE IPsn='" + IPsn + "'");
				System.out.println("\n◈ " + thisHPname + " " + IPsn + " " + now_date + " " + now_time + " 판매 완료");
			} catch (Exception e) {
				System.out.println(e);
			}

		} else if (status == 41) {// 직원 신원 조회
			EMPview();
		} else if (status == 42) {// 직원 자격증 조회
			try {
				rs = stmt.executeQuery(
						"SELECT E.Esn, Name, Certification FROM EMPLOYEE E, EMP_CERTIFICATION EC WHERE E.Esn=EC.Esn");
				System.out.println("사번	이름	소유 자격증");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3));
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
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 44) {// 직원 자격증 추가
			EMPview();
			System.out.print("▷ 사번 : ");
			Esn = sc.next();
			System.out.print("▷ 자격증 : ");
			Scanner sc1 = new Scanner(System.in);
			String certification = sc1.nextLine();
			try {
				stmt.executeUpdate("INSERT INTO EMP_CERTIFICATION VALUES('" + Esn + "', '" + certification + "')");
				System.out.println("◈ " + Esn + "의 자격증 정보에 '" + certification + "' 추가 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 45) {// 직원 삭제
			EMPview();
			try {
				System.out.println("※ 주문 내역이 있는 사원은 삭제할 수 없습니다.");
				System.out.println("◈ 직원 목록 ◈");
				rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
				System.out.println("사원번호	주민등록번호	이름	성별	전화번호		부서");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5) + "	" + rs.getString(6));
				System.out.print("\n▷ 삭제할  사원 번호 : ");
				Esn = sc.next();
				rs = stmt.executeQuery("SELECT Name FROM EMPLOYEE WHERE Esn='" + Esn + "'");
				while (rs.next())
					Ename = rs.getString(1);
				stmt.executeUpdate("DELETE FROM EMPLOYEE WHERE Esn='" + Esn + "'");
				System.out.println("\n◈ " + Esn + " " + Ename + " 직원 삭제 완료");
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
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 52) {// 부서 추가
			try {
				System.out.print("▷ 추가 부서명 :");
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
				System.out.println("◈" + okDsn + "으로 부서 추가 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 53) {// 부서 삭제
			try {
				System.out.println("◈ 부서 목록 ◈");
				rs = stmt.executeQuery("SELECT * FROM DEPARTMENT");
				System.out.println("부서 번호	부서명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n▷ 삭제할  부서 번호 : ");
				Dsn = sc.next();
				rs = stmt.executeQuery("SELECT DepartmentName FROM DEPARTMENT WHERE Dsn='" + Dsn + "'");
				while (rs.next())
					Dname = rs.getString(1);
				stmt.executeUpdate("DELETE FROM DEPARTMENT WHERE Dsn='" + Dsn + "'");
				System.out.println("\n◈ " + Dsn + " " + Dname + " 부서 삭제 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 61) {// 공급업체 조회
			try {
				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("업체 번호	업체명	전화번호");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3));
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
				System.out.println("\n◈ 공급업체 " + Sname + "을(를) '" + okSsn + "'번호로 할당해 추가 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 63) {// 공급업체 삭제
			try {
				System.out.println("※ 업체 삭제시 해당 업체가 공급하는 품목도 삭제됩니다.");
				System.out.println("◈ 공급업체 목록 ◈");
				rs = stmt.executeQuery("SELECT * FROM SUPPLYER");
				System.out.println("업체 번호	부서명");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2));
				System.out.print("\n▷ 삭제할  업체 번호 : ");
				Ssn = sc.next();
				rs = stmt.executeQuery("SELECT SupplyerName FROM SUPPLYER WHERE Ssn='" + Ssn + "'");
				while (rs.next())
					Sname = rs.getString(1);
				stmt.executeUpdate("DELETE FROM SUPPLYER WHERE Ssn='" + Ssn + "'");
				System.out.println("\n◈ " + Ssn + " " + Sname + " 공급업체 삭제 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 71) {// 고객 조회
			CUSTOMERview();
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
				System.out.println(Cname + "을(를) '" + okCsn + "'번호로 할당해 고객 추가 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (status == 73) {// 고객 삭제
			try {
				System.out.println("※ 거래 내역이 있는 고객은 삭제되지 않습니다.");
				System.out.println("◈ 고객 목록 ◈");
				rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
				System.out.println("고객 번호	주민등록번호	이름	성별	전화번호");
				while (rs.next())
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
							+ rs.getString(4) + "	" + rs.getString(5));
				System.out.print("\n▷ 삭제할  고객 번호 : ");
				Csn = sc.next();
				rs = stmt.executeQuery("SELECT Name FROM CUSTOMER WHERE Csn='" + Csn + "'");
				while (rs.next())
					Cname = rs.getString(1);
				stmt.executeUpdate("DELETE FROM CUSTOMER WHERE Csn='" + Csn + "'");
				System.out.println("\n◈ " + Csn + " " + Cname + " 고객 삭제 완료");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static int menu(int status) {
		status = 0;
		System.out.println("\n▼ Mart Management System\n" + "1. 물품 관리\n" + "2. 주문 관리\n" + "3. 판매 관리\n" + "4. 직원 관리\n"
				+ "5. 부서 관리\n" + "6. 업체 관리\n" + "7. 고객 관리\n"+"0. 종료\n");
		System.out.print("▷ 선택 : ");
		input = sc.nextInt();
		if (input == 1) {// 1. 물품관리
			System.out.println("\n▼ 물품 관리");
			System.out.println("1. 카테고리 관리\n" + "2. 취급 물품 관리\n" + "3. 입고 물품 관리\n");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {// 1-1. 카테고리
				System.out.println("\n▼ 카테고리 관리");
				System.out.println("1. 카테고리 및 담당 조회\n2. 카테고리 추가\n3. 카테고리 삭제");
				System.out.print("\n▷ 선택 : ");
				input = sc.nextInt();
				if (input == 1) {// 1-1-1
					System.out.println("\n▼ 카테고리 및 담당 조회");
					status = 111;
				} else if (input == 2) {// 1-1-2
					System.out.println("\n▼ 카테고리 추가");
					status = 112;
				} else if (input == 3) {// 1-1-3
					System.out.println("\n▼ 카테고리 삭제");
					status = 113;
				}
			} else if (input == 2) {// 1-2. 취급 물품
				System.out.println("\n▼ 취급 물품 관리");
				System.out.println("1. 취급 물품 조회\n2. 취급 물품 추가\n3. 취급 물품 수정\n4. 취급 물품 삭제\n");
				System.out.print("▷ 선택 : ");
				input = sc.nextInt();
				if (input == 1) {// 1-2-1
					System.out.println("\n▼ 취급 물품 조회");
					status = 121;
				} else if (input == 2) {// 1-2-2
					System.out.println("\n▼ 취급 물품 추가");
					status = 122;
				} else if (input == 3) {// 1-2-3
					System.out.println("\n▼ 취급 물품 수정");
					status = 123;
				} else if (input == 4) {// 1-2-3
					System.out.println("\n▼ 취급 물품 삭제");
					status = 124;
				}
			} else if (input == 3) {// 1-3. 입고 물품
				System.out.println("\n▼ 입고 물품 관리");
				System.out.println("1. 입고 물품 조회\n2. 입고 물품 추가\n3. 입고 물품 삭제");
				System.out.print("▷ 선택 : ");
				input = sc.nextInt();
				if (input == 1) {// 1-3-1
					System.out.println("\n▼ 입고 물품 조회");
					status = 131;
				} else if (input == 2) {// 1-3-2
					System.out.println("\n▼ 입고 물품 추가 (바코드 번호 입력)");
					status = 132;
				} else if (input == 3) {// 1-3-3
					System.out.println("\n▼ 입고 물품 삭제");
					status = 133;
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
			System.out.println("1. 직원 조회\n" + "2. 업무배치를 위한 직원 자격증 조회\n" + "3. 직원 추가\n4. 직원 자격증 추가\n5. 직원 삭제");
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
			} else if (input == 4) {// 4-4
				System.out.println("\n▼ 직원 자격증 추가");
				status = 44;
			} else if (input == 5) {// 4-5
				System.out.println("\n▼ 직원 삭제");
				status = 45;
			}
		} else if (input == 5) {// 5. 부서관리
			System.out.println("\n▼ 부서 관리");
			System.out.println("1. 부서 및 부서별 소속 직원 조회\n" + "2. 부서 추가\n3. 부서 삭제");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {// 5-1. 부서조회
				System.out.println("\n▼ 부서 조회 및 부서별 직원 조회");
				status = 51;
			} else if (input == 2) {// 5-2
				System.out.println("\n▼ 부서 추가");
				status = 52;
			} else if (input == 3) {// 5-2
				System.out.println("\n▼ 부서 삭제");
				status = 53;
			}
		} else if (input == 6) {// 6. 업체관리
			System.out.println("\n▼ 업체 관리");
			System.out.println("1. 업체 조회\n" + "2. 업체 추가\n3. 업체 삭제");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {
				System.out.println("\n▼ 업체 조회");
				status = 61;
			} else if (input == 2) {
				System.out.println("\n▼ 업체 추가");
				status = 62;
			} else if (input == 3) {
				System.out.println("\n▼ 업체 삭제");
				status = 63;
			}
		} else if (input == 7) {// 7. 고객관리
			System.out.println("\n▼ 고객 관리");
			System.out.println("1. 고객 조회\n" + "2. 고객 추가\n3. 고객 삭제");
			System.out.print("▷ 선택 : ");
			input = sc.nextInt();
			if (input == 1) {//
				System.out.println("\n▼ 고객 조회");
				status = 71;
			} else if (input == 2) {
				System.out.println("\n▼ 고객 추가");
				status = 72;
			} else if (input == 3) {
				System.out.println("\n▼ 고객 삭제");
				status = 73;
			}
		}

		return status;
	}

	public static void CUSTOMERview() {
		try {
			rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
			System.out.println("고객 번호	주민등록번호	이름	성별	전화번호");
			while (rs.next())
				System.out.println(rs.getString(1) + "	" + rs.getString(2) + "	" + rs.getString(3) + "	"
						+ rs.getString(4) + "	" + rs.getString(5));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void CATEGORYview() {
		try {
			System.out.println("번호	카테고리명");
			rs = stmt.executeQuery("SELECT *  FROM CATEGORY");
			while (rs.next())
				System.out.println(rs.getString(1) + "	" + rs.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void HPview() {// 취급 물폼 조회 줄맞춤
		try {
			rs = stmt.executeQuery(
					"SELECT HP.HPsn, HP.ProductName, HP.Price, C.Casn, C.CategoryName, S.Ssn, S.SupplyerName FROM HANDLING_PRODUCT HP, SUPPLYER S, CATEGORY C WHERE HP.Ssn=S.Ssn AND HP.Casn=C.Casn");
			System.out.println("번호	                  물품 이름	                 가격	카테고리 번호	카테고리명	업체 번호	업체명");
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
			System.out.println("사원번호	주민등록번호	이름	성별	전화번호		부서");
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
