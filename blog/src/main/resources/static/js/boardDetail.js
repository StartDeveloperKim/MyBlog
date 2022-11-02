let httpRequest;
let boardId = document.getElementById("boardId").value;
const commentArea = document.getElementById("comment-area-login").value;

function addCommentHtml(data) {
    const cardList = document.getElementById("cardList")
    cardList.innerHTML = ""; // 자식 태그 초기화

    //다시그리기
    let total = data.total;
    if (total > 0) {
        let comments = data.comments;
        console.log("댓글과 개수");
        for (let i = 0; i < comments.length; i++) {
            console.log(comments[i]);
            let commentId = comments[i].commentId;
            let comment = comments[i].content;
            let createDate = comments[i].createDate;
            let userName = comments[i].userName;
            let userThumbnail = comments[i].userThumbnail;

            let comment_html =
                "<div class='card-body p-2'>" +
                "   <div class='d-flex flex-start'>" +
                "       <img class='rounded-circle shadow-1-strong me-3' alt='이미지X' width='40' height='40' src=" + userThumbnail + ">" +
                "       <div>" +
                "           <h6 class='fw-bold mb-1'>" + userName + "  </h6>" +
                "           <div class='d-flex align-items-center mb-1 fs-6'>" + "작성일: " + createDate + "</div>" +
                "       <p class='fw-bold fs-7'>" + comment + "</p>" +
                "   </div>" +
                "</div>" +
                "<hr class=\"my-1\" />";

            cardList.innerHTML += comment_html;
        }
    } else {
        let comment_html = "<div class='container'></div>";
    }
}

function getCommentList() {
    httpRequest = new XMLHttpRequest();

    httpRequest.open('GET', '/comment/' + boardId, true);

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            const response = httpRequest.response;
            console.log(response);
            addCommentHtml(response);
        } else {
            alert("통신 실패!!!!!!");
        }
    };
}

function postComment() {
    let comment = document.getElementById("comment-area-login");
    if (comment === null) {
        alert("로그인이 필요합니다!!");
        window.location.href = "/login";
    } else {
        comment = document.getElementById("comment-area-login").value;
        if (comment === "") {
            alert("댓글을 입력해주세요");
            return;
        }
    }

    let responseData = {};
    responseData.comment = comment;
    httpRequest = new XMLHttpRequest();

    httpRequest.open('POST', '/comment/' + boardId, true);
    httpRequest.responseType = 'json';
    httpRequest.setRequestHeader('Content-Type', 'application/json');

    httpRequest.send(JSON.stringify(responseData));

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            const response = httpRequest.response;
            alert("댓글이 등록되었습니다!!");
            addCommentHtml(response);
        } else {
            alert("통신 실패!!!!!!");
        }
    };
}

/*수정 삭제 버튼 이벤트*/
const editBtn = document.getElementById("edit-btn");
const deleteBtn = document.getElementById("delete-btn");

editBtn.addEventListener("click", function () {
    location.href = "/board/edit/" + boardId;
});

deleteBtn.addEventListener("click", function () {
    if (confirm("정말로 삭제하시겠습니까??")) {
        httpRequest = new XMLHttpRequest();
        httpRequest.open('POST', '/board/delete/' + boardId, true);

        httpRequest.send();

        httpRequest.onload = function () {
            if (httpRequest.status === 200) {
                alert("삭제되었습니다!!");
                location.href = "/";
            } else {
                alert("오류가 발생하였습니다!!");
            }
        };
    }
});


