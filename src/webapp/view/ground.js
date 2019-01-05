
ground = {};
ground.tiles = {};
ground.unusedTiles = [];
ground.tileLookup = {};

ground.tileUpdate = function (tile) {
	t = ground.tiles[tile.id];
	if (t == null) {
		t = ground.newTile(tile.id);
	}

	ground.updatePosition(t, tile.position.x, tile.position.y, tile.position.z);
	ground.updateBiome(t, tile.biome);
	return ground.updateContents(tile, t);
}

ground.updatePosition = function (tile, x, y, z) {
	if (tile.position.x != x || tile.y2 != y || tile.position.z != z) {
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
		tile.y2 = y; // actual Yggdrasil value
	}
}

ground.updateBiome = function (tile, type) {
	tile.material = material[type];
}

ground.updateContents = function (tile, t) {
	let lostContents = t.contents;

	if (tile.contents) {
		t.contents = [];
		tile.contents.forEach((e) => {
			t.contents.push(entity.update(e, tile));
		})
	} else if (t.contents.length > 0) {
		t.contents = [];
	}

	if (lostContents.length > 0) {
		lostContents.forEach((e, i) => {
			let contains = t.contents.some((en) => {
				return en.id == e.id;
			});

			if (contains) {
				lostContents.splice(i, 1);
			}
		});
	}

	return lostContents;
}

ground.newTile = function (id) {
	let result = null;

	if (ground.unusedTiles.length > 0) {
		result = ground.unusedTiles.pop()
	} else {
		result = BABYLON.MeshBuilder.CreatePlane("tile", { size: 1 }, render.scene);
		result.setParent(ground.parent);
	}

	result.rotation.x = Math.PI / 2;
	result.id = id;
	ground.tiles[id] = result;
	result.contents = [];
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

	t.contents.forEach((e) => {
		entity.remove(e);
	})

	t.setEnabled(false);
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