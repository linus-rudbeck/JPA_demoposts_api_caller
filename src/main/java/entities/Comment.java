package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Comment {
    @GeneratedValue
    @Id
    private Long id;

    private int sourceId;

    private Long postId;

    private int sourcePostId;

    private String name;
    private String body;
    private String email;

    public Comment() {
    }

    public Comment(int sourceId, int sourcePostId, String name, String body, String email) {
        this.sourceId = sourceId;
        this.sourcePostId = sourcePostId;
        this.name = name;
        this.body = body;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public int getSourcePostId() {
        return sourcePostId;
    }

    public void setSourcePostId(int sourcePostId) {
        this.sourcePostId = sourcePostId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
