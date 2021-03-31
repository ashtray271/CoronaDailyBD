package com.example.coronadailybd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

//import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    TextView textView,text,text1,text2,text3,text4,textView9;
    Dataapi api;
    RecyclerView recyclerView;
    private Toolbar toolbar;
    Button btn;

   /*// EditText searchView;*/
    CharSequence search = "";
    Adapter adapterr;

    EditText editText;

    ImageView imageView;
    Data data = new Data();
    List<String> list=new ArrayList<String>();
    List<String> conf =new ArrayList<String>();
    List<String> death =new ArrayList<String>();
    List<String> rec =new ArrayList<String>();
    List<String> act =new ArrayList<String>();
    ImageView im;


    String str;

    List<Data> l = new ArrayList<Data>();
    //SearchView searchView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);

        text1 = findViewById(R.id.textView11);
        textView9 = findViewById(R.id.textView9);
        imageView = findViewById(R.id.i);
        text2 = findViewById(R.id.textView12);
        text3 = findViewById(R.id.textView13);
        text4 = findViewById(R.id.textView14);
        toolbar = findViewById(R.id.myToolBar);
        text = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.homeRV);
        editText = findViewById(R.id.searchview);
        btn = findViewById(R.id.cap);
        im = findViewById(R.id.img9);

        api = RetrofitClient.getClient().create(Dataapi.class);






        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        str = intent.getStringExtra("MODEL");
        int  image = intent.getIntExtra("img",2131165317);



        if (str==null){

            str = "Bangladesh";

        }

       imageView.setImageResource(image);
        textView9.setText(str);


        /*Date todayDate = Calendar.getInstance().getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = ;
*/

       // getCalculatedDate("dd-MM-yyyy", -10);




        api.getdata(getCalculatedDate("yyyy-MM-dd", -2),str).enqueue(new Callback<List<Data>>() {
           @Override
           public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

               if (!response.isSuccessful()){
                   textView.setText("Code: "+response.code());
                   return;
               }

               List<Data> datas = response.body();
               for (Data data: datas){

                  // String s = data.getDeaths().toString();

                   String content="";
                   content += data.getDeaths() + "\n\n";

                   list.add(data.getDeaths().toString());
                   conf.add(data.getConfirmed().toString());
                   death.add(data.getDeaths().toString());
                   rec.add(data.getRecovered().toString());
                   act.add(data.getActive().toString());


                  // textView.setText(data.getDeaths().toString()+"\n");
                   //textView.append(content);

               }


               int big = Integer.parseInt(list.get(1));
               int small = Integer.parseInt(list.get(0));

               String total = String.valueOf(big-small);
              // Toast.makeText(MainActivity.this,total, Toast.LENGTH_LONG).show();
               textView.setText(total);
               text.setText(getCalculatedDate("dd-MM-yyyy", -1));

               text1.setText(conf.get(1));
               text2.setText(death.get(1));
               text3.setText(rec.get(1));
               text4.setText(act.get(1));


           }

           @Override
           public void onFailure(Call<List<Data>> call, Throwable t) {
                textView.setText(t.getMessage());
           }
       });









        /*Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(todayDate);*/
        //textView.setText(d.toString());

        setRecyclerView();
        /*editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapterr.getFilter().filter(s);
                search = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setNew();
            }
        });


    }
    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    private void setRecyclerView(){


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        api.getda(str).enqueue(new Callback<List<Data>>() {
           @Override
           public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
               if (!response.isSuccessful()){
                   textView.setText("Code: "+response.code());
                   return;
               }
               List<Data> datas = response.body();
               Collections.reverse(datas);
                adapterr= new Adapter(MainActivity.this,datas);
               recyclerView.setAdapter(adapterr);
               adapterr.notifyDataSetChanged();





           }

           @Override
           public void onFailure(Call<List<Data>> call, Throwable t) {

               textView.setText(t.getMessage());
           }
       });

    }

    private void setNew(){


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        String sr = editText.getText().toString();


        /*if (pt.equals("dd-MM-yyyy")){

        }
        else {

        }*/

        SimpleDateFormat dateFormat = new SimpleDateFormat(determineDateFormat(sr));
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(sr);
            SimpleDateFormat sdfnewformat = new SimpleDateFormat("yyyy-MM-dd");
            String finalDateString = sdfnewformat.format(convertedDate);
            api.getdata(finalDateString,str).enqueue(new Callback<List<Data>>() {
                @Override
                public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                    if (!response.isSuccessful()){
                        textView.setText("Code: "+response.code());
                        return;
                    }
                    List<Data> datas = response.body();
                    Collections.reverse(datas);
                    adapterr= new Adapter(MainActivity.this,datas);
                    recyclerView.setAdapter(adapterr);
                    adapterr.notifyDataSetChanged();





                }

                @Override
                public void onFailure(Call<List<Data>> call, Throwable t) {

                    textView.setText(t.getMessage());
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.change:

                Intent intent = new Intent(MainActivity.this, Country.class);
                startActivity(intent);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "dd/MM/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
    }};

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    public String determineDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {

                String sf = DATE_FORMAT_REGEXPS.get(regexp);
                return sf;
            }
        }
        return null; // Unknown format.
    }


}