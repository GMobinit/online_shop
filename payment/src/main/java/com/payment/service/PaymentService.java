package com.payment.service;

import com.onlineShop.app.clients.payment.CreateUserRequest;
import com.payment.model.BuyProductResponse;
import com.payment.model.Transaction;
import com.payment.model.User;
import com.payment.model.WalletChargeResponse;
import com.payment.repository.TransactionRepository;
import com.payment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public void createCloneUser(CreateUserRequest createUserRequest) {
        Optional<User> existingUser = userRepository.findUserBySystemUserId(createUserRequest.userId());

        if (existingUser.isEmpty()) {
            Long wallet = (long) (Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
            User newUser = User.builder().systemUserId(createUserRequest.userId()).wallet(wallet).balance(BigDecimal.valueOf(0)).currency(Currency.getInstance("EUR"))
                    .build();
            userRepository.saveAndFlush(newUser);
        }
    }

    public WalletChargeResponse chargeUserWallet(String systemUserId, BigDecimal amountToCharge) {
        Optional<User> existUser = userRepository.findUserBySystemUserId(systemUserId);
        if (existUser.isPresent()) {
            User userObject = existUser.get();
            BigDecimal newBalance = userObject.getBalance().add(amountToCharge);
            userObject.setBalance(newBalance);
            userRepository.saveAndFlush(userObject);
        } else {
            return new WalletChargeResponse(false);
        }
        return new WalletChargeResponse(true);
    }

    public BuyProductResponse buyProduct(String systemUserId, BigDecimal price, String productId) {
//        try {
        Optional<User> user = userRepository.findUserBySystemUserId(systemUserId);
        if (user.isEmpty()) {
            throw new IllegalStateException("User does not exist in payment service");
        }
        User userObject = user.get();
        Transaction transaction = new Transaction();
        transaction.setAmount(price);
        transaction.setUser(userObject);
        transaction.setCurrency(Currency.getInstance("EUR"));
        transaction.setProductId(productId);
        if (userObject.getBalance().compareTo(price) < 0) {
            transaction.setResult(Transaction.TRANSACTION_RESULT_FAILED);
            transaction.setMessage("User balance is less than product's price");
            transactionRepository.saveAndFlush(transaction);
            return new BuyProductResponse(Transaction.TRANSACTION_RESULT_FAILED,"User balance is less than product's price",transaction.getReferenceCode());
        }
        userObject.setBalance(userObject.getBalance().subtract(price));
        transaction.setResult(Transaction.TRANSACTION_RESULT_SUCCESS);
        transaction.setMessage("Success");

        userRepository.saveAndFlush(userObject);
        transactionRepository.saveAndFlush(transaction);

        return new BuyProductResponse(Transaction.TRANSACTION_RESULT_SUCCESS,"Success",transaction.getReferenceCode());

//        }catch (Exception e){

//        }
    }
}
