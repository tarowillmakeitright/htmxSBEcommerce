package com.ecommerce.ecommerce;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

        // Check if user already exists in MongoDB
        User user = userRepository.findByEmail(email);
        if (user == null) {
            // Save new user to MongoDB
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPictureUrl(pictureUrl);
            userRepository.save(user);
            System.out.println("New user registered: " + email);
        } else {
            System.out.println("Existing user logged in: " + email);
        }

        // Return the OAuth2User object as is
        return oAuth2User;
    }
}