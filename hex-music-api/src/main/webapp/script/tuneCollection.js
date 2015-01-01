var TuneCollection = function (title) {
    this.title = title;
    this.collection = [];
};
var TuneItem = function (id, title) {
    this.id = id;
    this.title = title;
};
TuneCollection.prototype = {
    getTitle: function () {
        return this.title;
    },
    setTitle: function (title) {
        this.title = title;
    },
    add: function (param1, param2) {
        var tune;
        if (param2 === undefined && param1.getTitle() !== undefined && param1.getTitle() !== null) {
            tune = param1;
        } else {
            tune = new TuneItem(param1, param2);
        }
        if (tune !== null && tune !== undefined && this.getById(tune.id) === null) {
            this.collection[this.collection.length] = tune;
        }
    },
    getAll: function () {
        return this.collection;
    },
    getIdsAsString: function () {
        var result = new StringBuilder();
        for (var i = 0; i < this.collection.length; i++) {
            result.append(this.collection[i].getId());
        }
        return result.toString(',');
    },
    get: function (index) {
        return this.collection[index] || null;
    },
    getById: function (id) {
        for (var i = 0; i < this.collection.length; i++) {
            if (this.collection[i].id === id) {
                return this.collection[i];
            }
        }
        return null;
    }
};
TuneItem.prototype = {
    getId: function () {
        return this.id;
    },
    getTitle: function () {
        return this.title;
    }
};