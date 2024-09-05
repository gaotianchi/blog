package com.gaotianchi.resourceservice.service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {


//    private final AuthenticationManager authenticationManager;

//    @Autowired
//    public TokenService(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }


//    public String generateToken(Authentication authentication) {
//        String username = authentication.getName();
//        Date currentDate = new Date();
//        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
//
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(expireDate)
//                .signWith(key())
//                .compact();
//    }

//    public TokenResponse getTokenResponse(String email, String password) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    email, password));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            TokenResponse tokenResponse = new TokenResponse();
//            tokenResponse.setAccessToken(generateToken(authentication));
//            tokenResponse.setTokenType("Bearer");
//            return tokenResponse;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        return null;
//    }


}
