const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    height: '450px',
    hooks: {
        addImageBlobHook: function (blob, callback) {
            const formData = new FormData();
            formData.append("image", blob);
            let imageURL;
            $.ajax({
                type: "POST",
                url: "/board/toast",
                processData: false,
                contentType: false,
                data: formData,
                success: function (data) {
                    imageURL = data;
                    console.log("AJAX", imageURL);
                    callback(imageURL, "image");
                },
                error: function (request, status, error) {
                    alert(request + ", " + status + ", " + error);
                },
            });
        },
    },
    language: "ko-KR"
});

let httpRequest;
const thumbnailURL = document.getElementById("thumbnailURL")

function removeTemporalBoard() {
    const temporalBoardId = document.getElementById("temporalBoardId").value;
    console.log(temporalBoardId);
    if (temporalBoardId !== 'NotTemporalBoard') {
        $.ajax({
            type: "DELETE",
            url: "/temporal-board/"+temporalBoardId,
            async: false,
            success: function (data) {
                console.log("임시저장 글이 삭제되었습니다.");
            },
            error: function (request, status, error) {
                alert(request + ", " + status + ", " + error);
            },
        });
    }
}

function getRequestData() {
    const category = document.getElementById("category").value;
    const title = document.getElementById('title').value;
    const content = editor.getHTML();
    const tags = document.getElementById("tags").value;


    let requestData = {};
    requestData.title = title;
    requestData.content = content;
    requestData.categoryId = category === "" ? '1' : category;
    requestData.thumbnail = thumbnailURL.value;
    requestData.tags = tags;

    return requestData;
}

function boardRegister() {
    const requestData = getRequestData();
    httpRequest = new XMLHttpRequest();

    console.log(requestData);

    httpRequest.open('POST', '/board', true);
    httpRequest.responseType = 'json';
    httpRequest.setRequestHeader('Content-Type', 'application/json');

    console.log(JSON.stringify(requestData));

    httpRequest.send(JSON.stringify(requestData));

    httpRequest.onload = function () {
        if (httpRequest.status === 200) {
            removeTemporalBoard()
            alert("글이 등록되었습니다.");
            const boardId = httpRequest.response;
            window.location.href = "/board/" + boardId;
        } else if(httpRequest.status === 400) {
            const JsonData = httpRequest.response

            console.log(Object.keys(JsonData));
            for (let k of Object.keys(JsonData)) {
                alert(JsonData[k]);
            }
        } else {
            alert("통신 실패!!!!!!");
        }
    };
}

function boardUpdate() {
    let check = confirm("정말로 수정하시겠습니까?");
    if (check) {
        const boardId = document.getElementById('boardId').getAttribute('value');
        const requestData = getRequestData();

        console.log(requestData);

        $.ajax({
            type: "POST",
            url: "/board/edit/"+boardId,
            contentType: 'application/json',
            async: false,
            data: JSON.stringify(requestData),
            success: function (data) {
                alert('게시글이 수정되었습니다.');
                window.location.href = "/board/" + boardId;
            },
            error: function (request, status, error) {
                alert(request + ", " + status + ", " + error);
            },
        });
    }
}


window.onload = function () {
    let cancelBtn = document.getElementById("cancel-btn");
    const formFile = document.getElementById('formFile');

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

    cancelBtn.addEventListener("click", function () {
        let check = confirm("정말로 취소하시겠습니까?");
        if (check) {
            history.back();
        }
    });

};

/*나중에 임시저장 기능을 만들자*/
setInterval(function () {
    const temporalBoard = document.getElementById("temporalBoardId");
    const temporalId = temporalBoard.getAttribute("value");
    if (temporalId !== "updateBoard") {
        let requestData = getRequestData();
        let url = temporalId === "NotTemporalBoard" ? "/temporal-board" : "/temporal-board/" + temporalId;
        console.log(requestData, url);
        $.ajax({
            type: "POST",
            url: url,
            processData: false,
            contentType: "application/json",
            data: JSON.stringify(requestData),
            success: function (data) {
                if (data !== "success") {
                    temporalBoard.setAttribute("value", data);
                    toast("임시저장 되었습니다.");
                } else if (data === "success") {
                    toast("임시저장 되었습니다.");
                } else {
                    alert("임시저장에 실패했습니다.");
                }

            },
            error: function (request, status, error) {
                alert(request + ", " + status + ", " + error);
            },
        });
    }
}, 60000); // 1분마다 요청

// 토스트 UI
let removeToast;
function toast(string) {
    const toast = document.getElementById("toast");

    toast.classList.contains("reveal") ?
        (clearTimeout(removeToast), removeToast = setTimeout(function () {
            document.getElementById("toast").classList.remove("reveal")
        }, 1000)) :
        removeToast = setTimeout(function () {
            document.getElementById("toast").classList.remove("reveal")
        }, 1000)
    toast.classList.add("reveal"),
        toast.innerText = string
}
