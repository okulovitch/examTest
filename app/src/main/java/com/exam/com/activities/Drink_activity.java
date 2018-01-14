package com.exam.com.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.exam.com.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Drink_activity extends AppCompatActivity {

    // Array of strings storing country names
    // Array of strings storing country names
    String[] juse = new String[] {
            "orange juse",
            "Bear",
            "coffe",
            "cold cola",
            "stravbery coctel",
            "orange coctel",
            "pepsi",
            "fresh juse",
            "Cola",
            "Coffe"
    };

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] drincsimage = new int[]{
            R.drawable.drincs1,
            R.drawable.drincs2,
            R.drawable.drincs3,
            R.drawable.drink4,
            R.drawable.drink5,
            R.drawable.drink6,
            R.drawable.drink7,
            R.drawable.drincs1,
            R.drawable.drink4,
            R.drawable.drincs3
    };

    // Array of strings to store currencies
    String[] currency = new String[]{
            "2$",
            "2$",
            "3$",
            "4$",
            "2$",
            "1$",
            "1$",
            "3$",
            "5$",
            "2$"
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_activity);

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "Coctel : " + juse[i]);
            hm.put("cur","Cost : " + currency[i]);
            hm.put("flag", Integer.toString(drincsimage[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };


        int[] to = { R.id.imageOfProduct,R.id.txt,R.id.cur};


        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) findViewById(R.id.listview);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
    }

}
