import api_response_models.APIResponseComment;
import api_response_models.APIResponsePost;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Comment;
import entities.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {


    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] args) throws IOException, InterruptedException {

        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();

        updatePostsFromAPI();

        updateCommentsFromAPI();

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void updateCommentsFromAPI() throws IOException, InterruptedException {
        var commentsFromAPI = getCommentsFromAPI();

        for (var APIResponseComment : commentsFromAPI){
            var commentFromAPI = APIResponseComment.toComment();
            var postFromDB = getPostWithSourceId(commentFromAPI.getSourcePostId());
            commentFromAPI.setPostId(postFromDB.getId());

            var commentFromDB = getCommentWithSourceId(commentFromAPI.getSourceId());

            if(commentFromDB == null){
                saveCommentToDatabase(commentFromAPI);
                printAndPause("Added comment to db with sourceId: " + commentFromAPI.getSourceId());
            }
            else{
                commentFromAPI.setId(commentFromDB.getId());
                updateComment(commentFromDB);
                printAndPause("Updated post in db with sourceId: " + commentFromAPI.getSourceId());
            }
        }
    }

    private static void updatePostsFromAPI() throws IOException, InterruptedException {
        var postsFromAPI = getPostsFromAPI();

        for(var APIResponsePost: postsFromAPI){

            var postFromAPI = APIResponsePost.toPost();

            var postFromDB = getPostWithSourceId(postFromAPI.getSourceId());

            if(postFromDB == null){
                savePostToDatabase(postFromAPI);
                printAndPause("Added post to db with sourceId: " + postFromAPI.getSourceId());
            }
            else{
                postFromAPI.setId(postFromDB.getId());
                updatePost(postFromAPI);
                printAndPause("Updated post in db with sourceId: " + postFromAPI.getSourceId());
            }

        }
    }

    private static APIResponsePost[] getPostsFromAPI() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts");

        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");

        // This line makes the request
        InputStream responseStream = connection.getInputStream();

        // Manually converting the response body InputStream to APOD using Jackson
        ObjectMapper mapper = new ObjectMapper();
        var APIposts = mapper.readValue(responseStream, APIResponsePost[].class);

        return APIposts;
    }

    private static APIResponseComment[] getCommentsFromAPI() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/comments");

        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");

        // This line makes the request
        InputStream responseStream = connection.getInputStream();

        // Manually converting the response body InputStream to APOD using Jackson
        ObjectMapper mapper = new ObjectMapper();
        var APIComments = mapper.readValue(responseStream, APIResponseComment[].class);

        return APIComments;
    }


    private static void updatePost(Post post) {
        var transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(post);

        transaction.commit();
    }

    private static void updateComment(Comment comment) {
        var transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(comment);

        transaction.commit();
    }

    private static Post getPostWithSourceId(int sourceId) {
        String queryString = "SELECT p FROM Post p WHERE p.sourceId = :sourceId";
        TypedQuery<Post> query = entityManager.createQuery(queryString, Post.class);

        query.setParameter("sourceId", sourceId);

        var posts = query.getResultList();

        if (!posts.isEmpty()) {
            return posts.get(0);
        }

        return null;
    }

    private static Comment getCommentWithSourceId(int sourceId) {
        String queryString = "SELECT c FROM Comment c WHERE c.sourceId = :sourceId";
        TypedQuery<Comment> query = entityManager.createQuery(queryString, Comment.class);

        query.setParameter("sourceId", sourceId);

        var posts = query.getResultList();

        if (!posts.isEmpty()) {
            return posts.get(0);
        }

        return null;
    }


    private static void savePostToDatabase(Post post) {
        var transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(post);

        transaction.commit();
    }

    private static void saveCommentToDatabase(Comment comment) {
        var transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(comment);

        transaction.commit();
    }

    public static void printAndPause(String message) throws InterruptedException {
        System.out.println(message);
        Thread.sleep(50);
    }

}
