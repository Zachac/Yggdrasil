var Materials = {}

let addMaterial = function (name) {
    Materials[name.toUpperCase()] = `/materials/${name}.png`;
}

addMaterial("grass");
addMaterial("none");
addMaterial("sand");
addMaterial("stone");
addMaterial("water");
addMaterial("missing_texture");
addMaterial("human");
addMaterial("bush");