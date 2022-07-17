package com.shop.model;

import java.math.BigDecimal;

public record WalletChargeRequest(String systemUserId, BigDecimal amountToCharge) {
}
