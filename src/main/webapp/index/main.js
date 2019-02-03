/**
 * A script to initialize the Yggdrasil web client.
 */
window.onload = function () {
	Constants.init();

	let display = document.getElementById("display");
	let input = document.getElementById("input");
	let canvas = document.getElementById("canvas");
	let canvasBack = document.getElementById("canvas-back");

	output = new OutputHandler(display);
	updateHandler = new UpdateHandler(canvas);
	netHandler = new NetHandler(output, updateHandler);
	websocket = new YggdrasilWebSocket(output, netHandler);
	clickHandler = new ClickHandler(canvasBack, websocket, updateHandler);
	inputHandler = new InputHandler(input, websocket);
}
