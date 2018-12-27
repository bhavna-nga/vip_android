package com.vip.wellbeing;

public class EachMessage {
	public String id;
    public String message,clientID;
    public String time;
    String date;
    public Boolean isClient;
    public EachMessage(){
        super();
    }
    
    public EachMessage(String id,String client_id, String message, Boolean isClient , String time , String date) {
        super();
        this.id=id;
        this.clientID=client_id;
        this.message=message;
        this.isClient=isClient;
        this.time=time;
        this.date=date;
    }
}
