# 제품 정보 관리 시스템 (Product Management System)

## 프로젝트 목적

REST API를 기반으로 한 간단한 제품 정보 관리 시스템을 구축하고, 
프론트엔드(Thymeleaf)와 연동하여 제품의 등록, 조회, 수정 기능을 확인할 수 있도록 제작하였습니다. 
일부 민감 정보는 AES 방식으로 암호화되어 송수신됩니다.

---

## 기술 스택

### Backend
- Java 21
- Spring Boot 3.x
- Spring Web (REST API)
- Spring Data JPA
- MySQL (Docker 사용)
- AES 암호화 적용
- TestContainer (통합 테스트용)

### Frontend
- Vanilla JavaScript

---

## 주요 기능

| HTTP Method | URI                     | 설명                |
|-------------|-------------------------|---------------------|
| GET         | `/api/v1/products`      | 전체 제품 목록 조회 |
| GET         | `/api/v1/products/{id}` | 단일 제품 상세 조회 |
| POST        | `/api/v1/products`      | 신규 제품 등록      |
| PUT         | `/api/v1/products/{id}` | 제품 정보 수정      |

> 가격과 수량은 AES 방식으로 암호화되어 저장 및 송수신됩니다.

---

## API 테스트 방법

Docker demon을 실행시킨 후 서버를 구동하면 MySQL이 docker-container에서 실행됩니다.
http://localhost:8080/ 으로 접속하면 기본 상품 목록 페이지로 이동됩니다.
해당 페이지에서 목록 조회, 상품 등록버튼을 눌러 등록할 수 있습니다.
상품 목록에서 이름을 누르면 상세 페이지로 이동되며, 수정 버튼을 눌러 수정할 수 있습니다.