package com.gaotianchi.oauth2service.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class JwtConfig {
    @Bean
    public JWKSet jwkSet() throws Exception {
        RSAPrivateKey privateKey = loadPrivateKey();
        RSAPublicKey publicKey = loadPublicKey();

        // 创建 RSAKey，包含公钥和私钥
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID("your-key-id")  // 设置一个唯一的 Key ID
                .build();

        // 返回包含该 RSAKey 的 JWKSet
        return new JWKSet(rsaKey);
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        RSAPublicKey publicKey = loadPublicKey();
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        // Load your RSA private and public key (methods assumed to be already defined)
        RSAPrivateKey privateKey = loadPrivateKey();
        RSAPublicKey publicKey = loadPublicKey();

        // Build the RSAKey using the public and private key
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();

        // Create a JWKSet from the RSAKey
        JWKSet jwkSet = new JWKSet(rsaKey);

        // Use ImmutableJWKSet to create a JWKSource
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(jwkSet);

        // Return NimbusJwtEncoder that will sign JWT using the private key
        return new NimbusJwtEncoder(jwkSource);
    }

    private RSAPrivateKey loadPrivateKey() throws Exception {
        String privateKeyPEM = new String(Files.readAllBytes(Paths.get("src/main/resources/private_key.pem")));
        byte[] keyBytes = java.util.Base64.getDecoder().decode(privateKeyPEM
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", ""));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(spec);
    }

    private RSAPublicKey loadPublicKey() throws Exception {
        String publicKeyPEM = new String(Files.readAllBytes(Paths.get("src/main/resources/public_key.pem")));
        byte[] keyBytes = java.util.Base64.getDecoder().decode(publicKeyPEM
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(spec);
    }
}
