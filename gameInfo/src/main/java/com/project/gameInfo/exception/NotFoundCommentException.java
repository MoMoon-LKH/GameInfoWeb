package com.project.gameInfo.exception;

public class NotFoundCommentException extends RuntimeException{

    public NotFoundCommentException() {
        super("해당 코멘트를 찾을 수 없습니다.");
    }
}
