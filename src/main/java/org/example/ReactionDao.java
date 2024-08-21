package org.example;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ReactionDao {

    private final SessionFactory sessionFactory;

    public  ReactionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void saveReaction(Reaction Reaction) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(Reaction);
        tx.commit();
        session.close();
    }
    public static Reaction getReactionByCriteria(Long postId, Long userId, ReactTypes reactType) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Reaction reaction = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Reaction r WHERE r.post.id = :postId AND r.user.id = :userId AND r.reactType = :reactType");
            query.setParameter("postId", postId);
            query.setParameter("userId", userId);
            query.setParameter("reactType", reactType);

            reaction = (Reaction) ((org.hibernate.query.Query<?>) query).uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return reaction ;
    }
    public static boolean isReactionExist(Long postId, Long userId, ReactTypes reactionType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            Root<Reaction> root = query.from(Reaction.class);

            Predicate[] predicates = new Predicate[] {
                    builder.equal(root.get("post").get("id"), postId),
                    builder.equal(root.get("user").get("id"), userId),
                    builder.equal(root.get("reactType"), reactionType)
            };

            query.select(builder.count(root)).where(predicates);

            return session.createQuery(query).getSingleResult() > 0;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Reaction getReactionById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Reaction Reaction = session.get(Reaction.class, id);
        session.close();
        return Reaction;
    }

    public static List<Reaction> getAllReactions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Reaction> query = builder.createQuery(Reaction.class);
            Root<Reaction> root = query.from(Reaction.class);

            query.select(root);

            List<Reaction> Reactions = session.createQuery(query).getResultList();
            return Reactions;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions or log them as needed
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    public static void updateReaction(Reaction Reaction) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(Reaction);
        tx.commit();
        session.close();
    }

    public static void deleteReaction(Reaction Reaction) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(Reaction);
        tx.commit();
        session.close();
    }

//    public static boolean isReactionExist(Long ReactionId) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//
//        // Query the database for the Reaction
//        Reaction Reaction = session.createQuery("FROM Reaction WHERE id = :ReactionId", Reaction.class)
//                .setParameter("ReactionId", ReactionId)
//                .uniqueResult();
//
//        session.getTransaction().commit();
//        session.close();
//
//        return Reaction != null;
//    }
}
