entity = {
	moveTime: 0.5 + 0.02,
	maxStep: 2
}
entities = {};


entity.remove = function (e) {
	delete entities[e.id];

	if (e.isPlayer) {
		player.remove(e);
	}

	e.mesh.dispose();
}

entity.newEntity = function (e) {
	let result = {};

	result.id = e.id;
	result.moveAnimId = `${e.id} anim`;
	result.mesh = BABYLON.MeshBuilder.CreateSphere("sphere", { diameter: 1 }, render.scene);
	result.mesh.position.y = 0.5;
	result.position = {};

	entities[result.id] = result;

	if (e.type = "Player") {
		player.init(result, e);
	}

	return result;
}

entity.update = function (en, tile) {
	e = entities[en.id];

	if (e == null) {
		e = entity.newEntity(en);
	}

	entity.move(e, tile.position.x, tile.position.y, tile.position.z);

	if (e.isPlayer) {
		player.update(en, tile);
	}

	return e;
}

entity.move = function (e, x, y, z) {

	let xdiff = e.mesh.position.x - x;
	let zdiff = e.mesh.position.z - z;

	if (xdiff < -entity.maxStep || xdiff > entity.maxStep ||
		zdiff < -entity.maxStep || zdiff > entity.maxStep) {
		entity.teleportMove(e, x, e.mesh.position.y, z);
	} else {
		entity.animateMove(e, x, e.mesh.position.y, z);
	}

	e.position.x = x;
	e.position.y = y;
	e.position.z = z;

	if (e.isPlayer) {
		player.update(e, x, y, z);
	}
}

entity.animateMove = function (e, x, y, z) {
	BABYLON.Animation.CreateAndStartAnimation(
		e.moveAnimId, e.mesh, 'position.x',
		render.fps, render.fps * entity.moveTime,
		e.mesh.position.x, x,
		0 /* loop mode */, animation.entityEasing);

	BABYLON.Animation.CreateAndStartAnimation(
		e.moveAnimId, e.mesh, 'position.y',
		render.fps, render.fps * entity.moveTime,
		e.mesh.position.y, y,
		0, animation.entityEasing);

	BABYLON.Animation.CreateAndStartAnimation(
		e.moveAnimId, e.mesh, 'position.z',
		render.fps, render.fps * entity.moveTime,
		e.mesh.position.z, z,
		0, animation.entityEasing);
}

entity.teleportMove = function (e, x, y, z) {
	e.mesh.position.x = x;
	e.mesh.position.y = y;
	e.mesh.position.z = z;
}