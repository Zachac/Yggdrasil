player = {};
players = [];

player.init = function(e, pl) {
	e.isPlayer = true;
	players[pl.userName] = e;
	e.userName = pl.userName;
	

	if (pl.userName == model.localPlayer) {
		model.setLocalPlayer(e);
	}
}

player.update = function(pl, t) {
	
}

player.move = function(e, x, y, z) {
	
}

player.remove = function(p) {
	delete players[p.userName];
	
	if (p.userName == model.localPlayer) {
		model.setLocalPlayer(null);
	}
}

player.get = function(userName) {
	return players[userName];
}