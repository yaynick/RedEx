package com.mushroomrobot.redex;

/**
 * Created by Nick.
 */

//This class holds the data of the JSON objects returned by the Reddit API

public class Post {

    String subreddit;
    String title;
    String author;
    int points;
    int numComments;
    String permalink;
    String url;
    String domain;
    String id;

    String getDetails(){
        String details = author + "posted this and got " + numComments + " replies";
        return details;
    }

    String getTitle(){
        return title;
    }

    String getScore(){
        return Integer.toString(points);
    }

}
