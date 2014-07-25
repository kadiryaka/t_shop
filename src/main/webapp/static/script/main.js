requirejs.config({
    baseUrl: "static/script/libs",
    paths: {
        'app': '../app',
        'template': '../../templates'
    },
    shim: {
        'jquery' : {
            exports : '$'
        },
        'underscore' : {
            exports : '_'
        },
        'backbone': {
            deps: ['jquery','underscore'],
            exports: 'Backbone'
        }
    }
});

require(['jquery', 'backbone', 'app/router'], function ($, Backbone, Router) {
    var router = new Router();
    Backbone.history.start();
});