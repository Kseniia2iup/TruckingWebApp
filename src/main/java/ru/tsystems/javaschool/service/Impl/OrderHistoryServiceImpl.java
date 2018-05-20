package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.OrderHistory;
import ru.tsystems.javaschool.repository.OrderHistoryDao;
import ru.tsystems.javaschool.service.OrderHistoryService;

import java.util.List;

@Service("orderHistoryService")
@Transactional
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private OrderHistoryDao orderHistoryDao;

    @Autowired
    public void setOrderHistoryDao(OrderHistoryDao orderHistoryDao) {
        this.orderHistoryDao = orderHistoryDao;
    }

    @Override
    public OrderHistory findHistoryById(Integer id) {
        return orderHistoryDao.findHistoryById(id);
    }

    @Override
    public void saveHistory(OrderHistory orderHistory) {
        orderHistoryDao.saveHistory(orderHistory);
    }

    @Override
    public void updateHistory(OrderHistory orderHistory) {
        orderHistoryDao.updateHistory(orderHistory);
    }

    @Override
    public List<OrderHistory> getHistoryForDriver(Driver driver) {
        return orderHistoryDao.getHistoryForDriver(driver);
    }
}
