# 🌷 Gardenary (정리 중)
<div align="center">
  <img src="https://user-images.githubusercontent.com/63358647/206158105-b7eed58a-eaec-4982-bc24-9c7fb6303d61.png" width="60%"/>
</div>

## 0. 서비스 소개
- 소개: 유니티를 이용한 일상 기록 정원 꾸미기 어플리케이션 [플레이스토어 출시 예정]
- 주요 기능
  - 일일 질문과 일기 작성을 통해 꽃과 나무를 기르고 나만의 정원을 꾸밀 수 있습니다.
  - 팔로워들 간 놀러가기 기능을 통해 나만의 정원을 자랑할 수 있습니다.
  - 자신의 기록을 보며 그 당시의 감정을 간직하세요!

## 1. 개요
- 기간: 2022.10 ~ 2022.11
- 소개
  - 로그인 및 회원 기능
  - 일기 작성(꽃) 및 일일 질문 답변(꽃)
  - 작성을 통한 꽃&나무&아이템&캐릭터 획득
  - 친구(팔로우) 및 놀러가기 기능
  - 꽃&나무&아이템을 이용한 정원 꾸미기
  - 캐릭터 및 닉네임 변경
  - 나무 및 꽃 도감 확인
  - 일일 질문 및 일기 기록 상세 보기

## 2. 팀소개
### 역할분담
<img src="https://user-images.githubusercontent.com/63358647/206184961-42d2d541-894a-40d9-84dd-d8d818a746a3.PNG" width="60%"/>

### 담당파트
백엔드(JPA) [30%], CI/CD [100%], 유니티 [10%]
- 백엔드
  - 나무 관련 API
  - 팔로우 관련 API
  - AES-256 암복호화 함수 구현을 통한 암호화된 데이터 전송 및 복호화 사용
- CI/CD
  - AWS EC2 환경에서 도커와 jenkins를 이용한 자동 배포 구축
  - Redis, MySQL DB 관리 담당
  - 기업 지원 서버 만료로 인한 서버 이전 작업 및 재구축
- 유니티
  - 정적 배경(Terrain) 꾸미기

## 3. 기술스택
|Back-end|CI/CD|Database|Unity|collaboration-tool|
|--------|-----|--------|-----|------------------|
| JAVA 11 <br> Spring Boot 2.7.3 <br> Spring Security <br> Hibernate <br> JPA <br> Gradle 7.5 <br> JWT 0.9.1 <br> Crypto | NginX <br> AWS EC2 Ubuntu 20.04 LTS <br> Docker 20.10.18 <br> Jenkins | MySQL <br> Redis 7.0.4 | Unity <br> C# | Jira <br> gitlab <br> postmanDocs <br> figma <br> ErdCloud <br> PlasticSCM |

## 4. 서비스 아키텍처
<img src="https://user-images.githubusercontent.com/63358647/206184984-00bbd9de-a4be-4b0a-9829-57babce23072.PNG" width="60%"/>

## 5. 그 외 산출물
### 1) API 명세서
<img src="https://user-images.githubusercontent.com/63358647/206184969-87850122-c566-4fc9-9098-f657c75bb539.PNG" width="60%"/>

### 2) 포스트맨 DOCS
<img src="https://user-images.githubusercontent.com/63358647/206184980-795e4d85-0662-419a-bdc0-7834776eb875.PNG" width="60%"/>

### 3) 와이어프레임
<img src="https://user-images.githubusercontent.com/63358647/206184966-bfae7d6e-75bb-4619-8a17-97561cc3a928.PNG" width="60%"/>

### 4) ERD
<img src="https://user-images.githubusercontent.com/63358647/206184974-a366ca3d-bee4-4a18-b2df-a6be0be090c2.png" width="60%"/>

## 6. 시연 영상

## 7. 회고



