$(function () {
    const languageOption = {
        "decimal": "",
        "emptyTable": "Không có dữ liệu",
        "info": "Hiển thị  dòng _START_ đến _END_ trong _TOTAL_ dòng",
        "infoPostFix": "",
        "thousands": ".",
        "infoEmpty":      "",
        "lengthMenu": "Hiển thị _MENU_ dòng/ trang",
        "loadingRecords": "Đang tải...",
        "processing": "Đang xử lý...",
        "paginate": {
            "first": "Trang đầu",
            "last": "Trang cuối",
            "next": "Trước",
            "previous": "Sau"
        }
    }
    $(".data-table").DataTable({
        ordering: false,
        searching: false,
        "lengthChange": false,
        "language": languageOption
    });
    /*const collapseButton =$('.collapse-button')
    const expandButton =$('.expand-button')
    const navbar = $('.navbar__tab-list')
    const navbarParent = $('.slide-navbar').parent();
    collapseButton.click(function () {
        navbar.hide();
        expandButton.show();
        navbarParent.removeClass("col col-2-4")
        $(this).hide();

    })
    expandButton.click(function () {
        navbar.show();
        collapseButton.show();
        navbarParent.addClass("col col-2-4")

        $(this).hide();
    })*/

});