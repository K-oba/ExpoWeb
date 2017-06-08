(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('TimelineDialogController', TimelineDialogController);

    TimelineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Timeline', 'Exposicion', 'Post', 'Usuario'];

    function TimelineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Timeline, Exposicion, Post, Usuario) {
        var vm = this;

        vm.timeline = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exposicions = Exposicion.query();
        vm.posts = Post.query();
        vm.usuarios = Usuario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.timeline.id !== null) {
                Timeline.update(vm.timeline, onSaveSuccess, onSaveError);
            } else {
                Timeline.save(vm.timeline, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:timelineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
