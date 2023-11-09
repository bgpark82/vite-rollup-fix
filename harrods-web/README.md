# Harrods Admin

## 설치

1. 의존성 설치
```sh
npm install
```

2. 환경변수 설정
루트 디렉토리에 `.env.local` 파일 생성
```sh
VITE_HARRODS_API_URL=http://localhost:8080/admin # 해롯 로컬 서버 API
```

## 실행
`.env.local` 환경변수를 읽어 로컬에서 실행
```sh
npm run dev
```

## 배포 파일 생성
```sh
npm run build
```

