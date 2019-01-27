material = {}

material.init = function () {
    material.loadMaterials();
    material.loadMultiMat();
}

material.loadMaterials = function () {
    material.mats = [
        "none", "grass", "sand", "stone", "water"
    ];

    for (let i = 0; i < material.mats.length; i++) {
        let capitalName = material.mats[i].toUpperCase();
        material[capitalName] = material.getMaterial(material.mats[i]);
        material[capitalName].index = i;
    }
}

material.loadMultiMat = function () {
    material.multimat = new BABYLON.MultiMaterial("multimat", render.scene);

    for (let i = 0; i < material.mats.length; i++) {
        material.multimat.subMaterials.push(material[material.mats[i].toUpperCase()]);
    }
}

material.getColor = function (name, r, g, b) {
    let myMaterial = new BABYLON.StandardMaterial(name, render.scene);
    myMaterial.diffuseColor = new BABYLON.Color3(r, g, b);
    return myMaterial;
}

material.getMaterial = function (name) {
    return material.getTexture(name, `materials/${name}.png`);
}

material.getTexture = function (name, path) {
    let myMaterial = new BABYLON.StandardMaterial(name, render.scene);
    let myTexture = new BABYLON.Texture(path, render.scene);
    myMaterial.diffuseTexture = myTexture;
    return myMaterial;
}

main.onload.push(material.init);