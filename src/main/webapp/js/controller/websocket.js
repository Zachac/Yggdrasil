/**
 * The Yggdrasil Websocket. 
 * output - The OutputHandler to display to.
 * netHandler - The NetHandler to transform system updates.
 */
var YggdrasilWebSocket = function (output, netHandler) {
	this._output = output;
	this._netHandler = netHandler;

	if ("WebSocket" in window) {
		this._ws = new WebSocket("ws://" + window.location.host + "/connect");

		let me = this;
		this._ws.onopen = function () { me._onopen() };
		this._ws.onmessage = function (evt) { me._onmessage(evt) }
		this._ws.onclose = function () { me._onclose() };
	} else {
		this._output.println("ERROR: WebSocket is not supported by your Browser!");
	}
}

YggdrasilWebSocket.prototype = {
	send: function (value) {
		if (this._ws.readyState != this._ws.CLOSED && this._ws.readyState != this._ws.CLOSING) {
			this._ws.send(value);
			this._output.displayElement(value.fontcolor("marroon"));
		}
	},

	_onmessage: function (evt) {
		this._netHandler.handleUpdate(evt.data);
	},

	_onopen: function () {

	},

	_onclose: function () {
		this._output.println("Connection is closed.");
	}
}
