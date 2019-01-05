material = {}

material.init = function () {
    material.GRASS = material.getColor("grass", 0, 1, 0);
    material.SAND = material.getColor("sand", 1, 1, 0);
    material.STONE = material.getColor("stone", 0.3, 0.3, 0.3);
    material.WATER = material.getColor("water", 0, 0, 1);
    material.NONE = null;
}

material.getColor = function (name, r, g, b) {
    var myMaterial = new BABYLON.StandardMaterial(name, render.scene);
    myMaterial.diffuseColor = new BABYLON.Color3(r, g, b);
    return myMaterial;
}

main.onload.push(material.init);