$(function () {
    const inventoryTabItem = $('.inventory_tabs__item')
    inventoryTabItem.click(function (e) {
        e.preventDefault()
        e.stopPropagation()

        window.location.href = $(this).attr('href')
    })

})