# Java_Project_Scheduler
Java_Project_Scheduler : 팀 프로젝트


#### **☑️ 팀 프로젝트\_최종완료**

\- 이벤트 캘린더 프로젝트

#### **☑️ 작업기간**

\- 2025/02/25 ~ 2025/02/28

#### **☑️ 주요기능** 

**\- 데이터 파일생성/저장/불러오기 :** event.ser 파일생성 + 사용자 별 id에 맞는 이벤트 일정 제목, 기간, 내용 저장/불러오기

**\- 달력표시 :** 년도, 월 입력받아 해당 년도의 월 달력 표시, 일정있는 일자 카운트기능

**\- 이벤트조회 :** 일자 입력시(2025/02/26) 해당 일자에 등록된 이벤트 리스트 출력

**\- 이벤트수정 :** 일자 입력시(2025/02/26) 해당 일자에 등록된 이벤트 리스트 중  원하는 일정내용 수정

**\- 이벤트삭제 :** 일자 입력시(2025/02/26) 해당 일자에 등록된 이벤트 리스트 중  원하는 일정내용 삭제

**\- 이벤트추가 :** 일자 입력시(2025/02/26) 해당 일자에 이벤트 제목, 기간, 상세내용 추가

#### **☑️ 역활 배분** 

**동인:** 데이터 파일생성/ 고객 데이터 저장/불러오기, 이벤트조회

---

#### **☑️ 주요 기능메소드** 

#### **✅ Event 클래스**

```
class Event implements Comparable<Event>, Serializable{
	String eventname;   // 이벤트 제목
	String content;     // 이벤트 상세 내용
	String feventdate;  // 이벤트 시작 날짜 (yyyy/MM/dd HH:mm:ss)
	String leventdate;  // 이벤트 종료 날짜 (yyyy/MM/dd HH:mm:ss)
    
	public Event(String eventname, String content, String feventdate, String leventdate) {
		this.eventname = eventname;
		this.content = content;
		this.feventdate = feventdate;
		this.leventdate = leventdate;
	}
	public String toString() {
		return "[제목] : " + eventname + "\n" + "[기간] : " + feventdate + "~" 
	            + leventdate + "\n[상세] : " + content + "\n";		
	}
	// 이벤트 날짜 비교(String → Date)
	public int compareTo(Event e){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return sf.parse(this.feventdate).compareTo(sf.parse(e.feventdate));
		} catch (ParseException e1) {
			e1.printStackTrace(); // 예외 메세지 + 오류 발생한 코드 줄까지 흔적 출력
			return 0; // 비교가 불가능 하기 때문에 기존 상태유지 (0)
		}
	}
}
```

#### **✅ compareTo(Event e) - 이벤트 날짜 비교**

**SimpleDateFormat : 객체**를 생성하여 날짜 형식을 지정. **(yyyy/MM/dd HH:mm:ss)**

**sf.parse() : 문자열을 Date 객체**로 변환

**비교 결과 반환 값**

date1 vs date2반환 값 **(compareTo)정렬 방식 (오름차순)**

| "2024/03/05 14:00:00" **vs** "2024/02/28 09:30:00" | **1 (양수)** | **뒤로 감** |
| --- | --- | --- |
| "2024/02/28 09:30:00" **vs** "2024/03/05 14:00:00" | **\-1 (음수)** | **앞으로 감** |
| "2024/03/05 14:00:00" **vs** "2024/03/05 14:00:00" | **0** | **위치 유지** |

**e1.printStackTrace : 예외 메시지(by zero)**와 **예외난 곳(TryEx12.java:8)**를 알려준다.

**return 0  :** 잘못된 데이터가 들어가면 비교가 불가능하니 **그대로 유지(0)**

---

#### **✅ 캘린더 시스템 (calendar 클래스) - 변수 선언**

```
static Map<String, ArrayList<Event>> manager = new HashMap<>();
static ArrayList<Event> list = new ArrayList<Event>();
static String userid;
static Scanner scan  = new Scanner(System.in);
static SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
static final String FILE_NAME = "events.ser";
```

| **manager**      | **사용자별(String = ID)**로 일정(**ArrayList<Event>**)을 관리하는 **맵(HashMap)** |
| --- | --- |
| **list**                | 현재 로그인한 사용자의 일정 목록 |
| **userid**           | 현재 로그인한 사용자 ID |
| **scan**               | 사용자 입력을 받기 위한 **Scanner 객체** |
| **sf**                | 날짜를 다루기 위한 **날짜 형식 객체** (yyyy/MM/dd HH:mm:ss) |
| **FILE\_NAME**  | 일정 데이터를 저장할 **파일 이름** |

#### **✅ main() 메서드**

