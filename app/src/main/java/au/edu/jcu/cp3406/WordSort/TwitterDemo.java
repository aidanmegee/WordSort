package au.edu.jcu.cp3406.WordSort;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterDemo {

    Twitter twitter = TwitterFactory.getSingleton();

    public void updateTweet(String tweet) {

        final String message = tweet;
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Status status = twitter.updateStatus(message);
                    System.out.println("Successfully updated the status to [" + status.getText() + "].");
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
