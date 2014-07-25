define(function (require) {
    "use strict";
    var Backbone    = require('backbone'),
        IndexShopView   = require('app/views/shop/index'),
        AddShopView    = require('app/views/shop/add'),
        EditShopView    = require('app/views/shop/edit'),

    indexShopView = new IndexShopView(),
    addShopView = new AddShopView(),
    editShopView = new EditShopView();

    return Backbone.Router.extend({
        routes: {
            '' : 'index',
            'new' : 'add',
            'edit/:id' : 'edit',
            'delete/:id' : 'delete',
            ':query' : 'search'
        },
        index: function () {
            indexShopView.render();
        },
        add: function () {
            addShopView.render();
        },
        edit: function (id) {
            editShopView.render({id:id});
        },
        delete: function (id) {
            var models = require('app/models/shop');
            var shop = new models.Shop({id: id});
            shop.destroy({
                success: function(){
                    var Router = require('app/router');
                    var router = new Router();
                    router.navigate('', {trigger:true});
                }
            });
        },
        search: function (query) {
            $("#search").val(query);
            indexShopView.search();
        }
    });
});