dynamicGround = {
    terrainSub: 29
}

dynamicGround.init = function () {
    dynamicGround.createTerrain();
}

dynamicGround.createTerrain = function () {
    dynamicGround.terrainOffsetX = (dynamicGround.terrainSub / 2) - 0.5;
    dynamicGround.terrainOffsetZ = (dynamicGround.terrainSub / 2) + 0.5;
    dynamicGround.vertexOffset = Math.ceil(dynamicGround.terrainSub / 2);
    dynamicGround.terrain = new BABYLON.DynamicTerrain("terrain", { terrainSub: dynamicGround.terrainSub }, render.scene);
    dynamicGround.terrain.subToleranceX = dynamicGround.terrainSub;
    dynamicGround.terrain.subToleranceZ = dynamicGround.terrainSub;
    dynamicGround.terrain.mesh.receiveShadows = true
    dynamicGround.terrain.useCustomVertexFunction = true;
    dynamicGround.terrain.updateVertex = dynamicGround.vertexUpdate;
    render.staticShadows.addShadowCaster(dynamicGround.terrain.mesh);
}

dynamicGround.vertexUpdate = function (vertex, i, j) {
    let x = model.position.x + i - dynamicGround.vertexOffset;
    let z = model.position.z + j - dynamicGround.vertexOffset;
    let tile = ground.lookup(x, model.position.y, z);

    if (tile) {
        vertex.position.y = tile.position.y + tile.corners[0];
    } else {
        console.log("missing tile", x, z);
    }
}

dynamicGround.update = function () {
    dynamicGround.beforeUpdate();
    dynamicGround.terrain.update(true);
    dynamicGround.afterUpdate();
}

dynamicGround.afterUpdate = function () {
    dynamicGround.terrain.mesh.position.x = model.position.x - dynamicGround.terrainOffsetX;
    dynamicGround.terrain.mesh.position.z = model.position.z - dynamicGround.terrainOffsetZ;
    render.refreshStaticShadows();
}

dynamicGround.beforeUpdate = function () { }

main.onload.push(dynamicGround.init);