var Elements = {
    tileSizeN: 2,
    tileSizeU: "rem",
    tileSize: "2rem",
    createImage: function (source) {
        let result = document.createElement("img");
        result.setAttribute("src", source);
        result.setAttribute("draggable", "false");
        result.classList.add('tile');
        result.classList.add('tile-image');
        return result;
    },

    createTile: function () {
        let result = document.createElement("span");
        result.classList.add('tile');
        return result;
    },

    br: function () {
        return document.createElement("br");
    }


}