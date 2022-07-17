package com.shop.controller;

import com.shop.model.BuyProductResponse;
import com.shop.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/charge-wallet")
    public ResponseEntity<String> chargeWallet(@RequestParam String username, BigDecimal amount){
        try {
            if (amount.compareTo(new BigDecimal(0)) != 1){
                throw new ValidationException("Amount can not be less than 0");
            }
            boolean result = paymentService.chargeUserWallet(username, amount);
            if (result) {
                return new ResponseEntity(OK);
            } else {
                return new ResponseEntity(BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),BAD_REQUEST);
        }
    }

    @PostMapping("/buy-product")
    public ResponseEntity<BuyProductResponse> buyProduct(@RequestParam String username, String productId){
        try {
            BuyProductResponse result = paymentService.buyProduct(username,productId);
            return new ResponseEntity(result,OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),BAD_REQUEST);
        }
    }
}
