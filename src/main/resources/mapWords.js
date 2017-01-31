function () {
    var regex = /(\w|\s)*\w(?=")|\w+/g;
    var res = this.text.match(regex);
    for (var i = 0; i < res.length; i++) {
        emit(res[i].toLowerCase(), 1);
    }
}