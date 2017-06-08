(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('TimelineDetailController', TimelineDetailController);

    TimelineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Timeline', 'Exposicion', 'Post', 'Usuario'];

    function TimelineDetailController($scope, $rootScope, $stateParams, previousState, entity, Timeline, Exposicion, Post, Usuario) {
        var vm = this;

        vm.timeline = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:timelineUpdate', function(event, result) {
            vm.timeline = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
