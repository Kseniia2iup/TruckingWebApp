package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.OrderHistory;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.OrderHistoryDao;

import java.util.List;

@Repository("orderHistoryDao")
public class OrderHistoryDaoImpl extends AbstractDao<Integer, OrderHistory> implements OrderHistoryDao {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public OrderHistory findHistoryById(Integer id) {
    Query query = getSession().createQuery("Select H from OrderHistory H" +
            " Left Join Fetch H.driver D Left Join Fetch D.city " +
            "WHERE H.id = :id");
        query.setParameter("id", id);
        return (OrderHistory) query.uniqueResult();
    }

    @Override
    public void saveHistory(OrderHistory orderHistory) {
        persist(orderHistory);
    }

    @Override
    public void updateHistory(OrderHistory orderHistory) {
        update(orderHistory);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<OrderHistory> getHistoryForDriver(Driver driver) {
        Query query = getSession().createQuery("Select H from OrderHistory H" +
                " Left Join Fetch H.driver D Left Join Fetch D.city " +
                "WHERE H.driver = :driver");
        query.setParameter("driver", driver);
        return query.list();
    }
}
