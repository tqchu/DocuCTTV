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



});