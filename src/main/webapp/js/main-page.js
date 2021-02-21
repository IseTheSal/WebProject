function autocomplete(inp, arr, idArr) {
    var currentFocus;
    inp.addEventListener("input", function (e) {
        var a, b, i, val = this.value;
        var c = this.value;
        closeAllLists();
        if (!val) {
            return false;
        }
        currentFocus = -1;
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        this.parentNode.appendChild(a);
        for (i = 0; i < arr.length; i++) {
            if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                b = document.createElement("DIV");
                b.innerHTML = "<strong class='neon-title-white'>" + arr[i].substr(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
                b.innerHTML += "<input type='hidden' id='" + idArr[i] + "' value='" + arr[i] + "'>";
                b.addEventListener("click", function (e) {
                    inp.value = this.getElementsByTagName("input")[0].value;
                    document.getElementById("gameIdClass").value=this.getElementsByTagName("input")[0].id;
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });

    inp.addEventListener("keydown", function (e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            currentFocus++;
            addActive(x);
        } else if (e.keyCode == 38) {
            currentFocus--;
            addActive(x);
        } else if (e.keyCode == 13) {
            e.preventDefault();
            if (currentFocus > -1) {
                if (x) x[currentFocus].click();
            }
        }
    });

    function addActive(x) {
        if (!x) return false;
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        x[currentFocus].classList.add("autocomplete-active");
    }

    function removeActive(x) {
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }

    function closeAllLists(elmnt) {
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }

    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}

var col = $('.card-title');
var gameNames = [];
var col1 = $('.custom-card');
var gameId = [];
for (i = 0; i < col.length; i++) {
    gameNames.push(col[i].innerHTML);
    gameId.push(col1[i].href.slice(-1));
}
autocomplete(document.getElementById("myInput"), gameNames, gameId);

//PAGINATION

$(document).ready(function () {
    var colShown = 3;
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