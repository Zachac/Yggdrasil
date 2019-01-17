
ground = {};
ground.tiles = {};
ground.unusedTiles = [];
ground.tileLookup = {};

ground.init = function () {
	ground.createVertexData();
	ground.parent = new BABYLON.TransformNode("ground parent");
}

ground.createVertexData = function () {
	//Set arrays for positions and indices
	let positions = [
		0.5, -0.5, 0,
		0.5, 0.5, 0,
		-0.5, 0.5, 0,
		-0.5, -0.5, 0,];

	let indices = [
		0, 1, 2,
		0, 2, 3
	];

	let uvs = [
		0, 1, // top left
		1, 1, // top right
		0, 0, // bottom left
		1, 0, // bottom right
	];

	let normals = [];

	ground.vertexData = new BABYLON.VertexData();
	BABYLON.VertexData.ComputeNormals(positions, indices, normals);

	//Assign positions, indices and normals to vertexData
	ground.vertexData.positions = positions;
	ground.vertexData.indices = indices;
	ground.vertexData.normals = normals;
	ground.vertexData.uvs = uvs;
}

ground.updateVertexData = function (corners) {
	ground.vertexData.positions[2] = - corners[0];
	ground.vertexData.positions[5] = - corners[1];
	ground.vertexData.positions[8] = - corners[2];
	ground.vertexData.positions[11] = - corners[3];
}

ground.tileUpdate = function (tile) {
	t = ground.tiles[tile.id];
	if (t == null) {
		t = ground.newTile(tile.id);
	}

	ground.updateCorners(t, tile.corners);
	ground.updatePosition(t, tile.position.x, tile.position.y, tile.position.z, tile.position.w);
	ground.updateBiome(t, tile.biome);
}

ground.updateCorners = function (t, corners) {
	t.corners = corners;
	ground.updateVertexData(corners)
	ground.vertexData.applyToMesh(t);
}

ground.updatePosition = function (tile, x, y, z, w) {
	if (tile.position.x != x || tile.y2 != y || tile.position.z != z || tile.position.y != w) {
		let ys = ground.tileLookup[x];
		if (!ys) {
			ys = {};
			ground.tileLookup[x] = ys;
		}

		let zs = ys[y];
		if (!zs) {
			zs = {};
			ys[y] = zs;
		}

		zs[z] = tile;
		tile.position.x = x;
		tile.position.z = z;
		tile.position.y = w;
		tile.y2 = y; // actual Yggdrasil value
	}
}

ground.updateBiome = function (tile, type) {
	tile.material = material[type];
}

ground.newTile = function (id) {
	let result = null;

	if (ground.unusedTiles.length > 0) {
		result = ground.unusedTiles.pop()
		result.position.x = null;
		result.position.y = null;
		result.position.z = null;
		result.position.y2 = null;
	} else {
		result = new BABYLON.Mesh("tile", render.scene);
		result.setParent(ground.parent);
		result.isGround = true;
		result.receiveShadows = true;
		render.shadows.addShadowCaster(result);
	}

	result.rotation.x = Math.PI / 2;
	result.id = id;
	ground.tiles[id] = result;
	result.setEnabled(true);

	return result;
}

ground.removeTile = function (t) {
	delete ground.tiles[t.id];
	ground.unusedTiles.push(t);

	let ys = ground.tileLookup[t.position.x];

	if (ys) {
		let zs = ys[t.position.y];
		if (zs) {
			delete zs[t.position.z];
		}
	}

	t.setEnabled(false);
}

ground.averageHeight = function (x, y, z) {
	let t = ground.lookup(x, y, z);

	let maxCorner = t.corners[0];
	let minCorner = t.corners[0];

	for (let i = 1; i < t.corners.length; i++) {
		if (t.corners[i] < minCorner) {
			minCorner = t.corners[i];
		}

		if (t.corners[i] > maxCorner) {
			maxCorner = t.corners[i];
		}
	}

	return t.position.y + (maxCorner + minCorner) / 2;
}

ground.lookup = function (x, y, z) {
	let ys = ground.tileLookup[x];
	if (ys) {
		let zs = ys[y];
		if (zs) {
			return zs[z];
		}
	}

	return null;
}

main.onload.push(ground.init);