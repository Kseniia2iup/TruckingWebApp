package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.Order;

import java.util.List;

public interface OrderDao {

    Order findOrderById(Integer id);

    void deleteOrder(Integer id);

    void saveOrder(Order order);

    void updateOrder(Order order);

    List<Order> findAllOrders();

    boolean isTruckHasOrder(Integer truckId);
}
