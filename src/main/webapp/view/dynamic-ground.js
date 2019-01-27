dynamicGround = {
    terrainSub: 29,
    terrainRetain: 2
}

dynamicGround.init = function () {
    dynamicGround.createTerrain();
    dynamicGround._map = new CoordinateMap(
        dynamicGround.terrainSub + dynamicGround.terrainRetain * 2);
    dynamicGround._y = 0;
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
    dynamicGround.terrain.mesh.material = material.multimat;
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

dynamicGround.tileUpdate = function (tile) {
    if (tile.position.y != dynamicGround._y) {
        dynamicGround._map.clear();
        dynamicGround._y = tile.position.y;
    }

    dynamicGround._map.put(tile.position.x, tile.position.z, {
        corners: tile.corners,
        w: tile.position.w
    });
}

dynamicGround.lookup = function (x, y, z) {
    if (y != dynamicGround._y) {
        throw "y level not supported"
    }

    return dynamicGround._map.get(x, z);
}

dynamicGround.averageHeight = function (x, y, z) {
    let t = dynamicGround.lookup(x, y, z);

    let maxCorner = t.corners[0];
    let minCorner = t.corners[0];

    for (let i = 1; i < t.corners.length; i++) {
        if (t.corners[i] < minCorner) {
            minCorner = t.corners[i];
        } else if (t.corners[i] > maxCorner) {
            maxCorner = t.corners[i];
        }
    }

    return w + (maxCorner + minCorner) / 2;
}

main.onload.push(dynamicGround.init);