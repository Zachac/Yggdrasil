/**
 * InputHandler to handle player input through the chatbox.
 * input - The input text field.
 * websocket - The YggdrasilWebSocket instance to send input to.
 */
var InputHandler = function (input, websocket) {
    this._messages = [];
    this._messageCursor = 0;
    this._websocket = websocket;
    this._in = input;

    let me = this;
    this._in.addEventListener("keyup", function (event) { me._inputEventHandler(event) });
}

InputHandler.prototype = {
    _inputEventHandler: function (event) {
        if (event.key === "Enter") {
            this._websocket.send(this._in.value);
            this._messages.push(this._in.value);
            this._in.value = "";
            this._messageCursor = this._messages.length;
        } else if (event.key === "ArrowUp") {
            let newCursor = this._messageCursor - 1;

            if (newCursor >= 0) {
                this._messageCursor = newCursor;
                this._in.value = this._messages[this._messageCursor];
            }
        } else if (event.key === "ArrowDown") {
            let newCursor = this._messageCursor + 1;

            if (newCursor < this._messages.length) {
                this._messageCursor = newCursor;
                this._in.value = this._messages[this._messageCursor];
            } else if (newCursor == this._messages.length) {
                this._messageCursor = newCursor;
                this._in.value = "";
            }
        } else if (event.ctrlKey && event.key === "c" && this._in.selectionEnd == this._in.selectionStart) {
            this._websocket.send("interrupt");
            this._in.value = "";
        }
    }
}
