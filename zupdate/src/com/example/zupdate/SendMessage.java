package com.example.zupdate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 

public class SendMessage extends Activity {
 

 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
        Button button1 = (Button) this.findViewById(R.id.button1);
       button1.setOnClickListener(new View.OnClickListener() {

   @Override
   public void onClick(View v) {
   
   }
        }
        );
    }

 
}


