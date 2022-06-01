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

    $("#product-ranking-table").DataTable({
        "dom": '<"pull-left"f><"pull-right"l>tip',
        lengthChange: false,
        language: {
            decimal: "",
            search:"Tìm kiếm",
            emptyTable: "Không có dữ liệu",
            info: "Hiển thị  dòng _START_ đến _END_ trong _TOTAL_ dòng",
            infoPostFix: "",
            thousands: ".",
            infoEmpty: "",
            lengthMenu: "Hiển thị _MENU_ dòng/ trang",
            loadingRecords: "Đang tải...",
            processing: "Đang xử lý...",
            paginate: {
                first: "Trang đầu",
                last: "Trang cuối",
                next: "Trước",
                previous: "Sau"
            }
        }
    });


})