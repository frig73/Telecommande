package com.example.alan.telev3;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Alan on 19.03.2017.
 */

public class VideoClass {
    String idv;
    String file;
    ArrayList<String> screen;
    int distributed;
    int volume;
    int mute;
    String departure;
    String state;
    int loop;

    public VideoClass (String idv, String file, ArrayList<String> screen, int distributed,  int volume, int mute, String departure,  String state, int loop)
    {
        this.idv = idv;
        this.file = file;
        this.screen=screen;
        this.distributed=distributed;
        this.volume=volume;
        this.mute=mute;
        this.departure=departure;
        this.state=state;
        this.loop=loop;
    }
}
