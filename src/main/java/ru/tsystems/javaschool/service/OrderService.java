package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.model.Order;

import java.util.List;

public interface OrderService {

    Order findOrderById(Integer id) throws TruckingServiceException;

    void deleteOrder(Integer id) throws TruckingServiceException;

    void saveOrder(Order order) throws TruckingServiceException;

    void updateOrder(Order order) throws TruckingServiceException;

    List<Order> findAllOrders() throws TruckingServiceException;

    Double calculateSumDistanceOfOrder(Order order) throws TruckingServiceException;

    Integer averageTimeInHoursForOrder(Order order) throws TruckingServiceException;

    void removeTruckAndDriversFromOrder(Order order) throws TruckingServiceException;
}