```
public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
		Scanner scan = new Scanner(System.in);
		System.out.print("사용자 아이디를 입력하세요 >>");
		userid = scan.nextLine();
        
		loadEvents();
        
		int menu;
		if(manager.containsKey(userid)) {
			list = manager.get(userid);
		} else {
			manager.put(userid, list);
		}
        
		while(true) {
			try {
				System.out.println("메뉴번호를 선택하시오(1:종료, 2:달력조회, 3:이벤트추가, 4:이벤트조회, 5:이벤트변경, 6:이벤트삭제");
				menu = scan.nextInt();
				if(menu == 1) {
					System.out.println("달력조회 프로그램을 종료합니다.");
					saveEvents();
					break;
				}
				switch(menu) {
				case 2 : findcal();  break;
				case 3 : addEvent(); break;
				case 4 : findEvent(); break;
				case 5 : changeEvent(); break;
				case 6 : deleteEvent(); break;
				default : System.out.println("잘못된 입력입니다. 1, 2, 3, 4, 5, 6 숫자만 가능합니다.");
				}
			} catch (InputMismatchException e) {
				System.out.println("1,2,3,4,5,6 숫자만 가능합니다.");
				scan.nextLine();
			}
		}
		
	}
```

사용자 id 받기 **(userid) → loadEvents() : 이전에 저장된 일정의 데이터가 있을 경우 불러오기**

```
if(manager.containsKey(userid)) {
	list = manager.get(userid);
	} else {
		manager.put(userid, list);
	}
}
```

\- 사용자가 기존 데이터에 있으면 **해당 사용자의 일정 리스트 불러오기**

\- 새로운 사용자면 새로운 리스트 생성

**\* 이벤트 종료, 추가 :** 데이터 저장 

\* 1~6 중 숫자를 미 입력시 예외처리

---

#### **✅** **loadEvents() - 데이터 불러오기**

```
// 이벤트 불러오기 (파일 역직렬화)
private static void loadEvents() {
	File file = new File(FILE_NAME);

	if (!file.exists()) return;
	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
		manager = (Map<String, ArrayList<Event>>) ois.readObject();
	} catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	}
}
```

**file.exists() :** 파일의 존재 여부 체크

**ObjectInputStream** ois = **new ObjectInputStream(new FileInputStream(file))**

: **ObjectInputStream**  : file 안 **byte로 변환된 Data**를 **읽어들이기 위해 ObjectInputStream 객체 생성**

**manager = (Map<String, ArrayList<Event>>) ois.readObject();**

: 파일안의 데이터를 **(Map<String, ArrayList<Event>>)** 타입으로 형변화 하여 읽어, manager 변수에 저장

---

#### **✅ save****Events() - 데이터 저장**

```
private static void saveEvents() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
        oos.writeObject(manager);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

**ObjectOutputStream** oos = new **ObjectOutputStream(new FileOutputStream(FILE\_NAME)**

: **ObjectOutputStream** : Object객체를 직렬화 하여 **String > byte** 타입으로 직렬화 하여 **파일에 저장(FileOutputStream)**

---

#### **✅ findcal() - 달력 조회**

```
private static void findcal() throws IOException, ParseException {
		int[] cntd = new int[31];
		int year, mon;
		Calendar cal = Calendar.getInstance();
		while(true) {
			try {
				System.out.print("년도를 입력하세요 =>");
				year = scan.nextInt();
				System.out.print("월을 입력하세요 =>");
				mon = scan.nextInt();
				scan.nextLine();
				break;
				
			} catch (InputMismatchException e) {
				System.out.println("숫자만 가능합니다.");
				scan.nextLine();
			}
		}
	}
