package org.adbms.orderplacement.controller;

import lombok.RequiredArgsConstructor;
import org.adbms.orderplacement.dto.OrderRequest;
import org.adbms.orderplacement.dto.OrderResponse;
import org.adbms.orderplacement.dto.OrdersResponse;
import org.adbms.orderplacement.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

    @GetMapping("/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@PathVariable String orderNumber){
        return orderService.getOrder(orderNumber);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public OrdersResponse getOrders(){
        return orderService.getAllOrders();
    }

    @DeleteMapping("/{orderNumber}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteOrder(@PathVariable String orderNumber){
        orderService.cancelOrder(orderNumber);
        return "Deleted";
    }
}
