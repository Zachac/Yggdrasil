ground = {
	terrainSub: 29,
	terrainRetain: 2
}

ground.init = function () {
	ground.createTerrain();
	ground._map = new CoordinateMap(
		ground.terrainSub + ground.terrainRetain * 2);
	ground._y = 0;
}

ground.createTerrain = function () {
	ground.terrainOffsetX = (ground.terrainSub / 2) - 0.5;
	ground.terrainOffsetZ = (ground.terrainSub / 2) + 0.5;
	ground.vertexOffset = Math.ceil(ground.terrainSub / 2);
	ground.terrain = new BABYLON.DynamicTerrain("terrain", { terrainSub: ground.terrainSub }, render.scene);
	ground.terrain.subToleranceX = ground.terrainSub;
	ground.terrain.subToleranceZ = ground.terrainSub;
	ground.terrain.mesh.receiveShadows = true
	ground.terrain.useCustomVertexFunction = true;
	ground.terrain.updateVertex = ground.vertexUpdate;
	ground.terrain.mesh.material = material.multimat;
	render.staticShadows.addShadowCaster(ground.terrain.mesh);
}

ground.vertexUpdate = function (vertex, i, j) {
	let x = model.position.x + i - ground.vertexOffset;
	let z = model.position.z + j - ground.vertexOffset;
	let tile = ground.lookup(x, model.position.y, z);

	if (tile) {
		vertex.position.y = tile.w + tile.corners[0];
	} else {
		console.log("missing tile", x, z);
	}
}

ground.update = function () {
	ground.beforeUpdate();
	ground.terrain.update(true);
	ground.afterUpdate();
}

ground.afterUpdate = function () {
	ground.terrain.mesh.position.x = model.position.x - ground.terrainOffsetX;
	ground.terrain.mesh.position.z = model.position.z - ground.terrainOffsetZ;
	render.refreshStaticShadows();
}

ground.beforeUpdate = function () { }

ground.tileUpdate = function (tile) {
	if (tile.position.y != ground._y) {
		ground._map.clear();
		ground._y = tile.position.y;
	}

	ground._map.put(tile.position.x, tile.position.z, {
		corners: tile.corners,
		w: tile.position.w
	});
}

ground.lookup = function (x, y, z) {
	if (y != ground._y) {
		throw "y level not supported"
	}

	return ground._map.get(x, z);
}

ground.averageHeight = function (x, y, z) {
	let t = ground.lookup(x, y, z);

	let maxCorner = t.corners[0];
	let minCorner = t.corners[0];

	for (let i = 1; i < t.corners.length; i++) {
		if (t.corners[i] < minCorner) {
			minCorner = t.corners[i];
		} else if (t.corners[i] > maxCorner) {
			maxCorner = t.corners[i];
		}
	}

	return t.w + (maxCorner + minCorner) / 2;
}

main.onload.push(ground.init);