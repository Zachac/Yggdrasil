updateHandler = {}

updateHandler.updateEntities = function (ents) {
	ents.forEach(function (e) {
		if (e.biome) {
			ground.tileUpdate(e);
		} else {
			entity.update(e);
		}
	});
}

updateHandler.updateLocalPlayer = function (userName) {
	let e = player.get(userName);

	model.setLocalPlayer(e ? e : userName);
}