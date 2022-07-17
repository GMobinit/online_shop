package com.payment.model;

import java.util.UUID;

public record BuyProductResponse(String status,String message, String referenceCode) {
}
