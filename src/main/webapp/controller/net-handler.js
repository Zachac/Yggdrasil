net = {}

net.handleUpdate = function (string) {
	updt = JSON.parse(string)
	net.translateCoordinates(updt);
	console.log(updt);
	updateHandler.handleUpdate(updt);
}

net.translateCoordinates = function (d) {
	if (d.entities) {
		d.entities.forEach((e) => {
			if (e.position) {
				let swap = e.position.y;
				e.position.y = e.position.x;
				e.position.x = swap;

				swap = e.position.z;
				e.position.z = e.position.y;
				e.position.y = swap;

				e.position.x = -e.position.x;
				e.position.z = -e.position.z;
			}
		});
	}
}