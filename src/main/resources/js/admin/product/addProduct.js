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

})