mesh = {}

mesh.init = function () {
    mesh.load("receiver")
}

mesh.load = function (name) {
    BABYLON.SceneLoader.ImportMesh("", "obj/", name + ".obj", render.scene, function (newMeshes) {
        console.log(newMeshes);
        mesh[name.toUpperCase()] = newMeshes[0];
    });
}

main.onload.push(mesh.init);