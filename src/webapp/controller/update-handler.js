updateHandler = {}


updateHandler.updateTiles = function(tiles) {
	let lostEntities = [];
	
	tiles.forEach((t) => {
		lostEntities.push(ground.tileUpdate(t));
	})
	
	lostEntities.forEach((es) => {
		es.forEach(model.cullEntity);
	});
	
	model.cullTiles();
}

updateHandler.updateLocalPlayer = function(userName) {
	let e = player.get(userName);
	
	model.setLocalPlayer(e? e: userName);
}