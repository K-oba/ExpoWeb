(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ExposicionDialogController', ExposicionDialogController);

    ExposicionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Exposicion', 'Distrito', 'Categoria', 'Charla', 'Amenidades', 'Beacon', 'Timeline', 'Click', 'Pregunta'];

    function ExposicionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Exposicion, Distrito, Categoria, Charla, Amenidades, Beacon, Timeline, Click, Pregunta) {
        var vm = this;

        vm.exposicion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.distritos = Distrito.query();
        vm.categorias = Categoria.query();
        vm.charlas = Charla.query();
        vm.amenidades = Amenidades.query();
        vm.beacons = Beacon.query();
        vm.timelines = Timeline.query();
        vm.clicks = Click.query({filter: 'exposicion-is-null'});
        $q.all([vm.exposicion.$promise, vm.clicks.$promise]).then(function() {
            if (!vm.exposicion.clickId) {
                return $q.reject();
            }
            return Click.get({id : vm.exposicion.clickId}).$promise;
        }).then(function(click) {
            vm.clicks.push(click);
        });
        vm.preguntas = Pregunta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.exposicion.id !== null) {
                Exposicion.update(vm.exposicion, onSaveSuccess, onSaveError);
            } else {
                Exposicion.save(vm.exposicion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:exposicionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
