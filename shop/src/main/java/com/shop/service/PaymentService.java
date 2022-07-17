package com.shop.service;

import com.shop.model.*;
import com.shop.repository.ProductRepository;
import com.shop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final static String PAYMENT_WALLET_CHARGE_URL = "http://PAYMENT/api/v1/payment/charge-wallet";
    private final static String PAYMENT_BUY_PRODUCT_URL = "http://PAYMENT/api/v1/payment/buy-product";

    public boolean chargeUserWallet(String userName, BigDecimal amount){
        Optional<User> user = userRepository.findUserByUserName(userName);
        WalletChargeResponse walletChargeResponse;
        if (user.isPresent()){
            WalletChargeRequest req = new WalletChargeRequest(user.get().getId(),amount);
            walletChargeResponse = restTemplate.postForObject(PAYMENT_WALLET_CHARGE_URL,req, WalletChargeResponse.class);
        }else {
            throw new IllegalStateException("User with this user name does not exist.");
        }
        assert walletChargeResponse != null;
        return walletChargeResponse.walletIsCharged();
    }

    public BuyProductResponse buyProduct(String userName, String productId) {
        Optional<User> user = userRepository.findUserByUserName(userName);
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalStateException("Product not found");
        }
        Product productObject = product.get();
        if (user.isEmpty()){
            throw new IllegalStateException("User with this user name not found");
        }
        User userObject = user.get();
        BuyProductRequest buyProductRequest = new BuyProductRequest(userObject.getId(),productObject.getPrice(),productObject.getId());

        return restTemplate.postForObject(PAYMENT_BUY_PRODUCT_URL,buyProductRequest,BuyProductResponse.class);
    }
}
