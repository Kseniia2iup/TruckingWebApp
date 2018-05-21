package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.OrderHistory;

import java.util.List;

public interface OrderHistoryService {

    OrderHistory findHistoryById(Integer id) throws TruckingServiceException;

    void saveHistory(OrderHistory orderHistory) throws TruckingServiceException;

    void updateHistory(OrderHistory orderHistory) throws TruckingServiceException;

    List<OrderHistory> getHistoryForDriver(Driver driver) throws TruckingServiceException;
}
