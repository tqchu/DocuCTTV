$(function () {
    // HANDLE SEARCH, FILTER , SORT, PAGE FORM
    console.log(1);
    const surrogateForm = $('#surrogateForm')
    const pageInput = surrogateForm.find('input[name=page]')
    const sortByInput = surrogateForm.find('input[name=sortBy]')
    const pageBtn = $('.page-link')
    pageBtn.click(function () {
        if (pageInput.attr('disabled')) {
            pageInput.removeAttr('disabled');
        }
        pageInput.val($(this).attr('data-page'))
        surrogateForm.submit()
    })


    const sortOptions = $('.order-bar__option')
    sortOptions.click(function () {
        if (sortByInput.attr('disabled')) {
            sortByInput.removeAttr('disabled');
        }
        sortByInput.val($(this).attr('data-sort'))
        surrogateForm.submit()
    })
    const minPriceSurrogateForm = surrogateForm.find('input[name=minPrice]')
    const maxPriceSurrogateForm = surrogateForm.find('input[name=maxPrice]')
    const applyFilterBtn = $('.search__filter__apply-btn')
    console.log(applyFilterBtn)
    applyFilterBtn.click(function () {
        console.log(1)
        const minPriceInput = $('.search__filter__price-range__input--min-price')
        const maxPriceInput = $('.search__filter__price-range__input--max-price')

        if (minPriceInput.val()!==""){
            if (minPriceSurrogateForm.attr('disabled')) {
                minPriceSurrogateForm.removeAttr('disabled');
            }
            minPriceSurrogateForm.val(minPriceInput.val())
        }
        if (maxPriceInput.val()!==""){
            if (maxPriceSurrogateForm.attr('disabled')) {
                maxPriceSurrogateForm.removeAttr('disabled');
            }
            maxPriceSurrogateForm.val(maxPriceInput.val())
        }

        surrogateForm.submit()
    })
})
