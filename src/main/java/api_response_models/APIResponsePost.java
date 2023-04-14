package api_response_models;

import entities.Post;

public class APIResponsePost {
    private int userId;
    private int id;
    private String title;
    private String body;

    public APIResponsePost() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Post toPost(){
        var post = new Post((long) userId, title, body, id);
        return post;
    }
}
