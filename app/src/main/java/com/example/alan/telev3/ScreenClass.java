package com.example.alan.telev3;

import java.util.ArrayList;

/**
 * Created by Alan on 19.03.2017.
 */

public class ScreenClass {
    String id;
    int row;
    int cols;
    String orientation;
    String type;
    String ipv4;

    public ScreenClass (String id, int row, int cols, String orientation, String type, String ipv4)
    {
        this.id = id;
        this.row = row;
        this.cols=cols;
        this.orientation=orientation;
        this.type=type;
        this.ipv4=ipv4;
    }
}
