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
            let result = clickHandler.getMoveString(model.position, mesh.position);
            if (result) {
                websocket.send(result);
            }
        }
    }
}

clickHandler.getMoveString = function (position1, position2) {
    let diffx = position1.x - position2.x;
    let diffz = position1.z - position2.z;
    if (diffx || diffz) {
        return `go ${clickHandler.getMoveCommands(diffx, diffz)}`;
    }
}

clickHandler.getMoveCommands = function (diffx, diffz) {
    let invertx = diffx < 0;
    let invertz = diffz < 0;

    if (invertx) {
        diffx = -diffx;
    }

    if (invertz) {
        diffz = -diffz;
    }

    let diagonal = Math.min(diffx, diffz);
    let diagonalRemaining = diffx - diffz;
    let invertDiagonalRemaining = diagonalRemaining < 0;

    if (invertDiagonalRemaining) {
        diagonalRemaining = -diagonalRemaining;
    }

    let directionx = invertx ? 'w' : 'e';
    let directionz = invertz ? 's' : 'n';
    let lastDirection = invertDiagonalRemaining ? directionz : directionx;

    return `${diagonal}${directionz}${directionx} ${diagonalRemaining}${lastDirection}`;
}

main.onload.push(clickHandler.init);