const inputEl = document.getElementById('cvc');
const payMM = document.getElementById('payMM');
const payYY = document.getElementById('payYY');
const card_holder = document.getElementById('card-holder');
const goodKey = '0123456789';
const holderRegex = /^(\w+ )+(\w+)$/;
const cardN = document.getElementById('card-number');

const checkInputTel = function (e) {
    const key = (typeof e.which == "number") ? e.which : e.keyCode;
    const start = this.selectionStart,
        end = this.selectionEnd;
    const filtered = this.value.split('').filter(filterInput);
    this.value = filtered.join("");

    const move = (filterInput(String.fromCharCode(key)) || (key == 0 || key == 8)) ? 0 : 1;
    this.setSelectionRange(start - move, end - move);
};

const checkHolder = function (e) {
    if (!this.value.match(holderRegex)) {
        this.value = '';
    }
}

const checkDate = function (e) {
    if (payMM.value > 12) {
        payMM.value = ''
        return;
    }
    if (payYY.value < new Date().getFullYear().toString().substring(2, 4)) {
        payYY.value = ''
        return;
    }
    const year = (20 + payYY.value)
    const month = payMM.value - 1;
    const date_1 = new Date(year, month);
    const currentDate = new Date();
    if (currentDate > date_1) {
        payMM.value = ''
        payYY.value = ''
    }
}

const filterInput = function (val) {
    return (goodKey.indexOf(val) > -1);
}

cardN.addEventListener('input', checkInputTel)
inputEl.addEventListener('input', checkInputTel);
payMM.addEventListener('input', checkInputTel);
payYY.addEventListener('input', checkInputTel);
payYY.addEventListener('focusout', checkDate);
payMM.addEventListener('focusout', checkDate);
card_holder.addEventListener('focusout', checkHolder);
