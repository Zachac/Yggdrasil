/**
 * TODO
 */
var UpdateHandler = function (canvas) {
    this._canvas = canvas;
    this._tiles = [];
}

UpdateHandler.prototype = {
    handle: function (update) {
        if (update.chunk) {
            this._chunkUpdate(update.chunk);
        }
    },

    _chunkUpdate: function (chunk) {
        this._removeChunk();

        for (let i = 0; i < chunk.tiles.length; i++) {
            let row = [];

            for (let j = 0; j < chunk.tiles[i].length; j++) {
                let tile = Elements.createTile();
                tile.appendChild(Elements.createImage(Materials[chunk.tiles[i][j]]), tile);
                this._canvas.appendChild(tile);
                row.push(tile);
            }

            this._canvas.appendChild(Elements.br());
            this._tiles.push(row);
        }
    },

    _removeChunk: function () {
        let newCanvas = this._canvas.cloneNode(false);
        this._canvas = this._canvas.parentElement.replaceChild(newCanvas, this._canvas);
        this._canvas = newCanvas;
        this._tiles.length = 0;
    }
}