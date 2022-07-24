package com.shop.service;

import com.ctc.wstx.shaded.msv_core.driver.textui.Debug;
import com.shop.model.*;
import com.shop.repository.ProductRepository;
import com.shop.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.debugging.MockitoDebuggerImpl;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    @Autowired
    private RestTemplate restTemplate;
    @InjectMocks
    private PaymentService paymentService;
    private BigDecimal bigDecimal;
    private Product product;
    @BeforeEach
    void setUp() {
        bigDecimal = new BigDecimal(0);
        product = Product.builder().category(Category.CLOTHES).currency(Currency.getInstance("EUR"))
                .description("description").price(bigDecimal).build();
        paymentService = new PaymentService(userRepository,productRepository,restTemplate);
    }

    @Test
    void canNotChargeWalletIfUserDoesNotExist() {
        when(userRepository.findUserByUserName(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(()->paymentService.chargeUserWallet(anyString(),this.bigDecimal))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User with this user name does not exist.");

    }

    @Test
    void canNotChargeWalletIfChargeResponseIsNull(){
        User user = new User();
        when(userRepository.findUserByUserName(anyString())).thenReturn(Optional.of(user));
        assertThatThrownBy(()->paymentService.chargeUserWallet(anyString(),new BigDecimal(0)))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void canChargeWallet(){
        User user = new User();
        doReturn(Optional.of(user)).when(userRepository).findUserByUserName(anyString());
        doReturn(new WalletChargeResponse(true)).when(restTemplate).postForObject("http://PAYMENT/api/v1/payment/charge-wallet",new WalletChargeRequest(null,bigDecimal),WalletChargeResponse.class);
       boolean charged = paymentService.chargeUserWallet(anyString(),bigDecimal);

       assertThat(charged).isEqualTo(true);
    }

    @Test
    void canNotBuyProductIfUserDoesNotExist(){
        when(userRepository.findUserByUserName(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(()->paymentService.buyProduct(anyString(),null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User with this user name not found");
    }
    @Test
    void canNotBuyProductIfProductDoesNotExist(){
        when(userRepository.findUserByUserName(anyString())).thenReturn(Optional.of(new User()));
        when(productRepository.findById(null)).thenReturn(Optional.empty());

        assertThatThrownBy(()->paymentService.buyProduct(anyString(),null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Product not found");
    }
    @Test
    void canBuyProduct(){
        when(userRepository.findUserByUserName(anyString())).thenReturn(Optional.of(new User()));
        when(productRepository.findById(null)).thenReturn(Optional.of(product));

        BuyProductResponse response = new BuyProductResponse("Success","Success","lkjjdaiuamsnd");
        doReturn(response).when(restTemplate).postForObject("http://PAYMENT/api/v1/payment/buy-product",new BuyProductRequest(null,bigDecimal,null),BuyProductResponse.class);

        BuyProductResponse buyProductResponse = paymentService.buyProduct(anyString(),null);
        assertThat(buyProductResponse).isEqualTo(response);
    }
}