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
- Thymeleaf (Spring Boot 내장 템플릿 엔진)

---

## 주요 기능

| HTTP Method | URI                     | 설명                |
|-------------|-------------------------|---------------------|
| GET         | `/api/v1/products`      | 전체 제품 목록 조회 |
| GET         | `/api/v1/products/{id}` | 단일 제품 상세 조회 |
| POST        | `/api/v1/products`      | 신규 제품 등록      |
| PUT         | `/api/v1/products/{id}` | 제품 정보 수정      |

> 🔐 제품명과 가격은 AES 방식으로 암호화되어 저장 및 송수신됩니다.

---