package com.example.alan.telev3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private LinearLayout grid_layout;
    private TableLayout table;
    private int n;
    private int m;
    private Button[][] btns;
    private ArrayList<VideoClass> videos = new ArrayList<VideoClass>();
    private ScenarioClass Scenario = new ScenarioClass();
    private ArrayList<ScreenClass> screens = new ArrayList<ScreenClass>();
    private WallClass Wall = new WallClass();
    int p=0;
    RelativeLayout rl= null;
    LayoutInflater li = null;
    TextView text = null;



    public class JsonConverter {
         //Filename
        InputStream jsonFileName;
        //constructor
        public JsonConverter(InputStream jsonFileName){
            this.jsonFileName = jsonFileName;
        }

        //Returns a json object from an input stream
        public JSONObject getJsonObject(){

            //Create input stream
            InputStream inputStreamObject = jsonFileName;

            try {
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStreamObject, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;

                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                return jsonObject;

                //returns the json object

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //if something wrong, return null
            return null;
        }

    }

    ////////////////////////////// Making Buttons

    private void createButtons() {
        table.removeAllViews();
        table.setStretchAllColumns(true);

        btns = new Button[n][m];
        for (int i = 0; i < n; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams paramsBtn = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1);

            for (int j = 0; j < m; j++) {
                Button btn = new Button(this);
                btns[i][j] = btn;
                btn.setPadding(0, 0, 0, 0);
              //  btn.setBackgroundColor(Color.rgb(43,125,225));
                GradientDrawable g = new GradientDrawable();
                g.setColor(Color.rgb(43,125,225));
                g.setCornerRadius(5);
                g.setStroke(7, 0000000);
                btn.setBackground(g);

             //  btn.setId(i * n + j);
                btn.setId(p);
                int b = btn.getId();
                btn.setLayoutParams(paramsBtn);
                row.addView(btn);
                String temp = "1234";
                String yolo = Integer.toString(b + 1);

                for (int t = 0; t < screens.size(); t++) {
                    if (screens.get(t).row == i + 1 & screens.get(t).cols == j + 1) {
                      // yolo = screens.get(t).id;
                        for (int k = 0; k < videos.size(); k++) {

                            //   for (int y = 0; y < videos.get(k).screen.size(); y++) {
                            // if (videos.get(y).screen.get(0).equals("yolo")) {
                            if (videos.get(k).screen.contains("s"+yolo)) {
                                temp = videos.get(k).file;
                                btn.setText(temp);

                            }
                        }

                        btn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                int id = view.getId();
                                int i = id / n;
                                int j = id - n * i;
                            }
                        });

                    }

                }
                p++;

            }
            table.addView(row);
            table.setColumnShrinkable(i, true);
        }
    }

            ////////////////////////////// Getting Video

        public void createVideoList () {
        InputStream jsonFileScenario;
        jsonFileScenario = getResources().openRawResource(R.raw.scenario);
        JSONObject reader = new JsonConverter(jsonFileScenario).getJsonObject();
        try {
            JSONObject jObject = reader.getJSONObject("scenario");

            Scenario.name = jObject.getString("name");

            JSONArray vids = jObject.getJSONArray("video");
            for (int i = 0; i < vids.length(); i++) {
                jObject = vids.getJSONObject(i);

                String idv = jObject.getString("idv");
                String file = jObject.getString("file");

                ArrayList<String> screen = new ArrayList<String>();
                JSONArray screens = jObject.getJSONArray("screens");
                for (int j = 0; j < screens.length(); j++) {
                    screen.add(screens.getString(j));
                }

                int distributed = jObject.getInt("distributed");
                int volume = jObject.getInt("volume");
                int mute = jObject.getInt("mute");
                String departure = jObject.getString("departure");
                String state = jObject.getString("state");
                int loop = jObject.getInt("loop");

                videos.add(new VideoClass(idv, file, screen, distributed, volume, mute, departure, state, loop));
            }
        } catch (JSONException e) {

        }
    }


