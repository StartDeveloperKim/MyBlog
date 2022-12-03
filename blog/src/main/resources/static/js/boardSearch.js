const searchInput = document.getElementById("searchInput");
searchInput.addEventListener('keydown', Event => checkKeyUp(Event));

function checkKeyUp(event) {
    let key = event.key || event.keyCode;
    if (key === 'Enter' || key === 13) {
        searchKeyword();
    }
}

function searchKeyword() {
    const searchWord = searchInput.value;
    if (searchWord === "" || searchWord === null) {
        alert("검색어를 입력해주세요");
    } else {
        window.location.href = "/board/search?query=" + searchWord;
    }
}