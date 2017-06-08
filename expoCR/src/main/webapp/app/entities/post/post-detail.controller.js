(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('PostDetailController', PostDetailController);

    PostDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Post', 'Timeline'];

    function PostDetailController($scope, $rootScope, $stateParams, previousState, entity, Post, Timeline) {
        var vm = this;

        vm.post = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:postUpdate', function(event, result) {
            vm.post = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
