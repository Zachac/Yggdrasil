updateHandler = {}

updateHandler.updateEntities = function (ents) {
	let actualEntities = [];

	ents.forEach(function (e) {

		if (e.biome) {
			ground.tileUpdate(e);
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
}

updateHandler.updateLocalPlayer = function (userName) {
	let e = player.get(userName);

	model.setLocalPlayer(e ? e : userName);
}