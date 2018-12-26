model = {
	localPlayer: null,
}

model.setLocalPlayer = function(p) {
	if (p != null) {
		model.position = p.position;
		render.camera.target = p.mesh;	
	} else {
		model.position = null;
		render.camera.target = null;
	}
}

model.cullTiles = function() {
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

model.cullEntity = function(e) {
	let g = ground.lookup(e.position.x, e.position.y, e.position.z);
	
	if (g) {
		let found = g.contents.some((en) => { return en.id == e.id; })
		
		if (!found) {
			entity.remove(e);
		}
	} else {
		entity.remove(e);
	}
}
