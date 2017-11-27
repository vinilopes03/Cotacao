package com.st.smarttrash;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
/**
 * Created by vinil on 27/11/2017.
 */

public class addCoin extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.insert_new_item);

        //Botão de confirmar
        Button btn = (Button) findViewById(R.id.button_inserir);


        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }

        });
    }
}
