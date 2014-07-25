define(function (require) {
    "use strict";

    var $                   = require('jquery'),
        _                   = require('underscore'),
        Backbone            = require('backbone'),
        models              = require('app/models/shop'),
        tpl                 = require('text!template/edit.html');

    return Backbone.View.extend({
        el: $('.page'),
        initialize: function(){
        },
        render: function(options){
            var that = this;
            var shop = new models.Shop({id: options.id});
            shop.fetch({
                success: function(){
                    var temp = _.template(tpl, {p: shop});
                    that.$el.html(temp);
                }
            });
        },
        events:{
            'click .update' : 'updateShop',
            'click .delete' : 'deleteShop',
            'click .cancel'  : 'cancelEditShop'
        },
        updateShop: function(){
            var id1 = $('#id').val();
            var code1 = $('#code').val();
            var name1 = $('#name').val();
            var shop = new models.Shop({id: id1, code: code1, name: name1});
            shop.save(null,{
                success: function(){
                    var Router = require('app/router');
                    var router = new Router();
                    router.navigate('', {trigger:true});
                }
            });
        },
        deleteShop: function(){
            var id1 = $('#id').val();
            var shop = new models.Shop({id: id1});
            shop.destroy({
                success: function(){
                    var Router = require('app/router');
                    var router = new Router();
                    router.navigate('', {trigger:true});
                }
            });

        },
        cancelEditShop: function(){
            var Router = require('app/router');
            var router = new Router();
            router.navigate('',{trigger:true});
        }
    });
});