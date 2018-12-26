
ground = {};
ground.tiles = {};
ground.unusedTiles = [];
ground.tileLookup = {};
ground.colors = {};

ground.initMats = function() {
	ground.colors.GRASS = ground.getMat("grass", 0, 1, 0); 
	ground.colors.SAND = ground.getMat("sand", 1, 1, 0);
	ground.colors.STONE = ground.getMat("stone", 0.3, 0.3, 0.3);
	ground.colors.WATER = ground.getMat("water", 0, 0, 1);
	ground.colors.NONE = null;
}

ground.getMat = function(name, r, g, b) {
	var myMaterial = new BABYLON.StandardMaterial(name, model.scene);
	myMaterial.diffuseColor = new BABYLON.Color3(r, g, b);
	return myMaterial;
}

ground.removeTile = function(t) {
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

ground.lookup = function(x, y, z) {
	let ys = ground.tileLookup[x];
	if (ys) {
		let zs = ys[y];		
		if (zs) {
			return zs[z];
		}		
	}
	
	return null;
}

ground.tileUpdate = function(tile) {
	t = ground.tiles[tile.id];
	
	if (t == null) {
		t = ground.newTile(tile.id, tile.biome, tile.position.x, tile.position.y, tile.position.z);
	}
	
	let lastContents = t.contents;
	
	if (tile.contents) {
		t.contents = [];
		tile.contents.forEach((e) => {
			t.contents.push(entities[e.id]);
		})
	} else if (t.contents.length > 0) {
		t.contents = [];
	}
	
	if (lastContents.length > 0) {
		lastContents.forEach((e, i) => {
			let contains = t.contents.some((en) => {
				return en.id == e.id;
			});
			
			if (contains) {
				lastContents.splice(i, 1);
			}
		});
	}
	
	return lastContents;
}

ground.clearTiles = function() {
	
}

ground.newTile = function(id, type, x, y, z) {
	let result = null;
	
	if (ground.unusedTiles.length > 0) {
		result = ground.unusedTiles.pop()
	} else {
		result = BABYLON.MeshBuilder.CreatePlane("tile", {size: 1}, model.scene);
		result.setParent(ground.parent);
	}
	
	result.rotation.x =  Math.PI / 2;

	result.id = id;
	ground.tiles[id] = result;
	

	
	let ys = ground.tileLookup[x];
	
	if (!ys) {
		ys = {}
		ground.tileLookup[x] = ys;
	}
	
	let zs = ys[y]
	
	if (!zs) {
		zs = {};
		ys[y] = zs;
	}

	zs[z] = result;
	
	result.position.x = x;
	result.position.z = z;
	result.y2= y;  // actual Yggdrasil value
	
	result.contents = [];
	
	result.material = ground.colors[type];
	
	result.setEnabled(true);
	
	return result;
}
