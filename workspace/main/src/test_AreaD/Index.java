package test_AreaD;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Event{
	String eventname;
	String content;
	String feventdate;
	String leventdate;
	
	public Event(String eventname, String content, String feventdate, String leventdate) {
		this.eventname = eventname;
		this.content = content;
		this.feventdate = feventdate;
		this.leventdate = leventdate;
	}
	public String toString() {
		return "[제목]: " + eventname + "\n" + "[기간]: " + feventdate + "~" + leventdate + "\n[상세]: " + content + "\n";		
	}
	public int compareTo(Event e) {
		return feventdate.compareTo(e.feventdate);
	}
}

public class Index {
	static Map<String, Event> events = new HashMap<>();
	static Scanner scan = new Scanner(System.in);
	static String useId;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("사용자 이이디를 입력하세요");
		useId =  scan.nextLine();
		// 사용자 아이디 값 : 파일생성
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(useId + ".ser"));
		
		while(true) {
			System.out.println("메뉴번호를 선택하시오(1.종료 2.달력조회 3.이벤트추가 4.이벤트조회 5.이벤트변경 6.이벤트삭제)");
			int menu =  scan.nextInt();
			scan.nextLine();
	
			if(menu == 0) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			switch(menu) {
			  case 1 : return;
			  case 2 : calendar(); break;
			  case 3 : event_add(); break;
			  case 4 : event_search(); break;
			  case 5 : evnet_change(); break;
			  default : System.out.println("잘못된 입력입니다");
			} 
		}
	}
	
	private static void evnet_change() {
		
	}
	// 이벤트 조회
	private static void event_search() {
		System.out.print("조회할 이벤트 날자 입력: (yyyy/MM/dd hh:mm:ss): ");
		String feventdate = scan.nextLine();
		 
		for (Map.Entry<String, Event> entry : events.entrySet()){
			if(feventdate.equals(entry.getValue())) {
				 System.out.println("key : " + entry.getKey() + " / " + "value : " + entry.getValue());
			}	
		}
		   
	}
	// 이벤트 추가
	private static void event_add() {
		System.out.print("이벤트 제목: ");
        String eventname = scan.nextLine();
        System.out.print("이벤트 시작일자(yyyy/MM/dd hh:mm:ss): ");
        String feventdate = scan.nextLine();
        System.out.print("이벤트 종료일자(yyyy/MM/dd hh:mm:ss): ");
        String leventdate = scan.nextLine();
        System.out.print("이벤트 세부사항: ");
        String content = scan.nextLine();
        events.put(useId, new Event(eventname, content, feventdate, leventdate));
        System.out.println("이벤트 등록완료");
	}
	private static void calendar() {
		
	}
}
