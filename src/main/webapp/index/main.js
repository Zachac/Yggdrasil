/**
 * A script to initialize the Yggdrasil web client.
 */
window.onload = function () {
	let display = document.getElementById("display");
	let input = document.getElementById("input");

	output = new OutputHandler(display);
	netHandler = new NetHandler(output);
	websocket = new YggdrasilWebSocket(output, netHandler);
	inputHandler = new InputHandler(input, websocket);
}
