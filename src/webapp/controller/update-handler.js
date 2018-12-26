updateHandler = {}


updateHandler.updateTiles = function(tiles) {
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

updateHandler.updateLocalPlayer = function(userName) {
	model.setLocalPlayer(userName);
}