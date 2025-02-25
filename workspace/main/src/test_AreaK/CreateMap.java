package building;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Event{
	String eventname;
	String content;
	String feventdate;
	String leventdate;
	
	public Event(String name, String content, String feventdate, String leventdate) {
		this.eventname = name;
		this.content = content;
		this.feventdate = feventdate;
		this.leventdate = leventdate;
	}
	public String toString() {
		return "[제목]: " + eventname + "\n" + "[기간]: " + feventdate + "~" 
	            + leventdate + "\n[상세]: " + content + "\n";		
	}
}
public class CreateMap {
	static Map<String, Event> manager = new HashMap<>();
	static String userid;
	static boolean isEvent;
public static void main(String[] args) throws IOException {
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
		
	}

	private static void changeEvent() {
		
	}

	private static void findEvent() {
		// TODO Auto-generated method stub
		
	}

	private static void addEvent() { //이벤트 추가
		Scanner scan  = new Scanner(System.in);
		System.out.print("이벤트 제목을 입력하세요>>");
		String eventname = scan.nextLine();
		System.out.print("이벤트 시작 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>");
		String start = scan.nextLine();
		System.out.print("이벤트 종료 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>");
		String end = scan.nextLine();
		System.out.print("이벤트 세부사항을 입력하시오>>");
		String details = scan.next();
		Event event =new Event(eventname, details, start, end);
		System.out.println(event);
		manager.put(userid, event);
		System.out.println(manager.values());
		isEvent = true;
	}

	private static void findcal() throws IOException, ParseException {
		Scanner scan = new Scanner(System.in);
		System.out.print("년도를 입력하세요 =>");
		int year = scan.nextInt();
		System.out.print("월을 입력하세요 =>");
		int mon = scan.nextInt();
		String sday = manager.get(userid).feventdate;
		Date startday = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		startday = sf.parse(sday);
		Calendar cal = Calendar.getInstance();
		cal.set(year,mon-1,1);
		int firstWeek = cal.get(Calendar.DAY_OF_WEEK);
		//입력한년월의 마지막일자
		int lastday = cal.getActualMaximum(Calendar.DATE);
		System.out.println("\t"+year + "년 " + mon + "월");
		System.out.printf("%3c %3c %3c%3c %3c%3c %3c",'일','월','화','수','목','금','토');
		System.out.println();
		int cnt = 0;
		for(int i=1,day=1;day<=lastday;i++) {
			if(i < firstWeek) System.out.printf("%4s"," ");
			if(isEvent && ) {
				System.out.print("("+ ++cnt +")");
			}
			else System.out.printf("%4d",day++);
			if(i%7==0)System.out.println();
		}
		System.out.println();
		
	}

}
