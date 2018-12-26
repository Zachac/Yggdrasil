net = {}

net.handleUpdate = function(string) {
	updt = JSON.parse(string);
	
	net.translateCoordinates(updt);
	
	console.log(updt);
	
	if (updt.localPlayer) {
		model.setLocalPlayer(updt.localPlayer);
	}
	
	if (updt.tiles) {
		model.updateTiles(updt.tiles);
	}
}

net.translateCoordinates = function(d) {
	if (d.tiles) {
		d.tiles.forEach((t) => {
			let swap = t.position.y;
			t.position.y = t.position.x;
			t.position.x = swap;
			
			swap = t.position.z;
			t.position.z = t.position.y;
			t.position.y = swap;
			
			t.position.x = -t.position.x;
			t.position.z = -t.position.z;
		});
	}
}