material = {}

material.init = function () {
    material.GRASS = material.getTexture("grass", "materials/grass.png");
    material.SAND = material.getTexture("sand", "materials/sand.png");
    material.STONE = material.getTexture("stone", "materials/stone.png");
    material.WATER = material.getTexture("water", "materials/water.png");
    material.NONE = null;
}

material.getColor = function (name, r, g, b) {
    let myMaterial = new BABYLON.StandardMaterial(name, render.scene);
    myMaterial.diffuseColor = new BABYLON.Color3(r, g, b);
    return myMaterial;
}

material.getTexture = function (name, path) {
    let myMaterial = new BABYLON.StandardMaterial(name, render.scene);
    let myTexture = new BABYLON.Texture(path, render.scene);
    myMaterial.diffuseTexture = myTexture;
    return myMaterial;
}

main.onload.push(material.init);