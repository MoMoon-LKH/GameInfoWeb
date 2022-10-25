# 게임 정보 웹 ( Back-End )

## 1. 소개
- 게임 정보 웹 REST API 서버 
<br>
이 프로젝트는 게임에 대한 정보 전달 웹 사이트 백엔드 프로젝트입니다. <br>
여러가지 플랫폼의 게임들의 대한 뉴스 및 정보와 해당 유저들의 정보를 공유하는 서비스를 제공하고자 개발하게 되었습니다.<br><br>


이와 더불어 전의 개발하였던 프로젝트의 부족한 부분들인
>  #### JWT 토큰의 보안에 대한 문제 ( 토큰 탈취 시) <br>
>  - Refresh 토큰을 추가함으로서 Access 토큰의 만료시간을 줄이고 재인증을 통해 Access 토큰 재발급
>  #### 프론트 작업 시 해당 API에 대해 찾아보는게 번거로웠음
>  - Rest Docs를 이용해 필요한 파라미터 및 결과를 문서로 작성 
<br>

위의 사항들을 보완하고자 하였고 <br>
추가적으로 현재는 AWS EC2 서버의 Docker를 통한 배포를 목표로 하고있습니다. <br> 
<br>
해당 프로젝트에 대해 부족하고 생각되는 점, 보완할 점에 대해 피드백을 해주시면 감사하겠습니다. <br>

<br>

## 2. 사용기술
- Java 11, Gradle
- Spring Boot, Security
- Spring Data JPA, AWS Cloud(Mysql)
- Spring Rest Docs

#### 추가 예정 기술
- AWS EC2, Docker
<br>

## 3. 주요 기능
- 게임 관련 뉴스, 리뷰, 정보를 제공하며 각 게임마다 유저가 작성할 수 있는 커뮤니티 게시판을 제공 (공략, 자유) <br>
- 관리자가 게임 관련 정보를 추가 및 관리할 수 있는 기능을 제공
<br>

## 4. 인증 방식
### JWT 토큰 방식
- Access-Refresh 방식

#### Access Token
- 인증에 필요한 기능에 쓰이는 인증 토큰
- 로그인 시 Authorization 헤더 및 payload에 제공
- 만료 시간이 짧지만 재로그인 하지않아도 Refresh Token을 통해 재발급

#### Refresh Token
- Access Token을 재발급 받는데 필요한 인증 토큰
- Cookie ( Http Only, Secure )로 제공<br>
  ㄴ해당 쿠키를 받아오는데 있어서 Https가 필요 <br>
  (해당 부분은 nginx로 설정할 예정)
- Refresh Token도 탈취 당할 위험이 있기때문에 해당 토큰과 Access 토큰도 같이 받아 DB 조회
 
  
<br>


## 5 DB 설계
![db](https://user-images.githubusercontent.com/66755342/196113931-62f8f3f0-7f60-4bec-8714-40a7da63cdea.PNG)

<br>

## 6. Rest Api Docs
[GameInfo Docs Link](http://43.201.119.217:8080/docs)
