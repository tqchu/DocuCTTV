$(function () {
    const addRowBtn = $('.form-group__add-row-btn')
    $('#addInventoryForm').click(function (e) {
        const minusRowBtn = $(e.target).parent('.form-group__minus-row-btn')
        if (minusRowBtn){
            if ($('.product-row').length > 1){
                minusRowBtn.parent().remove()
            }
        }
    })
    addRowBtn.click(function () {
        $(this).prev().after($(this).prev().clone())
    })
})