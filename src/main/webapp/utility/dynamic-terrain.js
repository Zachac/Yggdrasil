
var DynamicTerraino = function (size, scene) {
    //Set arrays for positions and indices
    this._vertexData = new BABYLON.VertexData();
    let positions = this._vertexData.positions = [];
    let indices = this._vertexData.indices = [];
    let uvs = this._vertexData.uvs = [];
    let normals = this._normals = [];

    for (let i = 0; i <= size; i++) {
        for (let j = 0; j <= size; j++) {
            let position = i * (size + 1) + j;
            position = position * 3;

            positions[position + 0] = i;
            positions[position + 1] = Math.sin(i / 5) * Math.sin(j / 5);
            positions[position + 2] = j;
        }
    }

    for (let i = 0; i < size; i++) {
        for (let j = 0; j < size; j++) {
            let bottomLeft = i * (size + 1) + j;
            let topLeft = bottomLeft + 1;
            let bottomRight = bottomLeft + size + 1;
            let topRight = bottomRight + 1;

            let position = i * size + j
            position = position * 6;

            indices[position + 0] = topLeft;
            indices[position + 1] = bottomLeft;
            indices[position + 2] = bottomRight;
            indices[position + 3] = topLeft;
            indices[position + 4] = bottomRight;
            indices[position + 5] = topRight;
        }
    }

    BABYLON.VertexData.ComputeNormals(positions, indices, normals);

    this._mesh = new BABYLON.Mesh("tile", scene);
    this._vertexData.applyToMesh(this._mesh);
    this._mat = new BABYLON.StandardMaterial("mat", scene);
    this._mesh.material = this._mat;
}

DynamicTerraino.prototype = {

}