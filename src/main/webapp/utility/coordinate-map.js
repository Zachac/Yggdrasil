/*
 * A map of x,z coordinates handling a fixed size. If a new x/z
 * coordinate is entered that exceeds in difference, the smallest
 * or largest x/z coordinate, then all coordinates farther away 
 * than size will be removed. This is to utilize fixed length arrays 
 * that will match our specific circumstances better than an
 * arbitrary size map.
 */
var CoordinateMap = function (size) {
    if (!size || size < 1) {
        throw "size must be > 1";
    }

    this._size = size;
    this._minx = 0;
    this._maxx = size - 1;
    this._minz = 0;
    this._maxz = size - 1;
    this._map = new Array(size);

    for (let i = 0; i < size; i++) {
        this._map[i] = new Array(size);
    }

    return this;
}

CoordinateMap.prototype = {
    put: function (x, z, value) {
        if (x < this._minx) {
            this._removex(x, this._minx - 1);
            this._minx = x;
            this._maxx = x + this._size - 1;
        } else if (x > this._maxx) {
            this._removex(this._maxx + 1, x);
            this._maxx = x;
            this._minx = x - this._size + 1;
        }

        if (z < this._minz) {
            this._removez(z, this._minz - 1);
            this._minz = z;
            this._maxz = z + this._size - 1;
        } else if (z > this._maxz) {
            this._removez(this._maxz + 1, z);
            this._maxz = z;
            this._minz = z - this._size + 1;
        }

        return this._map
        [x >= 0 ? x % this._size : x % this._size + this._size]
        [z >= 0 ? z % this._size : z % this._size + this._size] = value;
    },

    get: function (x, z) {
        if (x < this._minx || x > this._maxx ||
            z < this._minz || z > this._maxz) {
            throw "index out of bounds";
        }

        return this._map
        [x >= 0 ? x % this._size : x % this._size + this._size]
        [z >= 0 ? z % this._size : z % this._size + this._size];
    },

    remove: function (x, z) {
        if (x < this._minx || x > this._maxx ||
            z < this._minz || z > this._maxz) {
            throw "index out of bounds";
        }

        return delete this._map
        [x >= 0 ? x % this._size : x % this._size + this._size]
        [z >= 0 ? z % this._size : z % this._size + this._size];
    },

    clear: function () {
        for (let i = 0; i < this._size; i++) {
            for (let j = 0; j < this._size; j++) {
                delete this._map[i][j];
            }
        }

        return true;
    },

    _removex: function (minx, maxx) {
        for (let x = minx; x <= maxx; x++) {
            let ax = x >= 0 ? x % this._size : x % this._size + this._size;
            for (let z = 0; z < this._size; z++) {
                delete this._map
                [ax][z];
            }
        }
    },

    _removez: function (minz, maxz) {
        for (let z = minz; z <= maxz; z++) {
            let az = z >= 0 ? z % this._size : z % this._size + this._size;
            for (let x = 0; x < this._size; x++) {
                delete this._map
                [x][az];
            }
        }
    }
};