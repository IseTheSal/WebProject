var col = $('.gameTitleText');
var gameNames = [];
var col1 = $('.gameIdText');
var gameId = [];
for (i = 0; i < col.length; i++) {
    gameNames.push(col[i].innerHTML);
    gameId.push(col1[i].innerHTML);
}
autocomplete(document.getElementById("adminInputSearch"), gameNames, gameId);



