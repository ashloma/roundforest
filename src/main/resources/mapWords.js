function () {
    var regex = /\b[^\d\W]+\b/g;
    var res = this.text.match(regex);
    if (res == null) {
        return;
    }
    for (var i = 0; i < res.length; i++) {
        emit(res[i].toLowerCase(), 1);
    }
}