```

**int\[\] cntd = new int\[31\]** : 날짜별 이벤트 개수를 저장할 배열

```
for (Event l : list) {
    Date d = sf.parse(l.feventdate);  // 이벤트의 시작 날짜를 Date 객체로 변환
    cal.setTime(d);  // 캘린더에 해당 날짜 설정
    if (year == cal.get(Calendar.YEAR) && mon == cal.get(Calendar.MONTH) + 1) {
        for (int i = 0; i < cntd.length; i++) {
            if (cal.get(Calendar.DAY_OF_MONTH) - 1 == i) {
                cntd[i]++;  // 해당 날짜의 이벤트 개수 증가
            }
        }
    }
}
```

**sf.parse(l.feventdate)** : **이벤트의 시작 날짜(String)** 를 **Date 객체**로 변환.

**cal.setTime(d) :** 변환된 Date 객체를 Calendar에 설정.

**\* feventdate 일자**와 **달력의 일자**가 같을시 **이벤트 카운트 증가**

```
cal.set(year, mon - 1, 1);  // 입력한 연도의 해당 월의 1일로 설정
int firstWeek = cal.get(Calendar.DAY_OF_WEEK);  // 해당 월의 1일의 요일(1: 일요일, 2: 월요일, ...)
int lastday = cal.getActualMaximum(Calendar.DATE);  // 해당 월의 마지막 날짜(28, 30, 31 중 하나)
```

**Calendar.MONTH**는 **0부터 시작**하므로 **mon-1** 

```
for (int i = 0, day = 1; day <= lastday; i++) {
    if (i < firstWeek - 1) {
        System.out.printf("%-7s", "");  // 첫 번째 주의 빈칸 출력
    } else {
        if (cntd[day - 1] > 0) {
            System.out.printf("%-7s", String.format("%-2d(%d)", day, cntd[day - 1]));  // 이벤트 개수 표시
        } else {
            System.out.printf("%-7d", day);  // 일반 날짜 출력
        }
        day++;
    }
    if ((i+1) % 7 == 0) System.out.println();  // 일주일 단위로 줄바꿈
}
```

**firstWeek** 값은 **1(일요일) ~ 7(토요일)**

하지만 배열 인덱스는 **0부터 시작하므로 -1** 

| **1** | **일요일** |
| --- | --- |
| **2** | **월요일** |
| **3** | **화요일** |
| **4** | **수요일** |
| **5** | **목요일** |
| **6** | **금요일** |
| **7** | **토요일** |

#### **✅ addEvent() - 이벤트 추가**

```
System.out.print("이벤트 제목을 입력하세요>>");
String eventname = scan.nextLine();
System.out.print("이벤트 시작 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>");
String start = scan.nextLine();
System.out.print("이벤트 종료 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>");
String end = scan.nextLine();
System.out.print("이벤트 세부사항을 입력하시오>>");
String details = scan.nextLine();

list.add(new Event(eventname, details, start, end));
Collections.sort(list);
manager.put(userid, list);
saveEvents();
```

새로운 Event 객체를 만들어 **list에 추가**하고, **날짜순 정렬**한 후 저장.

---

#### **✅ findEvent() - 이벤트 조회**

```
System.out.println("조회할 이벤트 날짜를 입력하세요(yyyy/MM/dd)>>");
String searchDate = scan.nextLine();

for (List<Event> events : manager.values()) {
    for (Event event : events) {
        if(event.feventdate.startsWith(searchDate)) {
            System.out.print(event);
        }
    }
}
```

**event.feventdate.startsWith(searchDate)**

: 입력한 날짜로 시작하는(**startsWith(searchDate)**) 이벤트 출력

---

#### **✅ changeEvent() - 이벤트 변경**

```
System.out.print("변경할 이벤트 날짜를 입력하시오(yyyy/MM/dd)>>");
String d = scan.nextLine();

