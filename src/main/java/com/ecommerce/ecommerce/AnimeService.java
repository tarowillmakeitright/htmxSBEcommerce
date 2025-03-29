package com.ecommerce.ecommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private UserRepository userRepository;
    static final Logger logger = LoggerFactory.getLogger(AnimeService.class);

    // MongoDBからすべてのアニメを取得
    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }

    // 追加: IDで特定のアニメを取得（オプション）
    /*public Anime getAnimeById(String id) {
        return animeRepository.findById(id).orElse(null);
    }

    // 追加: 新しいアニメを保存
    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    // 追加: アニメを削除
    public void deleteAnime(String id) {
        animeRepository.deleteById(id);
    }

     */
    // 特定のタグでフィルタリングされたアニメを取得
    public List<Anime> getAnimeByTag(String tag) {
        return animeRepository.findAll().stream()
                .filter(anime -> anime.getTags() != null && anime.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public Anime getAnimeById(String id) {
        Optional<Anime> anime = animeRepository.findById(id);
        return anime.orElse(null);  // IDに対応するアニメが見つからなければnullを返す
    }





    //タグを全表示させる
    public List<String> getAllUniqueTags() {
       return animeRepository.findAll().stream()
               .flatMap(anime -> anime.getTags().stream())
               .distinct()
               .collect(Collectors.toList());
    }


//    public void addComment(String animeId, String content) {
//        Anime anime = animeRepository.findById(animeId).orElse(null);
//        if (anime != null) {
//            Comment newComment = new Comment(content);
//            anime.getComments().add(newComment);
//            animeRepository.save(anime);
//        }
//    }

    public List<Anime> getAnimeBySeason(String season, Integer year) {
    return animeRepository.findAll().stream()
            .filter(anime -> anime.getAnimeSeason() != null &&
                    anime.getAnimeSeason().getSeason() != null &&
                    anime.getAnimeSeason().getYear() != null &&
                    anime.getAnimeSeason().getYear().equals(year))
            .collect(Collectors.toList());
}

    public void addFavoriteAnime(String animeId, String userId) {
        logger.info("Looking for user with ID: " + userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            logger.error("User not found for ID: " + userId);
            return; // 処理を中断
        }
        logger.info("User found: " + user.toString());

        if (user.getFavoriteAnimeIds() == null) {
            user.setFavoriteAnimeIds(new ArrayList<>());
        }

        // ユーザーの favoriteAnimeIds に animeId が含まれていない場合のみ追加
        if (!user.getFavoriteAnimeIds().contains(animeId)) {
            user.getFavoriteAnimeIds().add(animeId);
            userRepository.save(user); // データベースに保存

            // 保存後に確認ログを出力
            User updatedUser = userRepository.findById(userId).orElse(null);
            if (updatedUser != null && updatedUser.getFavoriteAnimeIds().contains(animeId)) {
                logger.info("Anime successfully saved to favorites: " + animeId);
            } else {
                logger.error("Failed to save anime to favorites: " + animeId);
            }
        } else {
            logger.info("Anime already exists in favorites for user: " + userId);
        }
    }


    public List<Anime> getFavoriteAnimeByUserId(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getFavoriteAnimeIds() != null) {
            return animeRepository.findAllById(user.getFavoriteAnimeIds());
        }
        return new ArrayList<>();
    }


    public void removeFavoriteAnime(String animeId, String userId) {
        logger.info("Looking for user with ID: " + userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            logger.error("User not found for ID: " + userId);
            return; // 処理を中断
        }
        logger.info("User found: " + user.toString());

        if (user.getFavoriteAnimeIds() != null && user.getFavoriteAnimeIds().contains(animeId)) {
            user.getFavoriteAnimeIds().remove(animeId);
            userRepository.save(user); // データベースに保存

            // 保存後に確認ログを出力
            User updatedUser = userRepository.findById(userId).orElse(null);
            if (updatedUser != null && !updatedUser.getFavoriteAnimeIds().contains(animeId)) {
                logger.info("Anime successfully removed from favorites: " + animeId);
            } else {
                logger.error("Failed to remove anime from favorites: " + animeId);
            }
        } else {
            logger.info("Anime not found in favorites for user: " + userId);
        }
    }

    // Anime 投票　🗳️
    public void voteAnime(String animeId, String userId, boolean isGood) {
        User user = userRepository.findById(userId).orElse(null);
        Anime anime = animeRepository.findById(animeId).orElse(null);

        Map<String, Boolean> voted = user.getVotedAnime();
    if (voted.containsKey(animeId)) {
        logger.info("User already voted on anime: " + animeId);
        return; // すでに投票済みなら何もしない
    }

       // 「いいね」なら　「いいね」に＋１
       // 「だめ」なら「だめに＋１」　
        if (isGood) {
            anime.setGoodVotes(anime.getGoodVotes() + 1);
        } else {
            anime.setBadVotes(anime.getBadVotes() + 1);
        }

        // ユーザーの投票履歴を記録
    voted.put(animeId, isGood);
    user.setVotedAnime(voted);

    // 保存
    animeRepository.save(anime);
    userRepository.save(user);

    logger.info("User {} voted {} on anime {}", userId, isGood ? "GOOD" : "BAD", animeId);

    }

    public List<Anime> getTopRankedAnime() {
        return animeRepository.findAll().stream()
                // 10 - 5 だったら１０はAアニメ、５はBアニメ　だから、AーB　がなりたつ
                .sorted((a, b) -> (b.getGoodVotes() - b.getBadVotes()) - (a.getGoodVotes() - a.getBadVotes()))
                .limit(100)
                .collect(Collectors.toList());
    }
}
