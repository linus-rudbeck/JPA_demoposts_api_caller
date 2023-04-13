import entities.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main {


    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();

        var postFromAPI = new Post(1L, "NEW TITLE", "est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla", 1);
        
        var postFromDB = getPostWithSourceId(postFromAPI.getSourceId());

        if(postFromDB == null){
            saveToDatabase(postFromAPI);
        }
        else{
            postFromAPI.setId(postFromDB.getId());
            updatePost(postFromAPI);
        }

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void updatePost(Post post) {
        var transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(post);

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


    private static void saveToDatabase(Post post) {
        var transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(post);

        transaction.commit();
    }


}
