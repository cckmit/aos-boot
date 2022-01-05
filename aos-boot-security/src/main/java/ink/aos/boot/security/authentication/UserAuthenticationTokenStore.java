package ink.aos.boot.security.authentication;

public interface UserAuthenticationTokenStore {

    UserAuthenticationToken findByAccessToken(String token);

    void save(UserAuthenticationToken token);

    void delay(UserAuthenticationToken token);

    void remove(String token);
}
