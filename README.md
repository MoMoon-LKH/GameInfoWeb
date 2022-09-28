# 게임 정보 웹 ( Back-End )

## 1. 소개
- 게임 정보 관련 웹 사이트 
- REST API 서버
<br>

## 2. 사용기술
- Java 11
- Spring Boot, Security
- Spring Data JPA, AWS Cloud(Mysql)
<br>

## 3. 인증 방식
### JWT 토큰 방식
- Access-Refresh 방식
- Access 토큰 -> Session Storage에 저장
- Refresh 토큰 -> 쿠키에 저장 (Http Only Secure) <br> 
 ㄴ Http Only을 사용하기 위해서 Https 인증서 적용 (인증 받지않은 인증서)


## 4. 기능
#### 4.1) 회원
- 회원가입 
- 로그인
- 로그아웃

#### 4.2) 포스트
- 포스트 작성
- 포스트 JSON

#### 4.3) 코멘트

#### 4.4) 장르
- 장르 추가
- 장르 JSON
- 장르 수정
- 장르 삭제

#### 4.5) 플랫폼
- 플랫폼 추가
- 플랫폼 JSON
- 플랫폼 수정
- 플랫폼 삭제
