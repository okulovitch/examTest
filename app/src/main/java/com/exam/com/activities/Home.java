package com.exam.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.exam.com.R;

public class Home extends AppCompatActivity implements View.OnClickListener{

private CardView CardFood,CardDrink,CardRecomendations,CardHowToPay;
    Intent i ;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CardFood=(CardView) findViewById(R.id.CardFood);
        CardDrink=(CardView) findViewById(R.id.CardDrink);
        CardHowToPay=(CardView) findViewById(R.id.CardhowToPay);
        CardRecomendations=(CardView) findViewById(R.id.CardRecomendations);
        ll = (LinearLayout) findViewById(R.id.ll);
        CardFood.setOnClickListener(this);
        CardDrink.setOnClickListener(this);
        CardHowToPay.setOnClickListener(this);
        CardRecomendations.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.CardFood : i=new Intent(this,Food_activity.class);startActivity(i);break;
            case R.id.CardDrink :i=new Intent(this,Drink_activity.class);startActivity(i);break;
            case R.id.CardhowToPay:i=new Intent(this,HowToPay.class);startActivity(i);break;
            case R.id.CardRecomendations:i=new Intent(this,Recomendations.class);startActivity(i);break;
            default:break;


        }

    }
}
