package org.adbms.orderplacement.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.adbms.orderplacement.model.OrderStatus;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private String userEmail;
    private String userName;
    private String userAddress;
    private String userTelephone;
    private Date date;
    private String orderNumber;
    private OrderStatus status;
    private List<OrderItemResponseDTO> orderItems;

}
