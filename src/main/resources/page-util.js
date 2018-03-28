var P = P || {};
P.currentPage = 1;
P.firstPage = 1;
P.lastPage = 1;
P.nextPage = 1;
P.prevPage = 1;
P.pageSize = 0;
P.total = 0;
P.pageTotal = 0;
P.pageNumber = 0;
P.init = function (params) {
    //起始记录数，从一开始
    P.initPageNumber(params.pageNumber);
    P.initPageTotal(params.pageTotal);
    P.initPageSize(params.pageSize);
    $(params.root).append('<div id="pageRoot" class="pageRoot"></div>');
}
P.initPageNumber = function (_currentPage) {
    P.currentPage = parseInt(_currentPage <= 0 ? 1 : _currentPage);
    P.pageNumber = parseInt(P.currentPage - 1) * P.pageSize;
}
P.initPageTotal = function (_pageTotal) {
    P.pageTotal = parseInt(_pageTotal) == 2 ? 3 : parseInt(_pageTotal);
}
P.initPageSize = function (_pageSize) {
    P.pageSize = _pageSize > 1 ? parseInt(_pageSize) : 1;
}
P.bind = function (initData) {
    $("#pageRoot a").click(function () {
        var _currentPage = parseInt($(this).attr("data-id"));
        if (!_currentPage || _currentPage == P.currentPage) {
            return;
        }
        P.initPageNumber(_currentPage);
        initData();
    })
}
P.initPage = function (_total) {
    P.total = _total;
    var num = parseInt(P.total / P.pageSize);
    P.lastPage = parseInt(P.total) % parseInt(P.pageSize) == 0 ?
        parseInt(num) : parseInt(num) + 1;
    $('#pageRoot').empty();
    $('#pageRoot').append('<div><a href="javascript:void(0)" data-id="' +
        P.firstPage + '">第一页</a><span id="pages" class="pages"></span><a href="javascript:void(0)" data-id="'
        + P.lastPage + '">最后一页</a></div>');
    //当前页初始值为0
    P.nextPage = parseInt(P.currentPage) == parseInt(P.lastPage) ?
        parseInt(P.currentPage) : parseInt(P.currentPage + 1);
    P.prevPage = parseInt(P.currentPage) == 1 ? parseInt(P.currentPage) : parseInt(P.currentPage - 1);
    $("#pages").empty();
    var step = P.pageTotal % 2 == 0 ? parseInt(P.pageTotal / 2) : parseInt(P.pageTotal / 2) + 1;
    $("#pages").append('<a  href = "javascript:void(0)" data-id="' +
        P.prevPage + '" class="pre" ) ">上一页 </a>');
    for (var i = 1; i <= P.pageTotal; i++) {
        var index = i;
        if (P.lastPage <= P.pageTotal) {
            if (index > P.lastPage) {
                break;
            }
        } else {
            if (P.currentPage <= step) {
                index = i;
            } else if (P.currentPage > P.lastPage - step) {
                index = P.lastPage - P.pageTotal + i;
            } else {
                index = P.currentPage - step + i;
            }
        }
        if (index == P.currentPage) {
            $("#pages").append('<a class="pageActive" data-id="' +
                index + '">' + parseInt(index) + '</a>')
        } else {
            $("#pages").append('<a href = "javascript:void(0)" data-id="' +
                index + '">' + parseInt(index) + '</a>')
        }
    }
    $("#pages").append('<a class="next"  href = "javascript:void(0)" data-id="' +
        P.nextPage + '">下一页</a>');
    var list = $(".pageRoot a");
    for (var i = 0; i < list.length; i++) {
        var a = $(list[i]);
        if (a.attr("data-id") == P.currentPage && !a.hasClass("pageActive")) {
            a.addClass("pageDisabled");
        }
    }
}