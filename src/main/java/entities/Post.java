package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Post {
    @GeneratedValue
    @Id
    private Long id;

    private Long userId;

    private String title;

    private String body;
    private int sourceId;

    public Post(Long userId, String title, String body, int sourceId) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.sourceId = sourceId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public Long getUserId() {
        return userId;
    }

    public Post() {
    }



    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", sourceId=" + sourceId +
                '}';
    }
}
