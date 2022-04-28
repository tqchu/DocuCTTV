$(function () {
    $('.search__add-btn').click(function () {
        setTimeout(function () {
            $('#addModal').find("input[name='categoryName']").focus();
        }, 500)
    })
    $('.search__edit-btn').click(function () {
        setTimeout(function () {
            $('#editModal').find("input[name='categoryName']").focus();
        }, 500)
    })

    $(".toast").toast('show');

    const sortOptions = $('.order-bar__option')
    sortOptions.click(function () {
        const dataSort = $(this).attr('data-sort')
        const link = window.location.href
        const searchQueryString = $('[name="query-string"]').val()
        const servletLink = link.slice(0, link.search("categories") + 10)
        if (searchQueryString != '') {
            window.location.href = servletLink + "/search?" + searchQueryString + "&orderBy=" + dataSort;
        } else {
            window.location.href = servletLink + "?orderBy=" + dataSort;
        }

    })
})