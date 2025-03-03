package com.ecommerce.ecommerce;

import static com.ecommerce.ecommerce.AnimeService.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private CommentService commentService; // Injected service instance
    @Autowired
    private AnimeService animeService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public String index(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
//        String userId = oAuth2User.getAttribute("sub");
//        String email = oAuth2User.getAttribute("email");
//        String name = oAuth2User.getAttribute("name");
//        String pictureUrl = oAuth2User.getAttribute("picture");
//
//        if(!userRepository.existsById(userId)){
//            User newUser = new User();
//            newUser.setId(userId);
//            newUser.setName(name);
//            newUser.setEmail(email);
//            newUser.setPictureUrl(pictureUrl);
//            userRepository.save(newUser);
//            logger.info("New user registered: {}", newUser);
//        }
    	if (oAuth2User != null) {
            String userId = oAuth2User.getAttribute("sub");
            model.addAttribute("currentUser", userRepository.findById(userId).orElse(null));
        }
        return "index";
    }



//    @GetMapping("animeList")
//    public String showAnimeList(Model model) {
//        List<Anime> animeList = animeService.getAllAnime();
//        System.out.println(animeList);
//        model.addAttribute("list", animeList);
//        return "animeList"; // 使用するThymeleafのテンプレート名
//    }
    //タグ全表示
    @GetMapping("animeList/tags")
    public String showAnimeTags(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        List<String> tags = animeService.getAllUniqueTags();
        model.addAttribute("tags", tags);
        
        if (oAuth2User != null) {
            String userId = oAuth2User.getAttribute("sub");
            model.addAttribute("currentUser", userRepository.findById(userId).orElse(null));
        }
        
        return "tags";
    }
    // 特定のタグでアニメをフィルタリングするメソッド
    @GetMapping("animeList/tags/{tag}")
    public String showAnimeByTag(@PathVariable("tag") String tag,@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        List<Anime> animeListByTag = animeService.getAnimeByTag(tag);
        model.addAttribute("list", animeListByTag);
        model.addAttribute("selectedTag", tag); // 現在選択されているタグを表示するための変数
        
        if (oAuth2User != null) {
            String userId = oAuth2User.getAttribute("sub");
            model.addAttribute("currentUser", userRepository.findById(userId).orElse(null));
        }
        
        return "animeList"; // animeListテンプレートを再利用
    }
    //    @PostMapping("/add")
    //    public String addComment(@RequestParam("animeId") String animeId, @RequestParam("content") String content) {
    //        animeService.addComment(animeId, content); // コメント追加処理をサービスで実行
    //        return "redirect:/comments/" + animeId; // コメントページへリダイレクト
    //    }

    // アニメIDに基づくコメントページ表示
    @GetMapping("animeList/comments/{id}")
    public String showCommentsPage(@PathVariable("id") String id, @AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        // AnimeオブジェクトをIDで取得
        Anime anime = animeService.getAnimeById(id);
        // Fetch comments for this anime
        List<Comment> comments = commentService.getCommentsByAnimeId(id);
        // Add the anime and comments to the model
        model.addAttribute("anime", anime);
        model.addAttribute("comments", comments); // Add comments to the model
        System.out.println("Comments: " + comments);
        
        if (oAuth2User != null) {
            String userId = oAuth2User.getAttribute("sub");
            model.addAttribute("currentUser", userRepository.findById(userId).orElse(null));
        }
        // Render the comments.html template
        return "comments";
    }

    //comments
    @PostMapping("animeList/comments/add")
    public String addComment(@AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestParam("animeId") String animeId,
            @RequestParam("content") String content) {
            if (oAuth2User != null) {
                String userId = oAuth2User.getAttribute("sub"); // Unique Google user ID
                String userName = oAuth2User.getAttribute("name"); // Google user name
                commentService.addComment(animeId, userId, userName, content);
            }
            return "redirect:/animeList/comments/" + animeId; // Redirect to comments page
    }
    @GetMapping("animeList/latest")
    public String showFall2024Anime(Model model) {
        List<Anime> latestAnime = animeService.getAnimeBySeason("FALL", 2024);
        model.addAttribute("list", latestAnime);
        return "animeList"; // Reuse the animeList template for displaying results
    }
    @GetMapping("animeList/season")
    public String showAnimeBySeason(@RequestParam("season") String season, @RequestParam("year") Integer year, Model model) {
        List<Anime> animeBySeason = animeService.getAnimeBySeason(season, year);
        model.addAttribute("list", animeBySeason);
        return "animeList"; // Reuse the animeList template
    }

    @PostMapping("animeList/favorite")
    public String addToFavorites(@AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestParam("animeId") String animeId) {
            if (oAuth2User != null) {
                String userId = oAuth2User.getAttribute("sub"); // Unique Google user ID
                animeService.addFavoriteAnime(animeId, userId);
                logger.info("Anime ID: " + animeId);
                logger.info("User ID: " + userId);
            }
            return "redirect:/animeList/comments/" + animeId; // Redirect back to comments page
    }


    @PostMapping("animeList/favorite/delete")
    public String removeFromFavorites(@AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestParam("animeId") String animeId) {
            if (oAuth2User != null) {
                String userId = oAuth2User.getAttribute("sub"); // Unique Google user ID
                animeService.removeFavoriteAnime(animeId, userId);
                logger.info("Removed Anime ID: " + animeId);
                logger.info("User ID: " + userId);
            }
            return "redirect:/animeList/comments/" + animeId; // リストページにリダイレクト
    }

}
