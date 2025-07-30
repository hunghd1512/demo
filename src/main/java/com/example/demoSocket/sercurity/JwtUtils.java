package com.example.demoSocket.sercurity;
import com.example.demoSocket.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
   // private String jwtSecret = "4261656C64756E67";

    private long jwtExpiration = 3600;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

   public String generateJwtToken(Authentication authentication) {
       UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .subject((userDetails.getUsername()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    // Khóa bí mật (phải >=256bit nếu dùng HMAC-SHA256)
    private final String secretKey = "mysupersecretjwtkeywhichshouldbeverylong3557gfhfg2e3dfha890";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    // 1️⃣ Trích xuất username từ JWT
    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    // 2️⃣ Kiểm tra tính hợp lệ của token
    public boolean validateToken(String token) {
        try {
            parseToken(token); // nếu parse lỗi thì exception → invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 🛠 Hàm nội bộ để parse và xác minh chữ ký
    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token); // xác minh chữ ký + parse Claims
    }

}
