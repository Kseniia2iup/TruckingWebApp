package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Order;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.OrderDao;

import java.util.List;

@Repository("orderDao")
public class OrderDaoImpl extends AbstractDao<Integer, Order> implements OrderDao {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Order findOrderById(Integer id) {
        Query query = getSession().createQuery("Select O from Order O" +
                " Left Join Fetch O.truck T Left Join Fetch T.city " +
                "WHERE O.id = :id");
        query.setParameter("id", id);
        return (Order) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteOrder(Integer id) {
        delete(findOrderById(id));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveOrder(Order order) {
        persist(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateOrder(Order order) {
        update(order);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Order> findAllOrders() {
        Query query = getSession().createQuery("Select O from Order O" +
                " Left Join Fetch O.truck T Left Join Fetch T.city");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean isTruckHasOrder(Integer truckId) {
        Query query = getSession().createQuery("Select O from Order O Left Join Fetch O.truck WHERE O.truck.id = :truck_id");
        query.setParameter("truck_id", truckId);
        return query.uniqueResult()!=null;
    }
}
