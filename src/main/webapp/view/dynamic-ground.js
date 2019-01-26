dynamicGround = {}

dynamicGround.init = function () {
    let terrainSub = 29;
    let params = {
        terrainSub: terrainSub
    }

    dynamicGround.terrainOffsetX = (terrainSub / 2) - 0.5;
    dynamicGround.terrainOffsetZ = (terrainSub / 2) + 0.5;
    dynamicGround.vertexOffset = Math.ceil(terrainSub / 2);


    dynamicGround.terrain = new BABYLON.DynamicTerrain("t", params, render.scene);

    dynamicGround.terrain.subToleranceX = terrainSub;
    dynamicGround.terrain.subToleranceZ = terrainSub;

    dynamicGround.terrain.useCustomVertexFunction = true;
    dynamicGround.terrain.updateVertex = function (vertex, i, j) {
        // vertex.position.y = Math.sin((model.position.x + i) / 5) * Math.sin((model.position.z + j) / 5);

        let tile = ground.lookup(
            model.position.x + i - dynamicGround.vertexOffset,
            model.position.y,
            model.position.z + j - dynamicGround.vertexOffset
        );

        if (tile) {
            vertex.position.y = tile.position.y + tile.corners[0];
        } else {
            console.log(i, j);
        }
    };

    render.staticShadows.addShadowCaster(dynamicGround.terrain.mesh);
    dynamicGround.terrain.mesh.receiveShadows = true
    // dynamicGround.terrain.mesh.parent = render.lightPosition;
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