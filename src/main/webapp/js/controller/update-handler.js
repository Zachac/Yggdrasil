/**
 * TODO
 */
var UpdateHandler = function (canvas) {
    this._canvas = canvas;
    this._tiles = [];
    this._entities = {};
}

UpdateHandler.prototype = {
    handle: function (update) {
        if (update.chunk) {
            this._chunkUpdate(update.chunk);
        }

        if (update.entities) {
            this._entitiesUpdate(update.entities);
        }
    },

    _entitiesUpdate: function (entities) {
        for (let i = 0; i < entities.length; i++) {
            this._entityUpdate(entities[i]);
        }
    },

    _entityUpdate: function (entity) {
        let e = this._entities[entity.id];

        if (!e) {
            this._entities[entity.id] = e = this._createEntity(entity.material);
        } else {
            this._tiles[e.x][e.y].removeChild(e.el);
        }

        if (entity.material != e.material) {
            e.material = entity.material;
            e.el.setAttribute("src", Materials[entity.material]);
        }

        if (entity.facing != e.facing) {
            this._faceEntity(e, entity.facing);
        }

        e.x = entity.position.x;
        e.y = entity.position.y;

        this._tiles[e.x][e.y].appendChild(e.el);
    },

    _faceEntity: function (e, facing) {
        e.facing = facing;

        let degrees;

        switch (facing) {
            case "N": degrees = "180"; break;
            case "S": degrees = "0"; break;
            case "E": degrees = "270"; break;
            case "W": degrees = "90"; break;
            case "NE": degrees = "225"; break;
            case "SE": degrees = "315"; break;
            case "SW": degrees = "45"; break;
            case "NW": degrees = "135"; break;
            default: degress = 0;
        }

        e.el.style.transform = `rotate(${degrees}deg)`;

    },

    _createEntity: function (material) {
        return {
            x: null,
            y: null,
            facing: null,
            material: material,
            el: Elements.createImage(Materials[material], 1)
        };
    },

    _chunkUpdate: function (chunk) {
        this._removeChunk();

        for (let i = 0; i < chunk.tiles.length; i++) {
            let row = [];

            for (let j = 0; j < chunk.tiles[i].length; j++) {
                let tile = Elements.createTile();
                tile.appendChild(Elements.createImage(Materials[chunk.tiles[i][j]], 0));
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