demo 
아이메디신 - 기관이용자/개인 등록 api
======================


# 1. 개발 환경
    프레임워크 : springboot 2.5.6 
    뷸드 : gradle 7.2 
    자바 버전 : openjdk11 
    DB : oracle / mybatis
    api test: swagger2
# 2. 개발툴
    intellij


# 3. 테스트 방법
    http://localhost:8081/swagger-ui.html 에 접속하여 테스트

[등록요청]
<img width="1080" alt="등록요청" src="https://user-images.githubusercontent.com/12490512/178568634-0d24a177-719d-4469-9e61-88a64996c4a0.png">

[등록응답]
<img width="1073" alt="등록응답" src="https://user-images.githubusercontent.com/12490512/178568779-2eaddd28-04ae-4ff4-b05e-127d1993dcdd.png">

[조회요청]
<img width="1064" alt="조회요청" src="https://user-images.githubusercontent.com/12490512/178568796-85e08386-8a2c-4299-9d00-8013e9f91d4a.png">

[조회응답]
<img width="1091" alt="조회응답" src="https://user-images.githubusercontent.com/12490512/178568810-377bbfca-f755-43b8-b950-032ca07672e0.png">

# 4. api 명세 (TestController.java)
1. 사용자 등록
    - http://localhost:8081/regi

[등록요청]
<img width="608" alt="등록요청모델" src="https://user-images.githubusercontent.com/12490512/178583495-7b05e5cb-8081-4db8-af2f-404817d0ff1e.png">

[등록응답]
<img width="251" alt="등록응답모델" src="https://user-images.githubusercontent.com/12490512/178583500-05b9614e-4a4c-4c85-a9fb-1cbf69e28619.png">

2. 사용자 조회
    - http://localhost:8081/search

[조회요청]    
<img width="532" alt="조회요청모델" src="https://user-images.githubusercontent.com/12490512/178583503-64426b0a-b7e6-441c-ad60-c8121d2443a4.png">

[조회응답]
<img width="392" alt="조회응답모델" src="https://user-images.githubusercontent.com/12490512/178583509-ff96a456-7610-480d-b3c3-8077f03910e0.png">


# 6. 엔티티 설계와 등록/조회 쿼리

1. 엔티티 설계 :/etc/ddl 파일 스크립트 참조     
    - A라는 식별자를 가진 사람이 X기관에서는 기관이용자인데 Y기관에서는 개인인 케이스가 존재한다는 가정하에 
     사용자 권한을 각 기관 회원정보 테이블에 생성하였다.
    - 사용자 권한의 경우 CUS:개인 OP:기관 이용자 SUPER:기관로 정의한다. 

[t_user:사용자 메인, PK: 사용자 아이디]
<img width="877" alt="t_user" src="https://user-images.githubusercontent.com/12490512/178570491-e6f8a2a7-c433-4839-a9d3-2b4971f7a542.png">

[t_user_mapping:사용자-기관코드 맵핑, PK: 식별자번호(주민번호),기관코드]
<img width="748" alt="t_user_mapping" src="https://user-images.githubusercontent.com/12490512/178570513-86556dd5-b730-4c41-a82d-baead6877ea2.png">

[t_org0001: org0001 기관 회원 정보, PK: 식별자번호(주민번호)]
<img width="881" alt="t_org0001" src="https://user-images.githubusercontent.com/12490512/178570549-3cdf127c-bd37-48f4-8609-7d0f435879dc.png">

[t_org0002: org0002 기관 회원 정보, PK: 식별자번호(주민번호)]
<img width="880" alt="t_org0002" src="https://user-images.githubusercontent.com/12490512/178570572-f5083163-03ac-4543-9b9f-663eb83e33ba.png">

[t_org_info: 기관 정보]
<img width="322" alt="t_org_info" src="https://user-images.githubusercontent.com/12490512/178570593-022210f0-f24a-4a87-a2a0-27522d863bc9.png">

2. 등록/조회 쿼리 : UserMapper.xml 참조
    - 고객이 개인앱에서 가입할 경우에 사용자 메인 테이블에 적재한다.
    - 기관/기관이용자(의사 등)가 기관앱에서 회원등록을 할 경우에 사용자 메인 테이블과 기관코드 맵핑 테이블, 기관 회원 정보에 적재한다.
    - 식별자번호로 테이블간의 조인을 한다.
    
# 7. 비즈로직

RegiServiceImpl.java 파일 참조

1. 등록
    - 접속앱(개인앱/기업앱) 확인하여 biz로직 분개처리
    - 기업앱의 경우 사용자 권한과 기관코드 체크하여 등록 가능 여부 확인
    - 기등록된 회원인지 체크

2. 조회 
    - 로그인한 사용자의 권한이 고객일 경우 고객이 등록된 모든 기관정보 조회 가능
    - 로그인한 사용자의 권한이 기관이용자와 기관일 경우 소속된 기관의 모든 회원 정보 조회 가능 
