package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.Order;

import java.util.List;

public interface OrderService {

    Order findOrderById(Integer id);

    void deleteOrder(Integer id);

    void saveOrder(Order order);

    void updateOrder(Order order);

    List<Order> findAllOrders();

    Double calculateSumDistanceOfOrder(Order order);

    Integer averageTimeInHoursForOrder(Order order);

    void removeTruckAndDriversFromOrder(Order order);
}
