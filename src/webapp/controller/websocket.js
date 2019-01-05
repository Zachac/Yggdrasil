websocket = {
	messages: [],
	messagesSwap: [],
};

websocket.init = function () {
	websocket.startSocket();
}

websocket.send = function (value) {
	if (websocket.ws.readyState != websocket.ws.CLOSED && websocket.ws.readyState != websocket.ws.CLOSING) {
		websocket.ws.send(value);
	}
}

websocket.startSocket = function () {
	if ("WebSocket" in window) {
		websocket.ws = new WebSocket("ws://" + window.location.host + "/connect");

		websocket.ws.onopen = function () { };

		websocket.ws.onmessage = function (evt) {
			output.println(evt.data);
		};

		websocket.ws.onclose = function () {
			output.println("Connection is closed.");
			render.closeScene();
		};

	} else {
		output.println("WARNING: WebSocket is not supported by your Browser!");
	}
}

main.onload.push(websocket.init);