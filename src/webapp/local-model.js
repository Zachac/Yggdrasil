model = {
	localPlayer: null,
	scene: null,
	camera: null
}

model.setLocalPlayer = function(userName) {
	model.localPlayer = userName;
	
	if (model.camera.target == null) {
		model.onLoseLocalPlayer()
	}
	
	p = entities[userName];
	
	if (p != null) {
		model.onGainLocalPlayer(p)		
	}
}

model.setScene = function(scene, camera) {
	model.scene = scene;
	model.camera = camera;
}

model.updateTiles = function(tiles) {
	model.checkLocalPlayer(tiles);
	
	let maybeRemove = [];
	
	tiles.forEach((t) => {
		
		if (t.contents) {
			t.contents.forEach((e) => {
				entity.update(e, t);
			})
		}
		
		let maybeRemoveSwap = ground.tileUpdate(t);
		
		if (maybeRemoveSwap.length > 0) {
			maybeRemove = maybeRemove.concat(maybeRemoveSwap);
		}
	})
	
	maybeRemove.forEach((e) => {
		let g = ground.lookup(e.position.x, e.position.y, e.position.z);
		
		if (g) {
			let exists = g.contents.some((en) => {
				return en.id == e.id; 
			})
			
			if (!exists) {
				entity.remove(e);
			}
		} else {
			entity.remove(e);
		}
	});
	
	model.cull();
}

model.checkLocalPlayer = function(tiles) {
	if (model.localPlayer == null) {
		return;
	}
	
	let p = null;
	
	for (let i = 0; i < tiles.length; i++) {
		let tile = tiles[i];
		
		if (tile.contents) {
			
			for (let j = 0; j < tile.contents.length; j++) {
				let e = tile.contents[j];
				if (e.type = "Player" && e.userName == model.localPlayer) {
					p = players[e.userName];
					
					if (p == null) {
						p = entity.newEntity(e);
						entity.update(e, tile);
					}
					
					if (model.camera.target == null) {
						model.onGainLocalPlayer(p);
					}
				}
			}
		}
	}
}

model.cull = function() {
	if (model.position == null) {
		
	} else {
		Object.keys(ground.tiles).forEach(function(key) {
			let t = ground.tiles[key];
			if (t.y2 != model.position.y) {
				ground.removeTile(t);
			} else if (Math.abs(t.position.x-model.position.x) > 15) {
				ground.removeTile(t);
			} else if (Math.abs(t.position.z-model.position.z) > 15) {
				ground.removeTile(t);
			}
		});
	}
}

model.onGainLocalPlayer = function(p) {
	model.position = p.position;
	model.camera.target = p.mesh;
}

model.onLoseLocalPlayer = function() {
	model.position = null;
	model.camera.target = null;
}