(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ClickDeleteController',ClickDeleteController);

    ClickDeleteController.$inject = ['$uibModalInstance', 'entity', 'Click'];

    function ClickDeleteController($uibModalInstance, entity, Click) {
        var vm = this;

        vm.click = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Click.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
