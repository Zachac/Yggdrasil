
render = {
	scene: null,
	engine: null,
	canvas: null,
	lookSpeed: 0.02,
	minLookCap: 0.2,
	maxLookCap: 0.1,
	scrollZoomScalar: 1 / 500,
	maxCameraDistance: 10,
	minCameraDistance: 2,
	fps: 60
};

render.closeScene = function () {
	render.scene.rootNodes.forEach((n) => {
		n.setEnabled(false);
	});
}

render.createScene = function (canvas, engine) {

	let scene = new BABYLON.Scene(engine);

	render.light = new BABYLON.DirectionalLight("sun1", new BABYLON.Vector3(20, -40, 20), scene);
	render.light.specular = new BABYLON.Color3(0, 0, 0);
	render.light.intensity = 0.5;

	render.staticShadowLight = new BABYLON.DirectionalLight("sun2", new BABYLON.Vector3(20, -40, 20), scene);
	render.staticShadowLight.specular = new BABYLON.Color3(0, 0, 0);
	render.staticShadowLight.intensity = 0.5;

	render.shadows = new BABYLON.ShadowGenerator(1024, render.light);
	render.shadows.bias = 0.000001;
	render.shadows.contactHardeningLightSizeUVRatio = 0.4;
	render.shadows.useContactHardeningShadow = true;

	render.staticShadows = new BABYLON.ShadowGenerator(1024, render.staticShadowLight);
	render.staticShadows.bias = 0.00001;
	render.staticShadows.contactHardeningLightSizeUVRatio = 0.2;
	render.staticShadows.useContactHardeningShadow = true;
	render.staticShadows.getShadowMap().refreshRate = BABYLON.RenderTargetTexture.REFRESHRATE_RENDER_ONCE;

	render.camera = new BABYLON.ArcFollowCamera("Camera", Math.PI / 2, Math.PI / 4, 5, null, scene);
	render.camera.attachControl(canvas, true);

	return scene;
};

render.refreshStaticShadows = function () {
	render.staticShadows.getShadowMap().refreshRate = BABYLON.RenderTargetTexture.REFRESHRATE_RENDER_ONCE;
}

render.init = function () {
	render.canvas = document.getElementById("renderCanvas");
	render.engine = new BABYLON.Engine(render.canvas, true);
	render.engine.enableOfflineSupport = false;
	render.scene = render.createScene(render.canvas, render.engine);

	render.engine.runRenderLoop(function () {
		render.scene.render();
	});

	window.addEventListener("resize", function () {
		render.engine.resize();
	});

	render.canvas.addEventListener("keydown", function (event) {
		switch (event.code) {
			case "KeyA":
				render.camera.alpha -= Math.PI * render.lookSpeed;
				break;
			case "KeyD":
				render.camera.alpha += Math.PI * render.lookSpeed;
				break;
			case "KeyW":
				render.camera.beta += Math.PI * render.lookSpeed;
				render.camera.beta = Math.min(Math.PI / 2 - render.maxLookCap, render.camera.beta);
				break;
			case "KeyS":
				render.camera.beta -= Math.PI * render.lookSpeed;
				render.camera.beta = Math.max(render.minLookCap, render.camera.beta);
				break;
		}
	});

	render.canvas.addEventListener("wheel", function (event) {
		render.camera.radius += event.deltaY * render.scrollZoomScalar;

		if (render.camera.radius > render.maxCameraDistance) {
			render.camera.radius = render.maxCameraDistance;
		} else if (render.camera.radius < render.minCameraDistance) {
			render.camera.radius = render.minCameraDistance;
		}
	});
}

main.onload.push(render.init);