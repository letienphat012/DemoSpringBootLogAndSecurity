package tma.tft.phat.ss.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    private String secretKey = "secret";
    private long validityInMilliseconds = 10*1000; // 1h;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        logger.info("Init secret key: {}", secretKey);
    }

    /**
     * Resolve with header "Authorization: Bearer <token>" to take the <token>
     * 
     * @param request http request
     * @return token if Authorization type is Bearer, else return null
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.info("Receive bearer Token: {}", bearerToken);
        // check if is Bearer scheme, if not skip this token
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            String resolvedToken = bearerToken.substring(7, bearerToken.length());
            logger.info("Resolved token: {}", resolvedToken);
            return resolvedToken;
        }
        logger.info("Resolved token = null");
        return null;
    }

    /**
     * Validate if token is still not expired or not invalid
     * 
     * @param token token to validate
     * @return true if token is valid, else false.
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            logger.info("Decode JWT Claims: {}", claims.getBody());
            logger.info("Claims: subject:{}, issueAt:{}, expiration: {}", claims.getBody().getSubject(),
                    claims.getBody().getIssuedAt(), claims.getBody().getExpiration());
            if (claims.getBody().getExpiration().before(new Date())) {
                logger.info("Token expired");
                return false;
            }
            logger.info("Token validated");
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
                | IllegalArgumentException e) {
            logger.debug(e.getMessage(), e);
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        // get user authentication
        UserDetails userDetail = userDetailsService.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
    }

    private String getUsername(String token) {
        // parse token to get username
        String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        logger.info("Get username ({}) by token ({})", username, token);
        return username;
    }

    /**
     * Create token using email (to authentication), roles of user (to authorization).
     * @param email email of user.
     * @param roles roles of user.
     * @return token string with information: email, roles, issue date, expiration and signature using SHA256 algorithm.
     */
    public String createToken(String email, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);

        logger.info("Create token with secret key {}", secretKey);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        logger.info("Claim created: {}",claims);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
