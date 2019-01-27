updateHandler = {}

updateHandler.handleUpdate = function (updt) {
	let result;

	if (updt.entities) {
		result = updateHandler.updateEntities(updt.entities);
	}

	if (updt.localPlayer) {
		updateHandler.updateLocalPlayer(updt.localPlayer);
	}

	// TODO refactor tile update logic
	if (result) {
		ground.update();
	}

	model.cullEntities();
	model.cullTiles();
}

updateHandler.updateEntities = function (ents) {
	let actualEntities = [];
	let updatedTiles = false;

	ents.forEach(function (e) {

		if (e.biome) {
			ground.tileUpdate(e);
			updatedTiles = true;
		} else {
			actualEntities.push(e);
		}
	});

	// update entities after we have updated the ground
	actualEntities.forEach(function (e) {
		if (e.position && e.position.w != undefined) {
			e.position.w = ground.averageHeight(e.position.x, e.position.y, e.position.z);
		}

		entity.update(e);
	});

	return updatedTiles;
}

updateHandler.updateLocalPlayer = function (userName) {
	let e = player.get(userName);

	model.setLocalPlayer(e ? e : userName);
}