////////////////////////////// Getting Wall

    public  void createWall(){
        InputStream jsonFileWall;
        jsonFileWall = getResources().openRawResource(R.raw.wall);
        JSONObject reader = new JsonConverter(jsonFileWall).getJsonObject();
        try {
            JSONArray  warr = reader.getJSONArray("wall");
            JSONObject jObject = warr.getJSONObject(0);

                Wall.name = jObject.getString("name");
                Wall.rows = jObject.getInt("rows");
                Wall.cols = jObject.getInt("cols");

            JSONArray  scrns = jObject.getJSONArray("screen");
            for(int i=0;i<scrns.length();i++) {
                jObject = scrns.getJSONObject(i);

                String id = jObject.getString("id");
                int row = jObject.getInt("row");
                int cols = jObject.getInt("cols");
                String orientation = jObject.getString("orientation");
                String type = jObject.getString("type");
                String ipv4 = jObject.getString("ipv4");

                screens.add(new ScreenClass (id, row, cols, orientation, type, ipv4));
            }
        }catch (JSONException e){

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        createVideoList();
        createWall();


        setContentView(R.layout.activity_game);
        grid_layout = (LinearLayout) findViewById(R.id.grid_layout);
        table = new TableLayout(this);

        grid_layout.addView(table);
       // Wall.name="Yahoo";
        Toast.makeText(getApplicationContext(),Wall.name,Toast.LENGTH_SHORT).show();

        n=Wall.rows;
        m=Wall.cols;
        createButtons();
        rl = (RelativeLayout) findViewById(R.id.rl);
        rl.removeAllViews();
        li = LayoutInflater.from(getApplicationContext());
        rl = (RelativeLayout) li.inflate(R.layout.listlay, rl);

        ListView lv = (ListView) rl.findViewById(R.id.lv);
        // создаем адаптер
        ArrayList<String> lfile = new ArrayList<String>();
        for (int i=0; i<videos.size(); i++)
        {
            int u = i+1;
            lfile.add("Video file" + u + ": " +videos.get(i).file);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, lfile);

        // присваиваем адаптер списку
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                rl = (RelativeLayout) findViewById(R.id.rl);
                rl.removeAllViews();
                li = LayoutInflater.from(getApplicationContext());
                rl = (RelativeLayout) li.inflate(R.layout.controls, rl);
                text = (TextView) rl.findViewById(R.id.textView);
                CharSequence n = "Scenario: "+ Scenario.name + "\n "+"idv = " + videos.get(position).idv + "\n " + "file = " + videos.get(position).file +"\n " +
                        "screens = ["+ videos.get(position).screen + "]"+ "\n" + "distributed = " + videos.get(position).distributed +"\n "+ "volume = "+ videos.get(position).volume +"\n "+
                        "mute = " + videos.get(position).mute+"\n "+  "departure = "+ videos.get(position).departure+ "\n" + "state = "+ videos.get(position).state +"\n "+ "loop = " + videos.get(position).loop ;
                text.setTextColor(Color.BLACK);
                text.setText(n);
                rl.findViewById(R.id.backb).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent t= new Intent(GameActivity.this,GameActivity.class);
                        startActivity(t);
                    }
                });

                rl.findViewById(R.id.playb).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videos.get(position).state="pause";
                        CharSequence n = "idv = " + videos.get(position).idv + "\n " + "file = " + videos.get(position).file +"\n "+ "screens = ["+ videos.get(position).screen + "]"+ "\n" + "distributed = " + videos.get(position).distributed +"\n "+ "volume = "+ videos.get(position).volume +"\n "+ "mute = " + videos.get(position).mute+"\n "+  "departure = "+ videos.get(position).departure+ "\n" + "state = "+ videos.get(position).state +"\n "+ "loop = " + videos.get(position).loop ;
                        text.setText(n);
                    }
                });


            }
        });
    }
}
