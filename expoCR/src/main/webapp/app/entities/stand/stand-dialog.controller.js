(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('StandDialogController', StandDialogController);

    StandDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Stand', 'Usuario', 'Brouchure', 'Click', 'Beacon'];

    function StandDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Stand, Usuario, Brouchure, Click, Beacon) {
        var vm = this;

        vm.stand = entity;
        vm.clear = clear;
        vm.save = save;
        vm.usuarios = Usuario.query();
        vm.brouchures = Brouchure.query({filter: 'stand-is-null'});
        $q.all([vm.stand.$promise, vm.brouchures.$promise]).then(function() {
            if (!vm.stand.brouchureId) {
                return $q.reject();
            }
            return Brouchure.get({id : vm.stand.brouchureId}).$promise;
        }).then(function(brouchure) {
            vm.brouchures.push(brouchure);
        });
        vm.clicks = Click.query({filter: 'stand-is-null'});
        $q.all([vm.stand.$promise, vm.clicks.$promise]).then(function() {
            if (!vm.stand.clickId) {
                return $q.reject();
            }
            return Click.get({id : vm.stand.clickId}).$promise;
        }).then(function(click) {
            vm.clicks.push(click);
        });
        vm.beacons = Beacon.query({filter: 'stand-is-null'});
        $q.all([vm.stand.$promise, vm.beacons.$promise]).then(function() {
            if (!vm.stand.beaconId) {
                return $q.reject();
            }
            return Beacon.get({id : vm.stand.beaconId}).$promise;
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
            if (vm.stand.id !== null) {
                Stand.update(vm.stand, onSaveSuccess, onSaveError);
            } else {
                Stand.save(vm.stand, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:standUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
