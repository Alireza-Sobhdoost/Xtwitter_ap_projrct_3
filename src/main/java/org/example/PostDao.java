package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    private final SessionFactory sessionFactory;

    public  PostDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void savePost(Post post) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(post);
        tx.commit();
        session.close();
    }

    public static Post getPostById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Post post = session.get(Post.class, id);
        session.close();
        return post;
    }
    public static List<Post> getPostsByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = builder.createQuery(Post.class);
            Root<Post> postRoot = query.from(Post.class);

            Join<Post, User> userJoin = postRoot.join("user");

            query.select(postRoot)
                    .where(builder.equal(userJoin.get("id"), user.getId()));

            List<Post> posts = session.createQuery(query).getResultList();
            return posts;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Post> getAllPosts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = builder.createQuery(Post.class);
            Root<Post> root = query.from(Post.class);

            query.select(root);

            List<Post> posts = session.createQuery(query).getResultList();
            return posts;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions or log them as needed
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    public static void updatePost(Post post) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(post);
        tx.commit();
        session.close();
    }

    public void deletePost(Post post) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(post);
        tx.commit();
        session.close();
    }

    public boolean isPostExist(Long postId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Query the database for the post
        Post post = session.createQuery("FROM Post WHERE id = :postId", Post.class)
                .setParameter("postId", postId)
                .uniqueResult();

        session.getTransaction().commit();
        session.close();

        return post != null;
    }
}