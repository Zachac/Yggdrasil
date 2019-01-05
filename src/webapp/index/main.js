
main = {
	onload: []
}

window.onload = function () {
	main.onload.forEach(f => f());
}
