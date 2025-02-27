package building;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class calendar {
	static Map<String, ArrayList<Event>> manager = new HashMap<>();
	static ArrayList<Event> list = new ArrayList<Event>();
	static String userid;
	static Scanner scan  = new Scanner(System.in);
	static SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static final String FILE_NAME = "events.ser";
public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
		Scanner scan = new Scanner(System.in);
		System.out.print("사용자 아이디를 입력하세요 >>");
		userid = scan.next();
		manager.put(userid, null);
		int menu;
		while(true) {
			try {
				System.out.println("메뉴번호를 선택하시오(1:종료, 2:달력조회, 3:이벤트추가, 4:이벤트조회, 5:이벤트변경, 6:이벤트삭제");
				menu = scan.nextInt();
				if(menu == 1) {
					System.out.println("달력조회 프로그램을 종료합니다.");
					break;
				}
				switch(menu) {
				case 2 : findcal(); break;
				case 3 : addEvent(); break;
				case 4 : findEvent(); break;
				case 5 : changeEvent(); break;
				case 6 : deleteEvent(); break;
				default : System.out.println("잘못된 입력입니다. 1, 2, 3, 4, 5, 6 숫자만 가능합니다.");
				}
			} catch (InputMismatchException e) {
				System.out.println("1,2,3,4,5,6 숫자만 가능합니다.");
				scan.next();
			}
		}
		
	}

	private static void deleteEvent() {
		System.out.println("삭제할 이벤트 날짜를 입력하세요");
	}

	private static void changeEvent() {
		System.out.print("변경할 이벤트 날짜를 입력하시오(yyyy/MM/dd)>>");
		String d = scan.nextLine();
		List<Event> userDate = manager.get(userid);
		List<Event> result = new ArrayList<>();
		int j = 1;
		for(int i=0; i<userDate.size();i++) {
			if(userDate.get(i).feventdate.startsWith(d)) {
			System.out.println("번호:"+ j +"=>"+userDate.get(i));
			result.add(userDate.get(i));
			j++;
			}
		}
		System.out.print("수정할 번호를 선택하세요:");
		int num = scan.nextInt();
		System.out.println("수정내용확인:");
		System.out.println(result.get(num-1));
		System.out.print("변경할 이벤트가 맞습니까? 변경 하실려면 Y를 입력하세요:");
		String yn = scan.next();
		scan.nextLine();
		if(yn.equalsIgnoreCase("Y")) {
			userDate.remove(result.get(num-1)); //선택한 이벤트 삭제
			
			System.out.print("새 이벤트 제목을 입력하세요: ");
	        String e_name = scan.nextLine();
	        result.get(num - 1).eventname = e_name;

	        System.out.print("새 시작 날짜를 입력하세요 (yyyy/MM/dd HH:mm:ss): ");
	        String sd = scan.nextLine();
	        result.get(num - 1).feventdate = sd;

	        System.out.print("새 종료 날짜를 입력하세요 (yyyy/MM/dd HH:mm:ss): ");
	        String ed = scan.nextLine();
	        result.get(num - 1).leventdate = ed;

	        System.out.print("새 이벤트 세부사항을 입력하세요: ");
	        String dtl = scan.nextLine();
	        result.get(num - 1).content = dtl;

	        list.add(result.get(num - 1)); // 변경된 이벤트 다시 추가
	        manager.put(userid, list); // 업데이트된 리스트 저장

	        System.out.println("이벤트 변경 저장 완료!");
		}
	}

	   private static void findEvent() {
	        System.out.print("조회할 일자 입력 (yyyy/MM/dd): ");
	        String searchDate = scan.nextLine();
	        List<Event> userDate = manager.get(userid);
	        List<Event> result = new ArrayList<>(); // 일치하는 일자의 evnet 리스트 
	        for(Event event : userDate) {
	        	// startsWith() : 시작문자 , 2025/02/26 10:00:00
	        	if(event.feventdate.startsWith(searchDate)) {
	        		result.add(event);
	        	}
	        }
	        if(result.isEmpty()) {
	        	System.out.println("등록된 이벤트가 없습니다.");
	        } else {
	        	// sort .comparing : 첫째날 내림차순
	        	result.sort(Comparator.comparing(e -> e.feventdate));
	        	// for 문 안돌리면 [ ], [ ] 형식으로 표출
	        	for(Event event : result ) {
	        		System.out.println(event);
	        	}
			}
	    }

	private static void addEvent() throws FileNotFoundException, IOException { //이벤트 추가)
		System.out.print("이벤트 제목을 입력하세요>>");
		String eventname = scan.nextLine();
		System.out.print("이벤트 시작 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>");
		String start = scan.nextLine();
		System.out.print("이벤트 종료 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>");
		String end = scan.nextLine();
		System.out.print("이벤트 세부사항을 입력하시오>>");
		String details = scan.nextLine();
		list.add(new Event(eventname, details, start, end));
		manager.put(userid, list);
		Collections.sort(list,(n1,n2)-> n1.feventdate.compareTo(n2.feventdate));
		for (List<Event> events : manager.values()) {
            for (Event event : events) {
                System.out.println(event);
            }
        }
		System.out.println("이벤트 변경 저장 완료");
	}

	private static void findcal() throws IOException, ParseException {
		int[] cntd = new int[31];
		
		System.out.print("년도를 입력하세요 =>");
		int year = scan.nextInt();
		System.out.print("월을 입력하세요 =>");
		int mon = scan.nextInt();
		Calendar cal = Calendar.getInstance();

		for (Event l : list) {
			Date d = sf.parse(l.feventdate);
			cal.setTime(d);
			if(year == cal.get(Calendar.YEAR) && mon == cal.get(Calendar.MONTH)+1) {
				for(int i = 0; i < cntd.length; i++) {
					if (cal.get(Calendar.DAY_OF_MONTH)-1 == i) {
						cntd[i]++;
					}
				}
			}
        }
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		cal.set(year,mon-1,1);
		int firstWeek = cal.get(Calendar.DAY_OF_WEEK);
		int lastday = cal.getActualMaximum(Calendar.DATE);
		FileOutputStream fos = new FileOutputStream(userid+".txt"); //userid.txt 스트림 오픈
		PrintStream ps = new PrintStream(fos); // 프린트 스트림 오픈
	    System.out.printf("\n\t\t%4d년 %2d월\n", year, mon);
	    System.out.println("============================================");
	    System.out.printf("%-6s %-6s %-6s %-6s %-6s %-6s %-6s\n", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
	    System.out.println("============================================");

	    // 날짜 출력
	    for (int i = 0, day = 1; day <= lastday; i++) {	    	
	        if (i < firstWeek) {
	            System.out.printf("%-7s", ""); // 빈칸 출력 (한 칸당 7자리)
	        } else {
	            if (cntd[day-1] > 0) {
	                System.out.printf("%d(%d)%-2s", day, cntd[day-1],""); // 일정 있는 날짜               
	            } else {
	                System.out.printf("%-7d", day); // 일반 날짜
	            }
	            day++;
	        }
	        if (i % 7 == 0) System.out.println(); // 한 줄(일주일) 출력 후 줄바꿈
	    }
	    System.out.println("\n============================================");
	}

	private static void setTime(Date d) {
		// TODO Auto-generated method stub
		
	}
	private static void saveEvents() {
		// ObjectOutputStream : 객체를 직렬화 하여 String > byte 형식으로 변환해서 파일에
		// 저장(FileOutputStream)
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			oos.writeObject(manager); // 이벤트 정보를 파일에 저장
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 이밴트 불러오기 (파일 역직렬화)
	private static void loadEvents() {
		File file = new File(FILE_NAME);
		// exists() : 존재여부
		if (!file.exists())
			return;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			// byte -> String 형변환 화면표출가능
			manager = (Map<String, ArrayList<Event>>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}