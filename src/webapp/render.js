
render = {
	scene: null,
	engine: null,
	canvas: null,
	lookSpeed: 0.02,
	minLookCap: 0.2,
	maxLookCap: 0.1,
};

render.closeScene = function() {
	render.scene.rootNodes.forEach((n) => {
		n.setEnabled(false);
	});
}

render.createScene = function(canvas, engine)  {
	
	let scene = new BABYLON.Scene(engine);
	light = new BABYLON.HemisphericLight("sun", new BABYLON.Vector3(0, 800, 0), scene);
	render.camera = new BABYLON.ArcFollowCamera("Camera", Math.PI / 2, Math.PI / 4, 5, null, scene);
	render.camera.attachControl(canvas, true);

	model.setScene(scene, render.camera);
	
	return scene;
};

render.loadRender = function() {
	render.canvas = document.getElementById("renderCanvas");
	render.engine = new BABYLON.Engine(render.canvas, true);
	render.scene = render.createScene(render.canvas, render.engine);
	
	render.engine.runRenderLoop(function () { 
		render.scene.render();
	});
	
	window.addEventListener("resize", function () { 
		render.engine.resize();
	});
	
	render.canvas.addEventListener("keydown", function(event) {
		if (event.code == "KeyA") {
			render.camera.alpha -= Math.PI * render.lookSpeed;
		} else if (event.code == "KeyD") {
			render.camera.alpha += Math.PI * render.lookSpeed;
		} else if (event.code == "KeyW") {
			render.camera.beta += Math.PI * render.lookSpeed;
			render.camera.beta = Math.min(Math.PI/2 - render.maxLookCap, render.camera.beta);
		} else if (event.code == "KeyS") {
			render.camera.beta -= Math.PI * render.lookSpeed;
			render.camera.beta = Math.max(render.minLookCap, render.camera.beta);
		}
	});
}