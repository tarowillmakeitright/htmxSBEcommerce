package com.ecommerce.ecommerce;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private UserRepository userRepository;

    // ログインページ
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Render the login page
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout"; // Render the login page
    }

    // ログアウト成功ページ
    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout-success"; // Render logout-success.html
    }

    // 現在ログイン中のユーザーのプロフィール
    @GetMapping("/user/{id}")
    public String userProfile(
            @PathVariable("id") String id,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            Model model) {
        final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
                // Extract attributes from OAuth2User
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String pictureUrl = oAuth2User.getAttribute("picture");
        String userId = oAuth2User.getAttribute("sub");
        // 現在のログインユーザーIDを取得
        String currentUserId = oAuth2User.getAttribute("sub");

        // MongoDBから対象のユーザーを取得
        User targetUser = userRepository.findById(id).orElse(null);
        if (targetUser == null) {
        	 targetUser = new User();
             targetUser.setId(userId);
             targetUser.setName(name);
             targetUser.setEmail(email);
             targetUser.setPictureUrl(pictureUrl);
             userRepository.save(targetUser);
             logger.info("New user registered: {}", targetUser); // ユーザーが見つからない場合
        }
        
        // 自分自身かどうかを判定
        boolean isSelf = currentUserId.equals(id);
        model.addAttribute("isSelf", isSelf);

        // フォロー状態を判定（自分自身でない場合のみ）
        if (!isSelf) {
            User currentUser = userRepository.findById(currentUserId).orElse(null);
            boolean isFollowing = currentUser != null && currentUser.getFollowing().contains(id);
            model.addAttribute("isFollowing", isFollowing);
        }

        // モデルにデータを追加
        model.addAttribute("pictureUrl", targetUser.getPictureUrl());
        model.addAttribute("name", targetUser.getName());
        model.addAttribute("email", targetUser.getEmail());
        model.addAttribute("userId", targetUser.getId());
        model.addAttribute("followers", targetUser.getFollowers());
        model.addAttribute("following", targetUser.getFollowing());
        model.addAttribute("currentUserId", targetUser.getFollowing());
        // お気に入りのアニメリストを取得
        List<Anime> favoriteAnimeList = animeService.getFavoriteAnimeByUserId(id);
        model.addAttribute("favorites",favoriteAnimeList);

        return "user"; // user.html テンプレートをレンダリング
    }

//    @GetMapping("/home/user/{id}")
//    public String userProfile(
//            @PathVariable("id") String id,
//            @AuthenticationPrincipal OAuth2User oAuth2User,
//            Model model) {
//
//        if (oAuth2User == null) {
//            // 未認証の場合、ログインページにリダイレクト
//            return "redirect:/login";
//        }
//
//        // 現在ログイン中のユーザーのID
//        String currentUserId = oAuth2User.getAttribute("sub");
//
//        // MongoDBからURLのidで指定されたユーザーを取得
//        User targetUser = userRepository.findById(id).orElse(null);
//        if (targetUser == null) {
//            // ユーザーが見つからない場合、エラーページにリダイレクト
//            return "redirect:/error";
//        }
//
//        // フォロー状態を判定
//        if (!currentUserId.equals(id)) { // 自分自身ではない場合のみフォロー状態を設定
//            User currentUser = userRepository.findById(currentUserId).orElse(null);
//            boolean isFollowing = currentUser != null && currentUser.getFollowing().contains(id);
//            model.addAttribute("isFollowing", isFollowing);
//        }
//        // モデルに対象ユーザーの情報を追加
//        model.addAttribute("pictureUrl", targetUser.getPictureUrl());
//        model.addAttribute("name", targetUser.getName());
//        model.addAttribute("email", targetUser.getEmail());
//        model.addAttribute("userId", targetUser.getId());
//        model.addAttribute("followers", targetUser.getFollowers());
//        model.addAttribute("following", targetUser.getFollowing());
//        model.addAttribute("isFollowing",targetUser.isFollowing());
//
//
//        // 対象ユーザーのお気に入りのアニメを取得
//        List<Anime> favoriteAnimeList = animeService.getFavoriteAnimeByUserId(targetUser.getId());
//        model.addAttribute("favorites", favoriteAnimeList);
//
//
////        if (currentUser != null && currentUser.getFollowing().contains(id)) {
////            model.addAttribute("isFollowing", true);
////        } else {
////            model.addAttribute("isFollowing", false);
////        }
//
//
//        return "user"; // user.html テンプレートをレンダリング
//    }



    @PostMapping("/users/{id}/follow")
    public String followUser(@PathVariable String id, @AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return "redirect:/login";
        }

        String currentUserId = oAuth2User.getAttribute("sub");
        User currentUser = userRepository.findById(currentUserId).orElse(null);
        User targetUser = userRepository.findById(id).orElse(null);

        if (currentUser != null && targetUser != null) {
            // フォローリストの更新
            currentUser.getFollowing().add(id);
            targetUser.getFollowers().add(currentUserId);

            userRepository.save(currentUser);
            userRepository.save(targetUser);
        }

        return "redirect:/user/" + id;
    }

    @PostMapping("/users/{id}/unfollow")
    public String unfollowUser(@PathVariable String id, @AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return "redirect:/login";
        }

        String currentUserId = oAuth2User.getAttribute("sub");
        User currentUser = userRepository.findById(currentUserId).orElse(null);
        User targetUser = userRepository.findById(id).orElse(null);

        if (currentUser != null && targetUser != null) {
            // アンフォローリストの更新
            currentUser.getFollowing().remove(id);
            targetUser.getFollowers().remove(currentUserId);

            userRepository.save(currentUser);
            userRepository.save(targetUser);
        }

        return "redirect:/user/" + id;
    }
}
