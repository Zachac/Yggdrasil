var Elements = {
    tileSize: "20rem",
    createImage: function (source, tile = { elements: [] }) {
        let result = document.createElement("img");
        result.setAttribute("src", source);
        result.setAttribute("height", this.tileSize);
        result.setAttribute("width", this.tileSize);
        result.setAttribute("position", "absolute");
        result.setAttribute("z-index", tile.elements.length);

        tile.elements.push(result);

        return result;
    },

    createTile: function () {
        let result = document.createElement("span");
        result.setAttribute("display", "inline-block");
        result.setAttribute("height", this.tileSize);
        result.setAttribute("width", this.tileSize);

        result.elements = [];

        return result;
    },

    br: function () {
        return document.createElement("br");
    }


}