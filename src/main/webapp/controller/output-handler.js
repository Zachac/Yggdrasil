output = {};

output.init = function () {
    output.loadDisplay();
}

output.loadDisplay = function () {
    output.display = document.getElementById("display");
}

output.displayElement = function (value) {
    output.display.innerHTML += value + "<br>";
    output.display.scrollTop = display.scrollHeight
}

output.println = function (value) {
    if (value.indexOf('\n') > -1) {
        value.split('\n').forEach((value) => {
            if (/\S+/.test(value)) output.println(value);
        });
    } else if (value.startsWith('\t')) {
        display.innerHTML += "&nbsp;&nbsp;&nbsp;&nbsp;";
        output.println(value.substr(1));
    } else if (value.startsWith("{")) {
        net.handleUpdate(value);
    } else {
        display.append(value)
        display.innerHTML += "<br>";
        display.scrollTop = display.scrollHeight
    }
}

main.onload.push(output.init);