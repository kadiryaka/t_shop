define(function (require) {
    "use strict";
    var Backbone = require('backbone'),
    Shop = Backbone.Model.extend({
        urlRoot: '../../T_SHOP/api/shops',
        idAttribute: "id"
    }),
    Shops = Backbone.Collection.extend({
        url: '../../T_SHOP/api/shops'
    });
    return {
            Shop: Shop,
            Shops: Shops
    };
});