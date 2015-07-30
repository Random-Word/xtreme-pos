package com.example.xtremepos;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class JavaScriptInterface {
    private Activity activity;

    public JavaScriptInterface(Activity activiy) {
        this.activity = activiy;
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
    public void playTone() {
    	((Orders)this.activity).playTone();
    }
    
    /*@JavascriptInterface
    public void refresh(String url) {
    	((Orders)this.activity).refresh(url);
    }*/
}
