package com.ecommerce.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ユーザーを登録する
    public User registerUser(String id, String email, String name, String pictureUrl) {
        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " already exists");
        }

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setPictureUrl(pictureUrl);
        user.setFollowers(new HashSet<>());
        user.setFollowing(new HashSet<>());

        return userRepository.save(user);
    }

    // フォローする
    public void followUser(String userId, String targetUserId) {
        User user = userRepository.findById(userId).orElse(null);
        User targetUser = userRepository.findById(targetUserId).orElse(null);

        if (user != null && targetUser != null) {
            user.getFollowing().add(targetUserId);
            targetUser.getFollowers().add(userId);

            userRepository.save(user);
            userRepository.save(targetUser);
        }
    }

    // フォロー解除する
    public void unfollowUser(String userId, String targetUserId) {
        User user = userRepository.findById(userId).orElse(null);
        User targetUser = userRepository.findById(targetUserId).orElse(null);

        if (user != null && targetUser != null) {
            user.getFollowing().remove(targetUserId);
            targetUser.getFollowers().remove(userId);

            userRepository.save(user);
            userRepository.save(targetUser);
        }
    }

    // フォロワー一覧を取得
    public Set<String> getFollowers(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getFollowers() : new HashSet<>();
    }

    // フォロー中の一覧を取得
    public Set<String> getFollowing(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getFollowing() : new HashSet<>();
    }
}
