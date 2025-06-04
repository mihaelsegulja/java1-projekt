/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.utilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author miki
 */
public class RedditUrlUtils {
    
    private RedditUrlUtils() { }
    
    public static String normalizeRedditRssUrl(String userInput) throws IllegalArgumentException {
        if (userInput == null || userInput.isBlank()) {
            throw new IllegalArgumentException("Input is empty.");
        }

        userInput = userInput.trim().toLowerCase();

        // Shorthand: "r/subname" or just "subname"
        if (!userInput.startsWith("http")) {
            if (userInput.startsWith("r/")) {
                userInput = userInput.substring(2);
            }
            if (userInput.contains("/")) {
                throw new IllegalArgumentException("Invalid shorthand input.");
            }
            return "https://www.reddit.com/r/" + userInput + "/.rss";
        }

        try {
            URL url = new URL(userInput);
            String path = url.getPath();

            // Strip .rss for type detection
            if (path.endsWith(".rss")) {
                path = path.substring(0, path.length() - 4);
            }

            RedditUrlType type = detectRedditUrlType(userInput);

            switch (type) {
                case SUBREDDIT -> {
                    String[] parts = path.split("/");
                    return "https://www.reddit.com/r/" + parts[2] + "/.rss";
                }
                case POST, COMMENT -> {
                    String[] parts = path.split("/");
                    return "https://www.reddit.com/r/" + parts[2] + "/comments/" + parts[4] + "/.rss";
                }
                default -> throw new IllegalArgumentException("Unsupported Reddit URL for RSS.");
            }

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed Reddit URL.");
        }
    }
    
    public static RedditUrlType detectRedditUrlType(String input) {
        if (input == null || input.isBlank()) return RedditUrlType.UNKNOWN;

        input = input.trim().toLowerCase();

        if (!input.startsWith("http")) {
            input = "https://" + input;
        }

        try {
            URL url = new URL(input);
            String path = url.getPath();

            // Strip trailing .rss if present
            if (path.endsWith(".rss")) {
                path = path.substring(0, path.length() - 4);
            }

            // Subreddit: /r/subreddit/ or /r/subreddit
            if (path.matches("^/r/[^/]+/?$")) {
                return RedditUrlType.SUBREDDIT;
            }

            // Post: /r/sub/comments/postId/ or /r/sub/comments/postId/title/
            if (path.matches("^/r/[^/]+/comments/[a-z0-9]+(?:/[^/]+)?/?$")) {
                return RedditUrlType.POST;
            }

            // Comment (with ID of comment): /r/sub/comments/postId/title/commentId/
            if (path.matches("^/r/[^/]+/comments/[a-z0-9]+/[^/]+/[a-z0-9]+/?$")) {
                return RedditUrlType.COMMENT;
            }

            // User: /user/username or /u/username
            if (path.matches("^/(u|user)/[\\w\\d_-]+/?$")) {
                return RedditUrlType.USER;
            }

        } catch (MalformedURLException e) {
            return RedditUrlType.UNKNOWN;
        }

        return RedditUrlType.UNKNOWN;
    }

    public enum RedditUrlType {
        COMMENT,
        POST,
        SUBREDDIT,
        USER,
        UNKNOWN;
    }
}
