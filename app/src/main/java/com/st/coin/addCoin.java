package com.st.coin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by vinil on 27/11/2017.
 */

public class addCoin extends AppCompatActivity {

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner staticSpinner = (Spinner) findViewById(R.id.spinner1);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        staticSpinner.setAdapter(staticAdapter);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }





}
