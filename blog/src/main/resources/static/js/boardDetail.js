let httpRequest;
let boardId = document.getElementById("boardId").value;

//const commentArea = document.getElementById("comment-area-login").value;

function addCommentHtml(data) {
    const cardList = document.getElementById("cardList")
    cardList.innerHTML = ""; // 자식 태그 초기화

    //다시그리기
    let total = data.total;
    if (total > 0) {
        let comments = data.comments;

        for (let k of Object.keys(comments)) {
            console.log(comments[k]);
            let commentId = comments[k].commentId;
            let comment = comments[k].content;
            let createDate = comments[k].createDate;
            let userName = comments[k].userName;
            let userThumbnail = comments[k].userThumbnail;
            let childComments = comments[k].childCommentDtos;

            let comment_html =
                "<div class='card-body p-2'>" +
                "   <div class='d-flex flex-start'>" +
                "       <img class='rounded-circle shadow-1-strong me-3' alt='이미지X' width='40' height='40' src=" + userThumbnail + ">" +
                "       <div>" +
                "           <h6 class='fw-bold mb-1'>" + userName + "  </h6>" +
                "           <div class='d-flex align-items-center mb-1 fs-6'>" + "작성일: " + createDate + "</div>" +
                "       <div class='mb-0 fs-7'>" + comment + "</div></div>" +
                "   </div>" +
                "</div>";
            if (childComments.length === 0) {
                cardList.innerHTML += comment_html;
            } else {
                let childComment_html =
                    "<div>" +
                    "<button type='button' class='btn btn-outline-success btn-sm rounded-3' data-bs-toggle='collapse' data-bs-target='#comment" + commentId + "' aria-expanded='false' aria-controls='comment" + commentId + "' style='cursor: pointer; float: right'>답글</button>" +
                    "</div>" +
                    "<div class='collapse' id='comment" + commentId + "'>"
                for (let i = 0; i < childComments.length; i++) {
                    childComment_html +=
                        "   <div class='d-flex mt-4 collapse'>" +
                        "       <div class='d-flex flex-start ms-4'>" +
                        "       <img class='rounded-circle shadow-1-strong me-1' src='" + childComments[i].userThumbnail + "' width='40' height='40'>" +
                        "       <div>" +
                        "           <h6 class='fw-bold mb-1'>" + childComments[i].userName + "</h6>" +
                        "           <div class='d-flex align-items-center mb-1 fs-6'>" + childComments[i].createDate + "</div>" +
                        "           <div class='mb-0 fs-6'>" + childComments[i].content + "</div>" +
                        "       </div></div>" +
                        "   </div>";
                }
                cardList.innerHTML += comment_html;
                childComment_html+="<hr class='my-1'></div>"
                cardList.innerHTML += childComment_html;
            }

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

function sendComment(comment, parentId) {
    let requestsData = {};
    requestsData.comment = comment;
    requestsData.parentId = parentId;
    httpRequest = new XMLHttpRequest();

    httpRequest.open('POST', '/comment/' + boardId, true);
    httpRequest.responseType = 'json';
    httpRequest.setRequestHeader('Content-Type', 'application/json');

    httpRequest.send(JSON.stringify(requestsData));

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            const response = httpRequest.response;
            alert("댓글이 등록되었습니다!!");
            addCommentHtml(response);
        } else if (httpRequest.status === 400) {
            const error = JSON.parse(httpRequest.response);
            alert(error.comment);
        } else {
            alert("통신 실패!!!!!!");
        }
    };
}

function postChildComment(e) {
    let childCommentBtn = document.getElementById(e.getAttribute('id'));
    console.log('버튼ID'+childCommentBtn.getAttribute('id'));

    const parentId = childCommentBtn.getAttribute('parentId');
    console.log(parentId);

    const childComment = document.getElementById("child-comment-area" + parentId).value;
    console.log(childComment);

    if (childComment === "") {
        alert("댓글을 입력해주세요");
    } else {
        sendComment(childComment, parentId);
    }
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

    sendComment(comment, null);
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


