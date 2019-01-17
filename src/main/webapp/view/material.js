material = {}

material.init = function () {
    material.GRASS = material.getTexture("grass", "materials/grass.png");
    material.SAND = material.getColor("sand", 1, 1, 0);
    material.STONE = material.getColor("stone", 0.3, 0.3, 0.3);
    material.WATER = material.getColor("water", 0, 0, 1);
    material.NONE = null;
}

material.getColor = function (name, r, g, b) {
    let myMaterial = new BABYLON.StandardMaterial(name, render.scene);
    myMaterial.diffuseColor = new BABYLON.Color3(r, g, b);
    return myMaterial;
}

material.getTexture = function (name, path) {
    let myMaterial = new BABYLON.StandardMaterial(name, render.scene);
    let myTexture = new BABYLON.Texture("materials/grass.png", render.scene);
    myMaterial.diffuseTexture = myTexture;
    return myMaterial;
}

main.onload.push(material.init);