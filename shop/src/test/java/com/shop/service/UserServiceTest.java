package com.shop.service;

import com.netflix.discovery.converters.Auto;
import com.onlineShop.app.amqp.RabbitMqMessageProducer;
import com.shop.model.Gender;
import com.shop.model.PasswordResponse;
import com.shop.model.PhoneNumber;
import com.shop.model.User;
import com.shop.repository.UserRepository;
import com.shop.validator.UserNameValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import javax.validation.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.xml.transform.Source;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    @Autowired
    private RestTemplate restTemplate;
    @Mock
    @Autowired
    private RabbitMqMessageProducer rabbitMqMessageProducer;
    @Autowired
    @Mock
    private AmqpTemplate amqpTemplate;
    private UserService underTest;
    @BeforeEach
    void setUp() {
        rabbitMqMessageProducer = new RabbitMqMessageProducer(amqpTemplate);
        underTest = new UserService(userRepository,restTemplate,rabbitMqMessageProducer);
    }

    @Test
    void canGetAllUsers(){
        underTest.getAllUsers();
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void canRegisterUser(){
        PhoneNumber phoneNumber = new PhoneNumber("+989361660911","DE");
        User user = User.builder().firstName("mobin").middleName("Hajiuni").lastName("hajiuni")
                .email("mobin@gmail.com").phone(phoneNumber).gender(Gender.MALE).userName("mobin").password("asduj7@")
                .build();
        HashMap<String,String> params = new HashMap<>();
        params.put("salt","ASD55T5DFPMVs5");
        params.put("passWord",user.getPassword());
        doReturn(new PasswordResponse("ASD55T5DFPMVs5")).when(restTemplate).getForObject("http://SECURITY/api/v1/security/salt", PasswordResponse.class);
        doReturn(new PasswordResponse("jhdfASDJafdDLFasdASDJKI25ksjhadf245646sadf5sdfdfji")).when(restTemplate).getForObject("http://SECURITY/api/v1/security/encrypt_password/{salt}/{passWord}", PasswordResponse.class,params);
        underTest.registerUser(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canUpdateUserIfUserDoesExist(){
        PhoneNumber phoneNumber = new PhoneNumber("+989361660911","DE");
        User user = User.builder().firstName("mobin").middleName("Hajiuni").lastName("hajiuni")
                .email("mobin@gmail.com").phone(phoneNumber).gender(Gender.MALE).build();

        given(userRepository.findById(anyString())).willReturn(Optional.of(user));
        underTest.updateUser(user,anyString());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }
    @Test
    void canNotUpdateUserIfUserDoesNotExist(){
        PhoneNumber phoneNumber = new PhoneNumber("+989361660911","DE");
        User user = User.builder().firstName("mobin").middleName("Hajiuni").lastName("hajiuni")
                .email("mobin@gmail.com").phone(phoneNumber).gender(Gender.MALE).build();

        given(userRepository.findById(anyString())).willReturn(Optional.empty());

        assertThatThrownBy(()->underTest.updateUser(user,anyString()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("This user doesn't exist and can't be updated");
    }
}