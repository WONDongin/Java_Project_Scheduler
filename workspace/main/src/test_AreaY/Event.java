package test_AreaY;

class Event{
	String name;
	String content;
	String feventdate;
	String leventdate;
	
	public Event(String name, String content, String feventdate, String leventdate) {
		this.name = name;
		this.content = content;
		this.feventdate = feventdate;
		this.leventdate = leventdate;
	}
	public String toString() {
		return "[제목]: " + name + "\n" + "[기간]: " + feventdate + "~" 
	            + leventdate + "\n[상세]: " + content + "\n";		
	}
	public int compareTo(Event e) {
		return feventdate.compareTo(e.feventdate);
	}
}