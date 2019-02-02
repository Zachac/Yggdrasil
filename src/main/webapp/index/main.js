/**
 * A script to initialize the Yggdrasil web client.
 */
window.onload = function () {
	let display = document.getElementById("display");
	let input = document.getElementById("input");
	let canvas = document.getElementById("canvas");

	output = new OutputHandler(display);
	updateHandler = new UpdateHandler(canvas);
	netHandler = new NetHandler(output, updateHandler);
	websocket = new YggdrasilWebSocket(output, netHandler);
	inputHandler = new InputHandler(input, websocket);
}
