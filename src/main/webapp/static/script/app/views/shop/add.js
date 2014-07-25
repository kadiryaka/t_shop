define(function (require) {
    "use strict";
    var $                   = require('jquery'),
        _                   = require('underscore'),
        Backbone            = require('backbone'),
        models              = require('app/models/shop'),
        tpl                 = require('text!template/add.html');

    return Backbone.View.extend({
        el: $('.page'),
        initialize: function(){
        },
        render: function(){
            var temp = _.template(tpl, {});
            this.$el.html(temp);
        },
        events:{
            'click .add' : 'addShop',
            'click .cancel' : 'cancelAddShop'
        },
        addShop: function(){
            var code1 = $('#code').val();
            var name1 = $('#name').val();

            if(code1.length < 1 || name1.length < 1){
                alert('Please fill in form first!');
            }else{
                var shop = new models.Shop({code: code1, name: name1});
                shop.save(null,{
                    success: function(){
                        var Router = require('app/router');
                        var router = new Router();
                        router.navigate('', {trigger:true});
                    }
                });

            }
        },
        cancelAddShop: function(){
            var Router = require('app/router');
            var router = new Router();
            router.navigate('',{trigger:true});
        }
    });
});