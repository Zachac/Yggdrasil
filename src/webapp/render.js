
render = {}

render.closeScene = function() {
	model.scene.rootNodes.forEach((n) => {
		n.setEnabled(false);
	});
}

render.createScene = function(canvas, engine)  {
	
	let scene = new BABYLON.Scene(engine);
	light = new BABYLON.HemisphericLight("sun", new BABYLON.Vector3(0, 800, 0), scene);
	let camera = new BABYLON.ArcFollowCamera("Camera", Math.PI / 2, Math.PI / 4, 5, null, scene);
	camera.attachControl(canvas, true);

	model.setScene(scene, camera);
	
	return scene;
};

render.loadRender = function() {
	var canvas = document.getElementById("renderCanvas");
	var engine = new BABYLON.Engine(canvas, true);
	var scene = render.createScene(canvas, engine);
	
	engine.runRenderLoop(function () { 
		scene.render();
	});
	
	window.addEventListener("resize", function () { 
		engine.resize();
	});
}