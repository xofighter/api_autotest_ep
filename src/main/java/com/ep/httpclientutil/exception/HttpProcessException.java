package com.ep.httpclientutil.exception;

public class HttpProcessException extends Exception {

//	public HttpProcessException() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public HttpProcessException(String arg0) {
//		super(arg0);
//		// TODO Auto-generated constructor stub
//	}
//
//	public HttpProcessException(Throwable arg0) {
//		super(arg0);
//		// TODO Auto-generated constructor stub
//	}
//
//	public HttpProcessException(String arg0, Throwable arg1) {
//		super(arg0, arg1);
//		// TODO Auto-generated constructor stub
//	}
//
//	public HttpProcessException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
//		super(arg0, arg1, arg2, arg3);
//		// TODO Auto-generated constructor stub
//	}
	
	private static final long serialVersionUID = -2749168865492921426L;

	public HttpProcessException(Exception e){
		super(e);
	}

	/**
	 * @param msg	消息
	 */
	public HttpProcessException(String msg) {
		super(msg);
	}
	
	/**
	 * @param message	异常消息
	 * @param e			异常
	 */
	public HttpProcessException(String message, Exception e) {
		super(message, e);
	}

}
