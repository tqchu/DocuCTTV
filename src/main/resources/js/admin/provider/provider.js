$(function () {
    const sortOptions = $('.order-bar__option')
    sortOptions.click(function () {
        const dataSort = $(this).attr('data-sort')
        const link = window.location.href
        const searchQueryString = $('[name="query-string"]').val()
        const servletLink = link.slice(0, link.search("providers")+9)
        if (searchQueryString!=''){
            window.location.href = servletLink +"/search?" + searchQueryString + "&orderBy="+dataSort;
        }
        else {
            window.location.href =servletLink+"?orderBy="+dataSort;
        }

    })
})