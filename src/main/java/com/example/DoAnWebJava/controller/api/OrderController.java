package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.dto.OrderDto;
import com.example.DoAnWebJava.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> allOrders = orderService.getAllOrderDtos();
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/getActive/{isActive}")
    public ResponseEntity<List<OrderDto>> getActiveOrders(@PathVariable boolean isActive) {
        List<OrderDto> orders = orderService.getOrderDtosByIsActive(isActive);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable int id) {
        OrderDto order = orderService.getOrderDtoById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody OrderDto orderDto) {
        if (orderDto != null) {
            OrderDto addOrder = orderService.addOrder(orderDto);
            return ResponseEntity.ok("Order added successfully with ID: " + addOrder.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable int id, @RequestBody OrderDto orderDto) {
        if (orderDto != null) {
            OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
            if (updatedOrder != null) {
                return ResponseEntity.ok("Order updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }
}
