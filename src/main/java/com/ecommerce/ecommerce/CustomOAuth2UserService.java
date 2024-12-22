package com.ecommerce.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.ecommerce.ecommerce.AnimeService.logger;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract attributes from OAuth2User
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String pictureUrl = oAuth2User.getAttribute("picture");
        String userId = oAuth2User.getAttribute("sub");

        // データベースにユーザーを保存または更新
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            user = new User();
            user.setId(userId);
            user.setName(name);
            user.setEmail(email);
            user.setPictureUrl(pictureUrl);
            userRepository.save(user);
            logger.info("New user registered: {}", user);
        } else {
            // ユーザー情報を更新
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

        // Return the OAuth2User object as is
        return oAuth2User;
    }
}