
function postCategory(data, categoryName) {
    $.ajax({
        url: "/category/add",
        type: 'POST',
        async: false,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            if (data.status === "success") {
                alert("카테고리가 등록되었습니다.");
                /*$('#list-items').append("<li><span class='todo-text'>" + categoryName + "</span><a class='remove text-right' th:value=" + data.categoryId + "> <i class='fa fa-trash'></i></a><hr></li>");
                localStorage.setItem('listItems', $('#list-items').html());
                $('#todo-list-item').val("");*/
                window.location.href = "/category";
            } else if (data.status === "duplicate") {
                alert("카테고리가 중복되었습니다.");
                $('#todo-list-item').focus();
                }
            },
        error: function () {
            alert("통신실패");
        }
    });
}


function clickChildCategoryPost(e) {
    console.log("자식카테고리 추가")
    let childCategoryBtn = document.getElementById(e.getAttribute('id'));
    const parentId = childCategoryBtn.getAttribute('parentId');
    const childCategoryName = document.getElementById("child-category-form" + parentId).value;

    const data = {};
    data.categoryName = childCategoryName;
    data.parentCategoryId = parentId;

    console.log(data);

    postCategory(data, childCategoryName);
}

    $('.add-items').click(function (event) {
        event.preventDefault();
        let item = $('#todo-list-item').val();

        if (item) {
            const check = confirm("카테고리를 추가하시겠습까?")
            if (check) {
                const data = {};
                data.categoryName = item;

                $('#todo-list-item').val("");
                postCategory(data, item);
            }
        } else {
            alert("카테고리명을 입력해주세요!!")
            $('#todo-list-item').focus();
        }
    });

    $(document).on('click', '.remove', function () {
        const check = confirm("카테고리를 삭제하시겠습니까?");
        if (check) {
            const categoryId = $(this).attr('value');
            const data = {};
            data.categoryId = categoryId;

            console.log(categoryId);
            $.ajax({
                url: "/category/remove",
                type: 'POST',
                data: JSON.stringify(data),
                contentType: 'application/json',
                async: false,
                success: function (result) {
                    if (result === "success") {
                        alert("카테고리가 삭제되었습니다.");
                        window.location.href = "/category";
                    } else {
                        alert("오류가 발생했습니다!!")
                    }
                },
                error: function () {
                    alert("통신실패");
                }
            });
        }
    });


