model = {
	localPlayer: null,
}

model.setLocalPlayer = function(userName) {
	model.localPlayer = userName;
	
	if (render.camera.target != null) {
		model.onLoseLocalPlayer()
	}
	
	p = entities[userName];
	
	if (p != null) {
		model.onGainLocalPlayer(p)		
	}
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
					
					if (render.camera.target == null) {
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
	render.camera.target = p.mesh;
}

model.onLoseLocalPlayer = function() {
	model.position = null;
	render.camera.target = null;
}