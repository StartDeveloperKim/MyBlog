$(document).ready(function () {

    function postCategory(data, categoryName) {
        $.ajax({
            url: "/category/add",
            type: 'POST',
            async:false,
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (result) {
                if (result === "success") {
                    alert("카테고리가 등록되었습니다.");
                    $('#list-items').append("<li><span class='todo-text'>" + categoryName + "</span><a class='remove text-right'><i class='fa fa-trash'></i></a><hr></li>");
                    localStorage.setItem('listItems', $('#list-items').html());
                    $('#todo-list-item').val("");
                } else {
                    alert("카테고리가 중복되었습니다.");
                    $('#todo-list-item').focus();
                }
            },
            error: function () {
                alert("통신실패");
            }
        });
    }

    $('.add-items').click(function(event)
    {
        event.preventDefault();
        let item = $('#todo-list-item').val();

        if (item) {
            const check = confirm("카테고리를 추가하시겠습까?")
            if (check) {
                const data = {};
                data.name = item;

                $('#todo-list-item').val("");
                postCategory(data, item);
            }
        } else {
            alert("카테고리명을 입력해주세요!!")
            $('#todo-list-item').focus();
        }
    });

    $(document).on('click', '.remove', function()
    {
        const check = confirm("카테고리를 삭제하시겠습니까?");
        if (check) {
            $(this).parent().remove();
            localStorage.setItem('listItems', $('#list-items').html());
        }
    });
});