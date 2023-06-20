package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.OrderDetail;
import com.example.DoAnWebJava.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }



    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        orderDetail.setCreatedDate(new Date());
        orderDetail.setModifiedDate(new Date());
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail updateOrderDetail(int id, OrderDetail orderDetail) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order detail id: " + id));

        // Update the properties of the existing order detail
        existingOrderDetail.setPrice(orderDetail.getPrice());
        existingOrderDetail.setQuantity(orderDetail.getQuantity());
        existingOrderDetail.setOrder(orderDetail.getOrder());
        existingOrderDetail.setProduct(orderDetail.getProduct());
        existingOrderDetail.setModifiedDate(new Date());

        return orderDetailRepository.save(existingOrderDetail);
    }

}
