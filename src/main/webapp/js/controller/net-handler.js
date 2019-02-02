/**
 * NetHandler handles network updates
 * output - The OutputHandler to display to.
 */
var NetHandler = function (output, updateHandler) {
	this._output = output;
	this._updateHandler = updateHandler;
}

NetHandler.prototype = {
	handleUpdate: function (value) {
		value.trim().split(/\n/).forEach(element => {
			this.handleLine(element);
		});
	},

	handleLine: function (string) {
		if (string.startsWith("{")) {
			this.handleData(string)
		} else {
			this._output.println(string);
		}
	},

	handleData: function (data) {
		this._updateHandler.handle(JSON.parse(data));
	}
}