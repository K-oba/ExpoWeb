(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BrouchureDeleteController',BrouchureDeleteController);

    BrouchureDeleteController.$inject = ['$uibModalInstance', 'entity', 'Brouchure'];

    function BrouchureDeleteController($uibModalInstance, entity, Brouchure) {
        var vm = this;

        vm.brouchure = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Brouchure.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
