mesh = {}

mesh.init = function () {
    mesh.load("receiver")
}

mesh.load = function (name) {
    BABYLON.SceneLoader.ImportMesh("", "obj/", name + ".obj", render.scene, function (newMeshes) {
        let result = mesh[name.toUpperCase()] = newMeshes[0];
        result.setEnabled(false);
    });
}

main.onload.push(mesh.init);