package org.adbms.orderplacement.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long userId;
    private String userName;
    private String userAddress;
    private Date date;
    private String orderNumber;
    private List<OrderItemResponseDTO> orderItems;

}
