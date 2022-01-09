package ink.aos.boot.security.authentication;

public interface UserRememberMeTokenStore {

    UserRememberMeToken findByAccessToken(String token);

    void save(UserRememberMeToken token);

    void delay(UserRememberMeToken token);

    void remove(String token);

}
