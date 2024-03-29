package com.somnus.server.backend.exceptions;

public enum ErrorMessage {
    NO_ARTICLE_FOUND("No article found"),
    NO_NEWS_POST_FOUND("No news post found"),
    NO_TEAMMEMBER_FOUND("No team member found."),
    NO_USER_FOUND("No user found in database"),
    NO_TOPIC_PROVIDED("There is no topic provided for the given article"),
    ROLE_NOT_ALLOWED("This role is not allowed."),
    DELETE_ARTICLE_NOT_ALLOWED("The current user is not the owner of the article"),
    DELETE_NEWS_POST_NOT_ALLOWED("The current user is not the owner of the news post"),
    CANNOT_PARSE_ARTICLE_DATA("Cannot parse Article data"),
    NO_COMMENT_FOUND("No comment was found"),
    COMMENT_EDIT_NOT_ALLOWED("The current user cannot edit the given comment"),
    USERNAMES_DONT_MATCH("The usernames don't match"),
    DELETE_COMMENT_NOT_ALLOWED("The current user is not the owner of the comment"),

    ACCESS_DENIED("You do not have permission to access the requested resource"),
    BAD_REQUEST("Malformed Request"),
    INTERNAL_SERVER_ERROR("Server error"),

    FIREBASE_USER_DOES_NOT_EXIST("Firebase user does not exist"),
    ARTICLE_GET_NOT_AUTHORIZED("User not authorized to get this article"),
    INVALID_AUTH_TOKEN("Invalid auth token");


    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
