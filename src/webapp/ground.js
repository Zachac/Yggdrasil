
ground = {};
ground.tiles = {};
ground.unusedTiles = [];
ground.tileLookup = {};

ground.removeTile = function(t) {
	ground.tiles[t.id] = null;
	ground.unusedTiles.push(t.id);
	
	let ys = ground.tileLookup[t.position.x];
	
	if (ys) {
		let zs = ys[t.position.y];
		if (zs) {
			zs[t.position.z] = null;			
		}
	}
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
		t = ground.newTile(tile.id, tile.type, tile.position.x, tile.position.y, tile.position.z);
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
		result = BABYLON.Mesh.CreateBox("ground.tiile", 1, model.scene);
		result.setParent(ground.parent);
	}

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
	result.y = -1;
	result.y2= y;  // actual Yggdrasil value
	
	result.contents = [];
	
	result.setEnabled(true);
	
	return result;
}
