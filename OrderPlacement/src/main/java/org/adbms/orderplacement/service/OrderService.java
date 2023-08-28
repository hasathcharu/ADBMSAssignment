package org.adbms.orderplacement.service;

import lombok.RequiredArgsConstructor;
import org.adbms.orderplacement.dto.*;
import org.adbms.orderplacement.model.Order;
import org.adbms.orderplacement.model.OrderItem;
import org.adbms.orderplacement.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest)  {

        Order order = new Order();

        //get from user management
        Long userId = 1L;
        order.setUserId(userId);
        order.setUserName("Haritha");
        order.setUserAddress("Colombo");

        //confirm quantity availability and get prices from inventory management

        List<OrderItem> orderItems = orderRequest.getOrderItems()
                .stream()
                .map(this::mapToEntity)
                .toList();

        order.setOrderItems(orderItems);

        if(false){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product(s) out of stock");
        }
        order.setDate(new Date());
        this.orderRepository.save(order);


    }

    private OrderItem mapToEntity(OrderItemDTO orderItemDTO){
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(orderItemDTO.getPid());
        orderItem.setQuantity(orderItemDTO.getQty());
        return orderItem;
    }

    public OrdersResponse getAllOrders() {
        OrdersResponse ordersResponse = new OrdersResponse();
        ordersResponse.setOrders(orderRepository.findAll().stream().map(this::mapToOrderResponse).toList());
        return ordersResponse;
    }
    private OrderResponse mapToOrderResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderNumber(order.getOrderNumber());
        orderResponse.setDate(order.getDate());
        orderResponse.setUserId(order.getUserId());
        orderResponse.setUserName(order.getUserName());
        orderResponse.setUserAddress(order.getUserAddress());
        orderResponse.setOrderItems(order.getOrderItems().stream().map(this:: mapToOrderItemsDTO).toList());
        return orderResponse;
    }

    private OrderItemResponseDTO mapToOrderItemsDTO(OrderItem orderItem){
        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setPid(orderItem.getProductId());
        orderItemResponseDTO.setItemId(orderItem.getItemId());
        orderItemResponseDTO.setQty(orderItem.getQuantity());
        orderItemResponseDTO.setPrice(orderItem.getPrice());
        return orderItemResponseDTO;
    }

    public OrderResponse getOrder(String orderNumber) {
        Optional<Order> order = orderRepository.findByOrderNumber(orderNumber);
        if(order.isPresent()){
            return mapToOrderResponse(order.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
    }

    public void cancelOrder(String orderNumber) {

    }
}

