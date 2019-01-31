/**
 * OutputHandler class to handle displaying textual information.
 * display - the div element to write to.
 */
var OutputHandler = function (display, net) {
    this._display = display;
}

OutputHandler.prototype = {
    displayElement: function (value) {
        this._display.innerHTML += value + "<br>";
        this._display.scrollTop = display.scrollHeight
    },

    println: function (value) {
        if (value.startsWith('\t')) {
            display.innerHTML += "&nbsp;&nbsp;&nbsp;&nbsp;";
            this.println(value.substr(1));
        } else {
            display.append(value)
            display.innerHTML += "<br>";
            display.scrollTop = display.scrollHeight
        }
    }
}
