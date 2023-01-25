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
- JPA, QueryDSL
- Spring Security

### 빌드 도구

- Gradle

### 데이터베이스

- Oracle
- H2

### 프론트엔드

- Vue.js
- Vuetify

### 기타 라이브러리

- Lombok(BE)
- Toast UI(FE)
- Tagify(FE)

## 작성한 ERD
![image](https://user-images.githubusercontent.com/97887047/203299961-7cb32058-a308-4ea9-af58-fb5f393cab67.png)

# 주요기능
### 게시물 CRUD 기능
게시물 작성은 Toast UI Editor를 사용했고 게시글은 마크다운으로 작성됩니다. 

게시글의 추가, 수정 및 삭제는 권한이 ADMIN인 경우에만 가능하고 GUEST인 경우에는 댓글 작성이 가능하며 로그인을 하지 않은 사용자는 게시글 조회를 할 수 있습니다. 

![2023-01-23 18;32;01](https://user-images.githubusercontent.com/97887047/214006948-03d72b2b-fc78-4ff9-a538-e5de90bb1273.gif)

![2023-01-23 18;37;15](https://user-images.githubusercontent.com/97887047/214008112-131c4663-51e5-4a51-96b9-6c772c80c657.gif)

----

### 게시글 썸네일 업로드
각 게시글에는 썸네일을 등록할 수 있습니다. 

썸네일을 클라이언트에서 업로드하면 AJAX 통신을 통해 해당 이미지의 정보가 서버로 전송됩니다. 

서버에서는 썸네일 정보를 MultipartFile 타입으로 받고 깃허브 API를 통해 지정된 깃허브 레퍼지토리로 저장되게 됩니다. 마지막으로 저장된 사진의 경로가 클라이언트로 response되고 결론적으로 게시글을 POST 할 때 DB에는 썸네일의 URL이 저장됩니다.

----

### Toast UI Editor 게시글 사진 업로드
토스트 에디터를 사용해서 게시글을 작성할 때 별다른 조치를 하지않고 사진을 게시글에 포함시킨다면 Base64 형태로 입력되어 게시글에 어마어마한 글자가 입력되게되고 이를 DB에 그대로 저장하면 DB에 엄청난 부하가 가해질 것입니다.

Toast Editor에 hook 기능을 이요하여 이미지를 업로드할 때를 체크하여 업로드 된다면 사진의 정보를 가져와 AJAX 통신으로 서버에 전송하고 깃허브 API를 통해 깃허브 레포에 저장됩니다. 

따라서 게시글에는 장황한 이미지의 정보대신 저장된 이미지의 URL이 표시되게 됩니다.

![2023-01-23 18;56;56](https://user-images.githubusercontent.com/97887047/214011511-c82aaddb-bbf6-41a0-ae25-e5987d6d2c90.gif)

-----

### 태그(Tag)
> 프론트엔드에서는 Tagify 라이브러리를 통해 태그 UI를 표현했습니다. 
#### [태그의 중복체그 문제]
태그는 중복저장이 허용되지 않게 했으므로 서버에서 중복확인을 합니다. 

여기서 태그에 대한 중복확인 쿼리가 반복적으로 나가는 것을 방지하기 위해 Map 자료구조의 인메모리 저장소를 구성하고 DB에 있는 모든 태그를 해당 저장소에 저장하여 서버 내부에서 비교하도록 개선하였습니다. 

하지만 이 또한 자바 애플리케이션 내부 메모리에 자리를 잡고 있기 때문에 추후에 Redis를 통해 개선을 할 예정입니다.

------

### 게시글 임시저장 기능
임시저장 버튼을 활성화하면 1분마다 AJAX 통신으로 작성중인 게시글의 정보가 서버로 전송되어 임시저장 테이블에 저장되게 됩니다. 

해당 게시글을 임시저장하고 게시글을 다시 쓸 때 최근에 저장된 임시저장글을 조회하여 작성자에게 이어쓸거냐고 물어봅니다. 

이어쓴 임시 글은 저장시 삭제요청이 서버로 전송됩니다.

![2023-01-23 19;01;37](https://user-images.githubusercontent.com/97887047/214012591-8928694a-cea8-44e2-b4a9-c25546e9cd87.gif)

------

### 계층형 카테고리
카테고리 테이블에 컬럼으로 '부모 카테고리 아이디'를 컬럼으로 두었습니다. 조회를 할 때 카테고리의 PK의 오름차순으로 전체 조회하고 조회된 카테고리들을 응답 DTO로 변환합니다. 

이 때 Map의 형태로서 Map의 value에는 부모 카테고리 DTO가 들어가고 내부 해당 DTO 필드로 자식카테고리의 List가 있어 이 리스트에 자식카테고리들이 add 됩니다.

![2023-01-23 19;04;38](https://user-images.githubusercontent.com/97887047/214012997-ac209c96-bb3e-4851-9c4c-00f0b5feeaee.gif)

#### [카테고리 데이터에서의 캐시 활용]
카테고리레이아웃에 대한 정보를 조회할 때 카테고리 테이블에서 모든 데이터를 조회하고 이를 계층형 카테고리로 만드는 로직까지 포함되어있다.

한번 조회하는데 많은 로직이 담겨있다. 하지만 카테고리 데이터는 댓글이나 게시글처럼 자주 변화되는 데이터가 아니다.

따라서 카테고리 데이터를 처음 조회할 때 이를 캐시에 저장하고 그 다음 부터는 캐시를 먼저 확인하여 캐시에 데이터가 존재한다면 DB까지 가지않고 캐시에서 조회하도록 하였다.
카테고리 정보가 삭제되거나 이름이 수정될 경우 캐시에서 해당 데이터를 지우는 방식으로 활용하였다. 

### 카테고리 편집기

카테고리 편집기를 구현하여 카테고리 추가 및 삭제를 할 수 있도록했습니다. 해당 페이지의 경우 Vue Router에서 접근하기 전 token 및 권한 검사를 실시하고 ADMIN 사용자만이 접근할 수 있도록 하였습니다.

![2023-01-26 00;28;04](https://user-images.githubusercontent.com/97887047/214604902-ad47cc06-6686-4f99-abd2-224ed37d4daf.gif)


------

### 게시글 검색기능
제목 기반으로 검색할 수 있도록 구현하였고 추후에 검색 기준을 정할 수 있도록 기능을 추가할 예정입니다.

![2023-01-23 19;36;15](https://user-images.githubusercontent.com/97887047/214019251-e2d8301d-f183-4d8d-b8d4-c6d85fc57c46.gif)

------

### 댓글과 대댓글
댓글 또한 컬럼으로 부모 댓글의 아이디를 컬럼으로 두었고 게시물에 대한 댓글을 조회할 때 게시글의 PK를 FK로 참조하고 있는 댓글들을 모두 조회하고 부모 댓글 아이디를 사용하여 서버단에서 계층형 댓글로 만드는 작업을 진행하였습니다. 

그리고 서버단에서 현재 클라이언트의 댓글 삭제여부를 DTO로 담아서 전송하도록 하였습니다.

![2023-01-23 19;06;29](https://user-images.githubusercontent.com/97887047/214013396-fe541707-4c2d-49fe-8b53-e6cedeb8d263.gif)

------

### 소셜 로그인(OAuth2 + JWT)
로그인은 OAuth2 + JWT 방식을 사용했습니다. 

OAuth2를 통한 로그인이 성공한다면 OAuthSuccessHandler에서 JWT를 생성하고 이를 클라이언트로 전달합니다. 

클라이언트는 전달된 JWT LocalStorage에 저장하여 권한이 필요한 요청을 할 때 Authorization에 JWT를 담아 전송합니다.

![2023-01-23 19;14;36](https://user-images.githubusercontent.com/97887047/214014913-1bd8cf65-387d-43d4-b7e5-b04cc66c0f15.gif)

------

#### [로그인한 사용자 정보 사용하기]
컨트롤러 레이어의 메서드에서 사용자 정보가 필요한 경우가 있는데 이 경우에 JWT 필터와 커스텀한 어노테이션 그리고 이에대한 ArgumetnResolver를 추가하여 해결했습니다.

1. JWT 필터에서 JWT 검증이된다면 SecurityContext에 인증정보가 담긴 객체를 저장한다.
2. @Login 어노테이션이 붙은 파라미터에 대해 LoginUserArgumentResolver가 동작하고 파라미터 타입 등을 검증한다.
3. 검증이 된다면 SecurityContext에 저장되어있는 인증 객체를 반환한다.
