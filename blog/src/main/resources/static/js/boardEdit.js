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

    registerBtn.addEventListener("click", post());
    cancelBtn.addEventListener("click", cancel());

    function post() {
        let title = document.getElementById('title').value;
        let content = editor.getHTML();
        let thumbnail = null;

        let responseData = new Object();
        responseData.title = title;
        responseData.content = content;
        responseData.thumbnail = thumbnail;
        httpRequest = new XMLHttpRequest();

        httpRequest.onreadystatechange = () => {
            if (httpRequest.status === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    let result = httpRequest.response;
                    console.log(result);
                } else {
                    alert('오류오류!!!')
                }
            }
        };
        httpRequest.open('POST', '/board', true);
        httpRequest.responseType = 'json';
        httpRequest.setRequestHeader('Content-Type', 'application/json');

        httpRequest.send();
    }

    function cancel() {
        let check = confirm("정말로 취소하시겠습니까?");
        if (check) {
            window.location.href = "/board"
            alert("삭제되었습니다.");
        }
    }

};





/*나중에 임시저장 기능을 만들자*/

