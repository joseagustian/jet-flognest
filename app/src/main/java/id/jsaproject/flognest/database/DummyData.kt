package id.jsaproject.flognest.database

import id.jsaproject.flognest.database.model.Content

val dummyContentList = listOf<Content>(
    Content(
        id = "the-shawshank-redemption-movie",
        title = "The Shawshank Redemption",
        posterUrl = "https://image.tmdb.org/t/p/w1280/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
        type = "Movie",
        synopsis = "A banker convicted of uxoricide forms a friendship over a quarter century with a hardened convict, while maintaining his innocence and trying to remain hopeful through simple compassion.",
        yearRelease = "1994",
        genre = "Drama",
        isWatched = true,
        personalRating = 8.0,
        comment = "A timeless classic that beautifully portrays hope and friendship in the face of adversity. The performances are stellar, and the story is deeply moving. A must-watch for any film enthusiast.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
    Content(
        id = "the-dark-knight-movie",
        title = "The Dark Knight",
        posterUrl = "https://image.tmdb.org/t/p/w1280/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        type = "Movie",
        synopsis = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
        yearRelease = "2008",
        genre = "Action",
        isWatched = true,
        personalRating = 9.3,
        comment = "A masterpiece of modern cinema. Heath Ledger's portrayal of the Joker is iconic, and the film's exploration of morality and chaos is thought-provoking. The action sequences are breathtaking, and the pacing is perfect.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
    Content(
        id = "inception-movie",
        title = "Inception",
        posterUrl = "https://image.tmdb.org/t/p/w1280/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
        type = "Movie",
        synopsis = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
        yearRelease = "2010",
        genre = "Action",
        isWatched = true,
        personalRating = 8.3,
        comment = "A mind-bending journey through dreams and reality. Christopher Nolan delivers a visually stunning and intellectually stimulating film. The concept is unique, and the execution is flawless. A true cinematic experience.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
    Content(
        id = "when-life-gives-you-tangerines-series",
        title = "When Life Gives You Tangerines",
        posterUrl = "https://image.tmdb.org/t/p/w1280/q29q6AByug53pnylCytwLA7m6AY.jpg",
        type = "Series",
        synopsis = "In Jeju, a spirited girl and a steadfast boy's island story blossoms into a lifelong tale of setbacks and triumphs — proving love endures across time.",
        yearRelease = "2025",
        genre = "Drama",
        isWatched = false,
        personalRating = 0.0,
        comment = "-",
        reviewedBy = "Jose Agustian",
        isFavorite = false
    ),
    Content(
        id = "interstellar-movie",
        title = "Interstellar",
        posterUrl = "https://image.tmdb.org/t/p/w1280/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
        type = "Movie",
        synopsis = "When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.",
        yearRelease = "2014",
        genre = "Adventure",
        isWatched = true,
        personalRating = 7.4,
        comment = "A visually stunning and emotionally gripping film. The exploration of space and time is both awe-inspiring and thought-provoking. While some parts feel slow, the overall experience is unforgettable.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
    Content(
        id = "parasite-movie",
        title = "Parasite",
        posterUrl = "https://image.tmdb.org/t/p/w1280/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
        type = "Movie",
        synopsis = "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
        yearRelease = "2019",
        genre = "Drama",
        isWatched = true,
        personalRating = 8.3,
        comment = "A brilliant social commentary wrapped in a thrilling and unpredictable story. The direction, acting, and cinematography are all top-notch. It keeps you on the edge of your seat until the very end.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
    Content(
        id = "your-name-movie",
        title = "Your Name.",
        posterUrl = "https://image.tmdb.org/t/p/w1280/q719jXXEzOoYaps6babgKnONONX.jpg",
        type = "Movie",
        synopsis = "Two strangers find themselves linked in a bizarre way. When a connection forms, will distance be the only thing to keep them apart?",
        yearRelease = "2016",
        genre = "Fantasy",
        isWatched = true,
        personalRating = 9.2,
        comment = "A beautifully animated and emotionally resonant film. The story is both heartwarming and heartbreaking, with a unique twist that keeps you engaged. The soundtrack is also a standout.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
    Content(
        id = "my-mister-series",
        title = "My Mister",
        posterUrl = "https://image.tmdb.org/t/p/w1280/cRGJKYnzwNpzSE7ROKlhD4KBsA7.jpg",
        type = "Series",
        synopsis = "A man in his 40s withstands the weight of life. A woman in her 20s goes through different experiences, but also withstands the weight of her life. The man and woman get together to help each other.",
        yearRelease = "2018",
        genre = "Drama",
        isWatched = true,
        personalRating = 9.1,
        comment = "A deeply moving and heartfelt series that explores the struggles of life and human connections. The characters are well-developed, and the storytelling is both realistic and touching. Highly recommended.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
    Content(
        id = "severance-tv-series",
        title = "Severance",
        posterUrl = "https://image.tmdb.org/t/p/w1280/zoIJh4wTfnoIeKl8qzZRIIrNmKD.jpg",
        type = "TV Series",
        synopsis = "Mark leads a team of office workers whose memories have been surgically divided between their work and personal lives. When a mysterious colleague appears outside of work, it begins a journey to discover the truth about their jobs.",
        yearRelease = "2022",
        genre = "Drama",
        isWatched = true,
        personalRating = 7.3,
        comment = "A unique and intriguing concept that keeps you guessing. The series is well-acted and has a mysterious atmosphere that draws you in. While some episodes feel slow, the overall narrative is compelling.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
    Content(
        id = "stranger-things-tv-series",
        title = "Stranger Things",
        posterUrl = "https://image.tmdb.org/t/p/w1280/zFE44Qks8F1IFu7qkqxNjJoelvT.jpg",
        type = "TV Series",
        synopsis = "In 1980s Indiana, a group of young friends witness supernatural forces and secret government exploits. As they search for answers, the children unravel a series of extraordinary mysteries.",
        yearRelease = "2016",
        genre = "Horror",
        isWatched = true,
        personalRating = 8.2,
        comment = "A nostalgic and thrilling series that combines supernatural elements with heartfelt storytelling. The characters are lovable, and the suspense keeps you hooked. Perfect for fans of 80s vibes and mystery.",
        reviewedBy = "Jose Agustian",
        isFavorite = true
    ),
)