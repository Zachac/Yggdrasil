entity = {}
entities = {};


entity.remove = function(e) {
	delete entities[e.id];
	
	if (e.isPlayer) {
		player.remove(e);
	}
	
	e.mesh.dispose();
}

entity.newEntity = function(e) {
	let result = {};

	result.id = e.id;
	result.mesh = BABYLON.MeshBuilder.CreateSphere("sphere", {diameter:1}, model.scene);
	result.mesh.position.y = 0.5;
	result.position = {};
	
	entities[result.id] = result;
	
	if (e.type = "Player") {
		player.init(result, e);
	}
	
	return result;
}

entity.update = function(en, tile) {
	e = entities[en.id];

	if (e == null) {
		e = entity.newEntity(en);
	}
	
	entity.move(e, tile.position.x, tile.position.y, tile.position.z);
	
	if (e.isPlayer) {
		player.update(en, tile);
	}
}

entity.move = function(e, x, y, z) {

	e.position.x = x;
	e.position.y = y;
	e.position.z = z;
	
	e.mesh.position.x = x;
	e.mesh.position.z = z;
	
	if (e.isPlayer) {
		player.update(e, x, y, z);
	}
}
