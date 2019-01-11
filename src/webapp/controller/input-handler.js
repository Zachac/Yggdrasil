input = {
    messages: [],
    messageCursor: 0
};

input.init = function () {
    input.loadInput();
    input.addInputListeners();
}

input.loadInput = function () {
    input.in = document.getElementById("input");
}

input.addInputListeners = function () {
    input.in.addEventListener("keyup", function (event) {
        if (event.key === "Enter") {
            websocket.send(input.in.value);
            input.messages.push(input.in.value);
            input.in.value = "";
            input.messageCursor = input.messages.length;
        }

        if (event.key === "ArrowUp") {
            let newCursor = input.messageCursor - 1;

            if (newCursor >= 0) {
                input.messageCursor = newCursor;
                input.in.value = input.messages[input.messageCursor];
            }
        }

        if (event.key === "ArrowDown") {
            let newCursor = input.messageCursor + 1;

            if (newCursor < input.messages.length) {
                input.messageCursor = newCursor;
                input.in.value = input.messages[input.messageCursor];
            } else if (newCursor == input.messages.length) {
                input.messageCursor = newCursor;
                input.in.value = "";
            }
        }
    });
}

main.onload.push(input.init);