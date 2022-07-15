package com.onlineshop.app.clients.security;

public record CreateUserSecurityRequest(String systemUserId,String userName,String password) {
}
