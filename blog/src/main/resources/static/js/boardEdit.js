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
    const formFile = document.getElementById('formFile');
    const thumbnailURL = document.getElementById("thumbnailURL");

    formFile.addEventListener("change", e => {
        /*이미지 서버에서 업로드 후 저장된 URL을 반환하고 이를 DB에 저장하는 방식*/
        uploadImg(e.target);
    });

    /*이미지 업로드*/
    function uploadImg(data) {

        if (data.files && data.files[0]) {
            let formData = new FormData();
            formData.append('img', data.files[0]);

            const httpRequest = new XMLHttpRequest();
            httpRequest.open('POST', '/board/thumbnail', false);
            httpRequest.setRequestHeader("contentType", "multipart/form-data");

            httpRequest.onload = function () {
                alert("통신완료");
                console.log(httpRequest.response);
                if (httpRequest.status === 200) {
                    thumbnailURL.value = httpRequest.response;
                    alert("썸네일이 등록되었습니다!!");
                } else {
                    alert("이미지가 정상적으로 업로드되지 못했습니다!!");
                }
            };

            httpRequest.send(formData);
        }
    }
    
    registerBtn.addEventListener("click", function () {
        
        const category = document.getElementById("category").value;
        const title = document.getElementById('title').value;
        const content = editor.getHTML();
        const tags = document.getElementById("tags").value;
        

        let responseData = {};
        responseData.title = title;
        responseData.content = content;
        responseData.category = category;
        responseData.thumbnail = thumbnailURL.value;
        responseData.tags = tags;
        httpRequest = new XMLHttpRequest();
        
        httpRequest.open('POST', '/board', true);
        httpRequest.responseType = 'json';
        httpRequest.setRequestHeader('Content-Type', 'application/json');

        console.log(JSON.stringify(responseData));

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

