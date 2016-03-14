package SystemDesign;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Queue;

public class MiniTwitter {

    int id;
    Hashtable<Integer, TweetUser> userTable;
    ArrayList<Tweet> tweetList;

    class TweetUser{
        int user_id;
        ArrayList<Integer> tweetNumber;
        HashSet<Integer> followId;
        public TweetUser(int user_id){
            if (userTable.containsKey(user_id)){
                System.out.println("ERROR: This user ID is occupied");
                return;
            }
            this.user_id = user_id;
            tweetNumber = new ArrayList<Integer>();
            followId = new HashSet<Integer>();
        }
    }
    
    public MiniTwitter() {
        // initialize your data structure here.
        id = 1;
        userTable = new Hashtable<Integer, TweetUser>();
        tweetList = new ArrayList<Tweet>();
        userTable.put(1, new TweetUser(1));
        userTable.put(2, new TweetUser(2));
    }

    // @param user_id an integer
    // @param tweet a string
    // return a tweet
    public Tweet postTweet(int user_id, String tweet_text) {
        if (!userTable.containsKey(user_id)){
            userTable.put(user_id, new TweetUser(user_id));
        }
        Tweet tweet = new Tweet();
        tweet.id = id;
        id++;
        tweet.user_id = user_id;
        tweet.text = tweet_text;
        tweetList.add(tweet);
        userTable.get(user_id).tweetNumber.add(tweet.id);
        return tweet;
        //  Write your code here
    }

    // @param user_id an integer
    // return a list of 10 new feeds recently
    // and sort by timeline
    public ArrayList<Tweet> getNewsFeed(int user_id) {
        // Write your code here
        if (!userTable.containsKey(user_id)){
            userTable.put(user_id, new TweetUser(user_id));
        }
        ArrayList<Tweet> result = new ArrayList<Tweet>();
        Comparator<Tweet> tweetCmp = new Comparator<Tweet>(){
            @Override
            public int compare(Tweet o1, Tweet o2) {
                // TODO Auto-generated method stub
                return o2.id - o1.id;
            }
        };
        Queue<Tweet> path = new PriorityQueue<Tweet>(10, tweetCmp);
        ArrayList<Tweet> temp = getTimeline(user_id);
        if (temp != null){
            path.addAll(temp);
        }
        for (int followId : userTable.get(user_id).followId){
            temp = getTimeline(followId);
            if (temp != null){
                path.addAll(temp);
            }
        }
        int i = 0;
        while(!path.isEmpty() && i < 10){
            result.add(path.poll());
            i++;
        }
        return result;
    }
        
    // @param user_id an integer
    // return a list of 10 new posts recently
    // and sort by timeline
    public ArrayList<Tweet>  getTimeline(int user_id) {
        // Write your code here
        if (!userTable.containsKey(user_id)){
            userTable.put(user_id, new TweetUser(user_id));
        }
        ArrayList<Tweet> result = new ArrayList<Tweet>();
        int j = 0;
        int size = userTable.get(user_id).tweetNumber.size();
        if (size == 0){
            return null;
        }
        while (j < 10 && j < size){
            int tweetId = userTable.get(user_id).tweetNumber.get(size - j - 1) - 1;
            Tweet tweet = tweetList.get(tweetId);
            result.add(tweet);
            j++;
        }
        return result;
    }

    // @param from_user_id an integer
    // @param to_user_id an integer
    // from user_id follows to_user_id
    public void follow(int from_user_id, int to_user_id) {
        // Write your code here
        if (!userTable.containsKey(from_user_id)){
            userTable.put(from_user_id, new TweetUser(from_user_id));
        }
        if (!userTable.containsKey(to_user_id)){
            userTable.put(to_user_id, new TweetUser(to_user_id));
        }
        if (userTable.get(from_user_id).followId.contains(to_user_id)){
            System.out.println(from_user_id + " has already followed " + to_user_id);
            return;
        }
        userTable.get(from_user_id).followId.add(to_user_id);
    }

    // @param from_user_id an integer
    // @param to_user_id an integer
    // from user_id unfollows to_user_id
    public void unfollow(int from_user_id, int to_user_id) {
        // Write your code here
        if (!userTable.containsKey(from_user_id)){
            userTable.put(from_user_id, new TweetUser(from_user_id));
        }
        if (!userTable.containsKey(to_user_id)){
            userTable.put(to_user_id, new TweetUser(to_user_id));
        }
        if (!userTable.get(from_user_id).followId.contains(to_user_id)){
            System.out.println(from_user_id + " has already unfollowed " + to_user_id);
            return;
        }
        userTable.get(from_user_id).followId.remove(to_user_id);
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        long startTime=System.currentTimeMillis(); 
        MiniTwitter twitter = new MiniTwitter();
        twitter.getTimeline(9);
        twitter.follow(1, 9);
        twitter.getNewsFeed(4);
        twitter.getTimeline(9);
        twitter.postTweet(5, "lmnlintcodelintcodelintco");
        twitter.postTweet(1, "hiklmnlintco");
        twitter.postTweet(4, "iklmnlintcodelintcodelintcode i love lintcode lintcode ");
        twitter.postTweet(2, "e lint");
        twitter.follow(5, 4);
        twitter.follow(5, 6);
        twitter.getNewsFeed(9);
        twitter.unfollow(5, 6);
        twitter.getNewsFeed(1);
        twitter.postTweet(9, "de");
        twitter.getNewsFeed(4);
        twitter.getNewsFeed(7);
        twitter.postTweet(1, "klmnlintcodelintco");
        twitter.getNewsFeed(3);
        twitter.follow(6, 8);
        twitter.follow(4, 8);
        twitter.getTimeline(3);
        twitter.postTweet(8, "codelintcodelintcode i ");
        twitter.postTweet(5, "odelintcode i love lin");
        twitter.postTweet(1, "e i love lintcode lintcode");
        twitter.getTimeline(8);
        twitter.unfollow(4, 8);
        twitter.getTimeline(8);
        twitter.getNewsFeed(3);
        twitter.getNewsFeed(8);
        twitter.postTweet(8, "de i love l");
        twitter.postTweet(4, "intcodelintcodelintcode i love lint");
        twitter.postTweet(7, "delin");
        twitter.getNewsFeed(2);
        twitter.getNewsFeed(1);
        twitter.getNewsFeed(7);
        twitter.postTweet(3, " lintcode ");
        twitter.getNewsFeed(6);
        twitter.getNewsFeed(6);
        twitter.postTweet(9, "hiklmnlintcodelintcode");
        twitter.getNewsFeed(5);
        twitter.getNewsFeed(2);
        twitter.follow(6, 7);
        twitter.getNewsFeed(1);
        twitter.getTimeline(4);
        twitter.postTweet(4, "de is");
        twitter.postTweet(9, "hiklmnlintcodelint");
        twitter.getNewsFeed(3);
        twitter.getTimeline(4);
        twitter.postTweet(9, "ntcode lint");
        twitter.getNewsFeed(7);
        twitter.getTimeline(7);
        twitter.getNewsFeed(5);
        twitter.follow(3, 7);
        twitter.follow(9, 6);
        twitter.getNewsFeed(7);
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms"); 
    }
}