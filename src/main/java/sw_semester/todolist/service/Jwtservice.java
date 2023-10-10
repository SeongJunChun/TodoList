package sw_semester.todolist.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



@Service
public class Jwtservice {

    private static final String SECRET_KEY = "tuxnXvPb7rIMJknmsMwXmmR2G9LcKvcctuxnXvPb7rIMJknmsMwXmmR2G9LcKvcc";
    private static final long jwtExpiration=86400000;//24시간
    private static final long refreshExpiration=604800000;//24시간*7



    public String extractUserEmail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }


    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(SignatureAlgorithm.HS256, getSignIngKey())
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token);

    }
    public boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);

    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignIngKey())
                .parseClaimsJws(token)
                .getBody();
    }//토큰값을 추출 해오는매서드임

    private Key getSignIngKey() {
        byte[] keybyte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keybyte);
    }
    //토큰 발급/유효성 검사하는 메써드임
}
