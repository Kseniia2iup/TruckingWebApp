package ru.tsystems.javaschool.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.OrderHistory;
import ru.tsystems.javaschool.repository.OrderHistoryDao;
import ru.tsystems.javaschool.service.OrderHistoryService;

import java.util.List;

@Service("orderHistoryService")
@Transactional
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryServiceImpl.class);

    private OrderHistoryDao orderHistoryDao;

    @Autowired
    public void setOrderHistoryDao(OrderHistoryDao orderHistoryDao) {
        this.orderHistoryDao = orderHistoryDao;
    }

    @Override
    public OrderHistory findHistoryById(Integer id) throws TruckingServiceException {
        try {
            return orderHistoryDao.findHistoryById(id);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    @Override
    public void saveHistory(OrderHistory orderHistory) throws TruckingServiceException {
        try {
            orderHistoryDao.saveHistory(orderHistory);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    @Override
    public void updateHistory(OrderHistory orderHistory) throws TruckingServiceException {
        try {
            orderHistoryDao.updateHistory(orderHistory);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    @Override
    public List<OrderHistory> getHistoryForDriver(Driver driver) throws TruckingServiceException {
        try {
            return orderHistoryDao.getHistoryForDriver(driver);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }
}
