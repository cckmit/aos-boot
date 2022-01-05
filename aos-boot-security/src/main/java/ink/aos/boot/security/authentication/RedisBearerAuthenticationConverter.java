/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ink.aos.boot.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

public final class RedisBearerAuthenticationConverter implements AuthenticationConverter {

    @Autowired
    private UserAuthenticationTokenStore tokenStore;

    @Nullable
    @Override
    public Authentication convert(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }

        String[] parts = header.split("\\s");
        if (!parts[0].equalsIgnoreCase("Bearer")) {
            return null;
        }

        String token = parts[1];

        if (parts.length != 2) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }

        UserAuthenticationToken userAuthenticationToken = tokenStore.findByAccessToken(token);
        if (userAuthenticationToken == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }

        return userAuthenticationToken;
    }

    public void setTokenStore(UserAuthenticationTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
}
