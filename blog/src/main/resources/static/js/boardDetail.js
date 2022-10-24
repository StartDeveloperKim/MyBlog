
document.addEventListener("DOMContentLoaded", function () {
    getCommentList();
});

    let httpRequest;
    let commentBtn = document.getElementById("comment-btn");
    let boardId = document.getElementById("boardId").value;
    let commentContainer = document.getElementById("comment-container").value;

    function addCommentHtml(data) {

        let comment_html;
        if (data.total > 0) {
            let comments = data.comments;
            console.log(comments);
        } else {
            let comment_html = "<div class='container'></div>";
        }
    }

    function getCommentList() {
        console.log("getCommentList");
        httpRequest = new XMLHttpRequest();

        httpRequest.open('GET', '/comment/' + boardId, true);

        httpRequest.onload = function () {
            if (httpRequest.status === 200) {
                const response = httpRequest.response;
                addCommentHtml(response);
            } else {
                alert("통신 실패!!!!!!");
            }
        };
    }

    commentBtn.addEventListener("click", function () {
        let comment = document.getElementById("comment-area-login").value;

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
                addCommentHtml(response);
            } else {
                alert("통신 실패!!!!!!");
            }
        };
    });


