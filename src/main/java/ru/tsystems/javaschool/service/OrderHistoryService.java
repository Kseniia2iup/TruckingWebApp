package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.OrderHistory;

import java.util.List;

public interface OrderHistoryService {

    OrderHistory findHistoryById(Integer id);

    void saveHistory(OrderHistory orderHistory);

    void updateHistory(OrderHistory orderHistory);

    List<OrderHistory> getHistoryForDriver(Driver driver);
}
