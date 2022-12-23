package com.softwarehouse.serviceorder.security.service;

import com.auth0.jwt.algorithms.Algorithm;

public interface JwtSignToken {
    String TOKEN = "eyJzaWduIjoicGF1bG8tc2VjdXJpdHktc2VydmljZSJ9";
    Algorithm ALGO = Algorithm.HMAC512(TOKEN);
}
