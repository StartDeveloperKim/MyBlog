let httpRequest;
let boardId = document.getElementById("boardId").value;

//const commentArea = document.getElementById("comment-area-login").value;


function sendLike(httpMethod, userId, btn) {
    let requestData = {}
    requestData.boardId = boardId;
    requestData.userId = userId;
    const option = {
        method:httpMethod,
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
    };
    fetch("/heart", option)
        .then((response) => {
            console.log(response.body);
            if (httpMethod === "POST") {
                btn.classList.remove('btn-light');
                btn.classList.add('btn-primary');
            }else if (httpMethod === "DELETE") {
                btn.classList.remove('btn-primary');
                btn.classList.add('btn-light');
            }
        })
        .catch((error)=>{
            console.log(error);
        });
}

function clickLikeBtn() {
    let likeBtn = document.getElementById("like-btn");
    const userId = likeBtn.getAttribute("userId");
    const classes = likeBtn.classList;
    if (classes.contains("btn-light")) {
        sendLike("POST", userId, likeBtn);
    }else if (classes.contains("btn-primary")) {
        sendLike("DELETE", userId, likeBtn);
    }
}

function addCommentHtml(data) {
    const cardList = document.getElementById("cardList")
    cardList.innerHTML = ""; // 자식 태그 초기화

    //다시그리기
    let total = data.total;
    if (total > 0) {
        let comments = data.comments;
        const userInfo = data.userInfo;

        for (let k of Object.keys(comments)) {
            console.log(comments[k]);
            let commentId = comments[k].commentId;
            let comment = comments[k].content;
            let createDate = comments[k].createDate;
            let userName = comments[k].userName;
            let userThumbnail = comments[k].userThumbnail;
            let childComments = comments[k].childCommentDtos;
            const commentUserId = comments[k].userId;

            let comment_html =
                "<div class='card-body p-2'>" +
                "   <div class='d-flex flex-start'>" +
                "       <img class='rounded-circle shadow-1-strong me-3' alt='이미지X' width='40' height='40' src=" + userThumbnail + ">" +
                "       <div>" +
                "           <h6 class='fw-bold mb-1'>" + userName + "  </h6>" +
                "           <div class='d-flex align-items-center mb-1 fs-6'>" + "작성일: " + createDate + "</div>" +
                "           <div class='mb-0 fs-7'>" + comment + "</div>" +
                "           <div class='fs-6'>";
            if (childComments.length > 0) {
                comment_html += "               <a class='mx-auto my-auto' data-bs-toggle='collapse' data-bs-target='#comment" + commentId + "' aria-expanded='false' aria-controls='comment" + commentId + "' style='cursor: pointer; color: #0d6efd'>답글보기</a>";
            }
            comment_html +=
                "                               <a class='mx-auto my-auto' data-bs-toggle='collapse' data-bs-target='#commentArea" + commentId + "' aria-expanded='false' aria-controls='commentArea" + commentId + "' style='cursor: pointer; color: black'>답글쓰기</a>";
            if (commentUserId === userInfo.userId) {
                comment_html += "<a id='comment-deleteBtn" + commentId + "' commentId='" + commentId + "' onclick='deleteComment(this)' class='mx-auto my-auto' style='cursor: pointer; color: red'>댓글삭제</a>"
            }
            comment_html +=
                "           </div>" +
                "       </div>" +
                "   </div>" +
                "</div>";

            cardList.innerHTML += comment_html;

            let childComment_html =
                "<div id='commentArea" + commentId + "' class='collapse'>" +
                "   <div class='container'>" +
                "       <div class='row justify-content-center'>" +
                "           <textarea class='form-control comment-area' id='child-comment-area" + commentId + "' rows='1' placeholder='답글을 입력해주세요'></textarea>" +
                "           <button id='child-comment-btn" + commentId + "' type='button' class='btn btn-outline-dark mt-3 btn-sm rounded-3' parentId='" + commentId + "' onclick='postChildComment(this)'>답글등록</button>" +
                "       </div>" +
                "   </div>" +
                "</div>" +
                "<div id='comment" + commentId + "' class='collapse'>"
            for (let i = 0; i < childComments.length; i++) {
                const childCommentUserId = childComments[i].userId;
                childComment_html +=
                    "   <div class='d-flex mt-4 collapse'>" +
                    "       <div class='d-flex flex-start ms-4'>" +
                    "           <img class='rounded-circle shadow-1-strong me-1' src='" + childComments[i].userThumbnail + "' width='40' height='40'>" +
                    "           <div>" +
                    "               <h6 class='fw-bold mb-1'>" + childComments[i].userName + "</h6>" +
                    "               <div class='d-flex align-items-center mb-1 fs-6'>" + childComments[i].createDate + "</div>" +
                    "               <div class='mb-0 fs-6'>" + childComments[i].content + "</div>";

                if (childCommentUserId === userInfo.userId) {
                    childComment_html +=
                        "<div class='fs-6'>" +
                        "   <a id='child-comment-deleteBtn" + childComments[i].commentId + "' commentId='" + childComments[i].commentId + "' onclick='deleteChildComment(this)' class='mx-auto my-auto' style='cursor: pointer; color: red'>답글삭제</a>" +
                        "</div>"
                }
                childComment_html +=
                    "           </div>" +
                    "       </div>" +
                    "   </div>";
            }
            childComment_html += "<hr class='my-1'></div>"
            cardList.innerHTML += childComment_html;
        }
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
    const parentId = childCommentBtn.getAttribute('parentId');
    const childComment = document.getElementById("child-comment-area" + parentId).value;

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

/*삭제요청 전송*/
function sendCommentDeleteRequest(data) {
    console.log(data);
    console.log(JSON.stringify(data));
    httpRequest = new XMLHttpRequest();

    httpRequest.open('DELETE', '/comment', true);
    httpRequest.responseType = 'json';
    httpRequest.setRequestHeader('Content-Type', 'application/json');

    httpRequest.send(JSON.stringify(data));

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            const response = httpRequest.response;
            alert("댓글이 삭제되었습니다");
            addCommentHtml(response);
        } else {
            alert("통신 실패!!!!!!");
        }
    };
}

/*댓글 삭제 이벤트*/
function deleteComment(e) {
    let deleteBtn = document.getElementById(e.getAttribute('id'));
    const commentId = deleteBtn.getAttribute('commentId');

    const data = {};
    data.boardId = boardId;
    data.commentId = commentId;
    data.parentComment = true;

    sendCommentDeleteRequest(data);
}

/*대댓글 삭제 이벤트*/
function deleteChildComment(e) {
    let childCommentDeleteBtn = document.getElementById(e.getAttribute('id'));
    const commentId = childCommentDeleteBtn.getAttribute('commentId');

    const data = {};
    data.boardId = boardId;
    data.commentId = commentId;
    data.parentComment = false;

    sendCommentDeleteRequest(data);
}

/*게시글 수정 삭제 버튼 이벤트*/
const editBtn = document.getElementById("edit-btn");
const deleteBtn = document.getElementById("delete-btn");

editBtn.addEventListener("click", function () {
    location.href = "/board/edit/" + boardId;
});

deleteBtn.addEventListener("click", function () {
    if (confirm("정말로 삭제하시겠습니까??")) {
        const option = {
            method:'DELETE',
        };
        fetch("/board/"+boardId, option)
            .then((response) => {
                console.log(response.status);
                alert("삭제되었습니다!!");
                location.href = "/";
            })
            .catch((error)=>{
                console.log(error);
            });
    }
});


