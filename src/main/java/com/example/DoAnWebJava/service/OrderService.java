package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.dto.OrderDto;
import com.example.DoAnWebJava.entities.Order;
import com.example.DoAnWebJava.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDto> getAllOrderDtos() {
        List<Order> orders = orderRepository.findAll();
        return convertToDtoList(orders);
    }

    public List<OrderDto> getOrderDtosByIsActive(boolean isActive) {
        List<Order> orders = orderRepository.findByIsActivate(isActive);
        return convertToDtoList(orders);
    }

    public OrderDto getOrderDtoById(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.map(this::convertToDto).orElse(null);
    }

    public OrderDto addOrder(OrderDto orderDto) {
        Order order = convertToEntity(orderDto);
        order.setCreatedDate(new Date());
        order.setModifiedDate(new Date());
        Order addedOrder = orderRepository.save(order);
        return convertToDto(addedOrder);
    }

    private List<OrderDto> convertToDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setQuantity(order.getQuantity());
        orderDto.setTypePayment(order.getTypePayment());
        orderDto.setOrder(order.isOrder());
        orderDto.setOrderDetails(order.getOrderDetails());
        orderDto.setUser(order.getUser());
        return orderDto;
    }


    private Order convertToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setQuantity(orderDto.getQuantity());
        order.setTypePayment(orderDto.getTypePayment());
        order.setOrder(order.isOrder());
        order.setOrderDetails(orderDto.getOrderDetails());
        order.setUser(orderDto.getUser());
        return order;
    }

    public OrderDto updateOrder(int id, OrderDto orderDto) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            // Update the order properties
            order.setTotalAmount(orderDto.getTotalAmount());
            order.setQuantity(orderDto.getQuantity());
            order.setTypePayment(orderDto.getTypePayment());
            order.setOrder(order.isOrder());
            order.setOrderDetails(orderDto.getOrderDetails());
            order.setUser(orderDto.getUser());

            // Save the updated order
            Order updatedOrder = orderRepository.save(order);
            return convertToDto(updatedOrder);
        }
        return null;
    }
}
