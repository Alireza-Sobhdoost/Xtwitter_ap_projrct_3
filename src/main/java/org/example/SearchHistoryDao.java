package org.example;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SearchHistoryDao {

    private final SessionFactory sessionFactory;

    public  SearchHistoryDao (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void saveSearchHistory(SearchHistory SearchHistory) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(SearchHistory);
        tx.commit();
        session.close();
    }

    public static boolean isSearchHistoryExist(long userId, long postId, int reactType) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(SearchHistory.class);
            criteria.add(Restrictions.eq("userid", userId));
            criteria.add(Restrictions.eq("postid", postId));
            criteria.add(Restrictions.eq("react_type", reactType));

            return criteria.uniqueResult() != null;
        } finally {
            session.close();
        }
    }

    public static SearchHistory getSearchHistoryById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SearchHistory SearchHistory = session.get(SearchHistory.class, id);
        session.close();
        return SearchHistory;
    }

    public static List<SearchHistory> getSearchHistoryByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<SearchHistory> query = builder.createQuery(SearchHistory.class);
            Root<SearchHistory> root = query.from(SearchHistory.class);

            query.select(root);
            query.where(builder.equal(root.get("user").get("id"), userId));

            List<SearchHistory> searchHistories = session.createQuery(query).getResultList();
            return searchHistories;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions or log them as needed
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }


    public static void updateSearchHistory(SearchHistory SearchHistory) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(SearchHistory);
        tx.commit();
        session.close();
    }

    public static void deleteSearchHistory(SearchHistory SearchHistory) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(SearchHistory);
        tx.commit();
        session.close();
    }

//    public static boolean isSearchHistoryExist(Long SearchHistoryId) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//
//        // Query the database for the SearchHistory
//        SearchHistory SearchHistory = session.createQuery("FROM SearchHistory WHERE id = :SearchHistoryId", SearchHistory.class)
//                .setParameter("SearchHistoryId", SearchHistoryId)
//                .uniqueResult();
//
//        session.getTransaction().commit();
//        session.close();
//
//        return SearchHistory != null;
//    }
}
