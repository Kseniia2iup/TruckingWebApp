package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.exceptions.TruckingDaoException;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.OrderHistory;

import java.util.List;

public interface OrderHistoryDao {

    OrderHistory findHistoryById(Integer id) throws TruckingDaoException;

    void saveHistory(OrderHistory orderHistory) throws TruckingDaoException;

    void updateHistory(OrderHistory orderHistory) throws TruckingDaoException;

    List<OrderHistory> getHistoryForDriver(Driver driver) throws TruckingDaoException;
}
