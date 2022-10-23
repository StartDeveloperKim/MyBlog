const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    height: '450px',
});

const input = document.getElementById('tags');
let tagify = new Tagify(input); // initialize Tagify

// 태그가 추가되면 이벤트 발생
tagify.on('add', function() {
    console.log(tagify.value); // 입력된 태그 정보 객체
    console.log(tagify.value.length);
});

window.onload = function () {
    let httpRequest;
    let registerBtn = document.getElementById("register-btn");
    let cancelBtn = document.getElementById("cancel-btn");

    registerBtn.addEventListener("click", function () {
        let userId = 1; // userId 수정하자....
        let category = document.getElementById("category").value;
        let title = document.getElementById('title').value;
        let content = editor.getHTML();
        let thumbnail = null; // 나중에 썸네일 등록 화면도 만들자
        let tags = document.getElementById("tags").value;

        console.log(typeof tags); // tags의 타입은 스트링이다. 그래서 서버에서 파싱을 해야한다.

        let responseData = {};
        responseData.userId = userId;
        responseData.title = title;
        responseData.content = content;
        responseData.category = category;
        responseData.thumbnail = thumbnail;
        responseData.tags = tags;
        httpRequest = new XMLHttpRequest();
        
        httpRequest.open('POST', '/board', true);
        httpRequest.responseType = 'json';
        httpRequest.setRequestHeader('Content-Type', 'application/json');

        httpRequest.send(JSON.stringify(responseData));

        httpRequest.onload = function () {
            if (httpRequest.status === 200) {
                alert("글이 등록되었습니다.");
                const boardId = httpRequest.response;
                window.location.href = "/board/" + boardId;
            } else {
                alert("통신 실패!!!!!!");
            }
        };
    });

    cancelBtn.addEventListener("click", function () {
        let check = confirm("정말로 취소하시겠습니까?");
        if (check) {
            window.location.href = "/board"
        }
    });

};





/*나중에 임시저장 기능을 만들자*/

