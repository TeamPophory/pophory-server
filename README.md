# Pophory-Server <img src="https://github.com/TeamPophory/pophory-server/assets/65678579/e7fcdd42-556e-4291-9032-070f5093ca42" align=left width=100>
주머니 속 잃어버릴 걱정 없는 모바일 네컷앨범 서비스.

<br>
<img src="https://github.com/TeamPophory/pophory-server/assets/65678579/c2223c52-3eff-428c-9e2f-c8c4d33bc043">

## 💜 Pophory SERVER Developers

<img src="https://github.com/TeamPophory/pophory-server/assets/65678579/0a978b09-822f-4d1e-9bbb-13cc8c5ad6f2.png" width="200">|<img src="https://user-images.githubusercontent.com/65678579/210243739-e84cf9aa-2315-41b4-be82-df9d3e4cc614.png" width="200"> | 
:---------:|:----------:|
최윤한 | 강윤서 |
[unanchoi](https://avatars.githubusercontent.com/u/81692211?v=4) | [yungu0010](https://avatars.githubusercontent.com/u/65678579?v=4) |
 프로젝트 세팅 <br> DB 설계 <br> Github Action과 Code Deploy를 이용하여 배포 자동화 구성 <br> Photo, Album, Auth, Member API 구현 | 프로젝트 세팅 <br> DB 설계 <br> Auth, Studio, Member API 구현
<br>

## 📂 Project Foldering
```
.
├── domain
│   ├── album
│   │   └── dto
│   │       └── response
│   ├── member
│   │   ├── auth
│   │   │   └── dto
│   │   │       ├── request
│   │   │       └── response
│   │   └── dto
│   │       ├── request
│   │       └── response
│   ├── message
│   ├── photo
│   │   └── dto
│   │       ├── request
│   │       └── response
│   ├── studio
│   │   └── dto
│   └── tag
├── global
│   ├── advice
│   ├── config
│   │   └── jwt
│   ├── entity
│   ├── exception
│   └── util
└── infrastructure
    └── s3
```

<br>

## 📖 사용 스택

Java & Spring Boot 2.7 

 ![Springboot](https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white)

 ![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white)

 ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white)

 ![amazons3](https://img.shields.io/badge/amazons3-%569A31.svg?style=for-the-badge&logo=amazons3&logoColor=white&color=7aa116)

 ![amazonec2](https://img.shields.io/badge/amazonec2-%ED7100.svg?style=for-the-badge&logo=amazonec2&logoColor=white&color=ed7100)

 ![amazonrds](https://img.shields.io/badge/amazonrds-%27FFF.svg?style=for-the-badge&logo=amazonrds&logoColor=white&color=c925d1)

## Architecture

<img width="672" alt="image" src="https://github.com/TeamPophory/pophory-server/assets/81692211/4e1ac301-9bc5-4fb9-b877-d58c18c0ca8b">