package com.payment.model;

import java.math.BigDecimal;

public record BuyProductRequest(String systemUserId, BigDecimal price,String productId) {
}
