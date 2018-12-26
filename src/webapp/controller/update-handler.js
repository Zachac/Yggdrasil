updateHandler = {}


updateHandler.updateTiles = function(tiles) {
	let lostEntities = [];
	
	tiles.forEach((t) => {
		if (t.contents) {
			t.contents.forEach((e) => {
				
			});
		}
		
		let maybeRemoveSwap = ground.tileUpdate(t);
		
		if (maybeRemoveSwap.length > 0) {
			lostEntities = lostEntities.concat(maybeRemoveSwap);
		}
	})
	
	lostEntities.forEach((e) => {
		let g = ground.lookup(e.position.x, e.position.y, e.position.z);
		
		if (g) {
			let found = g.contents.some((en) => { return en.id == e.id; })
			
			if (!found) {
				entity.remove(e);
			}
		} else {
			entity.remove(e);
		}
	});
	
	model.cull();
}

updateHandler.updateLocalPlayer = function(userName) {
	model.setLocalPlayer(player.get(userName));
}