
var ClickHandler = function (canvasBack, webSocket, updateHandler) {
    this._canvas = canvasBack;
    this._ws = webSocket;
    this._updateHandler = updateHandler;

    let me = this;
    this._canvas.onclick = function (event) { me._handleClick(event) };
}

ClickHandler.prototype = {
    _handleClick(event) {
        let localEnt = this._updateHandler.getFollowEntity();

        if (localEnt) {
            let rect = localEnt.el.getBoundingClientRect();

            let offsetX = Math.floor((event.clientX - rect.x) / localEnt.el.width);
            let offsetY = Math.ceil((rect.y - event.clientY) / localEnt.el.height);

            let moveCommand = this._getMoveCommand(offsetX, offsetY);

            if (moveCommand) {
                websocket.send(moveCommand);
            }
        }
    },

    _getMoveCommand: function (x, y) {
        let absX = Math.abs(x);
        let absY = Math.abs(y);

        let dirX = x > 0 ? 'E' : 'W';
        let dirY = y > 0 ? 'N' : 'S';

        let result;
        let afterDiagonal = absX - absY;
        let finalDirection = "";

        if (afterDiagonal) {
            finalDirection = Math.abs(afterDiagonal) + (afterDiagonal > 0 ? dirX : dirY);
        }

        let diagonal = Math.min(absX, absY);
        let finalDiagonal = "";

        if (diagonal) {
            finalDiagonal = `${diagonal}${dirY}${dirX} `;
        }

        if (!finalDiagonal && !finalDirection) {
            return false;
        }

        return `go ${finalDiagonal}${finalDirection}`;
    }
}
