package test_AreaD;

import java.io.*;
import java.util.*;

// Event 객체를 파일에 저장할수 있게 하기위해서 Serializable
class Event implements Serializable {
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
        return "[제목]: " + eventname + "\n" +
               "[기간]: " + feventdate + " ~ " + leventdate + "\n" +
               "[상세]: " + content + "\n";
    }
}

public class Index {
	// 여러개의 Event 객체를 리스트로 저장하기위해서 List<Event>
    static Map<String, List<Event>> events = new HashMap<>();
    static Scanner scan = new Scanner(System.in);
    static String useId; // 사용자 ID 변수

    public static void main(String[] args) {
        System.out.println("사용자 아이디를 입력하세요: ");
        useId = scan.nextLine();

        // 기존 파일에서 데이터 불러오기, ID별 이벤트가 저장된 .ser 파일을 찾아 기존 데이터를 메모리에 로드
        loadEvents();

        while (true) {
            System.out.println("메뉴번호를 선택하시오(1.종료 2.달력조회 3.이벤트추가 4.이벤트조회 5.이벤트삭제)");
            int menu = scan.nextInt();
            scan.nextLine();

            switch (menu) {
                case 1:
                    System.out.println("프로그램을 종료합니다.");
                    // 저장 않으면 데이트 프로그램 종료시 날라감
                    // 파일 저장 , 사용하지 않으면 변경된 사항 메모리 에만 남아있고 파일에 저장이 안됨
                    saveEvents();
                    return;
                case 2:
                    calendar();
                    break;
                case 3:
                    event_add();
                    break;
                case 4:
                    event_search();
                    break;
                case 5:
                    event_delete();
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
    

	// 1. 이벤트 추가 (ID별 List<Event> 저장)
    private static void event_add() {
        System.out.print("이벤트 제목: ");
        String eventname = scan.nextLine();
        System.out.print("이벤트 시작일자(yyyy/MM/dd HH:mm:ss): ");
        String feventdate = scan.nextLine();
        System.out.print("이벤트 종료일자(yyyy/MM/dd HH:mm:ss): ");
        String leventdate = scan.nextLine();
        System.out.print("이벤트 세부사항: ");
        String content = scan.nextLine();

        Event newEvent = new Event(eventname, content, feventdate, leventdate);

        // ID별 이벤트 저장
        // putIfAbsent() :Key 값이 존재하는 경우 Map의 Value의 값을 반환하고, Key값이 존재하지 않는 경우 Key와 Value를 Map에 저장하고 Null을 반환
        // events.get(useId).add(new Event(...))
        // useid가 처음 추가 되는 값이면 event.get(useId) 는 null 값 > Null.add() NullPointerException 발생
        events.putIfAbsent(useId, new ArrayList<>());
        events.get(useId).add(newEvent);

        // 파일 저장 , 사용하지 않으면 변경된 사항 메모리 에만 남아있고 파일에 저장이 안됨
        saveEvents();
        System.out.println("이벤트 등록 완료!");
    }

    // 2. 달력 조회 
    private static void calendar() {
		System.out.print("조회할 달력의 년도를 입력하시오: ");
		int year = scan.nextInt();
		System.out.print("조회할 달력의 월을 입력하시오: ");
		int mon = scan.nextInt();
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(year,mon-1,1); //입력한 년월의 첫째날 설정
		int firstWeek = cal.get(Calendar.DAY_OF_WEEK); //첫번째 날의 요일
		int lastday = cal.getActualMaximum(Calendar.DATE); //입력한년월의 마지막일자
		System.out.printf("\t %d년 %d월 \t\n", year, mon);
		System.out.printf("%-5s %-5s %-5s %-5s %-5s %-5s %-5s", "Sun","Mon", "Tue", "Wed", "Tue", "Fri", "Sat");
		System.out.println();
		
		
		//1부터 lastday 까지 반복
		for(int i=1,day=1;day<=lastday;i++) {
			//공백출력 : 첫째날의 요일 이전 부분
			if(i < firstWeek) {
				System.out.printf("%-6s"," ");
			} else {
				System.out.printf("%-6d",day++);
			} 
			
			if(i%7==0) {
				System.out.println();
			}else {
				
			}
		}
		System.out.println();

    	
    }

    // 3. 이벤트 조회
    private static void event_search() {
        System.out.print("조회할 일자 입력 (yyyy/MM/dd): ");
        String searchDate = scan.nextLine();
        
        List<Event> userDate = events.get(useId);
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
    
    // 5. 삭제
    private static void event_delete() {}

    // 이벤트 저장 (파일 직렬화)
    private static void saveEvents() {
    	// ObjectOutputStream : 객체를 직렬화 하여 String > byte 형식으로 변환해서 파일에 저장(FileOutputStream)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(useId + ".ser"))) {
        	oos.writeObject(events); // 이벤트 정보를 파일에 저장
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 이밴트 불러오기 (파일 역직렬화)
    private static void loadEvents() {
        File file = new File(useId + ".ser");
        // exists() : 존재여부
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // byte -> String 형변환 화면표출가능
        	events = (Map<String, List<Event>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
