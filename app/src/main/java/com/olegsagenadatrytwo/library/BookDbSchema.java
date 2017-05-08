package com.olegsagenadatrytwo.library;
public class BookDbSchema {

    public static final class BookTable{
        public static final String NAME = "LibraryBooks";

        public static final class Cols{
            public static final String BOOKID = "BookId";
            public static final String BOOKISBN = "BookISBN";
            public static final String BOOKTITLE = "BookTitle";
            public static final String BOOKAUTHOR = "BookAuthor";
            public static final String BOOKGENRE = "BookGenre";
            public static final String BOOKSYNOPSIS = "BookSynopsis";
            public static final String ENCODEDIMAGE = "EncodedImage";
            public static final String BOOKPRICE = "Price";
        }
    }
}