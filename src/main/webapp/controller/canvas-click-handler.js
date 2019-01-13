clickHandler = {}

clickHandler.init = function () {
    clickHandler.canvas = document.getElementById("renderCanvas");
    clickHandler.canvas.onclick = clickHandler.onClick;
}

clickHandler.onClick = function (evt) {
    let res = render.scene.pick(render.scene.pointerX, render.scene.pointerY);

    if (res.hit) {
        let mesh = res.pickedMesh;

        if (mesh.isGround) {
            let position = mesh.position;

            let diffx = model.position.x - position.x;
            let diffz = model.position.z - position.z;

            if (diffx || diffz) {
                let invertx = diffx < 0;
                let invertz = diffz < 0;

                if (invertx) {
                    diffx = -diffx;
                }

                if (invertz) {
                    diffz = -diffz;
                }

                if (diffx < diffz) {
                    websocket.send(`go ${diffx}${invertx ? 'w' : 'e'} ${diffz}${invertz ? 's' : 'n'}`);
                } else {
                    websocket.send(`go ${diffz}${invertz ? 's' : 'n'} ${diffx}${invertx ? 'w' : 'e'}`);
                }
            }
        }
    }
}

main.onload.push(clickHandler.init);