package com.ecommerce.ecommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract user details
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String pictureUrl = oAuth2User.getAttribute("picture");
        String userId = oAuth2User.getAttribute("sub");

        // Register or update user
        upsertUser(userId, name, email, pictureUrl);

        return oAuth2User;
    }

    private void upsertUser(String userId, String name, String email, String pictureUrl) {
        userRepository.findById(userId).ifPresentOrElse(
                user -> updateUserIfNecessary(user, name, pictureUrl),
                () -> registerNewUser(userId, name, email, pictureUrl)
        );
    }

    private void registerNewUser(String userId, String name, String email, String pictureUrl) {
        User newUser = new User();
        newUser.setId(userId);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPictureUrl(pictureUrl);
        userRepository.save(newUser);
        logger.info("New user registered: {}", newUser);
    }

    private void updateUserIfNecessary(User user, String name, String pictureUrl) {
        boolean updated = false;

        if (!user.getName().equals(name)) {
            user.setName(name);
            updated = true;
        }
        if (!user.getPictureUrl().equals(pictureUrl)) {
            user.setPictureUrl(pictureUrl);
            updated = true;
        }

        if (updated) {
            userRepository.save(user);
            logger.info("User information updated: {}", user);
        }
    }
}