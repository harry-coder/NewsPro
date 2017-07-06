package com.example.harpreet.okhlee.volly;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Harpreet on 21/02/2017.
 */

public class MyApplication extends android.app.Application {

    static MyApplication application = null;

    //These are the webhose.io urls
    // private static String Api_Key = "f7722afb-257e-4cbf-84de-68a6657f13b6";
    private static String Api_Key = "b626f876-1f60-4632-bc80-f3d7bad6496f";
    private static String webhoseUrl = "https://webhose.io/search?token=";
    private static String filterQuery = "&format=json&language=english&country=IN&q=";//u have removed time stamp from here

    private static String relatedVideoUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&key=";

    private static String headlinesQuery = "&format=json&q=News&site=abc.net.au&site=aljazeera.com&site=arstechnica.com&site=bloomberg.com&site=cnbc.com" +
            "&site=engadget.com&site=buzzfeed.com&site=espn.go.com&site=espncricinfo.com&site=fortune.com" +
            "&site=fourfourtwo.com&site=foxsports.com&site=ycombinator.com&site=mashable.com" +
            "&site=mirror.co.uk&site=mtv.com&site=newsweek.com&site=nymag.com&site=nfl.com&site=reddit.com" +
            "&site=techcrunch.com&site=techradar.com&language=english";

    //this is Youtube Credentials
    private static int limit = 50;  //this is the max limit..
    static String youtubeBaseUrl = "https://www.googleapis.com/youtube/v3/videos?part=snippet&maxResults=";
    public static String youtubeApiKey = "AIzaSyCDVqkvSQpLf9NQTfUAHvs9FS-0vp0J7aI";
    public static String size = "&size=";

    private static String inShortApi="https://calm-bayou-63478.herokuapp.com/get_news?category=";
    private static String inShortLoadMoreApi="https://nameless-dusk-35156.herokuapp.com/load_more?category=entertainment&id=hcopdzny-1";

public static String  getCategoryWiseNews(String categoryName)
{

    return inShortApi+categoryName;
}

public static String getLoadMoreCategoryWiseNews(String categoryName, String id)
{
    return ""+"https://calm-bayou-63478.herokuapp.com/load_more?category="+categoryName+"&id="+id;
}

    public static String url(String category) {
        // return"https://api.themoviedb.org/3/discover/movie?"+Api_Key+"&language=en-US&sort_by=primary_release_date.desc&include_adult=false&include_video=false&page="+pageLimit;

//       return "https://api.themoviedb.org/3/discover/movie/upcoming?api_key=41930382bcc2c2f40b23477bf4947cec&page="+page;

//        return "http://www.omdbapi.com/?t=Deadpool&y=&plot=short&r=json";
        return webhoseUrl + Api_Key + filterQuery + category + size + 50;


    }



    public String getTimeStamp() {
        String value = String.valueOf(System.currentTimeMillis());

        return value;
    }

    public static String headlinesUrl() {
        return webhoseUrl + Api_Key + headlinesQuery;

      //  return webhoseUrl + Api_Key + filterQuery + size + 50;

    }

    public static String getYoutubeUrl() {

        return youtubeBaseUrl + limit + "&chart=mostPopular&regionCode=IN&key=" + youtubeApiKey;


    }

    public static String getRelatedYoutubeVideos(String id) {

        return relatedVideoUrl + youtubeApiKey + "&relatedToVideoId=" + id;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MyApplication getInstance() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }
}
