package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FollowDao {
    private final SessionFactory sessionFactory;

    public FollowDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void saveFollow(Follow follow) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(follow);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserAFollowedByUserB(User userA, User userB) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Query<Follow> query = session.createQuery(
                    "FROM Follow f WHERE f.followed = :userA AND f.follower = :userB", Follow.class
            );
            query.setParameter("userA", userA);
            query.setParameter("userB", userB);
            Follow follow = query.uniqueResult();

            if (follow != null) {
                session.delete(follow);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static boolean isUserAFollowedByUserB(User userA, User userB) {
        Session session = HibernateUtil.getSessionFactory().openSession() ;
        Query<Long> query = session.createQuery(
                "SELECT COUNT(f) FROM Follow f WHERE f.followed = :userA AND f.follower = :userB", Long.class
        );
        query.setParameter("userA", userA);
        query.setParameter("userB", userB);
        Long count = query.uniqueResult();
        return count > 0;
    }

    public Follow getFollowById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            return session.get(Follow.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public List<Follow> getAllFollows() {
//        try (Session session =HibernateUtil.getSessionFactory().openSession();) {
//            Query<Follow> query = session.createQuery("from Follow", Follow.class);
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void updateFollow(Follow follow) {
        try (Session session =HibernateUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.update(follow);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<User> getFollowedUsers(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                    "SELECT f.followed FROM Follow f WHERE f.follower = :user", User.class
            );
            query.setParameter("user", user);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteFollow(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            Follow follow = session.get(Follow.class, id);
            if (follow != null) {
                session.delete(follow);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSessionFactory() {
        sessionFactory.close();
    }
}