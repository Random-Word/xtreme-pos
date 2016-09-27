package com.example.xtremepos;

import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class JavaScriptInterface {
    private Orders activity;
	private Editor editor;
	private SharedPreferences data;

    public JavaScriptInterface(Orders activiy) {
        this.activity = activiy;
        this.data = activity.data;
        this.editor = this.data.edit();
    }

    @JavascriptInterface
    public String printText(String text, int font_id, String alignment, int line_space, int size_w, int size_h, int x_pos, boolean bold, boolean underline){
        Log.i("JavaScriptInterface", "Print called with text: " + text);
        return Orders.printText(text, font_id, alignment, line_space, size_w, size_h, x_pos, bold, underline); 
    }
    
    @JavascriptInterface
    public String cut(boolean feed) {
    	Log.i("JavaScriptInterface", "Cut called");
    	return Orders.cut(feed);
    }
    
    @JavascriptInterface
    public void showDialog(String message, String title) {
        Orders.showText(message, title, activity).show();
    }
    
    @JavascriptInterface
    public String openPrinter(String ip) {
    	return ((Orders)this.activity).openPrinter(ip);
    }
    
    @JavascriptInterface
    public String openDefaultPrinter() {
    	return ((Orders)this.activity).openDefaultPrinter();
    }
    
    @JavascriptInterface
    public String play_tone(String sound) {
    	return ((Orders)this.activity).playTone(sound);
    }
    
    @JavascriptInterface
    public String end_tone() {
    	return ((Orders)this.activity).endTone();
    }
    
    @JavascriptInterface
    public String set_current(String orderString, String receiptLines) {
    	if (this.data.getString("orderString", null) != null) {
    		if (this.data.getString("orderString", null).equals(orderString)) {
    			return ("{\"success\": true, \"error\": false}");
    		} else {
    			return ("{\"success\": false, \"error\": \"Order already set.\", \"data\": {\"Order\": "+this.data.getString("orderString", null)+"}}");
    		}
    	} else {
				editor.putString("receiptLines", receiptLines);
				editor.putString("orderString", orderString);
				editor.apply();
				return ("{\"success\": true, \"error\": false}");
    	}
    }
    
    @JavascriptInterface
    public String get_current() { 
    	if (this.data.getString("orderString", null) != null) {
    		Log.e("GetCurrent Response: ", "{\"success\": true, \"error\": false, \"data\": {\"Order\": " + this.data.getString("orderString", null) + "}}");
    		return  ("{\"success\": true, \"error\": false, \"data\": {\"Order\": " + this.data.getString("orderString", null) + "}}");
    	}
    	else return  ("{\"success\": true, \"error\": false, \"data\": {\"Order\": null}}");
    }
    
    @JavascriptInterface
    public String clear_current() {
    	this.editor.remove("orderString");
    	this.editor.remove("receiptLines");
    	this.editor.apply();
    	return  ("{\"success\": true, \"data\": {\"Order\": " + this.data.getString("orderString", null) + "}, \"error\": false}");
    }
    
    @JavascriptInterface
    public String print_current() {
    	try {
    		JSONArray receiptLines = new JSONArray(this.data.getString("receiptLines", null));
	    	for (int i=0; i<receiptLines.length(); i++) {
	    		JSONArray data = receiptLines.getJSONArray(i);
	    		String order = data.getString(0);
	    		JSONArray command = data.getJSONArray(1);
	    		Orders.printText(order, 1, command.getString(3), command.getInt(2),
	    				command.getInt(1), command.getInt(1), command.getInt(0), command.getBoolean(4), command.getBoolean(5));
	    		Thread.sleep(100);
	    	}
	    	Orders.cut(true);
	    	return("{\"success\": true, \"error\": false}");
    	} catch (JSONException e) {
    		return ("{\"success\": false, \"error\": \""+e+"\"}");
    	} catch (InterruptedException e) {
    		return ("{\"success\": false, \"error\": \""+e+"\"}");
		}
    }
    
    /*@JavascriptInterface
    public void refresh(String url) {
    	((Orders)this.activity).refresh(url);
    }*/
}
