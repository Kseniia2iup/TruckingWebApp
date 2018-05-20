package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.OrderHistory;

import java.util.List;

public interface OrderHistoryDao {

    OrderHistory findHistoryById(Integer id);

    void saveHistory(OrderHistory orderHistory);

    void updateHistory(OrderHistory orderHistory);

    List<OrderHistory> getHistoryForDriver(Driver driver);
}
