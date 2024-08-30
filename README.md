# BookMusicalTickets

## 뮤지컬 예매 서비스

사용자가 원하는 뮤지컬을 예매 할 수 있는 서비스

## Tech Stack
- Java (JDK 17)
- Spring Boot (3.3.2)
- MySQL
- Redis
- Elastic Search

## 프로젝트 기능 및 설계

[회원 가입, 로그인, 수정, 탈퇴]
- 회원가입 시 아이디 , 패스워드, 닉네임, 생년월일, 전화번호를 입력한다.
- 가입시 입력한 아이디와 패스워드로 로그인이 가능하다.
- 생년월일과 같은 개인정보를 수정할 수 있다.
- 아이디와 닉네임은 중복되지 않게 예외처리 한다.
- 회원 탈퇴 시 사용자의 정보를 삭제한다.

[사용자]
- 예매한 뮤지컬 제목, 시간, 날짜, 장소에 대한 정보를 확인할 수 있다.
- 뮤지컬 예매율(현재 예매수를 기준으로 범위 설정), 관람등급 조회가 가능하다.
- 필터링 기능으로(필터링 기준 - 지역별) 뮤지컬의 제목, 장르, 줄거리, 리뷰, 별점, 티켓 오픈, 공연하는 기간을 조회할 수 있다.
- 뮤지컬 별점 및 사진리뷰를 작성 할 수 있다.(사진 업로드와 텍스트로 리뷰로 작성한다.
- 사진을 저장한 후 해당 URL을 DB에 저장
- 사용자가 예매한 뮤지컬에 대해서만 리뷰 작성할 수 있다.)

[관리자]
- 뮤지컬 등록 -  뮤지컬 제목, 장르, 줄거리, 티켓오픈, 러닝타임, 관람등급
- 뮤지컬 극장 등록 – 뮤지컬 이름, 극장 주소, 좌석수
- 뮤지컬 스케쥴 등록 – 뮤지컬제목,극장, 상영시각

[예매 및 결제]
- 뮤지컬을 예매할 수 있다. ( 뮤지컬, 극장, 시간, 좌석 등을 선택한다.)
- 예매한 뮤지컬을 취소 할 수 있다.
- 예매한 내역을 결제할 수 있다. ( 아임포트 웹 결제 시스템 API 활용)
- 예매 과정에서 에러가 발생하면 결제에 대한 모든 정보를 없앨 수 있도록 설정
