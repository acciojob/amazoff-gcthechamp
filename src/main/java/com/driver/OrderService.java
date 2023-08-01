package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepositoryObj;

    public void addOrder(Order order)
    {
        orderRepositoryObj.addOrder(order);
    }

    public void addPartner(String partnerId)
    {
        orderRepositoryObj.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId){

        orderRepositoryObj.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {

        Order order = null;
        //order should be returned with an orderId.
        order = orderRepositoryObj.getOrderById(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId)
    {
        DeliveryPartner deliveryPartner = orderRepositoryObj.getPartnerById(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId)
    {
        Integer orderCount = orderRepositoryObj.getOrderCountByPartnerId(partnerId);
        return orderCount;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<Order> orderList = orderRepositoryObj.getOrdersByPartnerId(partnerId);
        List<String> stringOrderList = new ArrayList<>();
        for(Order order : orderList)
            stringOrderList.add(order.getId());
        return stringOrderList;

    }


    public List<String> getAllOrders() {
        List<String> allOrdersList = orderRepositoryObj.getAllOrders();
        return allOrdersList;
    }

    public Integer getCountOfUnassignedOrders() {
        Integer unassignedOrdersCount = orderRepositoryObj.getCountOfUnassignedOrders();
        return unassignedOrdersCount;
    }

    public Integer convertTimeToMinutes(String time)
    {
        String[] hhmm = time.split(":",-2);
        Integer hrs = Integer.parseInt(hhmm[0]);
        Integer mins = Integer.parseInt(hhmm[1]);
        Integer timeInMinutes = hrs*60 + mins;
        return timeInMinutes;
    }

    public String convertTimeToString(int time)
    {
        int hrs = time/60;
        int mins = time%60;
        return hrs+":"+mins;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count = 0;
        Integer timeInMinutes = convertTimeToMinutes(time);
        List<Order> orderList = orderRepositoryObj.getOrdersByPartnerId(partnerId);
        for(Order order : orderList)
        {
            if(order.getDeliveryTime() > timeInMinutes)
                count++;
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
       String lastDeliveryTime = "";
       int lastTime = 0;
       List<Order> orderList = orderRepositoryObj.getOrdersByPartnerId(partnerId);
       for(Order order : orderList)
       {
           lastTime = Math.max(lastTime,order.getDeliveryTime());
       }
       lastDeliveryTime = convertTimeToString(lastTime);
       return lastDeliveryTime;
    }

    public void deletePartnerById(String partnerId) {
        orderRepositoryObj.deletePartnerById(partnerId);

    }

    public void deleteOrderById(String orderId) {
        orderRepositoryObj.deleteOrderById(orderId);
    }
}
