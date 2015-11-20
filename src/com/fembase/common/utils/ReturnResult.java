package com.fembase.common.utils;

import java.util.HashMap;

public class ReturnResult {

	    HashMap<String, Object> state;
	    HashMap<String, Object> data;   
	    public static ReturnResult instance=new ReturnResult();
	    public ReturnResult(){
	        state = new HashMap<String, Object>();
	        data = new HashMap<String, Object>();
	    }

		public void setState(int code, String msg) {
	        this.state.put("code", code);
	        this.state.put("msg", msg);
	    }

	    public void putData(String key, Object value) {
	        this.data.put(key, value);
	    }

	    public HashMap<String, Object> getState() {
	        return state;
	    }

	    public void setState(HashMap<String, Object> state) {
	        this.state = state;
	    }

	    public HashMap<String, Object> getData() {
	        return data;
	    }

	    public void setData(Object data) {
	        this.data.put("data",data);
	    }
	
}
