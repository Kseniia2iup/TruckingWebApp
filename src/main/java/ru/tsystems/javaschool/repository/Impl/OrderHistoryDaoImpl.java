package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.exceptions.TruckingDaoException;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.OrderHistory;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.OrderHistoryDao;

import java.util.List;

@Repository("orderHistoryDao")
public class OrderHistoryDaoImpl extends AbstractDao<Integer, OrderHistory> implements OrderHistoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryDaoImpl.class);

    @Override
    @Transactional(rollbackFor = TruckingDaoException.class,
            readOnly = true, propagation = Propagation.SUPPORTS)
    public OrderHistory findHistoryById(Integer id) throws TruckingDaoException {
        try {
            Query query = getSession().createQuery("Select H from OrderHistory H" +
                    " Left Join Fetch H.driver D Left Join Fetch D.city " +
                    "WHERE H.id = :id");
            query.setParameter("id", id);
            return (OrderHistory) query.uniqueResult();
        } catch (Exception e){
            LOGGER.warn("From OrderHistoryDaoImpl method findHistoryById something went wrong:\n", e);
            throw new TruckingDaoException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = TruckingDaoException.class,
            propagation = Propagation.MANDATORY)
    public void saveHistory(OrderHistory orderHistory) throws TruckingDaoException {
        try {
            persist(orderHistory);
        } catch (Exception e){
            LOGGER.warn("From OrderHistoryDaoImpl method saveHistory something went wrong:\n", e);
            throw new TruckingDaoException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = TruckingDaoException.class,
            propagation = Propagation.MANDATORY)
    public void updateHistory(OrderHistory orderHistory) throws TruckingDaoException {
        try {
            update(orderHistory);
        } catch (Exception e){
            LOGGER.warn("From OrderHistoryDaoImpl method updateHistory something went wrong:\n", e);
            throw new TruckingDaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = TruckingDaoException.class,
            readOnly = true, propagation = Propagation.SUPPORTS)
    public List<OrderHistory> getHistoryForDriver(Driver driver) throws TruckingDaoException {
        try {
            Query query = getSession().createQuery("Select H from OrderHistory H" +
                    " Left Join Fetch H.driver D Left Join Fetch D.city " +
                    "WHERE H.driver = :driver");
            query.setParameter("driver", driver);
            return query.list();
        } catch (Exception e){
            LOGGER.warn("From OrderHistoryDaoImpl method getHistoryForDriver something went wrong:\n", e);
            throw new TruckingDaoException(e);
        }
    }
}
