package SystemDesign;

public class Tweet {
    public int id;
    public int user_id;
    public String text;
    public Tweet create(int user_id, String tweet_text) {
                // This will create a new tweet object,
                // and auto fill id
            this.id++;
            this.user_id = user_id;
            this.text = tweet_text;
            return null;
       }
}