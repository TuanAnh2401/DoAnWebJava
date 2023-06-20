package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.dto.OrderDetailDto;
import com.example.DoAnWebJava.entities.OrderDetail;
import com.example.DoAnWebJava.entities.ProductCategory;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        List<OrderDetail> allOrderDetails = orderDetailService.getAllOrderDetails();
        return ResponseEntity.ok(allOrderDetails);
    }
    @GetMapping("/getByActivate/{isActivate}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByActivate(@PathVariable boolean isActivate) {
        List<OrderDetail> activeOrderDetails = orderDetailService.getOrderDetailsByActivate(isActivate);
        return ResponseEntity.ok(activeOrderDetails);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addOrderDetail(@RequestBody OrderDetail orderDetail) {
        if (orderDetail != null) {
            OrderDetail addOrderDetail = orderDetailService.addOrderDetail(orderDetail);
            return ResponseEntity.ok("Order Detail add successfully with ID: " + addOrderDetail.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable int id, @RequestBody OrderDetail orderDetail) throws UserRegistrationException {
        if (orderDetail != null) {
            OrderDetail updateOrderDetail = orderDetailService.updateOrderDetail(id, orderDetail);
            return ResponseEntity.ok("Order Detail updated successfully");
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }
}
