player = {};
players = [];

player.init = function(e, pl) {
	e.isPlayer = true;
	players[pl.userName] = e;
	e.userName = pl.userName;
}

player.update = function(pl, t) {
	
}

player.move = function(e, x, y, z) {
	
}

player.remove = function(p) {
	delete players[p.userName];
	
	if (p.userName = model.localPlayer) {
		model.onLoseLocalPlayer();
	}
}