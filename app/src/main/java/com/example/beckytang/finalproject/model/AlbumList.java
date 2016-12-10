package com.example.beckytang.finalproject.model;

import java.util.ArrayList;
import java.util.List;

public class AlbumList {

    public static List<Album> albumList = new ArrayList<Album>() {{
        add(new Album("AIT", 47.562441, 19.054716));
        add(new Album("Metro", 47.565648, 19.049949));
        add(new Album("Budapest", 47.509176, 19.076193));
        add(new Album("Hungary", 47.355107, 19.059543));
        add(new Album("Greenwich", 0, 0));
    }};
}
