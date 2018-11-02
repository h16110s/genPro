package com.example.pokemon;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.Toast;

public class VoiceCommandService extends AccessibilityService{
    public static final String EXTRA_MESSAGE = "com.example.pokemon.MESSAGE";
    Handler _handler = new Handler();
    int WAIT_TIME = 1000;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo accessibilityNodeInfo = event.getSource();
        if (null == accessibilityNodeInfo) {
            return;
        }

        String className = accessibilityNodeInfo.getClassName().toString();
        final CharSequence text = accessibilityNodeInfo.getText();
        if(className.indexOf("com.google.android.apps.gsa.searchplate") == -1 || text == null) {
            return;
        }
        _handler.removeCallbacksAndMessages(null);
        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                execVoiceCommand(text.toString());
            }
        }, WAIT_TIME);

        Toast.makeText(getApplicationContext(), text.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        String message = text.toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    @Override
    public void onInterrupt() {
    }

    public void execVoiceCommand(String command){
        Log.d("voice command", command);
        // write your command

    }
}
