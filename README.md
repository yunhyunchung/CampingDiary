# CampingDiary
캠핑장 정보 조회 및 캠핑 기록 일지 작성하는 Android App

## 주요 기능
1. 캠핑장 조회: 각 캠핑장의 특징을 한 눈에 알 수 있다
(공공데이터, RecyclerView 활용)

2. 캠핑장 예약 지원: 
캠핑장 조회 결과로 전화번호와 예약사이트가 있으면, 전화 앱으로 연결하거나 예약 사이트로 간편 이동 지원

* 활용한 공공DB: 한국관광공사_고캠핑정보 조회 서비스(공공데이터포털 제공)
한국관광공사 고캠핑 홈페이지에서 제공하는 캠핑장 정보가 담겨있습니다.
고캠핑 기본정보 목록을 조회합니다.

3. 캠핑 일기: 캠핑 경험을 이미지와 글로 간단하게 기록
캠핑한 사진을 올리고, 캠핑하며 경험한 소중한 추억을 글로 입력하여 일기로 저장. 
→ Firebase의 Firestore & Storage 활용

## 추가 기능

1. 프로필 사진 설정 : 카메라와 갤러리를 연동하여 프로필 사진 설정

2. [ 설정 화면 항목 ] : 
- 소리 알림: 스위치를 ON 하면 소리 알림이 설정되었다는 메시지가 뜨고 알림음 재생, OFF 하면 알림 해제 메시지 뜸
- ID 변경: ID를 설정한다
- 글자색 변경: 홈 화면의 글자색을 변경한다. (검정, 초록, 파랑, 빨강)
- 배경색 변경: 홈 화면의 배경색을 변경한다. (화이트, 다크, 블루라이트(yellow) 모드)

## 전체 FLOW
![1.jpg](https://github.com/yunhyunchung/CampingDiary/blob/main/flow%20img/1.jpg)
![2.jpg](https://github.com/yunhyunchung/CampingDiary/blob/main/flow%20img/2.jpg)
![3.jpg](https://github.com/yunhyunchung/CampingDiary/blob/main/flow%20img/3.jpg)
![4.jpg](https://github.com/yunhyunchung/CampingDiary/blob/main/flow%20img/4.jpg)
![5.jpg](https://github.com/yunhyunchung/CampingDiary/blob/main/flow%20img/5.jpg)







