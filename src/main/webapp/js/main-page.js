var col = $('.card-title');
var gameNames = [];
var col1 = $('.custom-card');
var gameId = [];
for (i = 0; i < col.length; i++) {
    gameNames.push(col[i].innerHTML);
    gameId.push(col1[i].href.slice(col1[i].href.lastIndexOf('gameId=') + 7));
}
autocomplete(document.getElementById("myInput"), gameNames, gameId);

//PAGINATION
$(document).ready(function () {
    var colShown = 9;
    var colTotal = $('.t1 .col').length;
    var numPages = colTotal / colShown;
    for (i = 0; i < numPages; i++) {
        var pageNum = i + 1;
        $('#nav').append('<a style="color: #f4fffd; border-radius: 20%;text-align: center" class="neon-title-white btn-outline-secondary" rel="' + i + '">&emsp;' + pageNum + '&emsp;</a> ');
    }

    $('.t1 .col').hide();
    $('.t1 .col').slice(0, colShown).show();
    $('#nav a:nth-child(1)').addClass('active');
    updateLinks();
    $('#prev a').bind('click', function (e) {
            $('a.active').removeClass('active').prev().addClass('active');
            var currPage = $('a.active').attr('rel');
            var startItem = currPage * colShown;
            var endItem = startItem + colShown;
            updateLinks();
            $('.t1 .col').css('opacity', '0').hide().slice(startItem, endItem).css('display', 'flex').animate({
                opacity: 1
            }, 300);
        }
    );

    $('#next a').bind('click', function (e) {
        $('a.active').removeClass('active').next().addClass('active');
        var currPage = $('a.active').attr('rel');
        var startItem = currPage * colShown;
        var endItem = startItem + colShown;
        updateLinks();
        $('.t1 .col').css('opacity', '0').hide().slice(startItem, endItem).css('display', 'flex').animate({
            opacity: 1
        }, 300);

    });

    $('#nav a').bind('click', function (e) {
        e.preventDefault();
        $('#nav a').removeClass('active');
        $(this).addClass('active');
        var currPage = $(this).attr('rel');
        var startItem = currPage * colShown;
        var endItem = startItem + colShown;

        updateLinks();

        $('.t1 .col').css('opacity', '0').hide().slice(startItem, endItem).css('display', 'flex').animate({
            opacity: 1
        }, 300);
    });

    function updateLinks() {
        var page = $('a.active').attr('rel');
        var prev = document.getElementById("prev");
        if (page == 0) {
            prev.style.pointerEvents = "none";
        } else {
            prev.style.pointerEvents = "auto";
        }

        var next = document.getElementById("next");
        if (page == Math.ceil(numPages) - 1) {
            next.style.pointerEvents = "none";
        } else {
            next.style.pointerEvents = "auto";
        }
    }
});