$(function () {
    const periodForm = $('#period-form')
    const select = $('#period-select')
    select.change(
        function () {
            const actionTarget = periodForm.attr('action')
            const uri = select.find(':selected').val()
            window.location.href =  actionTarget + uri

        }
    )
})