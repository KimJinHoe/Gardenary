# 🌷 Gardenary 가드너리
<div align="center">
  <img src="https://user-images.githubusercontent.com/63358647/206158105-b7eed58a-eaec-4982-bc24-9c7fb6303d61.png" width="60%"/>
</div>

## 0. 서비스 소개
- 소개: 유니티를 이용한 일상 기록 정원 꾸미기 어플리케이션 [플레이스토어 출시 예정]
- 주요 기능
  - 일일 질문과 일기 작성을 통해 꽃과 나무를 기르고 나만의 정원을 꾸밀 수 있습니다.
  - 팔로워들 간 놀러가기 기능을 통해 나만의 정원을 자랑할 수 있습니다.
  - 자신의 기록을 보며 그 당시의 감정을 간직하세요! <br>
`플레이 스토어에 출시할 예정이기 때문에 소스코드는 공개하지 않습니다.`

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
<img src="https://user-images.githubusercontent.com/63358647/206184961-42d2d541-894a-40d9-84dd-d8d818a746a3.PNG" width="70%"/>

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
준비 중...

## 7. 회고
### 보안성을 고려하며
```
이번 프로젝트는 플레이 스토어 출시를 목표로 어플리케이션 개발을 진행하게 되었다.
때문에 그 전 프로젝트보다 보안에 대해서 깊은 고민을 하면서 보안성을 향상시키기 위해 노력을 했다.
민감한 데이터의 PK를 autoincrement가 아닌 UUID를 사용하고 (autoincrement는 1, 2, 3... 차례로 대입할 수 있기 때문에)
변경이 잦은 테이블과 아닌 테이블을 분리시켜 참조를 줄이고 보안을 향상시켰다. (참조를 줄여 데이터 변경을 줄이는 장점과 민감한 데이터는 최대한 참조를 줄이자는 의도)
또한, 편의를 위한 전체 데이터의 ResponseDto가 아닌 필요한 데이터만을 전송하는 ResponseDto를 작성했고,
민감한 데이터를 넘겨야 할 때에는 Crypto 라이브러리를 이용해 AES-256 암복호화 함수를 구현해서 암호화된 데이터를 넘기도록 했다. (ex. userid)
```

### 어플에서의 도메인, Certbot..?
```
이번 프로젝트는 모바일 어플 형태로 사용자가 웹에서 url을 통한 접근이 아니다 보니 도메인을 따로 구매해야 하는지에 대한 의문이 생겼다.
컨설턴트님에게 조언을 구하고 결국 도메인 사지 않도록 결정했다. 도메인이 없는 IP이다 보니 Certbot(https) 또한 적용하지 못했다.
* IP에도 적용하는 방법이 있다고는 알고 있지만 흔하지 않은 방법이라고 알고 있다.

하지만 도메인을 사용하지 않았기 때문에 만약 사용자가 어플을 해부한다면 백엔드 서버의 ip 주소가 그대로 노출 될 수 있겠다고 생각했다.
이를 위한 해결 방안으로는 도메인 주소를 구매하거나, 중간에 프록시 서버와 같은 중간 서버를 두어 백엔드 주소의 노출을 막을 수 있다.
```

### CI/CD 및 DB 영속성
```
이번 프로젝트에서 처음으로 CI/CD를 전적으로 담당했다. CI/CD의 처음부터 끝까지, 도커의 컨테이너 구축을 하나하나 하다보니 CI/CD에 대해
깊게 학습할 수 있었고, 추후 서버 이전 작업을 하면서 재구축을 할 때 어렵지 않게 성공할 수 있었다.

젠킨스를 재빌드할 때, compose down&up 과정을 진행했는데 이로 인해 DB가 초기화되는 문제가 있었다.
DB의 영속성을 유지하기 위해 AWS RDS를 이용한 방법이 있었지만, 본인은 volume설정을 두어 DB의 백업 및 적용 과정을 거쳐 영속성을 유지시킬 수 있었다.
```

### 유니티 Terrain 복사 문제
```
백엔드 개발자로 내가 유니티 프론트 부분을 맡을 일이 앞으로 언제 있을까 싶지만 직면한 문제를 기록하고자 한다.
기존 Terrain을 복사해서 다른 씬에 적용시키는 작업을 했는데, Terrain이 서로 연동되어 있어 다른 씬에서 오브젝트를 삭제하면 해당 씬에서도 삭제되는 문제가 있었다.
구글링 결과 Terrain을 그냥 복사하면 안되고 새로 생성된 New Terrain을 끌어다 사용해야 함을 알았다.
이로 인해 중간에 수정 작업이 진행됐고, 문제 원인을 찾는데 시간이 조금 소모되었다.
```
