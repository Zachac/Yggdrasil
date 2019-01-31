/**
 * NetHandler handles network updates
 * output - The OutputHandler to display to.
 */
var NetHandler = function (output) {
	this._output = output;
}

NetHandler.prototype = {
	handleUpdate: function (value) {
		value.trim().split(/\n/).forEach(element => {
			this.handleString(element);
		});
	},

	handleString: function (string) {
		if (string.startsWith("{")) {
			this.handleData(string)
		} else {
			this._output.println(string);
		}
	},

	handleData: function (data) {
		let value = JSON.parse(data);
		console.log(value);
		return value;
	}
}