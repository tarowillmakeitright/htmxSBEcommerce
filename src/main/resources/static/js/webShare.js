function shareAnime(button) {
    const title = button.getAttribute('data-title'); // ボタンのデータ属性からタイトルを取得
    const url = button.getAttribute('data-url');    // ボタンのデータ属性からURLを取得

    // Web Share API が利用可能か確認
    if (navigator.share) {
        navigator.share({
            title: title,   // シェアのタイトル
            text: "おすすめのアニメ: " + title, // 動的にタイトルを挿入
            url: url        // シェアするURL
        })
        .then(() => console.log('Anime shared successfully!'))
        .catch((error) => console.error('Error sharing anime:', error));
    } else {
        alert('Web Share API is not supported on this device/browser.');
    }
}
