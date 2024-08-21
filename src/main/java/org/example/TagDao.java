package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TagDao {

    private final SessionFactory sessionFactory;

    public  TagDao (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void saveTag(Tag tag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(tag);
        tx.commit();
        session.close();
    }

    public Post getPostById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Post post = session.get(Post.class, id);
        session.close();
        return post;
    }
    public static Tag getTagByUName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.where(builder.equal(root.get("name"), name));

        Tag tag = session.createQuery(query).getSingleResult();

        session.close();
        return tag;
    }

    public static List<String> getTagNamesByPostId(Long postId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> query = builder.createQuery(String.class);
            Root<Tag> root = query.from(Tag.class);

            query.select(root.get("name"));
            query.where(builder.equal(root.get("post").get("id"), postId));

            List<String> tagNames = session.createQuery(query).getResultList();
            return tagNames;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions or log them as needed
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    public void updatePost(Post post) {
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

    public static boolean isTagExist(String tag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Query the database for any tag with the provided name
        Tag mytag = session.createQuery("FROM Tag WHERE name = :tag", Tag.class)
                .setParameter("tag", tag)
                .setFirstResult(0)
                .setMaxResults(1)
                .uniqueResult();

        session.getTransaction().commit();
        session.close();

        return mytag != null;
    }
}