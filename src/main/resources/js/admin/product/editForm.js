$(function () {
    const dimensionRows = $('.dimension-form-group__row');
    const materialRows = $('.material-form-group__row');
    $('.dimension-form-group__add-row-btn').click(function () {
        dimensionRows.last().after(dimensionRows.first().clone());
    })
    $('.material-form-group__add-row-btn').click(function () {
            materialRows.last().after(materialRows.first().clone());
        }
    )

    // 
    const multipleFormGroupContainer = $('.multiple-row__form-group')
    multipleFormGroupContainer.click(function (e) {
        const target = $(e.target);
        const minusButton = target.parent(".form-group__minus-row-btn")
        const numberOfMinusButton = $(this).find('.form-group__minus-row-btn').length
        if (minusButton != null && numberOfMinusButton > 1) {
            minusButton.parent().remove()
        }
    })


    $("#coba").spartanMultiImagePicker({
        fieldName: 'images', maxCount: 5,
        rowHeight: '120px',
        groupClassName: 'col-md-3 col-sm-3 col-xs-6',
        dropFileLabel: 'Thả ảnh vào đây'
    })
    

})