List<Event> result = new ArrayList<>();
for(int i = 0; i < userDate.size(); i++) {
    if(userDate.get(i).feventdate.startsWith(d)) {
        System.out.println("번호:" + (i+1) + "=>" + userDate.get(i));
        result.add(userDate.get(i));
    }
}
```

입력한 날짜의 이벤트들을 찾아 **result 리스트에 저장**하고, **번호를 붙여 출력**

```
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
```

기존 데이터를 삭제하고 **새로운 데이터 입력 후 리스트에 다시 추가 & 저장**.

---

#### **✅ deleteEvent() - 이벤트 삭제**

```
System.out.print("삭제할 이벤트가 맞습니까? 삭제하려면 Y를 입력하세요");
if(scan.nextLine().equalsIgnoreCase("y")) {
    list.remove(deleteno - 1);
    saveEvents();
}
```

---

#### ☑️ **최종소스**

```
package finalbuild;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Event implements Comparable<Event>, Serializable{
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
		return "[제목] : " + eventname + "\n" + "[기간] : " + feventdate + "~" 
	            + leventdate + "\n[상세] : " + content + "\n";		
	}
	public int compareTo(Event e){
		//비교를 위해 입력받은 날짜를 정수형 변환
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return sf.parse(this.feventdate).compareTo(sf.parse(e.feventdate));
		} catch (ParseException e1) {
			e1.printStackTrace();
			return 0;
		}
	}
}
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
		userid = scan.nextLine();
		
		loadEvents();
		
		int menu;
		if(manager.containsKey(userid)) {
			list = manager.get(userid);
		} else {
			manager.put(userid, list);
		}
		
		while(true) {
			try {
				System.out.println("메뉴번호를 선택하시오(1:종료, 2:달력조회, 3:이벤트추가, 4:이벤트조회, 5:이벤트변경, 6:이벤트삭제");
				menu = scan.nextInt();
				if(menu == 1) {
					System.out.println("달력조회 프로그램을 종료합니다.");
                    saveEvents();
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
				scan.nextLine();
			}
		}
		
	}

	private static void deleteEvent() {
		System.out.println("삭제할 이벤트 날짜를 입력하세요(yyyy/MM/dd)>>");
		String deleteDate = scan.nextLine();
		ArrayList<Event> deletelist = new ArrayList<Event>();
		int num = 1;
		for (List<Event> events : manager.values()) {
            for (Event event : events) {
            	if(event.feventdate.startsWith(deleteDate)) {
            		deletelist.add(event);
					System.out.print("번호:" + num++ + "=>");
            		System.out.print(event);
            	}
            }
        }
		System.out.print("삭제할 번호를 선택하세요");
		int deleteno = scan.nextInt();
		scan.nextLine();
		System.out.println("삭제내용확인:");
		deletelist.get(deleteno-1);
		System.out.print("삭제할 이벤트가 맞습니까? 삭제하려면 Y를 입력하세요");
		while (true) {
			String deleteyn = scan.nextLine();
			if(deleteyn.equalsIgnoreCase("y")) {
				int number = list.indexOf(deletelist.get(deleteno-1));
				System.out.println(number);
				list.remove(number);
				manager.put(userid, list);
				saveEvents();
				System.out.println("이벤트 삭제 저장 완료");
				break;
			}else {
				System.out.println();
				break;
			}
		}
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
	        Collections.sort(list);
	        manager.put(userid, list); // 업데이트된 리스트 저장
	        System.out.println("이벤트 변경 저장 완료!");
		}
	}

	private static void findEvent() {
	
		System.out.println("조회할 이벤트 날짜를 입력하세요(yyyy/MM/dd)>>");
		String searchDate = scan.nextLine();
		for (List<Event> events : manager.values()) {
            for (Event event : events) {
            	if(event.feventdate.startsWith(searchDate)) {
            		System.out.print(event);
            	}
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
		Collections.sort(list);
		manager.put(userid, list);
		saveEvents();
	}

	private static void findcal() throws IOException, ParseException {
		int[] cntd = new int[31];
		int year, mon;
		Calendar cal = Calendar.getInstance();
		while(true) {
			try {
				System.out.print("년도를 입력하세요 =>");
				year = scan.nextInt();
				System.out.print("월을 입력하세요 =>");
				mon = scan.nextInt();
				System.out.println("dd");
				scan.nextLine();
				break;
				
			} catch (InputMismatchException e) {
				System.out.println("숫자만 가능합니다.");
				scan.nextLine();
			}
		}

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
		
		cal.set(year,mon-1,1);
		int firstWeek = cal.get(Calendar.DAY_OF_WEEK);
		int lastday = cal.getActualMaximum(Calendar.DATE);

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
```

#### ☑️ **팀 프로젝트를 하면서**

\- **Calendar 클래스**를 사용하여 **달력을 만드는 방법, ComparTo를 사용하여 일자 비교하는 공부가 되었다.**

\- **ObjectOutputStream, ObjectInputStream** 를 사용하여 **Object 형 자료를 바이트 형으로 반환**하여

  **.ser 파일(FiileOutStream)에 저장** 후 다시 **형변환를 통해서 바이트형 → String형**으로 반환하여 데이터를 표출하는

  파일 **직/역직렬화**에 대해 많은 공부가 되었다. 

\- **Map, List/ArrayList (컬랙션 프레임워크)**를 사용하여 **데이터를 관리하고, 저장/불러오기/조회**하는 소스 구현 경험을 하였다.

\- **나의 소스와 팀원**의 소스를 **머지하였을때, 발생하는 충돌**과 소스 적용 후 **Run 시 컴파일 오류**를 해결하는 과정을 통해서

  서로의 **소스 로직에 대해 이해**하고 **애기를 통해 하나의 소스**로 만들어 나가는 과정이 가장 좋았던 것 같다.

---

PS. 이로서 한달간의 **자바 교육일정이 종료**가 되었고, 조금 더 **자바라는 언어에 대해 알고 싶고, 흥미**가 더 생긴 것 같다.

**3월 4일 부터는 데이터 베이스**를 공부한다. 틈틈히 자바 개념을 **재 정비를 하여 튼튼한 기본기를 쌓을 것** 이다.

3일 동안 효율적인 소스를 만들기위해 같이 **밤, 낮으로 고생한 팀원분들에게 감사의 마음**을 전하면서 이글을 마친다 :D
## Blog
[https://dongin97.tistory.com/](https://dongin97.tistory.com/category/BootCamp)
