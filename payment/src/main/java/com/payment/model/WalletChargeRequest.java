package com.payment.model;

import java.math.BigDecimal;

public record WalletChargeRequest(String systemUserId, BigDecimal amountToCharge) {
}
