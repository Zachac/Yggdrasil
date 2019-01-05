websocket = {
	messages: [],
	messagesSwap: [],
};

websocket.init = function () {
	websocket.loadElements();
	websocket.addInputListener();
	websocket.startSocket();
}

websocket.loadElements = function () {
	websocket.input = document.getElementById("input");
	websocket.display = document.getElementById("display");
}

websocket.displayElement = function (value) {
	websocket.display.innerHTML += value + "<br>";
	websocket.display.scrollTop = display.scrollHeight
}

websocket.println = function (value) {
	if (value.indexOf('\n') > -1) {
		value.split('\n').forEach((value) => {
			if (/\S+/.test(value)) websocket.println(value);
		});
	} else if (value.startsWith('\t')) {
		display.innerHTML += "&nbsp;&nbsp;&nbsp;&nbsp;";
		websocket.println(value.substr(1));
	} else if (value.startsWith("{")) {
		net.handleUpdate(value);
	} else {
		display.append(value)
		display.innerHTML += "<br>";
		display.scrollTop = display.scrollHeight
	}
}

websocket.startSocket = function () {
	if ("WebSocket" in window) {
		websocket.ws = new WebSocket("ws://" + window.location.host + "/connect");

		websocket.ws.onopen = function () { };

		websocket.ws.onmessage = function (evt) {
			websocket.println(evt.data);
		};

		websocket.ws.onclose = function () {
			websocket.println("Connection is closed.");
			render.closeScene();
		};

	} else {
		println("WARNING: WebSocket is not supported by your Browser!");
	}
}

websocket.addInputListener = function () {
	websocket.input.addEventListener("keyup", function (event) {
		if (event.key === "Enter") {
			if (websocket.ws.readyState != websocket.ws.CLOSED && websocket.ws.readyState != websocket.ws.CLOSING) {
				websocket.ws.send(input.value);
			}

			websocket.displayElement(websocket.input.value.fontcolor("marroon"));
			websocket.messages.push(websocket.input.value);
			websocket.input.value = "";
		}

		if (event.key === "ArrowUp") {
			websocket.messagesSwap.push(websocket.input.value);
			websocket.input.value = messages.pop();
		}

		if (event.key === "ArrowDown") {
			if (messagesSwap.length > 0) {
				websocket.messages.push(websocket.input.value);
				websocket.input.value = messagesSwap.pop();
			}
		}
	});
}

main.onload.push(websocket.init);