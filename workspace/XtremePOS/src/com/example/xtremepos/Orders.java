package com.example.xtremepos;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.epson.eposprint.BatteryStatusChangeEventListener;
import com.epson.eposprint.Builder;
import com.epson.eposprint.EposException;
import com.epson.eposprint.Print;
import com.epson.eposprint.StatusChangeEventListener;
import com.example.xtremepos.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
@SuppressWarnings("deprecation")
public class Orders extends Activity implements StatusChangeEventListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    static Print printer = null;
    static String printer_ip = "192.168.0.78";//.0.78";
    int connectionType = Print.DEVTYPE_TCP;
    static int language = com.epson.eposprint.Builder.LANG_EN;
    static String printerName = "TM-T20II";
    
    static final String vendorURL = "http://xtreme-pizza.ca/pos";//"http://development-xtreme-pizza.ca/vendor";//
    
    static final int SEND_TIMEOUT = 10 * 1000;
    static final int SIZEWIDTH_MAX = 8;
    static final int SIZEHEIGHT_MAX = 8;
    
    private int status;
    
    public SharedPreferences data;
    WebView webView;
    
    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

	private int order_tone_id;
	private int gangnam_tone_id;
	private int current_playing_tone = -1;

	private SoundPool sound_pool;

    String openDefaultPrinter() {
    	return openPrinter(printer_ip);
    }
    
    String openPrinter(String ip) {
    	//this.playTone();
    	int deviceType = Print.DEVTYPE_TCP;
    	int enabled = Print.TRUE;
    	int updateInterval = 1000;
    	
    	Print printer = new Print(getApplicationContext());
    	
        try{
            printer.openPrinter(deviceType, ip, enabled, updateInterval);
            printer.setStatusChangeEventCallback(this);
        }catch(Exception e){
            printer = null;
            ShowMsg.showException(e, "openPrinter" , this);
            return "0:"+e.toString();
        }
    	
    	Intent intent = new Intent();
    	intent.putExtra("language", Builder.MODEL_ANK);
        intent.putExtra("devtype", deviceType);
        intent.putExtra("ipaddress", ip);
        
        setPrinter(printer);
        
        printText("Tablet Connected to "+ip, 1, "left", 1, 1, 1, 0, false, false);
        cut(true);
        
		return "1:false";
    }
    
    static void resetPrinter(String ip) {
    	int deviceType = Print.DEVTYPE_TCP;
    	int enabled = Print.TRUE;
    	int updateInterval = 1000;
    	try {
			printer.closePrinter();
			printer.openPrinter(deviceType, ip, enabled, updateInterval);
		} catch (EposException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void login(WebView webView) {
    	String url = vendorURL;//"http://kleinlab.psychology.dal.ca/xtreme/vendor";//
        String postData = "number=42";

        webView.postUrl(url, EncodingUtils.getBytes(postData, "base64"));
    }
    
    static void setPrinter(Print obj){
        printer = obj;
    }

    static Print getPrinter(){
        return printer;
    }

    static void closePrinter(){
        try{
            printer.closePrinter();
            printer = null;
        }catch(Exception e){
            printer = null;
        }
    }
    
    @SuppressLint({ "SetJavaScriptEnabled", "InlinedApi", "NewApi" }) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orders);

        AudioAttributes attr = new AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build();
        
        data = getPreferences(0);
        Log.e("Last Stored Order", data.getString("orderString", "Empty"));
        
        sound_pool = new SoundPool.Builder().setAudioAttributes(attr).setMaxStreams(1).build();
        order_tone_id = sound_pool.load(this, R.raw.new_order_tone, 1);
        gangnam_tone_id = sound_pool.load(this, R.raw.gangnam_style, 1);
        
        JavaScriptInterface jsInterface = new JavaScriptInterface(this);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString("xtreme-pos-tablet");
        webView.addJavascriptInterface(jsInterface, "Android");
        webView.setWebViewClient(new WebViewClient());
        //login(webView);
        webView.loadUrl(vendorURL);//"http://kleinlab.psychology.dal.ca/xtreme/pos");//"http://xtreme-pizza.ca/pos");//
        webView.setKeepScreenOn(true);
        
        openDefaultPrinter();
        
        //Log.i("Orders", "Printer is: " + getPrinter());
        
        //Log.i("Orders", "Return value is: " + jsInterface.printText("Delivery Order\nPhone Number: 902-467-7834\nName: Jane Doe\n\n9\" BBQ Bacon Cheeseburger Pizza - $14.10\n\t+ Xtra Cheese\n\nDelivery: $2.00\nTaxes: $2.42\nTotal: $18.52\n\nAddress: 6397 South Bland St, Unit 205, B3J 3D6\nType: Apartment\nDelivery Instructions: None", 0, "Left", 1, 1, 1, 1, false, false));
        //Log.i("Orders", "Return value is: " + jsInterface.cut(true));
        //Log.i("Orders", "Finished.");
        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        //this.startLockTask();
        mSystemUiHider = SystemUiHider.getInstance(this, webView, HIDER_FLAGS);
        mSystemUiHider.setup();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            view.performClick();
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    
    public void onDestroy() {
        super.onDestroy();
        closePrinter();
    }
    
    static String printText(String text, int font_id, String alignment, int line_space, int size_w, int size_h, int x_pos, boolean bold, boolean underline){
    	resetPrinter(printer_ip);
    	Builder builder = null;
        Log.i("FOR_JON!", "Printing: "+text);
        @SuppressWarnings("unused")
		String method = "";
        String to_return = "{failed}";
        try{
            method = "Builder";
            builder = new Builder(
                    printerName, language);

            //add command
            method = "addTextFont";
            builder.addTextFont(getBuilderFont(font_id));
            
            method = "addTextAlign";
            builder.addTextAlign(getBuilderAlign(alignment));
            
            method = "addTextLineSpace";
            builder.addTextLineSpace(line_space);
            
            method = "addTextLang";
            builder.addTextLang(Builder.LANG_EN);
            
            method = "addTextSize";
            builder.addTextSize(size_w, size_h);
            
            method = "addTextStyle";
            builder.addTextStyle(Builder.FALSE, underline ? 1:0, bold ? 1:0, Builder.COLOR_1);
            
            method = "addTextPosition";
            builder.addTextPosition(x_pos);
            
            method = "addText";
            builder.addText(text);
            
            method = "addFeedUnit";
            builder.addFeedUnit(30);

            //send builder data
            int[] status = new int[1];
            int[] battery = new int[1];
            try{
                Print printer = getPrinter();
                printer.sendData(builder, SEND_TIMEOUT, status, battery);
                to_return = "{\"success\": true, \"error\": false, \"printerStatus\": \""+status+"\", \"batteryStatus\": \""+battery+"\"}";
            }catch(EposException e){
                return "{\"success\": false, \"error\": \""+e.getErrorStatus()+"\", \"printerStatus\": \""+e.getPrinterStatus()+"\", \"batteryStatus\": \""+e.getBatteryStatus()+"\"}";
            }
        }catch(Exception e){
        	return "{\"success\": false, \"error\": \""+e+"\", \"printerStatus\": null, \"batteryStatus\": null}";
        }
        
        //remove builder
        if(builder != null){
            try{
                builder.clearCommandBuffer();
                builder = null;
            }catch(Exception e){
                builder = null;
            }
        }
        return to_return;
    }
    
    private static int getBuilderFont(int font_id) {
        switch(font_id){
        case 1:
            return Builder.FONT_B;
        case 2:
            return Builder.FONT_C;
		case 3:
            return Builder.FONT_D;
		case 4:
            return Builder.FONT_E;
        case 0:
        default:
            return Builder.FONT_A;
        }
    }
    
    private static int getBuilderAlign(String alignment) {
        switch(alignment){
        case "centre":
            return Builder.ALIGN_CENTER;
        case "right":
            return Builder.ALIGN_RIGHT;
        case "left":
        default:
            return Builder.ALIGN_LEFT;
        }
    }
    
    static String cut(boolean feed){
        Builder builder = null;
        String to_return = "";
        try{
            builder = new Builder(printerName, language);

            builder.addCut(getFeedType(feed));

            //send builder data
            int[] status = new int[1];
            int[] battery = new int[1];
            try{
                Print printer = getPrinter();
                printer.sendData(builder, SEND_TIMEOUT, status, battery);
                to_return = "{\"success\": true, \"error\": false, \"data\": {\"response\": \""+EposException.SUCCESS+"\", \"printerStatus\": \""+status[0]+"\", \"batteryStatus\": \""+battery[0]+"\"}}";
            } catch (EposException e) {
            	return "{\"success\": false, \"error\": \""+e.getErrorStatus()+"\", \"data\": {\"printerStatus\": \""+e.getPrinterStatus()+"\", \"batteryStatus\": \""+e.getBatteryStatus()+"\"}}";
            }
        } catch (Exception e) {
        	return "{\"success\": false, \"error\": '"+e+"'}";
        }
        
        //remove builder
        if(builder != null){
            try{
                builder.clearCommandBuffer();
                builder = null;
            }catch(Exception e){
                builder = null;
            }
        }
        
        return to_return;
    }
        
    private static int getFeedType(boolean feed) {
            if (feed) {
                return Builder.CUT_FEED;
            } else {
                return Builder.CUT_NO_FEED;
            }
    }
    
    @Override
	public void onStatusChangeEvent(final String deviceName, final int status) {
		Log.e("Status Change: ", Integer.toString(status));
	}

	public static AlertDialog showText(String message, String title, Activity activity) {
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage(message)
		       .setTitle(title);

		// 3. Get the AlertDialog from create()
		return builder.create();
	}
	public String playTone(String sound) {
		if (current_playing_tone == -1) {
			try {
				Log.e("MUSIC", sound);
				if (sound.equals("tone")) current_playing_tone = sound_pool.play(order_tone_id, 1, 1, 0, 0, 1);
				else if (sound.equals("gangnam")) current_playing_tone = sound_pool.play(gangnam_tone_id, 1, 1, 0, -1, 1);
				else return("{\"success\": false, \"error\": \"Sound name "+sound+" not in library.\"}");
				return("{\"success\": true, \"error\": false}");
			} catch (Exception e) {
				return("{\"success\": false, \"error\": \""+e+"\"}");
			}
		} else {
			return("{\"success\": false, \"error\": \"Sound already playing.\"}");
		}
	}
	
	public String endTone() {
		try {
			sound_pool.stop(current_playing_tone);
			current_playing_tone = -1;
			return("{\"success\": true, \"error\": false}");
		} catch (Exception e) {
			return("{\"success\": false, \"error\": \""+e+"\"}");
		}
	}
	
	public String downloadTone(String slug) {
		try {
			new DownloadFileFromURL(slug).execute("xtreme-pizza.ca/tones/"+slug);
		} catch (Exception e) {
			return ("{\"success\": false, \"error\": '" + e + "'}");
		}
		return ("{\"success\": true, \"error\": false}");
	}
	
	public String loadTone(String slug) {
		try {
			this.sound_pool.load(Environment
	                    .getExternalStorageDirectory().toString()
	                    + "/sounds/" +slug, 1);
		} catch (Exception e) {
			return ("{\"success\": true, \"error\": false}");
		}
		return ("{\"success\": true, \"error\": false}");
	}
	
	public void refresh(String url) {
		Log.i("Loading URL", url);
		webView.loadUrl(url);
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Background Async Task to download file
	 * */
	class DownloadFileFromURL extends AsyncTask<String, String, String> {
		
		private String fname;

		public DownloadFileFromURL(String fname) {
			super();
			this.fname = fname;
		}
	    /**
	     * Downloading file in background thread
	     * */
	    @Override
	    protected String doInBackground(String... f_url) {
	        int count;
	        try {
	            URL url = new URL(f_url[0]);
	            URLConnection conection = url.openConnection();
	            conection.connect();

	            // this will be useful so that you can show a tipical 0-100%
	            // progress bar
	            int lenghtOfFile = conection.getContentLength();

	            // download the file
	            InputStream input = new BufferedInputStream(url.openStream(),
	                    8192);

	            // Output stream
	            OutputStream output = new FileOutputStream(Environment
	                    .getExternalStorageDirectory().toString()
	                    + "/raw/" + this.fname);

	            byte data[] = new byte[1024];

	            long total = 0;

	            while ((count = input.read(data)) != -1) {
	                total += count;
	                // publishing the progress....
	                // After this onProgressUpdate will be called
	                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

	                // writing data to file
	                output.write(data, 0, count);
	            }

	            // flushing output
	            output.flush();

	            // closing streams
	            output.close();
	            input.close();

	        } catch (Exception e) {
	            Log.e("Error: ", e.getMessage());
	        }

	        return null;
	    }

	}
	
}
