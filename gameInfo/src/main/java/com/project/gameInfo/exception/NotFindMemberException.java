package com.project.gameInfo.exception;

public class NotFindMemberException extends RuntimeException{

    public NotFindMemberException() {
        super("해당 멤버를 찾을 수 없습니다.");
    }
}
