package util;

import java.util.HashMap;

public class IntranetConstants {
	//status
	public static final int FILE_DRAFT=0;
	public static final int FILE_SENT=4;
	//after file sent
	public static final int FILE_INBOX=1;
	public static final int FILE_OUTBOX=2;
	public static final int FILE_TRASH=3;
	public static final int FILE_PERMANENT_DELETED=-1;
	//action
	public static final int FILE_TRASH_ACTION=11; //same as trash
	public static final int FILE_PARMANENT_DELETE_ACTION=12; //same as trash
	public static final int FILE_READ_ACTION=13;
	public static final int FILE_DRAFT_DELETE_ACTION=14;
	//receiver type
	public static final int FILE_RECIEVER_ORGINAL=21;
	public static final int FILE_RECIEVER_CC=22;
	//file data type
	public static final int FILE_DATA_FRESH=31;
	public static final int FILE_DATA_TRASH=32;
	//file sending type
	public static final int FILE_SENDING_FRESH=41;
	public static final int FILE_SENDING_DRAFT=42;
	public static final int FILE_SENDING_REPLY_FORWARD=43;
	
	public static final int MESSAGE_INBOX=1;
	public static final int MESSAGE_OUTBOX=2;
	
	public static HashMap<Integer,String> receiverTypeToDescMap = new HashMap<Integer,String>();
	public static HashMap<Integer,String> descToReceiverTypeMap = new HashMap<Integer,String>();
	
	public static HashMap<Integer,String> fileSecurityMap = new HashMap<Integer,String>();
	public static HashMap<Integer,String> filePriorityMap = new HashMap<Integer,String>();
	public static HashMap<String,String> fileExtensionIconMap = new HashMap<String,String>();
	public static int RECORDS_PER_PAGE=20;
	static{
		fileSecurityMap.put(1,"-");
		fileSecurityMap.put(2,"অতি গোপনীয়");
		fileSecurityMap.put(3,"বিশেষ গোপনীয়");
		fileSecurityMap.put(4,"গোপনীয়");
		fileSecurityMap.put(5,"সীমিত");
		
		filePriorityMap.put(1,"-");
		filePriorityMap.put(2,"জরুরি");
		filePriorityMap.put(3,"অবিলম্বে");
		filePriorityMap.put(4,"সর্বোচ্চ অগ্রাধিকার");

		fileExtensionIconMap.put("pdf", "fa fa-file-pdf-o");
		fileExtensionIconMap.put("doc", "fa fa-file-word-o");
		fileExtensionIconMap.put("jpg", "fa fa-file-photo-o");
		fileExtensionIconMap.put("png", "fa fa-file-photo-o");
		fileExtensionIconMap.put("bmp", "fa fa-file-photo-o");
		fileExtensionIconMap.put("zip", "fa fa-file-archive-o");
		fileExtensionIconMap.put("mp3", "fa fa-file-audio-o");
		fileExtensionIconMap.put("mp4", "fa fa-file-movie-o");
		fileExtensionIconMap.put("txt", "fa fa-file-text");
		fileExtensionIconMap.put("any", "fa fa-file-o");
	}
	public static final byte encryptKey[]={4,5,6,7,4,4,9,1,3,5,8,9,7,4,2,3,6,4,9,5,2,3,2,4,3,6,5,2,4,4,5,5,2,2,4,5,9,9,3,2,4,4,8,9,6,5,2,2,3,5,5,4,7,9,5,5,2,2,3,5,5,4,5,5,5,22,1,14,5,9,6,3,2,25,4,8,52,20,};
}
