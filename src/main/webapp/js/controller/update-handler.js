/**
 * TODO
 */
var UpdateHandler = function (canvas) {
    this._canvas = canvas;
    this._tiles = [];
    console.log(this._canvas);
}

UpdateHandler.prototype = {
    handle: function (update) {
        if (update.chunk) {
            let newCanvas = this._canvas.cloneNode(false);
            this._canvas = this._canvas.parentElement.replaceChild(newCanvas, this._canvas);
            this._canvas = newCanvas;
            this._tiles.length = 0;

            let rows = this._tiles;
            let canvas = this._canvas;

            update.chunk.tiles.forEach(function (e) {
                let row = [];

                e.forEach(function (biome) {
                    let tile = Elements.createTile();
                    tile.appendChild(Elements.createImage(Materials[biome]), tile);
                    canvas.appendChild(tile);
                    row.push(tile);
                })

                canvas.appendChild(Elements.br());

                rows.push(row);
            })
        }
    }
}