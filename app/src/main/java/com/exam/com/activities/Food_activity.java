package com.exam.com.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.exam.com.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Food_activity extends Activity {


    String[] foods = new String[] {
            "buabes",
            "midas sup",
            "modnie krivetki",
            "interesnoe",
            "Banana",
            "Dranili",
            "Sloenoe",
            "Gary",
            "Midias",
            "modnie krivetki"
    };

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] foodimage = new int[]{
            R.drawable.food1,
            R.drawable.food2,
            R.drawable.food3,
            R.drawable.food4,
            R.drawable.food5,
            R.drawable.food6,
            R.drawable.food7,
            R.drawable.food1,
            R.drawable.food2,
            R.drawable.food3
    };

    // Array of strings to store currencies
    String[] currency = new String[]{
            "10$",
            "12$",
            "13$",
            "14$",
            "8$",
            "10$",
            "7$",
            "15$",
            "4$",
            "8$"
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_activity);

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "Dish : " + foods[i]);
            hm.put("cur","Cost : " + currency[i]);
            hm.put("flag", Integer.toString(foodimage[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };

        // Ids of views in listview_layout
        int[] to = { R.id.imageOfProduct,R.id.txt,R.id.cur};

        // Instantiating an adapter to store each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

        ListView listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(adapter);
    }

}