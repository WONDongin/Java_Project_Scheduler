package java_teamprojectY;

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