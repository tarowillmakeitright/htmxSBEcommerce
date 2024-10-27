package com.ecommerce.ecommerce;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeRepository extends MongoRepository<Anime, String> {
    // ここにカスタムクエリメソッドを追加することも可能です

    // 例: 特定のタイトルでアニメを検索
   // List<Anime> findByTitle(String title);

    // 例: 特定のジャンルが含まれるアニメを検索
    //List<Anime> findByGenresContaining(String genre);

}
