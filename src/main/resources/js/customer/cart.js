$(function () {
    const quantitySpinner = $('input[name=quantity]')

    const checkBoxes = $('input[name=item]')
    const numOfItems = $('#numOfItems')
    const totalAmount = $('#totalAmount')
    const allItem = $('input[name=all-item]')
    allItem.change(function () {
        if (allItem.prop('checked')) {
            for (const checkbox of checkBoxes) {
                $(checkbox).prop('checked', true)
                $(checkbox).change()
            }
        } else {
            for (const checkbox of checkBoxes) {
                $(checkbox).prop('checked', false)
                $(checkbox).change()
            }
        }
    })
    quantitySpinner.change(
        function () {
            const checkBtnInput = $(this).parent().prevAll('.cart__check-btn').first().find('input')
            if (checkBtnInput.prop('checked')) {
                totalAmount.text(accounting.formatMoney(countTotalAmount(checkBoxes), '', 0, '.', '.'))
            }
        }
    )
    checkBoxes.change(function () {
        if (isAllBoxChecked(checkBoxes)) {
            allItem.prop('checked', true)
        } else {
            allItem.prop('checked', false)

        }
        numOfItems.text(countCheckboxes(checkBoxes))
        totalAmount.text(accounting.formatMoney(countTotalAmount(checkBoxes), '', 0, '.', '.'))
    })

    function countCheckboxes(checkBoxes) {
        let count = 0;
        for (const checkBox of checkBoxes) {
            if (checkBox.checked) {
                count++;
            }
        }
        return count;
    }

    function countTotalAmount(checkBoxes) {
        let totalAmount = 0
        for (const checkBox of checkBoxes) {
            if (checkBox.checked) {
                const item = $(checkBox).parents('.cart-item').eq(0)
                const quantity = item.find('input[name=quantity]').val()

                const price = item.find('.cart-header__price-column').text().replaceAll('.', '')
                totalAmount += quantity * price

            }
        }
        return totalAmount;
    }

    function isAllBoxChecked(checkBoxes) {
        for (const checkBox of checkBoxes) {
            if (!checkBox.checked) return false;
        }
        return true;
    }
});