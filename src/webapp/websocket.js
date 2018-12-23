var ws;
var messages = [];
var messagesSwap = [];

function loadElements() {
	const input = document.getElementById("input");
	const display = document.getElementById("display");
}

function displayElement(value) {
	display.innerHTML += value + "<br>";
	display.scrollTop = display.scrollHeight
}

function println(value) {
	if (value.indexOf('\n') > -1) {
		value.split('\n').forEach((value) => {
			if (/\S+/.test(value)) println(value);
		});
	} else if (value.startsWith('\t')) {
		display.innerHTML += "&nbsp;&nbsp;&nbsp;&nbsp;";
		println(value.substr(1));
	} else {
		display.append(value)
		display.innerHTML += "<br>";
		display.scrollTop = display.scrollHeight
	}
}

function startSocket() {
	if ("WebSocket" in window) {
	    ws = new WebSocket("ws://" + window.location.host + "/connect");
			
	    ws.onopen = function() {
	    	
	    };
			
	    ws.onmessage = function (evt) { 
	    	println(evt.data);
	    };
			
	    ws.onclose = function() {
	    	println("Connection is closed."); 
	    };

	} else {
		println("WARNING: WebSocket is not supported by your Browser!");
	}
}

function addInputListener() {
	input.addEventListener("keyup", function(event) {
	    if (event.key === "Enter") {
	    	if (ws.readyState != ws.CLOSED && ws.readyState != ws.CLOSING) {
	    		ws.send(input.value);	    		
	    	}
	    	
	    	displayElement(input.value.fontcolor("marroon"));
	    	messages.push(input.value);
	    	input.value = "";
	    }

	    if (event.key === "ArrowUp") {
	    	messagesSwap.push(input.value);
	    	input.value = messages.pop();
	    }
	    
	    if (event.key === "ArrowDown") {
	    	if (messagesSwap.length > 0) {
	    		messages.push(input.value);
	    		input.value = messagesSwap.pop();
	    	}
	    }
	});
}

window.onload = function () {
	loadElements();
	addInputListener();
	startSocket();
}


