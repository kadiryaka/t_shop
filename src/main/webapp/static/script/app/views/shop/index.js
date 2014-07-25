define(function (require) {
    "use strict";
    var $                   = require('jquery'),
        _                   = require('underscore'),
        Backbone            = require('backbone'),
        models              = require('app/models/shop'),
        tpl                 = require('text!template/index.html');

    return Backbone.View.extend({
        el: $('.container'),
        initialize: function(){
        },
        events:{
            'keyup #search' : 'search'
        },
        render: function(){
            $("#search").focus();
            var shops = new models.Shops();
            shops.fetch({
                success: function(){
                    var temp = _.template(tpl, {shops: shops.models});
                    $('.page').html(temp);
                }
            });
        },
        search: function() {
            var Router = require('app/router');
            var router = new Router();
            router.navigate($('#search').val(), {trigger:true});
            if($('#search').val()=="" || $('#search').val()==null) {
                this.render();
            }
            var pattern = new RegExp($('#search').val(),"i"); // global-insensitive match
            var shops = new models.Shops();
            shops.fetch({
                success: function(){
                    var temp = _.template(tpl, {
                        shops: shops.filter(function(data) {
                            return pattern.test(data.get("name"));
                        })
                    });
                    $('.page').html(temp);
                }
            });
        }
    });
});