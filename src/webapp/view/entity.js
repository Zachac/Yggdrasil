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

entity.update = function (en) {
	e = entities[en.id];

	if (!en.position) {
		if (e != null) {
			entity.remove(e);
			return;
		}
	} else if (e == null) {
		e = entity.newEntity(en);
	}

	entity.move(e, en.position.x, en.position.y, en.position.z);

	if (e.isPlayer) {
		player.update(en);
	}

	return e;
}

entity.newEntity = function (e) {
	let result = {};

	result.id = e.id;
	result.moveAnimId = `${e.id} anim`;
	result.mesh = BABYLON.MeshBuilder.CreateSphere("sphere", { diameter: 1 }, render.scene);
	result.mesh.position.y = 0.5;
	result.mesh.position.x = e.position.x;
	result.mesh.position.z = e.position.z;
	result.position = {
		x: result.mesh.position.x,
		y: result.mesh.position.y,
		z: result.mesh.position.z,
	}

	result.moveAnims = [];

	entities[result.id] = result;

	if (e.userName) {
		player.init(result, e);
	}

	return result;
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
	entity.clearMoveAnimations(e);

	e.moveAnims = [];

	e.moveAnims.push(
		BABYLON.Animation.CreateAndStartAnimation(
			e.moveAnimId, e.mesh, 'position.x',
			render.fps, render.fps * entity.moveTime,
			e.mesh.position.x, x,
			0 /* loop mode */, animation.entityEasing)
	);

	e.moveAnims.push(
		BABYLON.Animation.CreateAndStartAnimation(
			e.moveAnimId, e.mesh, 'position.y',
			render.fps, render.fps * entity.moveTime,
			e.mesh.position.y, y,
			0, animation.entityEasing)
	);

	e.moveAnims.push(
		BABYLON.Animation.CreateAndStartAnimation(
			e.moveAnimId, e.mesh, 'position.z',
			render.fps, render.fps * entity.moveTime,
			e.mesh.position.z, z,
			0, animation.entityEasing)
	);
}

entity.clearMoveAnimations = function (e) {
	e.moveAnims.forEach(anim => {
		anim.stop();
	});
}

entity.teleportMove = function (e, x, y, z) {
	e.mesh.position.x = x;
	e.mesh.position.y = y;
	e.mesh.position.z = z;
}