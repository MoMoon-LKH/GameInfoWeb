package com.project.gameInfo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RefreshTokenSecurityException extends RuntimeException{
    public RefreshTokenSecurityException() {
        super("요청한 정보와 토큰의 정보가 달라서 삭제합니다");

    }
}
