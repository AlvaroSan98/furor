package com.ifun.furor.model.enums

enum class TestType(val value: Int) {
    CONTINUE_SONG_TEXT(1),
    TITLE_SONG_TEXT(2),
    AUTHOR_SONG_TEXT(3),
    SONGS_OF_AUTHOR(4),
    MIME(5),
    THE_STRANGE_ONE(6),
    THE_OLDEST(7),
    SONG_SOUND(8),
    POTPURRI(9),
    TITLE_SONG_EMOJIS(10),
    CURIOSITY(11);

    companion object {
        fun fromInt(value: Int) = TestType.values().first {it.value == value}
    }
}