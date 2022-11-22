# 토이프로젝트 - 블로그 개발하기(꾸준함이 목표인 사람의 블로그)
- 기간 : 2022-10-15 ~ 진행 중
## 프로젝트 목적
이전에 토이 프로젝트로 간단하게 게시판을 구현했습니다. 하지만 게시판 프로젝트는 너무 기본적인 기능만을 포함하고 있었고 배포까지 하기에는 부족한 부분이 너무 많았습니다. 그래서 이전 프로젝트에 대한 부족한 부분을 보완하고 다양한 기능을 적용한 뒤 배포를 목표로 하여 웹 어플리케이션 개발의 전반적인 과정을 몸소 겪어보고자 해당 프로젝트를 진행하게 되었습니다.

## 개발환경

- IntelliJ
- Postman
- GitHub
- SourceTree
- H2
- Visual Studio Code

## 사용기술

### 백엔드

- Java 11 openjdk
- SpringBoot 2.7.4
- Spring Data JPA
- Spring Security

### 빌드 도구

- Gradle

### 데이터베이스

- Oracle
- H2

### 프론트엔드

- HTML/CSS
- JavaScript
- Bootstrap
- JQuery
- Thymeleaf

### 기타 라이브러리

- Lombok

## 작성한 ERD
![image](https://user-images.githubusercontent.com/97887047/203299961-7cb32058-a308-4ea9-af58-fb5f393cab67.png)

# 주요기능
### 게시물 CRUD 기능
> 게시물 작성은 Toast UI Editor를 사용했습니다. 게시글은 마크다운으로 작성됩니다. 게시글의 추가, 수정 및 삭제는 권한이 ADMIN인 경우에만 가능토록 했습니다.

![image](https://user-images.githubusercontent.com/97887047/203287677-4f33776c-8a46-446b-a8ef-68809e918f10.png)
### 게시글 썸네일 업로드
> 각 게시글에는 썸네일을 등록할 수 있습니다. 썸네일을 input 태그 file 타입으로해서 업로드를 진행하면 AJAX 통신을 통해 해당 이미지의 정보가 서버로 전송됩니다. 서버에서는 썸네일 정보를 MultipartFile 타입으로 받고 깃허브 API를 통해 지정된 깃허브 레퍼지토리로 저장되게 됩니다. 마지막으로 저장된 사진의 경로가 클라이언트로 response되고 결론적으로 게시글을 POST 할 때 DB에는 썸네일의 URL이 저장됩니다.

![image](https://user-images.githubusercontent.com/97887047/203289126-e4b21efc-61dc-4774-8cb0-18891f363b1f.png)
### Toast UI Editor 게시글 사진 업로드
> 토스트 에디터를 사용해서 게시글을 작성할 때 별다른 조치를 하지않고 사진을 게시글에 포함시킨다면 Base64 형태로 입력되어 게시글에 어마어마한 글자가 입력되게 됩니다. 따라서 이에 대한 처리를 진행하였습니다.
> 게시글에 이미지가 업로드 된다면 이때 사진의 정보를 가져와 AJAX 통신으로 서버에 전송합니다. 그 이외의 과정은 썸네일 업로드 기능과 동일합니다. 따라서 게시글에는 장황한 이미지의 정보대신 저장된 이미지의 URL이 표시되게 됩니다.

![image](https://user-images.githubusercontent.com/97887047/203292118-6a488601-3a3f-469f-ae55-8bd1c8e4569c.png)
### 태그(Tag)
> 프론트엔드에서는 Tagify 라이브러리를 통해 태그 UI를 표현했습니다. 
> 이 때 태그는 다음과 같은 형태로 JSON 데이터에 포함되어 전송됩니다. 

tags=[{"value":"테스트"},{"value":"프로젝트"}] 
> 이는 리스트 내부에 JSON 데이터가 들어있는 형태로 GSON 라이브러리를 통해 JSON 형태의 문자열을 파싱하고 Tag 테이블에 저장하도록 했습니다.

### 계층형 카테고리
> 카테고리 테이블에 컬럼으로 '부모 카테고리 아이디'를 컬럼으로 두었습니다. 조회를 할 때 카테고리의 PK의 오름차순으로 전체 조회하고 조회된 카테고리들을 응답 DTO로 변환합니다. 이 때 Map의 형태로서 Map의 value에는 부모 카테고리 DTO가 들어가고 내부 해당 DTO 필드로 자식카테고리의 List가 있어 이 리스트에 자식카테고리들이 add 됩니다.

<img src="https://user-images.githubusercontent.com/97887047/203295791-1ff15a55-2366-441e-a993-bac65da3e28c.png" width="400" height="400">

### 카테고리 편집기
<img src="https://user-images.githubusercontent.com/97887047/203304661-8bb01624-b60f-4bc1-a3ad-f5dcbabd5299.png" width="400" height="400">

> 위 사진에서 위의 input 공간은 부모카테고리를 추가하는 공간이고 부모카테고리 옆의 '+' 버튼을 누르면 자식카테고리를 추가할 수 있는 공간이 나오게됩니다. 또한 해당 카테고리 아래에 글이 하나도 없으면 휴지통 버튼이 나오고 해당 카테고리를 삭제할 수 있습니다. 

### 댓글과 대댓글
> 댓글 또한 컬럼으로 부모 댓글의 아이디를 컬럼으로 두었고 게시물에 대한 댓글을 조회할 때 게시글의 PK를 FK로 참조하고 있는 댓글들을 모두 조회하고 부모 댓글 아이디를 사용하여 서버단에서 계층형 댓글로 만드는 작업을 진행하였습니다.

<img src="https://user-images.githubusercontent.com/97887047/203298989-ff2168c1-4226-449b-8c30-81792c874929.png" width="600" height="450">

### 소셜 로그인(OAuth2)
> 로그인은 OAuth2를 활용했고 스프링 시큐리티를 통해 권한별 접근 페이지를 달리했습니다.

<img src="https://user-images.githubusercontent.com/97887047/203299827-c3ed3d65-2924-4f79-a135-018e81ab3e77.png" width="400" height="350">
