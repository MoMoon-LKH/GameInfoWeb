# 게임 정보 웹 ( Back-End )

## 1. 소개
- 게임 정보 웹 REST API 서버
<br>

## 2. 사용기술
- Java 11
- Gradle
- Spring Boot, Security
- Spring Data JPA, AWS Cloud(Mysql)
- Spring Rest Docs
<br>

## 3. 인증 방식
### JWT 토큰 방식
- Access-Refresh 방식
- Access 토큰 -> Session Storage에 저장
- Refresh 토큰 -> 쿠키에 저장 (Http Only Secure) <br> 
 ㄴ Http Only을 사용하기 위해서 Https 인증서 적용 (인증 받지않은 인증서)
<br>

## 4. 기능
#### 4.1) 회원
- 회원가입 
- 로그인
- 로그아웃

#### 4.2) 포스트
- 생성
- 리스트, 단일 조회
- 수정
- 삭제

#### 4.3) 코멘트
- 생성
- 조회
- 수정
- 삭제
- 좋아요, 싫어요 기능

#### 4.4) 장르
- 추가
- 리스트 조회
- 수정
- 삭제

#### 4.5) 플랫폼
- 추가
- 리스트 조회
- 수정
- 삭제

<br>

## 5 DB 설계
![db](https://user-images.githubusercontent.com/66755342/196113931-62f8f3f0-7f60-4bec-8714-40a7da63cdea.PNG)

<br>

## 6. Rest Docs

