package org.adbms.orderplacement.service;

import lombok.RequiredArgsConstructor;
import org.adbms.orderplacement.dto.*;
import org.adbms.orderplacement.exception.RestException;
import org.adbms.orderplacement.model.Order;
import org.adbms.orderplacement.model.OrderItem;
import org.adbms.orderplacement.model.OrderStatus;
import org.adbms.orderplacement.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest)  {

        Order order = new Order();

        //get from user management
        order.setUserId(orderRequest.getUserId());
        order.setUserName("Haritha");
        order.setUserAddress("Colombo");



        //confirm quantity availability and get prices from inventory management

        List<OrderItem> orderItems = orderRequest.getOrderItems()
                .stream()
                .map(this::mapToEntity)
                .toList();

        order.setOrderItems(orderItems);

//        if(false){
//            throw new RestException(HttpStatus.NOT_FOUND, "Product(s) out of stock");
//        }
        order.setDate(new Date());
        order.setStatus(OrderStatus.PLACED);
        order.setOrderNumber(generateOrderNumber(order));
        this.orderRepository.save(order);
    }



    public OrdersResponse getAllOrders() {
        OrdersResponse ordersResponse = new OrdersResponse();
        ordersResponse.setOrders(orderRepository.findAll().stream().map(this::mapToOrderResponse).toList());
        return ordersResponse;
    }


    public OrderResponse getOrder(String orderNumber) {
        Optional<Order> order = orderRepository.findByOrderNumber(orderNumber);
        if(order.isEmpty()){
            throw new RestException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return mapToOrderResponse(order.get());
    }

    public void cancelOrder(String orderNumber) {
        Optional<Order> order = orderRepository.findByOrderNumber(orderNumber);
        if(order.isEmpty()){
            throw new RestException(HttpStatus.NOT_FOUND, "Order not found");
        }
        if(order.get().getStatus() == OrderStatus.COMPLETED){
            throw new RestException(HttpStatus.FORBIDDEN, "Order completed");
        }
        order.get().setStatus(OrderStatus.CANCELLED);
        //send details to inventory management to add back the quantities
        orderRepository.save(order.get());
    }
    public void updateStatus(String orderNumber, String status) {
        Optional<Order> order = orderRepository.findByOrderNumber(orderNumber);
        if(order.isEmpty()){
            throw new RestException(HttpStatus.NOT_FOUND, "Order not found");
        }
        if(order.get().getStatus() == OrderStatus.CANCELLED){
            throw new RestException(HttpStatus.FORBIDDEN, "Order already cancelled");
        }
        //can be expanded to accommodate more statuses
        switch (status){
            case "completed":
                order.get().setStatus(OrderStatus.COMPLETED);
                break;
            default:
                break;
        }
        orderRepository.save(order.get());
    }
    private OrderResponse mapToOrderResponse(Order order){
        return OrderResponse.builder()
                .orderNumber(order.getOrderNumber())
                .date(order.getDate())
                .userId(order.getUserId())
                .userName(order.getUserName())
                .userAddress(order.getUserAddress())
                .orderItems(order.getOrderItems().stream().map(this:: mapToOrderItemsDTO).toList())
                .status(order.getStatus())
                .build();
    }

    private OrderItemResponseDTO mapToOrderItemsDTO(OrderItem orderItem){
        return OrderItemResponseDTO.builder()
                .pid(orderItem.getProductId())
                .itemId(orderItem.getItemId())
                .qty(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
    private OrderItem mapToEntity(OrderItemDTO orderItemDTO){
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(orderItemDTO.getPid());
        orderItem.setQuantity(orderItemDTO.getQty());
        return orderItem;
    }
    private String generateOrderNumber(Order order){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(order.getDate());
        return order.getUserId().toString() + "_" + timeStamp + "_" + new Random().nextInt(1001);
    }


}

