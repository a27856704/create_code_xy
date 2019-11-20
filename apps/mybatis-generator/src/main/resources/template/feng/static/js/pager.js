$(function () {
    /**
     * 分页
     * @type {Number}
     * @private
     */
    var _totalPage = parseInt($('#pager').attr('data-total-page'));
    var _currentPage = parseInt($('#pager').attr('data-current-page'));
    if(_currentPage<=0)
        _currentPage=1;
    var _innerStr = '';
    if (_currentPage > 1) {
        _innerStr = '<a href="javascript:;" data-page="prev">上一页</a>';
    }
    if (_totalPage < 8) {
        for (var i = 1; i <= _totalPage; i++) {
            if (_currentPage == i)
                _innerStr += '<a href="javascript:;" data-page="cur" class="sel">' + i + '</a>';
            else
                _innerStr += '<a href="javascript:;" data-page="' + i + '">' + i + '</a>';
        }
    } else {

        if (_currentPage <= 3) {
            for (var j = 1; j <= 5; j++) {
                if (_currentPage == j)
                    _innerStr += '<a href="javascript:;" data-page="cur" class="sel">' + j + '</a>';
                else
                    _innerStr += '<a href="javascript:;" data-page="' + j + '">' + j + '</a>';
            }
            _innerStr += '<span class="more">...</span>';
            for (var k = _totalPage - 1; k <= _totalPage; k++) {
                _innerStr += '<a href="javascript:;" data-page="' + k + '" >' + k + '</a>';
            }
        } else if (_currentPage >= _totalPage - 2) {
            _innerStr += '<a href="javascript:;" data-page="1">' + 1 + '</a><a href="javascript:;" data-page="2">' + 2 + '</a><span>...</span>';
            for (var m = _totalPage - 3; m <= _totalPage; m++) {
                if (_currentPage == m)
                    _innerStr += '<a href="javascript:;" data-page="cur" class="sel">' + m + '</a>';
                else
                    _innerStr += '<a href="javascript:;" data-page="' + m + '" >' + m + '</a>';
            }
        } else {
            _innerStr += '<a href="javascript:;" data-page="1">' + 1 + '</a><span>...</span>';
            for (var n = _currentPage - 2; n <= _currentPage + 2; n++) {
                if (_currentPage == n)
                    _innerStr += '<a href="javascript:;" data-page="cur" class="sel">' + n + '</a>';
                else
                    _innerStr += '<a href="javascript:;"  data-page="' + n + '" >' + n + '</a>';
            }
            _innerStr += '<span>...</span><a href="javascript:;" data-page="' + _totalPage + '">' + _totalPage + '</a>';
        }
    }
    if (_currentPage < _totalPage) {
        _innerStr += '<a href="javascript:;" data-page="next">下一页</a><input type="text" class="jump_page_num" value="' + _currentPage + '"> <button type="button" class="jump_btn">跳转</button>';
    }
    var innerHtml = '<ul class="pager align_center"><li>' + _innerStr + '</li></ul>';
    $('#pager').html(innerHtml);
    $('#pager').on('click', 'button.jump_btn', function () {
        var pagerNum = $('.jump_page_num').val();
        if (pagerNum > _totalPage) {
            pagerNum = _totalPage
        }
        $('#searchForm input[name="pageNumber"]').val(pagerNum);
        $('#searchForm').submit();
    })
    $('#pager').on('click', 'a', function () {
        var _pageData = $(this).attr('data-page');
        if (_pageData == 'cur') return;
        if (_pageData == 'prev' && _currentPage > 1) {
            _pageData = _currentPage - 1;
        }
        if (_pageData == 'next' && _currentPage < _totalPage) {
            _pageData = _currentPage < 1 ? 2 : _currentPage + 1;
        }

        $('#searchForm input[name="pageNumber"]').val(_pageData);
        $('#searchForm').submit();
    })
})