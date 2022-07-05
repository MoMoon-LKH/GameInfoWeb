package com.project.gameInfo.exception;

public class DuplicateMemberIdException extends RuntimeException{
    public DuplicateMemberIdException() {
        super("중복된 회원 아이디가 있습니다.");
    }
}
