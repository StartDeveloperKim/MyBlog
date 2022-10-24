window.onload = function () {
    let httpRequest;
    let commentBtn = document.getElementById("comment-btn");

    commentBtn.addEventListener("click", function () {
        let comment = document.getElementById("comment-area-login").value;
        let boardId = document.getElementById("boardId").value;

        let responseData = {};
        responseData.comment = comment;
        httpRequest = new XMLHttpRequest();

        httpRequest.open('POST', '/comment/' + boardId, true);
        httpRequest.responseType = 'json';
        httpRequest.setRequestHeader('Content-Type', 'application/json');

        httpRequest.send(JSON.stringify(responseData));
    });
};