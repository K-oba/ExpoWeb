(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('SubCategoriaDialogController', SubCategoriaDialogController);

    SubCategoriaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SubCategoria', 'Categoria', 'Brouchure', 'Click', 'Beacon'];

    function SubCategoriaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, SubCategoria, Categoria, Brouchure, Click, Beacon) {
        var vm = this;

        vm.subCategoria = entity;
        vm.clear = clear;
        vm.save = save;
        vm.categorias = Categoria.query();
        vm.brouchures = Brouchure.query({filter: 'subcategoria-is-null'});
        $q.all([vm.subCategoria.$promise, vm.brouchures.$promise]).then(function() {
            if (!vm.subCategoria.brouchureId) {
                return $q.reject();
            }
            return Brouchure.get({id : vm.subCategoria.brouchureId}).$promise;
        }).then(function(brouchure) {
            vm.brouchures.push(brouchure);
        });
        vm.clicks = Click.query({filter: 'subcategoria-is-null'});
        $q.all([vm.subCategoria.$promise, vm.clicks.$promise]).then(function() {
            if (!vm.subCategoria.clickId) {
                return $q.reject();
            }
            return Click.get({id : vm.subCategoria.clickId}).$promise;
        }).then(function(click) {
            vm.clicks.push(click);
        });
        vm.beacons = Beacon.query({filter: 'subcategoria-is-null'});
        $q.all([vm.subCategoria.$promise, vm.beacons.$promise]).then(function() {
            if (!vm.subCategoria.beaconId) {
                return $q.reject();
            }
            return Beacon.get({id : vm.subCategoria.beaconId}).$promise;
        }).then(function(beacon) {
            vm.beacons.push(beacon);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subCategoria.id !== null) {
                SubCategoria.update(vm.subCategoria, onSaveSuccess, onSaveError);
            } else {
                SubCategoria.save(vm.subCategoria, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:subCategoriaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
