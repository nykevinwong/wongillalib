package com.gamecopter.wongillalib.ui;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Kevin Wong on 6/12/2014.
 */
public class UIAnimation {
    private String name;
    private String indexes;
    private float duration;
    private Animation.PlayMode playmode = Animation.PlayMode.NORMAL;


    public UIAnimation(String name, String indexes, float duration, Animation.PlayMode mode) {
        this.name = name;
        this.indexes = indexes.replaceAll("\\s+", "");
        this.duration = duration;
        this.playmode = mode;
    }

    public int[][] get2DIndexes() {
        int[][] IndexList = new int[0][0];

        if (indexes != null) {
            String Indexes2d = this.indexes.replace("],[", "@").replace("]", "").replace("[", "");
            String[] splitedIndexes = Indexes2d.split("@");
            IndexList = new int[splitedIndexes.length][2];

            for (int i = 0; i < splitedIndexes.length; i++) {
                splitedIndexes[i] = splitedIndexes[i];
                String[] pos = splitedIndexes[i].split(",");
                int x = Integer.parseInt(pos[0]);
                int y = Integer.parseInt(pos[1]);
                IndexList[i][0] = x;
                IndexList[i][1] = y;
            }

        }

        return IndexList;
    }

    public int[] getIndexes() {
        int[] IndexList = new int[0];

        if (indexes != null &&
                !indexes.contains("[") &&
                !indexes.contains("]")) {
            String[] splitedIndexes = indexes.split(",");
            IndexList = new int[splitedIndexes.length];

            for (int i = 0; i < splitedIndexes.length; i++) {
                int number = Integer.parseInt(splitedIndexes[i]);
                IndexList[i] = number;
            }

        }

        return IndexList;
    }

    public String getName() {
        return name;
    }

    public float getDuration() {
        return duration;
    }
}
