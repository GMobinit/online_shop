package com.payment.controller;

import com.onlineShop.app.clients.payment.CreateUserRequest;
import com.payment.model.BuyProductRequest;
import com.payment.model.BuyProductResponse;
import com.payment.model.WalletChargeRequest;
import com.payment.model.WalletChargeResponse;
import com.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody CreateUserRequest createUserRequest){
        paymentService.createCloneUser(createUserRequest);
    }

    @PostMapping("/charge-wallet")
    public WalletChargeResponse chargeWallet(@RequestBody WalletChargeRequest walletChargeRequest){
        return paymentService.chargeUserWallet(walletChargeRequest.systemUserId(),walletChargeRequest.amountToCharge());
    }

    @PostMapping("/buy-product")
    public BuyProductResponse buyProduct(@RequestBody BuyProductRequest buyProductRequest){
        return paymentService.buyProduct(buyProductRequest.systemUserId(),buyProductRequest.price(),buyProductRequest.productId());
    }
}
