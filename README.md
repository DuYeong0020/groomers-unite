# groomers-unite

## 개요
### groomers-unite?
애견 미용 업계에서는 미용사마다 실력 편차가 크며, 체계적인 피드백을 받기 어려운 현실입니다.  
대부분 유명한 학원을 다니거나, 실력 있는 선생님께 사진을 보내 피드백을 받는 방법밖에 없습니다.

이를 해결하기 위해 미용사들이 서로 피드백을 주고받을 수 있는 프로젝트를 만들었습니다.  
이 플랫폼에서는 사진과 글을 올려 피드백을 요청하면, 다른 미용사들이 댓글로 조언을 남길 수 있습니다.  
또한, 선택적으로 별점을 부여하여 정량적인 평가도 가능합니다.  

#### 별점 평가 기준
별점은 4가지 기준으로 평가되며, 선택적으로 남길 수 있습니다.

- 완성도: 전체적인 마무리가 얼마나 깔끔한가
- 면처리: 컷이 얼마나 정교하고 깨끗한가
- 대칭: 기준에 맞춰 좌우 대칭이 잘 이루어졌는가
- 밸런스: 이상적인 비율에 맞춰 균형감 있게 컷이 되었는가

### 주요 기능
- 사용자 가입 및 관리
- 게시글 기능
- 댓글 및 평점 기능

### 팀원 
- Beckend: 1명(최두영)

### 개발 기간
2025. 01 ~ 2025. 02
## 🛠기술 스택
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=hibernate&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-6DB33F?style=for-the-badge)
![Session](https://img.shields.io/badge/Auth-Session--Based-blue?style=flat-square)
![AWS S3](https://img.shields.io/badge/Infra-AWS%20S3-orange?style=flat-square)  
![AWS EC2](https://img.shields.io/badge/Infra-AWS%20EC2-yellow?style=flat-square)


## ERD
<img width="1169" alt="Image" src="https://github.com/user-attachments/assets/a5648762-95d1-413d-9de3-bd72a75204be" />

### 실행 방법
#### 1. 프로젝트 클론
```bash
git clone https://github.com/DuYeong0020/groomers-unite
cd project
```
#### 2. IntelliJ 실행 시  환경 변수 설정 (Jasypt 암호화 키)
Run/Debug Configurations 창을 엽니다.  
Environment Variables 항목에 JASYPT_ENCRYPTOR_PASSWORD 추가

#### 3. Mysql 설정
MySQL을 사용한다면, groomers_unite 데이터베이스를 미리 생성해야 합니다.
