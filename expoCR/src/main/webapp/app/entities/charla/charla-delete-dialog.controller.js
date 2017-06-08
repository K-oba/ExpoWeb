(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('CharlaDeleteController',CharlaDeleteController);

    CharlaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Charla'];

    function CharlaDeleteController($uibModalInstance, entity, Charla) {
        var vm = this;

        vm.charla = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Charla.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
