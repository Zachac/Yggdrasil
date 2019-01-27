model = {
	localPlayer: null,
	position: { x: 0, y: 0, z: 0 }
}

model.setLocalPlayer = function (p) {
	if (typeof p == "string") {
		model.localPlayer = p;
	} else if (p) {
		model.localPlayer = p.userName;
		model.position = p.position;
		render.camera.target = p.mesh;
		render.lightPosition.parent = p.mesh;
	} else {
		model.position = null;
		render.camera.target = null;
		render.lightPosition.parent = null;
	}
}

model.cullTiles = function () {
	// done automatically by the rolling coordinate-map
}

model.cullEntities = function () {
	if (model.position) {
		Object.keys(entities).forEach(function (key) {
			let e = entities[key];

			if (model.shouldCull(e.position.x, e.position.y, e.position.z)) {
				entity.remove(e);
			}
		});
	}
}

model.shouldCull = function (x, y, z) {
	if (y != model.position.y) {
		return true;
	} else if (Math.abs(x - model.position.x) > 15) {
		return true;
	} else if (Math.abs(z - model.position.z) > 15) {
		return true;
	}

	return false;
}