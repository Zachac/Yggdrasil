var Elements = {
    tileSizeN: 2,
    tileSizeU: "rem",
    tileSize: "2rem",
    createImage: function (source, zIndex) {
        let result = document.createElement("img");
        result.setAttribute("src", source);
        result.style.height = this.tileSize;
        result.style.width = this.tileSize;
        result.style.position = "absolute";
        result.style.zIndex = zIndex;

        return result;
    },

    createTile: function () {
        let result = document.createElement("span");
        result.style.display = "inline-block";
        result.style.height = this.tileSize;
        result.style.width = this.tileSize;

        return result;
    },

    br: function () {
        return document.createElement("br");
    }


}